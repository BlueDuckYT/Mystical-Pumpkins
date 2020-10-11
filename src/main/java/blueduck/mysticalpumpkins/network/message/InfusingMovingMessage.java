package blueduck.mysticalpumpkins.network.message;

import blueduck.mysticalpumpkins.registry.RegisterHandler;
import blueduck.mysticalpumpkins.tileentity.InfusionTableRecipe;
import mezz.jei.transfer.BasicRecipeTransferHandlerServer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class InfusingMovingMessage implements IMessage<InfusingMovingMessage> {

	public Map<Integer, Integer> recipeMap;
	public List<Integer> craftingSlots;
	public List<Integer> inventorySlots;
	public InfusionTableRecipe recipeToBeMoved;
	private boolean maxTransfer;
	private boolean requireCompleteSets;

	public InfusingMovingMessage() {}

	public InfusingMovingMessage(Map<Integer, Integer> recipeMap, InfusionTableRecipe recipeToBeMoved, List<Integer> craftingSlots, List<Integer> inventorySlots, boolean maxTransfer, boolean requireCompleteSets) {
		this.recipeMap = recipeMap;
		this.craftingSlots = craftingSlots;
		this.inventorySlots = inventorySlots;
		this.maxTransfer = maxTransfer;
		this.requireCompleteSets = requireCompleteSets;
		this.recipeToBeMoved = recipeToBeMoved;
	}

	@Override
	public void encode(InfusingMovingMessage message, PacketBuffer buf) {
		buf.writeVarInt(recipeMap.size());
		for (Map.Entry<Integer, Integer> recipeMapEntry : recipeMap.entrySet()) {
			buf.writeVarInt(recipeMapEntry.getKey());
			buf.writeVarInt(recipeMapEntry.getValue());
		}
		buf.writeItemStack(recipeToBeMoved.getInput());
		buf.writeItemStack(new ItemStack(RegisterHandler.PUMPKIN_ESSENCE.get(), recipeToBeMoved.getEssenceAmount()));
		buf.writeItemStack(recipeToBeMoved.getSecondary());
		buf.writeItemStack(recipeToBeMoved.getOutput());
		buf.writeVarInt(craftingSlots.size());
		for (Integer craftingSlot : craftingSlots) {
			buf.writeVarInt(craftingSlot);
		}

		buf.writeVarInt(inventorySlots.size());
		for (Integer inventorySlot : inventorySlots) {
			buf.writeVarInt(inventorySlot);
		}

		buf.writeBoolean(maxTransfer);
		buf.writeBoolean(requireCompleteSets);
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
		int craftingSlotsSize = buf.readVarInt();
		List<Integer> craftingSlots = new ArrayList<>(craftingSlotsSize);
		for (int i = 0; i < craftingSlotsSize; i++) {
			int slotIndex = buf.readVarInt();
			craftingSlots.add(slotIndex);
		}

		int inventorySlotsSize = buf.readVarInt();
		List<Integer> inventorySlots = new ArrayList<>(inventorySlotsSize);
		for (int i = 0; i < inventorySlotsSize; i++) {
			int slotIndex = buf.readVarInt();
			inventorySlots.add(slotIndex);
		}

		boolean maxTransfer = buf.readBoolean();
		boolean requireCompleteSets = buf.readBoolean();

		return new InfusingMovingMessage(recipeMap, recipe, craftingSlots, inventorySlots, maxTransfer, requireCompleteSets);
	}

	@Override
	public void handle(InfusingMovingMessage message, Supplier<NetworkEvent.Context> supplier) {
		supplier.get().enqueueWork(() -> {
			ServerPlayerEntity player = supplier.get().getSender();
			InfusingRecipeTransferHandlerServer.setItems(player, message.recipeToBeMoved, message.recipeMap, message.craftingSlots, message.inventorySlots, message.maxTransfer, message.requireCompleteSets);
		});
		supplier.get().setPacketHandled(true);
	}
}
