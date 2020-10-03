package blueduck.tileentity;

import net.minecraft.item.ItemStack;

public class InfuserRecipe {

	private final ItemStack input;
	private final int essenceAmount;
	private final ItemStack secondary;
	private final ItemStack output;

	public InfuserRecipe(ItemStack input, int fuelAmount, ItemStack secondary, ItemStack output) {
		this.input = input;
		this.essenceAmount = fuelAmount;
		this.secondary = secondary;
		this.output = output;
	}


	public ItemStack getOutput() {
		return output;
	}

	public int getEssenceAmount() {
		return essenceAmount;
	}

	public ItemStack getSecondary() {
		return secondary;
	}

	public ItemStack getInput() {
		return input;
	}

	@Override
	public String toString() {
		return "InfuserRecipe{" +
				       "input=" + input +
				       ", essenceAmount=" + essenceAmount +
				       ", secondary=" + secondary +
				       ", output=" + output +
				       '}';
	}
}
