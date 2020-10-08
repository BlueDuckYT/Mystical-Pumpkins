package blueduck.mysticalpumpkins;

import blueduck.mysticalpumpkins.misc.BooleanPacket;
import blueduck.mysticalpumpkins.registry.InfuserRecipeRegistry;
import blueduck.mysticalpumpkins.registry.RegisterHandler;
import net.java.games.input.Component;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mod("mystical_pumpkins")
public class MysticalPumpkinsMod {

	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "mystical_pumpkins";
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(MODID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);
	public static ArrayList<String> players = new ArrayList<String>();

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

		INSTANCE.registerMessage(
				0,
				BooleanPacket.class,
				(booleanPacket, buffer) -> {
					try {
						booleanPacket.writePacketData(buffer);
					} catch (IOException e) {
						e.printStackTrace();
					}
				},
				buffer -> new BooleanPacket(buffer),
				new BiConsumer<BooleanPacket, Supplier<NetworkEvent.Context>>() {
					@Override
					public void accept(BooleanPacket booleanPacket, Supplier<NetworkEvent.Context> contextSupplier) {
						if (booleanPacket.isPressed && !players.contains(booleanPacket.UUID)) {
							players.add(booleanPacket.UUID);
						}
						else {
							players.remove(booleanPacket.UUID);
						}
					}
				}
		);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.addListener(this::onPlayerTick);

	}

	private void setup(final FMLCommonSetupEvent event)
	{
		// some preinit code
		InfuserRecipeRegistry.initRegistry();
		LOGGER.info("HELLO FROM PREINIT");
		LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		// do something that can only be done on the client
		RegisterHandler.initClient();
		LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
	}

	private void enqueueIMC(final InterModEnqueueEvent event)
	{
		// some example code to dispatch IMC to another mod
		InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
	}

	private void processIMC(final InterModProcessEvent event)
	{
		// some example code to receive and process InterModComms from other mods
		LOGGER.info("Got IMC {}", event.getIMCStream().
				map(m->m.getMessageSupplier().get()).
				collect(Collectors.toList()));
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (players.contains(event.player.getUniqueID().toString())) {
			event.player.getPersistentData().putBoolean("isPressingSpace", true);
		}
		else {
			event.player.getPersistentData().putBoolean("isPressingSpace", false);
		}
	}

	@Mod.EventBusSubscriber(modid = "mystical_pumpkins", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
	public static class ClientEventBusSubscriber {
		@SubscribeEvent
		public static void onKeyPress(InputEvent.KeyInputEvent event) {
			if (Minecraft.getInstance().isGamePaused()) {
				return;
			}
			if (event.getKey() == 32) {
				if (event.getAction() != GLFW.GLFW_RELEASE) {
					MysticalPumpkinsMod.INSTANCE.sendToServer(new BooleanPacket(Minecraft.getInstance().player.getUniqueID().toString(), true));

				}
				else {
					MysticalPumpkinsMod.INSTANCE.sendToServer(new BooleanPacket(Minecraft.getInstance().player.getUniqueID().toString(), false));

				}
			}
		}
	}

}
