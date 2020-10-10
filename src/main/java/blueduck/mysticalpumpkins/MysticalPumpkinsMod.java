package blueduck.mysticalpumpkins;

import blueduck.mysticalpumpkins.network.MysticalPumpkinsMessageHandler;
import blueduck.mysticalpumpkins.network.message.BooleanMessage;
import blueduck.mysticalpumpkins.registry.InfusionTableRecipeRegistry;
import blueduck.mysticalpumpkins.registry.RegisterHandler;
import blueduck.mysticalpumpkins.utils.SpecialConstants;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

@Mod("mystical_pumpkins")
public class MysticalPumpkinsMod {

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
	public void afterCommonSetup() {
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

	@Mod.EventBusSubscriber(modid = SpecialConstants.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
	public static class ClientEventBusSubscriber {

		@SubscribeEvent
		public static void onKeyPress(InputEvent.KeyInputEvent event) {
			try {
				if (!Minecraft.getInstance().isGamePaused() && event.getKey() == GLFW.GLFW_KEY_SPACE) {
					MysticalPumpkinsMessageHandler.HANDLER.sendToServer(new BooleanMessage(Minecraft.getInstance().player.getUniqueID().toString(), event.getAction() != GLFW.GLFW_RELEASE));
				}
			}
			catch(Exception ignored) {}
		}
	}

	/**
	 * For Blueduck:
	 * This thing
	 * is
	 * useless
	 * we already have a method that checks for the ClientSetupEvent
	 * and it's "doClientStuff"
	 * also, on RegisterHandler class, there is a "initClient" method
	 * for registration of things like Renderers.
	 */

	/*@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ForgeEventBusSubscriber {
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(RegisterHandler.DRAGOURD.get(), DragourdRenderer::new);

		}
	}*/

}
