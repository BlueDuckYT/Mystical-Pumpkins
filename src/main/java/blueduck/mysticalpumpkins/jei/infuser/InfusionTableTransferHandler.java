package blueduck.mysticalpumpkins.jei.infuser;

import blueduck.mysticalpumpkins.container.InfusionTableContainer;
import blueduck.mysticalpumpkins.network.MysticalPumpkinsMessageHandler;
import blueduck.mysticalpumpkins.network.message.InfusingMovingMessage;
import blueduck.mysticalpumpkins.registry.InfusionTableRecipeRegistry;
import blueduck.mysticalpumpkins.registry.RegisterHandler;
import blueduck.mysticalpumpkins.tileentity.InfusionTableRecipe;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiIngredient;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IStackHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import mezz.jei.config.ServerInfo;
import mezz.jei.transfer.RecipeTransferUtil;
import mezz.jei.util.Translator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

//TODO: fix this
public class InfusionTableTransferHandler implements IRecipeTransferHandler<InfusionTableContainer> {
	private static final Logger LOGGER = LogManager.getLogger("Infusion Table Transfer Handler");

	private final IStackHelper stackHelper;
	private final IRecipeTransferHandlerHelper handlerHelper;
	private final IRecipeTransferInfo<InfusionTableContainer> transferHelper;

	public InfusionTableTransferHandler(IStackHelper stackHelper, IRecipeTransferHandlerHelper handlerHelper, IRecipeTransferInfo<InfusionTableContainer> transferHelper) {
		this.stackHelper = stackHelper;
		this.handlerHelper = handlerHelper;
		this.transferHelper = transferHelper;
	}

	@Override
	public Class<InfusionTableContainer> getContainerClass() {
		return InfusionTableContainer.class;
	}

	@Override
	public IRecipeTransferError transferRecipe(InfusionTableContainer container, Object recipe, IRecipeLayout recipeLayout, PlayerEntity player, boolean maxTransfer, boolean doTransfer) {
		InfusionTableRecipe infusionTableRecipe = (InfusionTableRecipe) recipe;
		if (!ServerInfo.isJeiOnServer()) {
			String tooltipMessage = Translator.translateToLocal("jei.tooltip.error.recipe.transfer.no.server");
			return handlerHelper.createUserErrorWithTooltip(tooltipMessage);
		}

		if (!transferHelper.canHandle(container)) {
			return handlerHelper.createInternalError();
		}

		Map<Integer, Slot> inventorySlots = new HashMap<>();
		for (Slot slot : transferHelper.getInventorySlots(container)) {
			inventorySlots.put(slot.slotNumber, slot);
		}

		Map<Integer, Slot> craftingSlots = new HashMap<>();
		for (Slot slot : transferHelper.getRecipeSlots(container)) {
			craftingSlots.put(slot.slotNumber, slot);
		}

		int inputCount = 0;
		IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
		for (IGuiIngredient<ItemStack> ingredient : itemStackGroup.getGuiIngredients().values()) {
			if (ingredient.isInput() && !ingredient.getAllIngredients().isEmpty()) {
				inputCount++;
			}
		}

		if (inputCount > craftingSlots.size()) {
			LOGGER.error("Recipe Transfer helper {} does not work for container {}", transferHelper.getClass(), container.getClass());
			return handlerHelper.createInternalError();
		}

		Map<Integer, ItemStack> craftingItemStack = new HashMap<>();
		Map<Integer, ItemStack> inventoryItemStack = new HashMap<>();
		int filledCraftSlotCount = 0;
		int emptySlotCount = 0;

		for (Slot slot : craftingSlots.values()) {
			final ItemStack stack = slot.getStack();
			if (!stack.isEmpty()) {
				if (!slot.canTakeStack(player)) {
					LOGGER.error("Recipe Transfer helper {} does not work for container {}. Player can't move item out of Crafting Slot number {}", transferHelper.getClass(), container.getClass(), slot.slotNumber);
					return handlerHelper.createInternalError();
				}
				filledCraftSlotCount++;
				craftingItemStack.put(slot.slotNumber, stack.copy());
			}
		}

		for (Slot slot : inventorySlots.values()) {
			final ItemStack stack = slot.getStack();

			if (!stack.isEmpty()) {
				inventoryItemStack.put(slot.slotNumber, stack.copy());
			} else {
				emptySlotCount++;
			}
		}

		// check if we have enough inventory space to shuffle items around to their final locations
		if (filledCraftSlotCount - inputCount > emptySlotCount) {
			String message = Translator.translateToLocal("jei.tooltip.error.recipe.transfer.inventory.full");
			return handlerHelper.createUserErrorWithTooltip(message);
		}

		//TODO: adjust this thing
		//Guess what, JEI obviously complicates everything!
		RecipeTransferUtil.MatchingItemsResult matchingItemsResult = RecipeTransferUtil.getMatchingItems(stackHelper, inventoryItemStack, itemStackGroup.getGuiIngredients());

		if (matchingItemsResult.missingItems.size() > 0) {
			String message = Translator.translateToLocal("jei.tooltip.error.recipe.transfer.missing");
			return handlerHelper.createUserErrorForSlots(message, matchingItemsResult.missingItems);
		}

		Collection<Integer> slots = checkStack(infusionTableRecipe, inventoryItemStack);

		if (!slots.isEmpty()) {
			String message = Translator.translateToLocal("jei.tooltip.error.recipe.transfer.tooFewStack");
			return handlerHelper.createUserErrorForSlots(message, slots);
		}

		int itemsFound = 0;

		for (ItemStack inInfuser : craftingItemStack.values()) {
			if (inInfuser.isEmpty()
					    || inInfuser.isItemEqual(infusionTableRecipe.getInput())
					    || inInfuser.isItemEqual(RegisterHandler.PUMPKIN_ESSENCE.get().getDefaultInstance())
					    || inInfuser.isItemEqual(infusionTableRecipe.getSecondary()))
				itemsFound++;
		}

		if (itemsFound < craftingItemStack.size()) {
			String message = Translator.translateToLocal("jei.tooltip.error.recipe.transfer.differentStackThere");
			return handlerHelper.createUserErrorWithTooltip(message);
		}

		List<Integer> craftingSlotIndexes = new ArrayList<>(craftingSlots.keySet());
		Collections.sort(craftingSlotIndexes);

		// check that the slots exist and can be altered
		for (Map.Entry<Integer, Integer> entry : matchingItemsResult.matchingItems.entrySet()) {
			int craftNumber = entry.getKey();
			int slotNumber = craftingSlotIndexes.get(craftNumber);
			if (slotNumber < 0 || slotNumber >= container.inventorySlots.size()) {
				LOGGER.error("Recipes Transfer Helper {} references slot {} outside of the inventory's size {}", transferHelper.getClass(), slotNumber, container.inventorySlots.size());
				return handlerHelper.createInternalError();
			}
		}

		if (doTransfer) {
			InfusingMovingMessage message = new InfusingMovingMessage(matchingItemsResult.matchingItems, infusionTableRecipe, maxTransfer);
			MysticalPumpkinsMessageHandler.HANDLER.sendToServer(message);
		}

		return null;
	}

	private static Collection<Integer> checkStack(InfusionTableRecipe infusionTableRecipe, Map<Integer, ItemStack> stacks) {
		Collection<Integer> slot = new ArrayList<>();
		Map<Item, Integer> amountAndItem = new HashMap<>();
		for (ItemStack copyStack : stacks.values()) {
			if (!amountAndItem.containsKey(copyStack.getItem())) {
				amountAndItem.put(copyStack.getItem(), copyStack.getCount());
			} else {
				int oldStack = amountAndItem.get(copyStack.getItem());
				amountAndItem.replace(copyStack.getItem(), oldStack + copyStack.getCount());
			}
		}

		for (Map.Entry<Item, Integer> entry : amountAndItem.entrySet()) {
			int count = entry.getValue();
			ItemStack copyStack = new ItemStack(entry.getKey());
			if (InfusionTableRecipeRegistry.isValidInput(copyStack) && count < infusionTableRecipe.getInput().getCount()) {
				slot.add(0);
			} else if (copyStack.getItem() == RegisterHandler.PUMPKIN_ESSENCE.get() && count < infusionTableRecipe.getEssenceAmount()) {
				slot.add(1);
			} else if (InfusionTableRecipeRegistry.canBeInfused(copyStack) && count < infusionTableRecipe.getSecondary().getCount()) {
				slot.add(2);
			}
		}

		return slot;
	}
}
