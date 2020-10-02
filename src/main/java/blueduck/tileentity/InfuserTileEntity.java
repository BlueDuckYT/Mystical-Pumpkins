package blueduck.tileentity;

import blueduck.registry.RegisterHandler;
import com.google.common.collect.Maps;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;

import java.util.Map;

public class InfuserTileEntity extends TileEntity implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity {

	private int infusingTime;
	private int infusingTimeTotal;
	protected NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);

	public InfuserTileEntity() {
		super(RegisterHandler.INFUSER_TILE_ENTITY.get());
	}

	public Map<Item, Integer> getTotalTimes() {
		Map<Item, Integer> map = Maps.newLinkedHashMap();
		addInfuserRecipe(map, Items.ACACIA_BUTTON, 12000);
		return map;
	}

	@Override
	public void func_230337_a_(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
		super.func_230337_a_(p_230337_1_, p_230337_2_);
		this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(p_230337_2_, this.items);
		this.infusingTime = p_230337_2_.getInt("InfusingTime");
		this.infusingTimeTotal = p_230337_2_.getInt("InfusingTimeTotal");
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		compound.putInt("InfusingTime", this.infusingTime);
		compound.putInt("CookTimeTotal", this.infusingTimeTotal);
		ItemStackHelper.saveAllItems(compound, this.items);
		return compound;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return new int[0];
	}

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
			this.infusingTimeTotal = getTotalTimes().get(stack.getItem());
			this.infusingTime = 0;
			this.markDirty();
		}
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		if (this.world != null && this.world.getTileEntity(this.pos) != this) {
			return false;
		} else {
			return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
		}
	}

	@Override
	public void clear() {
		this.items.clear();
	}

	@Override
	public void fillStackedContents(RecipeItemHelper helper) {
		for(ItemStack itemstack : this.items) {
			helper.accountStack(itemstack);
		}
	}

	@Override
	public void setRecipeUsed(IRecipe<?> recipe) {

	}

	@Override
	public IRecipe<?> getRecipeUsed() {
		return null;
	}

	@Override
	public void tick() {
		boolean dirty = false;
		if (!getWorld().isRemote) {
			++this.infusingTime;
			if (this.infusingTime == this.infusingTimeTotal) {
				this.infusingTime = 0;
				dirty = true;
			}
		}
		if (dirty) {
			this.markDirty();
		}
	}

	private static void addInfuserRecipe(Map<Item, Integer> map, IItemProvider itemProvider, int burnTimeIn) {
		Item item = itemProvider.asItem();
		map.put(item, burnTimeIn);
	}
}
