package blueduck.mysticalpumpkins.jei.infuser;

import blueduck.mysticalpumpkins.container.InfusionTableContainer;
import blueduck.mysticalpumpkins.registry.RegisterHandler;
import blueduck.mysticalpumpkins.tileentity.InfusionTableRecipe;
import blueduck.mysticalpumpkins.utils.SpecialConstants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Please end me.
public class InfusingRecipeTransferHandlerServer {

	private static final Logger LOGGER = LogManager.getLogger("Infusion Table Transfer Handler SERVER");

	public static void setItems(PlayerEntity player, InfusionTableRecipe recipe, Map<Integer, Integer> slotsWithStacks, List<Integer> craftingSlots, List<Integer> inventorySlots, boolean maxTransfer, boolean requireCompleteSets) {
		Container container = player.openContainer;
		InfusionTableContainer infuser = (InfusionTableContainer) container;

		Map<Slot, ItemStack> mapSlotToItemStack = new HashMap<>();

		for (int slotId : slotsWithStacks.values()) {
			Slot inventorySlot = infuser.getSlot(slotId);
			if (inventorySlot.getHasStack()) {
				if (recipe.getInput().isItemEqual(inventorySlot.getStack())) {
					mapSlotToItemStack.put(inventorySlot, inventorySlot.getStack());
				} else if (RegisterHandler.PUMPKIN_ESSENCE.get() == inventorySlot.getStack().getItem()) {
					mapSlotToItemStack.put(inventorySlot, inventorySlot.getStack());
				} else if (recipe.getSecondary().isItemEqual(inventorySlot.getStack())) {
					mapSlotToItemStack.put(inventorySlot, inventorySlot.getStack());
				}
			}
		}

		LOGGER.info(mapSlotToItemStack);

		if (maxTransfer) {
			for (ItemStack stackInInventorySlot : mapSlotToItemStack.values()) {
				ItemStack stackToPutInInfuser = stackInInventorySlot.copy();
				if (recipe.getInput().isItemEqual(stackInInventorySlot)) {
					setMaxStackToInfuser(infuser, stackToPutInInfuser, stackInInventorySlot, 0);
				} else if (RegisterHandler.PUMPKIN_ESSENCE.get() == stackInInventorySlot.getItem()) {
					setMaxStackToInfuser(infuser, stackToPutInInfuser, stackInInventorySlot, 1);
				} else if (recipe.getSecondary().isItemEqual(stackInInventorySlot)) {
					setMaxStackToInfuser(infuser, stackToPutInInfuser, stackInInventorySlot, 2);
				}
			}
		} else {
			for (ItemStack stackInInventorySlot : mapSlotToItemStack.values()) {
				ItemStack stackToPutInInfuser = stackInInventorySlot.copy();
				if (recipe.getInput().isItemEqual(stackInInventorySlot)) {
					setItemToInfuser(recipe, infuser, stackInInventorySlot, stackToPutInInfuser, 0);
				} else if (RegisterHandler.PUMPKIN_ESSENCE.get() == stackInInventorySlot.getItem()) {
					setItemToInfuser(recipe, infuser, stackInInventorySlot, stackToPutInInfuser, 1);
				} else if (recipe.getSecondary().isItemEqual(stackInInventorySlot)) {
					setItemToInfuser(recipe, infuser, stackInInventorySlot, stackToPutInInfuser, 2);
				}
			}
		}

		SpecialConstants.LOGGER.info(infuser.getInventory());

		infuser.detectAndSendChanges();
	}

	private static void setItemToInfuser(InfusionTableRecipe recipe, InfusionTableContainer infuser, ItemStack stackInInventorySlot, ItemStack stackToPutInInfuser, int index) {
		ItemStack stackThatWasAlreadyThere = infuser.getSlot(index).getStack();
		if (stackThatWasAlreadyThere.isEmpty()) {
			stackToPutInInfuser.setCount(recipe.getIngredients().get(index).getCount());
		} else {
			stackToPutInInfuser.grow(recipe.getIngredients().get(index).getCount());
		}
		stackInInventorySlot.shrink(recipe.getIngredients().get(index).getCount());
		infuser.getSlot(index).putStack(stackToPutInInfuser);
	}

	private static void setMaxStackToInfuser(InfusionTableContainer infuser, ItemStack stackToPutInInfuser, ItemStack stackInInventorySlot, int index) {
		int infuserSlotCount = infuser.getSlot(index).getStack().getCount();
		if (infuserSlotCount < 64) {
			if (infuserSlotCount + stackInInventorySlot.getCount() > stackToPutInInfuser.getMaxStackSize()) {
				stackInInventorySlot.setCount(infuserSlotCount);
				stackToPutInInfuser.setCount(stackToPutInInfuser.getMaxStackSize());
			} else {
				stackToPutInInfuser.setCount(infuserSlotCount + stackInInventorySlot.getCount());
				stackInInventorySlot.setCount(0);
			}
			infuser.getSlot(index).putStack(stackToPutInInfuser);
		}
	}
	/*
	private static boolean areStackable(ItemStack stack1, ItemStack stack2) {
		return stack1.getCount() + stack2.getCount() <= 64 && ItemStack.areItemsEqual(stack1, stack2);
	}

	private static void stackTogether(ItemStack stack1, ItemStack stack2) {
		stack2.grow(stack1.getCount());
		stack1.setCount(0);
	}*/
}
