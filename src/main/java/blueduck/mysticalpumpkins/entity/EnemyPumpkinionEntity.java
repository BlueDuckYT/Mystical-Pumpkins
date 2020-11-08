package blueduck.mysticalpumpkins.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import software.bernie.geckolib.animation.builder.AnimationBuilder;
import software.bernie.geckolib.animation.controller.AnimationController;
import software.bernie.geckolib.animation.controller.EntityAnimationController;
import software.bernie.geckolib.entity.IAnimatedEntity;
import software.bernie.geckolib.event.AnimationTestEvent;
import software.bernie.geckolib.manager.EntityAnimationManager;

public class EnemyPumpkinionEntity extends MonsterEntity implements IAnimatedEntity {

    public EntityAnimationManager animationManager = new EntityAnimationManager();

    private AnimationController moveController = new EntityAnimationController(this, "moveController", 10F, this::moveController);

    public int attackTimer = 0;

    public EnemyPumpkinionEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
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
            moveController.setAnimation(new AnimationBuilder().addAnimation("attack", true));
            b = true;
            return true;
        }
        else if (event.isWalking()) {
            moveController.setAnimation(new AnimationBuilder().addAnimation("walk", true));
            if (!b) {

            }
            return true;
        }
        return false;


    }
    public boolean attackEntityAsMob(Entity entityIn) {
        attackTimer = 60;
        if(world.isRemote) {
            moveController.setAnimation(new AnimationBuilder().addAnimation("attack", true));
        }
        return super.attackEntityAsMob(entityIn);
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
                .createMutableAttribute(Attributes.MAX_HEALTH, 10.0D) //health
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3D) //movement speed
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0D) //attack damage
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 0.2D); //attack knockback
    }

    public SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.BLOCK_WOOD_BREAK;
    }

    public SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_WOOD_BREAK;
    }

}
