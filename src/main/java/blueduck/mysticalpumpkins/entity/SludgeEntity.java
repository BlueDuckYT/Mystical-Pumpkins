package blueduck.mysticalpumpkins.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.EnumSet;

public class SludgeEntity extends SlimeEntity {

	public SludgeEntity(EntityType<? extends SlimeEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new SludgeEntity.FloatGoal(this));
		this.goalSelector.addGoal(2, new SludgeEntity.AttackGoal(this));
		this.goalSelector.addGoal(3, new SludgeEntity.FaceRandomGoal(this));
		this.goalSelector.addGoal(5, new SludgeEntity.HopGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, 10, true, false, (p_213811_1_) -> {
			return (Math.abs(p_213811_1_.getPosY() - this.getPosY()) <= 4.0D) && !(p_213811_1_ instanceof SludgeEntity);
		}));

	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.func_233666_p_()
				.func_233815_a_(Attributes.field_233818_a_, 16.0D) //health
				.func_233815_a_(Attributes.field_233821_d_, 0.3D) //movement speed
				.func_233815_a_(Attributes.field_233823_f_, 4.0D) //attack damage
				.func_233815_a_(Attributes.field_233819_b_, 32.0D) //follow range
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
		this.setMotion(vector3d.x, (this.getJumpUpwardsMotion() + (float) this.getSlimeSize() * 0.1F), vector3d.z);
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


	static class FaceRandomGoal extends Goal {
		private final SludgeEntity slime;
		private float chosenDegrees;
		private int nextRandomizeTime;

		public FaceRandomGoal(SludgeEntity slimeIn) {
			this.slime = slimeIn;
			this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		public boolean shouldExecute() {
			return this.slime.getAttackTarget() == null && (this.slime.func_233570_aj_() || this.slime.isInWater() || this.slime.isInLava() || this.slime.isPotionActive(Effects.LEVITATION)) && this.slime.getMoveHelper() instanceof SlimeEntity.MoveHelperController;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void tick() {
			if (--this.nextRandomizeTime <= 0) {
				this.nextRandomizeTime = 40 + this.slime.getRNG().nextInt(60);
				this.chosenDegrees = (float) this.slime.getRNG().nextInt(360);
			}

			((SlimeEntity.MoveHelperController) this.slime.getMoveHelper()).setDirection(this.chosenDegrees, false);
		}
	}

	static class FloatGoal extends Goal {
		private final SludgeEntity slime;

		public FloatGoal(SludgeEntity slimeIn) {
			this.slime = slimeIn;
			this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
			slimeIn.getNavigator().setCanSwim(true);
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		public boolean shouldExecute() {
			return (this.slime.isInWater() || this.slime.isInLava()) && this.slime.getMoveHelper() instanceof SlimeEntity.MoveHelperController;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void tick() {
			if (this.slime.getRNG().nextFloat() < 0.8F) {
				this.slime.getJumpController().setJumping();
			}

			((SlimeEntity.MoveHelperController) this.slime.getMoveHelper()).setSpeed(1.2D);
		}
	}

	static class HopGoal extends Goal {
		private final SludgeEntity slime;

		public HopGoal(SludgeEntity slimeIn) {
			this.slime = slimeIn;
			this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		public boolean shouldExecute() {
			return !this.slime.isPassenger();
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void tick() {
			((SlimeEntity.MoveHelperController) this.slime.getMoveHelper()).setSpeed(1.0D);
		}
	}

	static class AttackGoal extends Goal {
		private final SludgeEntity slime;
		private int growTieredTimer;

		public AttackGoal(SludgeEntity slimeIn) {
			this.slime = slimeIn;
			this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		public boolean shouldExecute() {
			LivingEntity livingentity = this.slime.getAttackTarget();
			if (livingentity == null) {
				return false;
			} else if (!livingentity.isAlive()) {
				return false;
			} else {
				return livingentity instanceof PlayerEntity && ((PlayerEntity) livingentity).abilities.disableDamage ? false : this.slime.getMoveHelper() instanceof SlimeEntity.MoveHelperController;
			}
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void startExecuting() {
			this.growTieredTimer = 300;
			super.startExecuting();
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		public boolean shouldContinueExecuting() {
			LivingEntity livingentity = this.slime.getAttackTarget();
			if (livingentity == null) {
				return false;
			} else if (!livingentity.isAlive()) {
				return false;
			} else if (livingentity instanceof PlayerEntity && ((PlayerEntity) livingentity).abilities.disableDamage) {
				return false;
			} else {
				return --this.growTieredTimer > 0;
			}
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void tick() {
			this.slime.faceEntity(this.slime.getAttackTarget(), 10.0F, 10.0F);
			((SlimeEntity.MoveHelperController) this.slime.getMoveHelper()).setDirection(this.slime.rotationYaw, true);
		}
	}

}
