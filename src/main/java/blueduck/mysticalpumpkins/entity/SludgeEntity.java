package blueduck.mysticalpumpkins.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biomes;
import software.bernie.geckolib.animation.builder.AnimationBuilder;
import software.bernie.geckolib.animation.controller.AnimationController;
import software.bernie.geckolib.animation.controller.EntityAnimationController;
import software.bernie.geckolib.entity.IAnimatedEntity;
import software.bernie.geckolib.event.AnimationTestEvent;
import software.bernie.geckolib.manager.EntityAnimationManager;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public class SludgeEntity extends SlimeEntity {


    private static final DataParameter<Integer> SLIME_SIZE = EntityDataManager.createKey(SludgeEntity.class, DataSerializers.VARINT);
    public float squishAmount;
    public float squishFactor;
    public float prevSquishFactor;
    private boolean wasOnGround;

    public SludgeEntity(EntityType<? extends SludgeEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveController = new SludgeEntity.MoveHelperController(this);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SludgeEntity.FloatGoal(this));
        this.goalSelector.addGoal(2, new SludgeEntity.AttackGoal(this));
        this.goalSelector.addGoal(3, new SludgeEntity.FaceRandomGoal(this));
        this.goalSelector.addGoal(5, new SludgeEntity.HopGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, 10, true, false, (p_213811_1_) -> {
            return Math.abs(p_213811_1_.getPosY() - this.getPosY()) <= 4.0D;
        }));
        }

    public static AttributeModifierMap.MutableAttribute func_234294_m_() {
        return MonsterEntity.func_234295_eP_().func_233815_a_(Attributes.field_233821_d_, (double) 0.2F);
    }
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .func_233815_a_(Attributes.field_233818_a_, 16.0D) //health
                .func_233815_a_(Attributes.field_233821_d_, 0.3D) //movement speed
                .func_233815_a_(Attributes.field_233823_f_, 4.0D) //attack damage
                .func_233815_a_(Attributes.field_233824_g_, 0.2D); //attack knockback
    }

    public static boolean func_223367_b(EntityType<MagmaCubeEntity> p_223367_0_, IWorld p_223367_1_, SpawnReason p_223367_2_, BlockPos p_223367_3_, Random p_223367_4_) {
        return true;
    }

    public boolean isNotColliding(IWorldReader worldIn) {
        return worldIn.checkNoEntityCollision(this) && !worldIn.containsAnyLiquid(this.getBoundingBox());
    }

    protected void setSlimeSize(int size, boolean resetHealth) {
        super.setSlimeSize(size, resetHealth);
        this.getAttribute(Attributes.field_233826_i_).setBaseValue((double) (size * 3));
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness() {
        return 1.0F;
    }

    protected IParticleData getSquishParticle() {
        return ParticleTypes.DRIPPING_HONEY;
    }

    protected ResourceLocation getLootTable() {
        return this.isSmallSlime() ? LootTables.EMPTY : this.getType().getLootTable();
    }

    /**
     * Returns true if the entity is on fire. Used by render to add the fire effect on rendering.
     */
    public boolean isBurning() {
        return false;
    }

    /**
     * Gets the amount of time the slime needs to wait between jumps.
     */
    protected int getJumpDelay() {
        return super.getJumpDelay() * 4;
    }

    protected void alterSquishAmount() {
        this.squishAmount *= 0.9F;
    }

    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    protected void jump() {
        Vector3d vector3d = this.getMotion();
        this.setMotion(vector3d.x, (double) (this.getJumpUpwardsMotion() + (float) this.getSlimeSize() * 0.1F), vector3d.z);
        this.isAirBorne = true;
        net.minecraftforge.common.ForgeHooks.onLivingJump(this);
    }

    protected void handleFluidJump(ITag<Fluid> fluidTag) {
        if (fluidTag == FluidTags.LAVA) {
            Vector3d vector3d = this.getMotion();
            this.setMotion(vector3d.x, (double) (0.22F + (float) this.getSlimeSize() * 0.05F), vector3d.z);
            this.isAirBorne = true;
        } else {
            super.handleFluidJump(fluidTag);
        }

    }

    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    /**
     * Indicates weather the slime is able to damage the player (based upon the slime's size)
     */
    protected boolean canDamagePlayer() {
        return this.isServerWorld();
    }

    protected float func_225512_er_() {
        return super.func_225512_er_() + 2.0F;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return this.isSmallSlime() ? SoundEvents.ENTITY_MAGMA_CUBE_HURT_SMALL : SoundEvents.ENTITY_MAGMA_CUBE_HURT;
    }

    protected SoundEvent getDeathSound() {
        return this.isSmallSlime() ? SoundEvents.ENTITY_MAGMA_CUBE_DEATH_SMALL : SoundEvents.ENTITY_MAGMA_CUBE_DEATH;
    }

    protected SoundEvent getSquishSound() {
        return this.isSmallSlime() ? SoundEvents.ENTITY_MAGMA_CUBE_SQUISH_SMALL : SoundEvents.ENTITY_MAGMA_CUBE_SQUISH;
    }

    protected SoundEvent getJumpSound() {
        return SoundEvents.ENTITY_MAGMA_CUBE_JUMP;
    }

    private float func_234304_m_() {
        float f = this.isSmallSlime() ? 1.4F : 0.8F;
        return ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * f;
    }

    static class AttackGoal extends Goal {
        private final SlimeEntity slime;
        private int growTieredTimer;

        public AttackGoal(SlimeEntity slimeIn) {
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
                return livingentity instanceof PlayerEntity && ((PlayerEntity)livingentity).abilities.disableDamage ? false : this.slime.getMoveHelper() instanceof SludgeEntity.MoveHelperController;
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
            } else if (livingentity instanceof PlayerEntity && ((PlayerEntity)livingentity).abilities.disableDamage) {
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
            ((SludgeEntity.MoveHelperController)this.slime.getMoveHelper()).setDirection(this.slime.rotationYaw, this.slime.isServerWorld());
        }
    }

    static class FaceRandomGoal extends Goal {
        private final SlimeEntity slime;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public FaceRandomGoal(SlimeEntity slimeIn) {
            this.slime = slimeIn;
            this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            return this.slime.getAttackTarget() == null && (this.slime.func_233570_aj_() || this.slime.isInWater() || this.slime.isInLava() || this.slime.isPotionActive(Effects.LEVITATION)) && this.slime.getMoveHelper() instanceof SludgeEntity.MoveHelperController;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = 40 + this.slime.getRNG().nextInt(60);
                this.chosenDegrees = (float)this.slime.getRNG().nextInt(360);
            }

            ((SludgeEntity.MoveHelperController)this.slime.getMoveHelper()).setDirection(this.chosenDegrees, false);
        }
    }

    static class FloatGoal extends Goal {
        private final SlimeEntity slime;

        public FloatGoal(SlimeEntity slimeIn) {
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

            ((SludgeEntity.MoveHelperController)this.slime.getMoveHelper()).setSpeed(1.2D);
        }
    }

    static class HopGoal extends Goal {
        private final SlimeEntity slime;

        public HopGoal(SlimeEntity slimeIn) {
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
            ((SludgeEntity.MoveHelperController)this.slime.getMoveHelper()).setSpeed(1.0D);
        }
    }

    static class MoveHelperController extends MovementController {
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
                if (this.mob.func_233570_aj_()) {
                    this.mob.setAIMoveSpeed((float) (this.speed * this.mob.func_233637_b_(Attributes.field_233821_d_)));
                    if (this.jumpDelay-- <= 0) {
                        this.jumpDelay = this.slime.getJumpDelay();
                        if (this.isAggressive) {
                            this.jumpDelay /= 3;
                        }

                        this.slime.getJumpController().setJumping();
                        if (this.slime.makesSoundOnJump()) {
                            this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), this.slime.func_234304_m_());
                        }
                    } else {
                        this.slime.moveStrafing = 0.0F;
                        this.slime.moveForward = 0.0F;
                        this.mob.setAIMoveSpeed(0.0F);
                    }
                } else {
                    this.mob.setAIMoveSpeed((float) (this.speed * this.mob.func_233637_b_(Attributes.field_233821_d_)));
                }

            }
        }
    }
}


