package blueduck.mysticalpumpkins.registry;

import blueduck.mysticalpumpkins.tileentity.InfusionTableRecipe;
import blueduck.mysticalpumpkins.utils.SpecialConstants;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;

/**
 * Registry for Infusion Table Recipes.
 * You can add, remove, search for recipes using the methods below.
 * Works even at runtime, to let you add recipes for some challenges, remove some from other mods and so on.
 * */

public class InfusionTableRecipeRegistry {

	public static final ArrayList<InfusionTableRecipe> recipes = new ArrayList<>();

	private static void fillWithBuiltInRecipes() {
		addInfuserRecipe(new ItemStack(Items.CARVED_PUMPKIN), 10, new ItemStack(Items.SEA_PICKLE, 8), new ItemStack(RegisterHandler.AQUASIGHT_PUMPKIN_ITEM.get()));
		addInfuserRecipe(new ItemStack(RegisterHandler.AQUASIGHT_PUMPKIN_ITEM.get()), 20, new ItemStack(Items.HEART_OF_THE_SEA, 1), new ItemStack(RegisterHandler.WATER_PUMPKIN_ITEM.get()));
		addInfuserRecipe(new ItemStack(RegisterHandler.AQUASIGHT_PUMPKIN_ITEM.get()), 32, new ItemStack(Items.ENDER_EYE, 1), new ItemStack(RegisterHandler.NIGHT_VISION_PUMPKIN_ITEM.get()));
		addInfuserRecipe(new ItemStack(RegisterHandler.WATER_PUMPKIN_ITEM.get()), 40, new ItemStack(RegisterHandler.HEART_OF_PUMPKLOPS.get(), 1), new ItemStack(RegisterHandler.CONDUIT_PUMPKIN_ITEM.get()));
		addInfuserRecipe(new ItemStack(Items.CARVED_PUMPKIN), 30, new ItemStack(Items.GOLD_INGOT, 7), new ItemStack(RegisterHandler.LUCK_PUMPKIN_ITEM.get()));
		addInfuserRecipe(new ItemStack(Items.CARVED_PUMPKIN), 20, new ItemStack(Items.FEATHER, 10), new ItemStack(RegisterHandler.FLOATING_PUMPKIN_ITEM.get()));
		addInfuserRecipe(new ItemStack(RegisterHandler.FLOATING_PUMPKIN_ITEM.get()), 40, new ItemStack(Items.TOTEM_OF_UNDYING, 1), new ItemStack(RegisterHandler.VOID_PUMPKIN_ITEM.get()));
		addInfuserRecipe(new ItemStack(RegisterHandler.VOID_PUMPKIN_ITEM.get()), 64, new ItemStack(RegisterHandler.HEART_OF_PUMPKLOPS.get(), 1), new ItemStack(RegisterHandler.GRAVITY_PUMPKIN_ITEM.get()));
		addInfuserRecipe(new ItemStack(RegisterHandler.VOID_PUMPKIN_ITEM.get()), 64, new ItemStack(Items.ELYTRA, 1), new ItemStack(RegisterHandler.GALAXY_PUMPKIN_ITEM.get()));
		addInfuserRecipe(new ItemStack(Items.CARVED_PUMPKIN), 30, new ItemStack(Items.BLAZE_ROD, 8), new ItemStack(RegisterHandler.LAVA_PUMPKIN_ITEM.get()));
		addInfuserRecipe(new ItemStack(Items.CARVED_PUMPKIN), 10, new ItemStack(Items.SLIME_BALL, 1), new ItemStack(RegisterHandler.MIRE_PUMPKIN_ITEM.get()));
		addInfuserRecipe(new ItemStack(Items.CARVED_PUMPKIN), 30, new ItemStack(Items.GOLDEN_APPLE, 1), new ItemStack(RegisterHandler.HEALING_PUMPKIN_ITEM.get()));
		addInfuserRecipe(new ItemStack(Items.CARVED_PUMPKIN), 20, new ItemStack(Items.DIAMOND_SWORD, 1), new ItemStack(RegisterHandler.WRATH_PUMPKIN_ITEM.get()));

		addInfuserRecipe(new ItemStack(Items.ENDER_EYE), 64, new ItemStack(Items.CARVED_PUMPKIN, 1), new ItemStack(RegisterHandler.EYE_OF_DREAD.get()));

		addInfuserRecipe(new ItemStack(Items.PUMPKIN), 64, new ItemStack(RegisterHandler.EYE_OF_DREAD.get(), 1), new ItemStack(RegisterHandler.DREAD_PUMPKIN_ITEM.get()));

		addInfuserRecipe(new ItemStack(Items.PUMPKIN), 8, new ItemStack(Items.TNT.get(), 1), new ItemStack(RegisterHandler.PUMPKIN_MINE_ITEM.get()));



		for (InfusionTableRecipe recipe : recipes) {
			SpecialConstants.LOGGER.debug("Registered recipe " + recipe);
		}
		SpecialConstants.LOGGER.info("Registered Builtin Infuser Recipes");
	}

	private static InfusionTableRecipe createInfuserRecipe(ItemStack input, int essenceAmount, ItemStack secondaryMaterials, ItemStack output) {
		return new InfusionTableRecipe(input, essenceAmount, secondaryMaterials, output);
	}

	public static void addInfuserRecipe(ItemStack input, int essenceAmount, ItemStack secondaryMaterials, ItemStack output) {
		InfusionTableRecipe toAdd = createInfuserRecipe(input, essenceAmount, secondaryMaterials, output);
		recipes.add(toAdd);
	}

	public static void removeRecipe(ItemStack input, int essenceAmount, ItemStack secondaryMaterials) {
		InfusionTableRecipe toRemove = searchRecipe(input, essenceAmount, secondaryMaterials);
		recipes.remove(toRemove);
	}

	public static InfusionTableRecipe searchRecipe(ItemStack inputStack, int essenceAmount, ItemStack secondaryStack) {
		if (!secondaryStack.isEmpty() && !inputStack.isEmpty() && essenceAmount > 0) {
			for (InfusionTableRecipe recipe : recipes) {
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
			for (InfusionTableRecipe recipe : recipes) {
				if (recipe.getInput().isItemEqual(stack)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean canBeInfused(ItemStack stack) {
		if (!stack.isEmpty()) {
			for (InfusionTableRecipe recipe : recipes) {
				if (recipe.getSecondary().isItemEqual(stack)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isInfused(ItemStack stack) {
		if (!stack.isEmpty()) {
			for (InfusionTableRecipe recipe : recipes) {
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
