package blueduck.mysticalpumpkins.registry;

import blueduck.mysticalpumpkins.MagicPumpkinsMod;
import blueduck.mysticalpumpkins.tileentity.InfuserRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;

public class InfuserRecipeRegistry {

	private static final List<InfuserRecipe> recipes = new ArrayList<>();

	private static void fillWithBuiltInRecipes() {
		addInfuserRecipe(new ItemStack(Items.JACK_O_LANTERN, 10), 1, new ItemStack(Items.EGG, 1), new ItemStack(Items.KELP, 9));
		addInfuserRecipe(new ItemStack(Items.PUMPKIN), 2, new ItemStack(Items.FEATHER, 3), new ItemStack(Items.EGG, 1));
		MagicPumpkinsMod.LOGGER.info("Registered Builtin recipe");
	}

	public static void addInfuserRecipe(InfuserRecipe recipe) {
		recipes.add(recipe);
	}

	public static void addInfuserRecipe(ItemStack inputAmount, int essenceAmount, ItemStack secondaryMaterials, ItemStack output) {
		InfuserRecipe recipe = new InfuserRecipe(inputAmount, essenceAmount, secondaryMaterials, output);
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
