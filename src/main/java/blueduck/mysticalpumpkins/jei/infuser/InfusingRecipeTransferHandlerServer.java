package blueduck.mysticalpumpkins.jei.infuser;

import blueduck.mysticalpumpkins.container.InfusionTableContainer;
import blueduck.mysticalpumpkins.tileentity.InfusionTableRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

import java.util.List;
import java.util.Map;

public class InfusingRecipeTransferHandlerServer {
	public static void setItems(PlayerEntity player, InfusionTableRecipe recipe, Map<Integer, Integer> slotsWithStacks, boolean maxTransfer) {
		Container container = player.openContainer;
		InfusionTableContainer infuser = (InfusionTableContainer) container;
		List<ItemStack> ingredients = recipe.getIngredients();
		for (Map.Entry<Integer, Integer> e : slotsWithStacks.entrySet()) {
			Slot infuserSlot = infuser.getSlot(e.getKey());
			Slot inventorySlot = infuser.getSlot(e.getValue());
			if (!inventorySlot.canTakeStack(player)) {
				continue;
			}
			ItemStack inventoryStack = inventorySlot.getStack();
			if (inventoryStack.isEmpty()) {
				continue;
			}
			if (!infuserSlot.isItemValid(inventoryStack)) {
				continue;
			}
			ItemStack infuserStack = infuserSlot.getStack();
			int transfer = maxTransfer ? infuserSlot.getItemStackLimit(inventoryStack) : ingredients.get(e.getKey()).getCount();
			int count = Math.min(inventoryStack.getCount(), transfer - infuserStack.getCount());
			if (count <= 0) {
				continue;
			}
			if (infuserStack.isEmpty()) {
				infuserSlot.putStack(inventoryStack.split(count));
			} else if (ItemHandlerHelper.canItemStacksStackRelaxed(infuserStack, infuserStack)) {
				inventoryStack.shrink(count);
				infuserStack.grow(count);
			} else {
				// let full transfer be incomplete due to insufficient space to clear ingredient slots
				ItemStack remainder = ItemHandlerHelper.insertItemStacked(new PlayerMainInvWrapper(player.inventory), infuserStack, false);
				if (remainder.isEmpty()) {
					infuserSlot.putStack(inventoryStack.split(count));
				} else {
					infuserSlot.putStack(remainder);
				}
			}
		}
		infuser.detectAndSendChanges();
	}
}
