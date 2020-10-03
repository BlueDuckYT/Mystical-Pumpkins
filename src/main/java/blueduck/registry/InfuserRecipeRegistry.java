package blueduck.registry;

import blueduck.MagicPumpkinsMod;
import blueduck.tileentity.InfuserRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;

public class InfuserRecipeRegistry {

	private static final List<InfuserRecipe> recipes = new ArrayList<>();

	private static void fillWithBuiltInRecipes() {
		addInfuserRecipe(10, 1, new ItemStack(Items.EGG, 1), new ItemStack(Items.KELP, 9));
		addInfuserRecipe(1, 2, new ItemStack(Items.FEATHER, 3), new ItemStack(Items.EGG, 1));
		MagicPumpkinsMod.LOGGER.info("Registered Builtin recipe");
	}

	public static void addInfuserRecipe(InfuserRecipe recipe) {
		recipes.add(recipe);
	}

	public static void addInfuserRecipe(int inputAmount, int essenceAmount, ItemStack secondaryMaterials, ItemStack output) {
		InfuserRecipe recipe = new InfuserRecipe(inputAmount, essenceAmount, secondaryMaterials, output);
		addInfuserRecipe(recipe);
	}

	public static InfuserRecipe searchRecipe(int inputAmount, int essenceAmount, ItemStack secondaryStack) {
		if (secondaryStack.isEmpty()) {
			return null;
		} else {
			for (InfuserRecipe recipe : recipes) {
				if (recipe.getSecondary().isItemEqual(secondaryStack) && essenceAmount >= recipe.getEssenceAmount() && inputAmount >= recipe.getInputAmount()) {
					return recipe;
				}
			}
		}
		return null;
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
