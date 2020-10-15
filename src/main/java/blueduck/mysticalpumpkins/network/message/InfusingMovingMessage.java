package blueduck.mysticalpumpkins.network.message;

import blueduck.mysticalpumpkins.jei.infuser.InfusingRecipeTransferHandlerServer;
import blueduck.mysticalpumpkins.registry.RegisterHandler;
import blueduck.mysticalpumpkins.tileentity.InfusionTableRecipe;
import blueduck.mysticalpumpkins.utils.SpecialConstants;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class InfusingMovingMessage implements IMessage<InfusingMovingMessage> {

	public Map<Integer, Integer> recipeMap;
	public InfusionTableRecipe recipeToBeMoved;
	private boolean maxTransfer;

	public InfusingMovingMessage() {}

	public InfusingMovingMessage(Map<Integer, Integer> recipeMap, InfusionTableRecipe recipeToBeMoved, boolean maxTransfer) {
		this.recipeMap = recipeMap;
		this.maxTransfer = maxTransfer;
		this.recipeToBeMoved = recipeToBeMoved;
	}

	@Override
	public void encode(InfusingMovingMessage message, PacketBuffer buf) {
		buf.writeVarInt(message.recipeMap.size());
		for (Map.Entry<Integer, Integer> recipeMapEntry : message.recipeMap.entrySet()) {
			buf.writeVarInt(recipeMapEntry.getKey());
			buf.writeVarInt(recipeMapEntry.getValue());
		}
		buf.writeItemStack(message.recipeToBeMoved.getInput());
		buf.writeItemStack(new ItemStack(RegisterHandler.PUMPKIN_ESSENCE.get(), message.recipeToBeMoved.getEssenceAmount()));
		buf.writeItemStack(message.recipeToBeMoved.getSecondary());
		buf.writeItemStack(message.recipeToBeMoved.getOutput());
		buf.writeBoolean(message.maxTransfer);
	}

	@Override
	public InfusingMovingMessage decode(PacketBuffer buf) {
		int recipeMapSize = buf.readVarInt();
		Map<Integer, Integer> recipeMap = new HashMap<>(recipeMapSize);
		for (int i = 0; i < recipeMapSize; i++) {
			int slotIndex = buf.readVarInt();
			int recipeItem = buf.readVarInt();
			recipeMap.put(slotIndex, recipeItem);
		}
		InfusionTableRecipe recipe = new InfusionTableRecipe(buf.readItemStack(), buf.readItemStack().getCount(), buf.readItemStack(), buf.readItemStack());
		boolean maxTransfer = buf.readBoolean();

		return new InfusingMovingMessage(recipeMap, recipe, maxTransfer);
	}

	@Override
	public void handle(InfusingMovingMessage message, Supplier<NetworkEvent.Context> supplier) {
		supplier.get().enqueueWork(() -> {
			ServerPlayerEntity player = supplier.get().getSender();
			SpecialConstants.LOGGER.info(message.toString());
			InfusingRecipeTransferHandlerServer.setItems(player, message.recipeToBeMoved, message.recipeMap, message.maxTransfer);
		});
		supplier.get().setPacketHandled(true);
	}
}
