package blueduck.container;

import blueduck.registry.InfuserRecipeRegistry;
import blueduck.registry.RegisterHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class InfuserContainer extends Container {

	private final IInventory tileInfuser;
	private final IIntArray timeArray;
	//private final Slot slot;

	public InfuserContainer(int id, PlayerInventory inv) {
		this(id, inv, new Inventory(4), new IntArray(2));
	}

	public InfuserContainer(int id, PlayerInventory playerInventory, IInventory tileEntity, IIntArray iIntArray) {
		super(RegisterHandler.INFUSER_CONTAINER.get(), id);
		assertInventorySize(tileEntity, 4);
		assertIntArraySize(iIntArray, 2);
		this.tileInfuser = tileEntity;
		this.timeArray = iIntArray;
		this.addSlot(new InputSlot(tileEntity, 0, 53, 12));
		this.addSlot(new FuelSlot(tileEntity, 1, 17, 30));
		this.addSlot(new SecondaryItemSlot(tileEntity, 2, 53, 48));
		this.addSlot(new OutputSlot(tileEntity, 3, 143, 30));
		this.trackIntArray(iIntArray);

		for(int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
		}

		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return this.tileInfuser.isUsableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}
		return itemstack;
	}

	public int infusingScaled() {
		int infusing = this.timeArray.get(0);
		int total = this.timeArray.get(1);
		return total != 0 && infusing != 0 ? infusing * 70 / total : 0;
	}

	static class InputSlot extends Slot {

		public InputSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		public static boolean isValidInput(ItemStack stack) {
			return InfuserRecipeRegistry.isValidInput(stack);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			return isValidInput(stack);
		}

		@Override
		public int getSlotStackLimit() {
			return 64;
		}
	}

	static class FuelSlot extends Slot {

		public FuelSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			return stack.getItem() == Items.PUMPKIN_SEEDS; //Essence
		}

		@Override
		public int getSlotStackLimit() {
			return 64;
		}
	}

	static class SecondaryItemSlot extends Slot {

		public SecondaryItemSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			return InfuserRecipeRegistry.canBeInfused(stack);
		}

		@Override
		public int getSlotStackLimit() {
			return 64;
		}

	}

	static class OutputSlot extends Slot {

		public OutputSlot(IInventory inventory, int index, int xPosition, int yPosition) {
			super(inventory, index, xPosition, yPosition);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			return InfuserRecipeRegistry.isInfused(stack);
		}

		@Override
		public int getSlotStackLimit() {
			return 64;
		}

	}
}
