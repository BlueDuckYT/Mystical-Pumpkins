package blueduck.mysticalpumpkins.network.message;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class BooleanMessage implements IMessage<BooleanMessage>{

	public boolean isPressed;

    public BooleanMessage() {}

	public BooleanMessage(boolean pressed) {
        this.isPressed = pressed;
    }

	@Override
	public void encode(BooleanMessage message, PacketBuffer buffer) {
	    buffer.writeBoolean(message.isPressed);
	}

	@Override
	public BooleanMessage decode(PacketBuffer buffer) {
		return new BooleanMessage(buffer.readBoolean());
	}

	@Override
	public void handle(BooleanMessage message, Supplier<Context> supplier) {
		ServerPlayerEntity player = supplier.get().getSender();
		if (player != null) {
			supplier.get().enqueueWork(() -> {
				player.getPersistentData().putBoolean("isPressingSpace", message.isPressed);
			});
		}
		supplier.get().setPacketHandled(true);
	}
}
