package blueduck.mysticalpumpkins.tileentity;

import blueduck.mysticalpumpkins.registry.RegisterHandler;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InfusionTableRecipe {

	private final ItemStack input;
	private final int essenceAmount;
	private final ItemStack secondary;
	private final ItemStack output;

	public InfusionTableRecipe(ItemStack input, int fuelAmount, ItemStack secondary, ItemStack output) {
		this.input = input;
		this.essenceAmount = fuelAmount;
		this.secondary = secondary;
		this.output = output;
	}

	public ItemStack getOutput() {
		return output.copy();
	}

	public int getEssenceAmount() {
		return essenceAmount;
	}

	public ItemStack getSecondary() {
		return secondary.copy();
	}

	public ItemStack getInput() {
		return input.copy();
	}

	public List<ItemStack> getIngredients() {
		List<ItemStack> ingredients = new ArrayList<>();
		ingredients.add(input);
		ingredients.add(new ItemStack(RegisterHandler.PUMPKIN_ESSENCE.get(), essenceAmount));
		ingredients.add(secondary);
		return ingredients;
	}

	@Override
	public String toString() {
		return "InfuserRecipe -> " +
				       "input=" + input +
				       ", essenceAmount=" + essenceAmount +
				       ", secondary=" + secondary +
				       ", output=" + output
					;
	}
}
