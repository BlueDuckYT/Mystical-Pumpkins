package blueduck.mysticalpumpkins.network;

import blueduck.mysticalpumpkins.network.message.BooleanMessage;
import blueduck.mysticalpumpkins.network.message.IMessage;
import blueduck.mysticalpumpkins.network.message.InfusingMovingMessage;
import blueduck.mysticalpumpkins.utils.SpecialConstants;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class MysticalPumpkinsMessageHandler {

	static int disc = 0;

	private static final String PROTOCOL_VERSION = Integer.toString(1);
	public static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(SpecialConstants.MODID, "main_channel"))
			.clientAcceptedVersions(PROTOCOL_VERSION::equals)
			.serverAcceptedVersions(PROTOCOL_VERSION::equals)
			.networkProtocolVersion(() -> PROTOCOL_VERSION)
			.simpleChannel();
	
	public static void register() {
		registerMessage(BooleanMessage.class, new BooleanMessage());
		registerMessage(InfusingMovingMessage.class, new InfusingMovingMessage());
	}
	
	public static <T> void registerMessage(Class<T> clazz, IMessage<T> message) {
		HANDLER.registerMessage(disc++, clazz, message::encode, message::decode, message::handle);
	}
	
}
