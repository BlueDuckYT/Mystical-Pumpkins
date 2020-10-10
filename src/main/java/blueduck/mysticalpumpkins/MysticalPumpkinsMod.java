package blueduck.mysticalpumpkins;

import blueduck.mysticalpumpkins.client.renderer.DragourdRenderer;
import blueduck.mysticalpumpkins.item.MysticalPumpkinSpawnEgg;
import blueduck.mysticalpumpkins.network.MysticalPumpkinsMessageHandler;
import blueduck.mysticalpumpkins.network.message.BooleanMessage;
import blueduck.mysticalpumpkins.registry.InfusionTableRecipeRegistry;
import blueduck.mysticalpumpkins.registry.RegisterHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

@Mod("mystical_pumpkins")
public class MysticalPumpkinsMod {

	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "mystical_pumpkins";
	public static ArrayList<String> players = new ArrayList<>();

	public MysticalPumpkinsMod() {
		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		// Register the enqueueIMC method for modloading
		//FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		// Register the processIMC method for modloading
		//FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		// Register the doClientStuff method for modloading
		RegisterHandler.init();

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.addListener(this::onPlayerTick);

	}

	private void setup(final FMLCommonSetupEvent event) {
		InfusionTableRecipeRegistry.initRegistry();
		MysticalPumpkinsMessageHandler.register();

		event.enqueueWork(this::afterCommonSetup);

	}
	public void afterCommonSetup()
	{
		RegisterHandler.attributeStuff();
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		RegisterHandler.initClient();
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

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		event.player.getPersistentData().putBoolean("isPressingSpace", players.contains(event.player.getUniqueID().toString()));
	}

	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
	public static class ClientEventBusSubscriber {



		@SubscribeEvent
		public static void onKeyPress(InputEvent.KeyInputEvent event) {
			try {
				if (Minecraft.getInstance().isGamePaused()){
					return;
				}
				if (event.getKey() == GLFW.GLFW_KEY_SPACE) {
					MysticalPumpkinsMessageHandler.HANDLER.sendToServer(new BooleanMessage(Minecraft.getInstance().player.getUniqueID().toString(), event.getAction() != GLFW.GLFW_RELEASE));
				}
			}
			catch(Exception e) {

			}
		}
	}

	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ForgeEventBusSubscriber {
		@SubscribeEvent(priority = EventPriority.LOWEST)
		public static void onPostRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
			MysticalPumpkinSpawnEgg.SetupStuff();
		}
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(RegisterHandler.DRAGOURD.get(), (manager) -> {
				return new DragourdRenderer(manager);
			});

		}
		@SubscribeEvent
		public static void onItemColorEvent(ColorHandlerEvent.Item event) {
			for (final SpawnEggItem egg : MysticalPumpkinSpawnEgg.SPAWN_EGGS) {
				event.getItemColors().register((stack, i) -> egg.getColor(i), egg);
			}
		}
	}

}
