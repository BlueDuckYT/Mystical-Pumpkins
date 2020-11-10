package blueduck.mysticalpumpkins;

import blueduck.mysticalpumpkins.client.renderer.*;
import blueduck.mysticalpumpkins.item.MysticalPumpkinSpawnEgg;
import blueduck.mysticalpumpkins.network.MysticalPumpkinsMessageHandler;
import blueduck.mysticalpumpkins.network.message.BooleanMessage;
import blueduck.mysticalpumpkins.registry.InfusionTableRecipeRegistry;
import blueduck.mysticalpumpkins.registry.RegisterHandler;
import blueduck.mysticalpumpkins.utils.ConfigHelper;
import blueduck.mysticalpumpkins.utils.MysticalPumpkinsConfig;
import blueduck.mysticalpumpkins.utils.SpecialConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

@Mod("mystical_pumpkins")
public class MysticalPumpkinsMod {

	public static MysticalPumpkinsConfig CONFIG;

	public MysticalPumpkinsMod() {

		ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
		CONFIG = ConfigHelper.register(ModLoadingContext.get(), FMLJavaModLoadingContext.get(), ModConfig.Type.COMMON, MysticalPumpkinsConfig::new);

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		// Register the enqueueIMC method for modloading
		//FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		// Register the processIMC method for modloading
		//FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		RegisterHandler.init();



		MinecraftForge.EVENT_BUS.register(this);

	}

	private void setup(FMLCommonSetupEvent event) {
		InfusionTableRecipeRegistry.initRegistry();
		MysticalPumpkinsMessageHandler.register();
		event.enqueueWork(this::afterCommonSetup);
	}

	private void afterCommonSetup() {
		RegisterHandler.attributeStuff();
	}

	/*private void enqueueIMC(final InterModEnqueueEvent event) {
		// some example code to dispatch IMC to another mod
		InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
	}*/

	/*private void processIMC(final InterModProcessEvent event) {
		// some example code to receive and process InterModComms from other mods
		LOGGER.info("Got IMC {}", event.getIMCStream().
				map(m->m.getMessageSupplier().get()).
				collect(Collectors.toList()));
	}*/

	@Mod.EventBusSubscriber(modid = SpecialConstants.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
	public static class ClientEventBusSubscriber {

		@SubscribeEvent
		public static void onKeyPress(InputEvent.KeyInputEvent event) {
			if (event.getKey() == GLFW.GLFW_KEY_SPACE && !Minecraft.getInstance().isGamePaused()) {
				try {
					MysticalPumpkinsMessageHandler.HANDLER.sendToServer(new BooleanMessage(event.getAction() != GLFW.GLFW_RELEASE));
				}
				catch(Exception e) {

				}
			}
		}
	}

	@Mod.EventBusSubscriber(modid = SpecialConstants.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class OtherEvents {

		@SubscribeEvent
		public static void onBiomeLoad(BiomeLoadingEvent event) {
//			if (event.getName().equals(Biomes.DARK_FOREST.getLocation())) {
//				//event.getSpawns().getEntityTypes().add(RegisterHandler.DRAGOURD.get());
//				event.getSpawns().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(RegisterHandler.DRAGOURD.get(), 20, 1, 4));
//			}
			event.getSpawns().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(RegisterHandler.DRAGOURD.get(), 20, 1, 4));
		}
	}

	@Mod.EventBusSubscriber(modid = SpecialConstants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ModEventBusSubscriber {

		@SubscribeEvent(priority = EventPriority.LOWEST)
		public static void onPostRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
			MysticalPumpkinSpawnEgg.SetupStuff();
		}

		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			RegisterHandler.initClient();
			RenderingRegistry.registerEntityRenderingHandler(RegisterHandler.DRAGOURD.get(), DragourdRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(RegisterHandler.ENEMY_PUMPKINION.get(), EnemyPumpkinionRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(RegisterHandler.FRIENDLY_PUMPKINION.get(), FriendlyPumpkinionRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(RegisterHandler.PUMPKIN_SLUDGE.get(), SludgeRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(RegisterHandler.PUMPKLOPS.get(), PumpklopsRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(RegisterHandler.GREEN_MAGIC_BALL.get(), manager -> new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer(), 4, false));
		}

		@SubscribeEvent
		public static void onItemColorEvent(ColorHandlerEvent.Item event) {
			for (final SpawnEggItem egg : MysticalPumpkinSpawnEgg.SPAWN_EGGS) {
				event.getItemColors().register((stack, i) -> egg.getColor(i), egg);
			}
		}
	}
}
