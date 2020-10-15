package blueduck.mysticalpumpkins.entity;

import blueduck.mysticalpumpkins.registry.RegisterHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class GreenMagicBallEntity extends DamagingProjectileEntity implements IRendersAsItem {

	public static DamageSource greenSource(GreenMagicBallEntity source, Entity indirectEntityIn) {
		return (new IndirectEntityDamageSource("pumpklop.magic", source, indirectEntityIn)).setProjectile();
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

	@Override
	public ItemStack getItem() {
		return RegisterHandler.GREEN_MAGIC_BALL_ITEM.get().getDefaultInstance();
	}
}
