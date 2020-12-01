package blueduck.mysticalpumpkins.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class SludgeEntity extends CreatureEntity {

	private static final DataParameter<Integer> SLIME_SIZE = EntityDataManager.createKey(SludgeEntity.class, DataSerializers.VARINT);
	public float squishAmount;
	public float squishFactor;
	public float prevSquishFactor;
	private boolean wasOnGround;

	public SludgeEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
		super(type, worldIn);
		this.moveController = new SludgeEntity.MoveHelperController(this);
	}

	@Override
	public void registerGoals() {
		this.goalSelector.addGoal(1, new SludgeEntity.FloatGoal(this));
		this.goalSelector.addGoal(2, new SludgeEntity.AttackGoal(this));
		this.goalSelector.addGoal(3, new SludgeEntity.FaceRandomGoal(this));
		this.goalSelector.addGoal(5, new SludgeEntity.HopGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, 10, true, false, (p_213811_1_) -> {
			return (Math.abs(p_213811_1_.getPosY() - this.getPosY()) <= 4.0D) && (p_213811_1_ instanceof IMob) && !(p_213811_1_ instanceof SludgeEntity);
		}));

	}

	@Override
	protected void registerData()
	{
		super.registerData();
		this.dataManager.register(SLIME_SIZE, 1);
	}

	@Nullable
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		int j = 4;
		this.setSlimeSize(j, true);
		return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.func_233666_p_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 16.0D) //health
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3D) //movement speed
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 4.0D) //attack damage
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 32.0D) //follow range
				.createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 0.2D); //attack knockback
	}

	@Override
	public boolean isNotColliding(IWorldReader worldIn) {
		return worldIn.checkNoEntityCollision(this) && !worldIn.containsAnyLiquid(this.getBoundingBox());
	}

	public IParticleData getSquishParticle() {
		return ParticleTypes.DRIPPING_HONEY;
	}

	@Override
	public ResourceLocation getLootTable() {
		return this.isSmallSlime() ? LootTables.EMPTY : this.getType().getLootTable();
	}

	@Override
	public boolean isBurning() {
		return false;
	}

	public int getJumpDelay() {
		return (this.rand.nextInt(20) + 10) * 4;
	}

	public void alterSquishAmount() {
		this.squishAmount *= 0.9F;
	}

	public boolean isSmallSlime() {
		return this.getSlimeSize() <= 1;
	}

	@Override
	public void remove(boolean keepData) {
		int i = this.getSlimeSize();
		if (!this.world.isRemote && i > 1 && this.getShouldBeDead() && !this.removed) {
			ITextComponent itextcomponent = this.getCustomName();
			boolean flag = this.isAIDisabled();
			float f = (float) i / 4.0F;
			int j = i / 2;
			int k = 2 + this.rand.nextInt(3);

			for (int l = 0; l < k; ++l) {
				float f1 = ((float) (l % 2) - 0.5F) * f;
				float f2 = ((float) (l / 2) - 0.5F) * f;
				SludgeEntity slimeentity = (SludgeEntity) this.getType().create(this.world);
				if (this.isNoDespawnRequired()) {
					slimeentity.enablePersistence();
				}

				slimeentity.setCustomName(itextcomponent);
				slimeentity.setNoAI(flag);
				slimeentity.setInvulnerable(this.isInvulnerable());
				slimeentity.setSlimeSize(j, true);
				slimeentity.setLocationAndAngles(this.getPosX() + (double) f1, this.getPosY() + 0.5D, this.getPosZ() + (double) f2, this.rand.nextFloat() * 360.0F, 0.0F);
				this.world.addEntity(slimeentity);
			}
		}
		super.remove(keepData);
	}


	@Override
	public void jump() {
		Vector3d vector3d = this.getMotion();
		this.setMotion(vector3d.x, (double)this.getJumpUpwardsMotion() * 0.75 * this.getSlimeSize(), vector3d.z);
		this.isAirBorne = true;
	}

	@Override
	public boolean onLivingFall(float distance, float damageMultiplier) {
		return false;
	}

	public boolean canDamagePlayer() {
		return this.isServerWorld();
	}

	public float func_225512_er_() {
		return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) + 2.0F;
	}


	@Override
	public SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return this.isSmallSlime() ? SoundEvents.ENTITY_MAGMA_CUBE_HURT_SMALL : SoundEvents.ENTITY_MAGMA_CUBE_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return this.isSmallSlime() ? SoundEvents.ENTITY_MAGMA_CUBE_DEATH_SMALL : SoundEvents.ENTITY_MAGMA_CUBE_DEATH;
	}

	public SoundEvent getSquishSound() {
		return this.isSmallSlime() ? SoundEvents.ENTITY_MAGMA_CUBE_SQUISH_SMALL : SoundEvents.ENTITY_MAGMA_CUBE_SQUISH;
	}

	public SoundEvent getJumpSound() {
		return SoundEvents.ENTITY_MAGMA_CUBE_JUMP;
	}

	public int getSlimeSize() {
		return this.dataManager.get(SLIME_SIZE);
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

	public void collideWithEntity(Entity entityIn) {
		if (this.canDamagePlayer() && entityIn instanceof MonsterEntity && !(entityIn instanceof SludgeEntity)) {
			this.dealDamage((LivingEntity) entityIn);
		}

	}

	public void setSlimeSize(int size, boolean resetHealth) {
		this.dataManager.set(SLIME_SIZE, size);
		this.recenterBoundingBox();
		this.recalculateSize();
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)(size * size));
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double)(0.2F + 0.1F * (float)size));
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((double)size);
		if (resetHealth) {
			this.setHealth(this.getMaxHealth());
		}

		this.experienceValue = size;
	}


	public void dealDamage(LivingEntity entityIn) {
		if (this.isAlive()) {
			int i = this.getSlimeSize();
			if (this.getDistanceSq(entityIn) < 0.6D * (double)i * 0.6D * (double)i && this.canEntityBeSeen(entityIn) && entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), this.func_225512_er_())) {
				this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
				this.applyEnchantments(this, entityIn);
			}
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
			return this.slime.getAttackTarget() == null && (this.slime.isOnGround() || this.slime.isInWater() || this.slime.isInLava() || this.slime.isPotionActive(Effects.LEVITATION)) && this.slime.getMoveHelper() instanceof SludgeEntity.MoveHelperController;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void tick() {
			if (--this.nextRandomizeTime <= 0) {
				this.nextRandomizeTime = 40 + this.slime.getRNG().nextInt(60);
				this.chosenDegrees = (float) this.slime.getRNG().nextInt(360);
			}

			((SludgeEntity.MoveHelperController) this.slime.getMoveHelper()).setDirection(this.chosenDegrees, false);
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
			return (this.slime.isInWater() || this.slime.isInLava()) && this.slime.getMoveHelper() instanceof SludgeEntity.MoveHelperController;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void tick() {
			if (this.slime.getRNG().nextFloat() < 0.8F) {
				this.slime.getJumpController().setJumping();
			}

			((SludgeEntity.MoveHelperController) this.slime.getMoveHelper()).setSpeed(1.2D);
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
			((SludgeEntity.MoveHelperController) this.slime.getMoveHelper()).setSpeed(1.0D);
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
				return this.slime.getMoveHelper() instanceof SludgeEntity.MoveHelperController;
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
			((SludgeEntity.MoveHelperController) this.slime.getMoveHelper()).setDirection(this.slime.rotationYaw, true);
		}
	}

	public static class MoveHelperController extends MovementController {
		private float yRot;
		private int jumpDelay;
		private final SludgeEntity slime;
		private boolean isAggressive;

		public MoveHelperController(SludgeEntity slimeIn) {
			super(slimeIn);
			this.slime = slimeIn;
			this.yRot = 180.0F * slimeIn.rotationYaw / (float) Math.PI;
		}

		public void setDirection(float yRotIn, boolean aggressive) {
			this.yRot = yRotIn;
			this.isAggressive = aggressive;
		}

		public void setSpeed(double speedIn) {
			this.speed = speedIn;
			this.action = MovementController.Action.MOVE_TO;
		}

		public void tick() {
			this.mob.rotationYaw = this.limitAngle(this.mob.rotationYaw, this.yRot, 90.0F);
			this.mob.rotationYawHead = this.mob.rotationYaw;
			this.mob.renderYawOffset = this.mob.rotationYaw;
			if (this.action != MovementController.Action.MOVE_TO) {
				this.mob.setMoveForward(0.0F);
			} else {
				this.action = MovementController.Action.WAIT;
				if (this.mob.isOnGround()) {
					this.mob.setAIMoveSpeed((float) (this.speed * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
					if (this.jumpDelay-- <= 0) {
						this.jumpDelay = this.slime.getJumpDelay();
						if (this.isAggressive) {
							this.jumpDelay /= 3;
						}

						this.slime.getJumpController().setJumping();
						this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), 1F);
					} else {
						this.slime.moveStrafing = 0.0F;
						this.slime.moveForward = 0.0F;
						this.mob.setAIMoveSpeed(0.0F);
					}
				} else {
					this.mob.setAIMoveSpeed((float) (this.speed * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
				}

			}
		}
	}
}
