package blueduck.mysticalpumpkins.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;
import software.bernie.geckolib.animation.builder.AnimationBuilder;
import software.bernie.geckolib.animation.controller.AnimationController;
import software.bernie.geckolib.animation.controller.EntityAnimationController;
import software.bernie.geckolib.entity.IAnimatedEntity;
import software.bernie.geckolib.event.AnimationTestEvent;
import software.bernie.geckolib.manager.EntityAnimationManager;

import javax.annotation.Nullable;

public class PumpklopsEntity extends MonsterEntity implements IAnimatedEntity {

    private final ServerBossInfo bossInfo = (ServerBossInfo)(new ServerBossInfo(this.getDisplayName(), BossInfo.Color.YELLOW, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);

    public EntityAnimationManager animationManager = new EntityAnimationManager();

    private AnimationController moveController = new EntityAnimationController(this, "moveController", 10F, this::moveController);

    public int attackTimer = 0;

    public PumpklopsEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        registerAnimationControllers();
    }
    public void registerAnimationControllers()
    {
        if(world.isRemote)
        {
            this.animationManager.addAnimationController(moveController);
        }
    }

    @Override
    public EntityAnimationManager getAnimationManager() {
        return animationManager;
    }

    private <ENTITY extends Entity> boolean moveController(AnimationTestEvent<ENTITY> event)
    {
        boolean b = false;
        if (attackTimer > 0) {
            attackTimer--;
            //moveController.setAnimation(new AnimationBuilder().addAnimation("attack", true));
            b = true;
            return true;
        }
        else if (event.isWalking() || true) {
            moveController.setAnimation(new AnimationBuilder().addAnimation("move", true));
            if (!b) {

            }
            return true;
        }
        return false;


    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (this.hasCustomName()) {
            this.bossInfo.setName(this.getDisplayName());
        }

    }
    protected void updateAITasks() {
        super.updateAITasks();
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }

    public void setCustomName(@Nullable ITextComponent name) {
        super.setCustomName(name);
        this.bossInfo.setName(this.getDisplayName());
    }
    public boolean attackEntityAsMob(Entity entityIn) {
        attackTimer = 60;
       // moveController.setAnimation(new AnimationBuilder().addAnimation("attack", true));

        return super.attackEntityAsMob(entityIn);
    }

    public void addTrackingPlayer(ServerPlayerEntity player) {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    /**
     * Removes the given player from the list of players tracking this entity. See {@link Entity#addTrackingPlayer} for
     * more information on tracking.
     */
    public void removeTrackingPlayer(ServerPlayerEntity player) {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .func_233815_a_(Attributes.field_233818_a_, 500.0D) //health
                .func_233815_a_(Attributes.field_233821_d_, 0.3D) //movement speed
                .func_233815_a_(Attributes.field_233823_f_, 8.0D) //attack damage
                .func_233815_a_(Attributes.field_233824_g_, 0.2D); //attack knockback
    }

    public SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.BLOCK_WOOD_BREAK;
    }

    public SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_WOOD_BREAK;
    }

}
