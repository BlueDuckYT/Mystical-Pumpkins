package blueduck.tileentity;

import net.minecraft.item.ItemStack;

public class InfuserRecipe {

	private final int inputAmount;
	private final int essenceAmount;
	private final ItemStack secondary;
	private final ItemStack output;

	public InfuserRecipe(int inputAmount, int fuelAmount, ItemStack secondary, ItemStack output) {
		this.inputAmount = inputAmount;
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

	public int getInputAmount() {
		return inputAmount;
	}

	@Override
	public String toString() {
		return "InfuserRecipe{" +
				       "inputAmount=" + inputAmount +
				       ", essenceAmount=" + essenceAmount +
				       ", secondary=" + secondary +
				       ", output=" + output +
				       '}';
	}
}
