package blueduck.mysticalpumpkins.registry;

import blueduck.mysticalpumpkins.MysticalPumpkinsMod;
import blueduck.mysticalpumpkins.tileentity.InfuserRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;

public class InfuserRecipeRegistry {

	private static final List<InfuserRecipe> recipes = new ArrayList<>();

	private static void fillWithBuiltInRecipes() {
		//TODO add recipes
		addInfuserRecipe(new ItemStack(Items.CARVED_PUMPKIN), 10, new ItemStack(Items.SEA_PICKLE, 8), new ItemStack(RegisterHandler.WATER_PUMPKIN_ITEM.get()));
		addInfuserRecipe(new ItemStack(Items.CARVED_PUMPKIN), 30, new ItemStack(Items.GOLD_INGOT, 7), new ItemStack(RegisterHandler.LUCK_PUMPKIN_ITEM.get()));

		//addInfuserRecipe(new ItemStack(Items.CARVED_PUMPKIN), 30, new ItemStack(Items.FEATHER, 10), new ItemStack(RegisterHandler.FLOATING_PUMPKIN_ITEM.get()));

		MysticalPumpkinsMod.LOGGER.info("Registered Builtin Infuser Recipes");
	}

	public static void addInfuserRecipe(InfuserRecipe recipe) {
		recipes.add(recipe);
	}

	public static void addInfuserRecipe(ItemStack input, int essenceAmount, ItemStack secondaryMaterials, ItemStack output) {
		InfuserRecipe recipe = new InfuserRecipe(input, essenceAmount, secondaryMaterials, output);
		addInfuserRecipe(recipe);
	}

	public static InfuserRecipe searchRecipe(ItemStack inputStack, int essenceAmount, ItemStack secondaryStack) {
		if (secondaryStack.isEmpty()) {
			return null;
		} else {
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
		if (stack.isEmpty()) return false;
		else {
			for (InfuserRecipe recipe : recipes) {
				if (recipe.getInput().isItemEqual(stack)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean canBeInfused(ItemStack stack) {
		if (stack.isEmpty()) return false;
		else {
			for (InfuserRecipe recipe : recipes) {
				if (recipe.getSecondary().isItemEqual(stack)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isInfused(ItemStack stack) {
		if (stack.isEmpty()) return false;
		else {
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
