package blueduck.mysticalpumpkins.misc;

import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class BooleanPacket implements IPacket {

    public boolean isPressed;
    public String UUID;

    public BooleanPacket(String info, boolean value) {
        this.UUID = info;
        isPressed = value;
    }

    public BooleanPacket(PacketBuffer buffer) {
        try {
            readPacketData(buffer);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        isPressed = buf.readBoolean();
        UUID = buf.readString();
    }

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeBoolean(isPressed);
        buf.writeString(UUID);
    }

    @Override
    public void processPacket(INetHandler handler) {

    }
}
