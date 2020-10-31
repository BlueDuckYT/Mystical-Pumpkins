package blueduck.mysticalpumpkins.network.message;

import blueduck.mysticalpumpkins.MysticalPumpkinsMod;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class BooleanMessage implements IMessage<BooleanMessage>{

	public boolean isPressed;
	public String UUID;
    
    public BooleanMessage() {}

	public BooleanMessage(String UUID, boolean pressed) {
        this.UUID = UUID;
        this.isPressed = pressed;
    }

	@Override
	public void encode(BooleanMessage message, PacketBuffer buffer) {
		buffer.writeString(message.UUID);
	    buffer.writeBoolean(message.isPressed);
	}

	@Override
	public BooleanMessage decode(PacketBuffer buffer) {
		return new BooleanMessage(buffer.readString(32767), buffer.readBoolean());
	}

	@Override
	public void handle(BooleanMessage message, Supplier<Context> supplier) {
		supplier.get().enqueueWork(() -> {
			if (message.isPressed && !MysticalPumpkinsMod.players.contains(message.UUID)) {
				MysticalPumpkinsMod.players.add(message.UUID);
			} else {
				MysticalPumpkinsMod.players.remove(message.UUID);
			}
		});
		supplier.get().setPacketHandled(true);
	}
}
