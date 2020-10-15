package blueduck.mysticalpumpkins.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class SludgeEntity extends SlimeEntity {

	public SludgeEntity(EntityType<? extends SlimeEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new AttackEveryFreakingAttackAbleEntity(this));
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.func_233666_p_()
				       .func_233815_a_(Attributes.field_233818_a_, 16.0D) //health
				       .func_233815_a_(Attributes.field_233821_d_, 0.3D) //movement speed
				       .func_233815_a_(Attributes.field_233823_f_, 4.0D) //attack damage
				       .func_233815_a_(Attributes.field_233824_g_, 0.2D); //attack knockback
	}

	@Override
	public boolean isNotColliding(IWorldReader worldIn) {
		return worldIn.checkNoEntityCollision(this) && !worldIn.containsAnyLiquid(this.getBoundingBox());
	}

	@Override
	protected IParticleData getSquishParticle() {
		return ParticleTypes.FLAME;
	}

	@Override
	protected ResourceLocation getLootTable() {
		return this.isSmallSlime() ? LootTables.EMPTY : this.getType().getLootTable();
	}

	@Override
	public boolean isBurning() {
		return false;
	}

	@Override
	protected int getJumpDelay() {
		return super.getJumpDelay() * 4;
	}

	@Override
	protected void alterSquishAmount() {
		this.squishAmount *= 0.9F;
	}

	@Override
	protected void jump() {
		Vector3d vector3d = this.getMotion();
		this.setMotion(vector3d.x, (this.getJumpUpwardsMotion() + (float)this.getSlimeSize() * 0.1F), vector3d.z);
		this.isAirBorne = true;
		net.minecraftforge.common.ForgeHooks.onLivingJump(this);
	}

	@Override
	public boolean onLivingFall(float distance, float damageMultiplier) {
		return false;
	}

	@Override
	protected boolean canDamagePlayer() {
		return this.isServerWorld();
	}

	@Override
	protected float func_225512_er_() {
		return super.func_225512_er_() + 2.0F;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return this.isSmallSlime() ? SoundEvents.ENTITY_MAGMA_CUBE_HURT_SMALL : SoundEvents.ENTITY_MAGMA_CUBE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return this.isSmallSlime() ? SoundEvents.ENTITY_MAGMA_CUBE_DEATH_SMALL : SoundEvents.ENTITY_MAGMA_CUBE_DEATH;
	}

	@Override
	protected SoundEvent getSquishSound() {
		return this.isSmallSlime() ? SoundEvents.ENTITY_MAGMA_CUBE_SQUISH_SMALL : SoundEvents.ENTITY_MAGMA_CUBE_SQUISH;
	}

	@Override
	protected SoundEvent getJumpSound() {
		return SoundEvents.ENTITY_MAGMA_CUBE_JUMP;
	}

	private static class AttackEveryFreakingAttackAbleEntity extends NearestAttackableTargetGoal<LivingEntity> {
		public AttackEveryFreakingAttackAbleEntity(SludgeEntity sludge) {
			super(sludge, LivingEntity.class, 0, true, true, LivingEntity::attackable);
		}

		@Override
		public boolean shouldExecute() {
			return super.shouldExecute() && this.target instanceof MonsterEntity;
		}
	}
}
