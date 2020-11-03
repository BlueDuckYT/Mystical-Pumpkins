package blueduck.mysticalpumpkins.entity;

import blueduck.mysticalpumpkins.registry.RegisterHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.Util;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(
	value = Dist.CLIENT,
	_interface = IRendersAsItem.class
)

public class GreenMagicBallEntity extends DamagingProjectileEntity implements IRendersAsItem {

	private static final DataParameter<ItemStack> ITEMSTACK_DATA = EntityDataManager.createKey(ProjectileItemEntity.class, DataSerializers.ITEMSTACK);

	public static DamageSource greenSource(GreenMagicBallEntity source, Entity indirectEntityIn) {
		return (new IndirectEntityDamageSource("pumpklops.magic", source, indirectEntityIn)).setProjectile();
	}

	public GreenMagicBallEntity(double x, double y, double z, double accelX, double accelY, double accelZ, World world) {
		super(RegisterHandler.GREEN_MAGIC_BALL.get(), x, y, z, accelX, accelY, accelZ, world);
	}

	public GreenMagicBallEntity(LivingEntity shooter, double accelX, double accelY, double accelZ, World world) {
		super(RegisterHandler.GREEN_MAGIC_BALL.get(), shooter, accelX, accelY, accelZ, world);
	}

	public GreenMagicBallEntity(EntityType<? extends DamagingProjectileEntity> greenMagicBallEntityType, World world) {
		super(greenMagicBallEntityType, world);
	}

	@Override
	public boolean isBurning() {
		return false;
	}

	@Override
	protected void onEntityHit(EntityRayTraceResult ert) {
		super.onEntityHit(ert);
		if (!this.world.isRemote) {
			Entity entity = ert.getEntity();
			Entity entity1 = this.func_234616_v_();
			boolean flag;
			if (entity1 instanceof LivingEntity) {
				LivingEntity livingentity = (LivingEntity) entity1;
				flag = entity.attackEntityFrom(greenSource(this, livingentity), 8.0F);
				if (flag) {
					if (entity.isAlive()) {
						this.applyEnchantments(livingentity, entity);
					} else {
						livingentity.heal(5.0F);
					}
				}
			}
		}
	}

	@Override
	public void onImpact(RayTraceResult result) {
		super.onImpact(result);
		if (!this.world.isRemote)
			this.remove();
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return false;
	}

	@Override
	protected boolean isFireballFiery() {
		return false;
	}

	public void setStack(ItemStack stack) {
		if (stack.getItem() != RegisterHandler.GREEN_MAGIC_BALL_ITEM.get() || stack.hasTag()) {
			this.getDataManager().set(ITEMSTACK_DATA, Util.make(stack.copy(), (newstack) -> newstack.setCount(1)));
		}

	}

	protected ItemStack getStack() {
		return this.getDataManager().get(ITEMSTACK_DATA);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public ItemStack getItem() {
		ItemStack itemstack = this.getStack();
		return itemstack.isEmpty() ? new ItemStack(RegisterHandler.GREEN_MAGIC_BALL_ITEM.get()) : itemstack;
	}

	@Override
	protected void registerData() {
		this.getDataManager().register(ITEMSTACK_DATA, ItemStack.EMPTY);
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		ItemStack itemstack = this.getStack();
		if (!itemstack.isEmpty()) {
			compound.put("Item", itemstack.write(new CompoundNBT()));
		}

	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		ItemStack itemstack = ItemStack.read(compound.getCompound("Item"));
		this.setStack(itemstack);
	}
}
