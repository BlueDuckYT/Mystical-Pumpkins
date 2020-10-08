package blueduck.mysticalpumpkins.registry;

import blueduck.mysticalpumpkins.MysticalPumpkinsMod;
import blueduck.mysticalpumpkins.tileentity.InfuserRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;

/**
 * Registry for Infusion Table Recipes.
 * You can add, remove, search for recipes using the methods below.
 * Works even at runtime, to let you add recipes for some challenges, remove some from other mods and so on.
 * */

public class InfuserRecipeRegistry {

	private static final ArrayList<InfuserRecipe> recipes = new ArrayList<>();

	private static void fillWithBuiltInRecipes() {
		//TODO add recipes
		addInfuserRecipe(new ItemStack(Items.CARVED_PUMPKIN), 10, new ItemStack(Items.SEA_PICKLE, 8), new ItemStack(RegisterHandler.WATER_PUMPKIN_ITEM.get()));
		addInfuserRecipe(new ItemStack(Items.CARVED_PUMPKIN), 30, new ItemStack(Items.GOLD_INGOT, 7), new ItemStack(RegisterHandler.LUCK_PUMPKIN_ITEM.get()));

		//addInfuserRecipe(new ItemStack(Items.CARVED_PUMPKIN), 30, new ItemStack(Items.FEATHER, 10), new ItemStack(RegisterHandler.FLOATING_PUMPKIN_ITEM.get()));

		for (InfuserRecipe recipe : recipes) {
			MysticalPumpkinsMod.LOGGER.debug("Registered recipe " + recipe);
		}
		MysticalPumpkinsMod.LOGGER.info("Registered Builtin Infuser Recipes");
	}

	private static InfuserRecipe createInfuserRecipe(ItemStack input, int essenceAmount, ItemStack secondaryMaterials, ItemStack output) {
		return new InfuserRecipe(input, essenceAmount, secondaryMaterials, output);
	}

	public static void addInfuserRecipe(ItemStack input, int essenceAmount, ItemStack secondaryMaterials, ItemStack output) {
		InfuserRecipe toAdd = createInfuserRecipe(input, essenceAmount, secondaryMaterials, output);
		recipes.add(toAdd);
	}

	public static void removeRecipe(ItemStack input, int essenceAmount, ItemStack secondaryMaterials, ItemStack output) {
		InfuserRecipe toRemove = searchRecipe(input, essenceAmount, secondaryMaterials);
		recipes.remove(toRemove);
	}

	public static InfuserRecipe searchRecipe(ItemStack inputStack, int essenceAmount, ItemStack secondaryStack) {
		if (!secondaryStack.isEmpty() && !inputStack.isEmpty() && essenceAmount > 0) {
			for (InfuserRecipe recipe : recipes) {
				ItemStack input = recipe.getInput();
				ItemStack second = recipe.getSecondary();
				if (second.isItemEqual(secondaryStack) && secondaryStack.getCount() >= second.getCount() && essenceAmount >= recipe.getEssenceAmount() && input.isItemEqual(inputStack) && inputStack.getCount() >= input.getCount()) {
					return recipe;
				}
			}
		}
		return null;
	}

	public static boolean isValidInput(ItemStack stack) {
		if (!stack.isEmpty()) {
			for (InfuserRecipe recipe : recipes) {
				if (recipe.getInput().isItemEqual(stack)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean canBeInfused(ItemStack stack) {
		if (!stack.isEmpty()) {
			for (InfuserRecipe recipe : recipes) {
				if (recipe.getSecondary().isItemEqual(stack)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isInfused(ItemStack stack) {
		if (!stack.isEmpty()) {
			for (InfuserRecipe recipe : recipes) {
				if (recipe.getOutput().isItemEqual(stack)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void initRegistry() {
		fillWithBuiltInRecipes();
	}
}
