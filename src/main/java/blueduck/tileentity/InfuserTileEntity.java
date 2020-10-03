package blueduck.tileentity;

import blueduck.container.InfuserContainer;
import blueduck.registry.InfuserRecipeRegistry;
import blueduck.registry.RegisterHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class InfuserTileEntity extends LockableTileEntity implements ISidedInventory, ITickableTileEntity {

	private InfuserRecipe currentRecipe;
	private int infusingTime;
	private int infusingTimeTotal;
	protected final IIntArray timeArray = new IIntArray() {
		public int get(int index) {
			switch(index) {
				case 0:
					return InfuserTileEntity.this.infusingTime;
				case 1:
					return InfuserTileEntity.this.infusingTimeTotal;
				default:
					return 0;
			}
		}

		public void set(int index, int value) {
			switch(index) {
				case 0:
					InfuserTileEntity.this.infusingTime = value;
					break;
				case 1:
					InfuserTileEntity.this.infusingTimeTotal = value;
			}

		}

		public int size() {
			return 2;
		}
	};
	protected NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);

	public InfuserTileEntity() {
		super(RegisterHandler.INFUSER_TILE_ENTITY.get());
	}

	@Override
	public void func_230337_a_(BlockState state, CompoundNBT compound) {
		super.func_230337_a_(state, compound);
		this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.items);
		this.infusingTime = compound.getInt("InfusingTime");
		this.infusingTimeTotal = compound.getInt("InfusingTimeTotal");
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		compound.putInt("InfusingTime", this.infusingTime);
		compound.putInt("InfusingTimeTotal", this.infusingTimeTotal);
		ItemStackHelper.saveAllItems(compound, this.items);
		return compound;
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("E");
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new InfuserContainer(id, player, this, timeArray);
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return new int[0];
	} //TODO SLOTS AUTOMATION

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, Direction direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
		if (direction == Direction.DOWN && index == 3) {
			return !stack.isEmpty();
		}
		return true;
	}

	@Override
	public int getSizeInventory() {
		return this.items.size();
	}

	@Override
	public boolean isEmpty() {
		for(ItemStack itemstack : this.items) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.items.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.items, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.items, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = this.items.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.items.set(index, stack);
		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !flag) {
			this.infusingTimeTotal = 12000;
			this.infusingTime = 0;
			this.markDirty();
		}
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		if (this.world != null && this.world.getTileEntity(this.pos) != this) {
			return false;
		} else {
			return player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 16.0D;
		}
	}

	@Override
	public void clear() {
		this.items.clear();
	}

	@Override
	public void tick() {
		boolean dirty = false;
		boolean isThereInput = !this.items.get(0).isEmpty();
		boolean isThereFuel = !this.items.get(1).isEmpty();
		boolean isThereSecondary = !this.items.get(2).isEmpty();
		if (!world.isRemote) {
			if (isThereFuel && isThereInput && isThereSecondary)  {
				if (this.currentRecipe == null) {
					this.currentRecipe = InfuserRecipeRegistry.searchRecipe(this.items.get(0).getCount(), this.items.get(1).getCount(), this.items.get(2));;
				}
				if (currentRecipe != null) {
					if (infusingTime == 0) {
						this.items.get(0).setCount(this.items.get(0).getCount() - currentRecipe.getInputAmount());
						this.items.get(1).setCount(this.items.get(1).getCount() - currentRecipe.getEssenceAmount());
						this.items.get(2).setCount(this.items.get(2).getCount() - currentRecipe.getSecondary().getCount());
						dirty = true;
					}
					++this.infusingTime;
					if (this.infusingTime == this.infusingTimeTotal) {
						this.items.set(3, currentRecipe.getOutput());
						this.items.get(3).setCount(currentRecipe.getOutput().getCount());
						this.infusingTime = 0;
						dirty = true;
					}
				}
			}
		}
		if (dirty) {
			this.markDirty();
		}
	}
}
