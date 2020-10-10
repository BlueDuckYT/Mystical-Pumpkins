package blueduck.mysticalpumpkins.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieEntity;
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
import software.bernie.geckolib.example.KeyboardHandler;
import software.bernie.geckolib.manager.EntityAnimationManager;

public class DragourdEntity extends MonsterEntity implements IAnimatedEntity {

    public EntityAnimationManager animationManager = new EntityAnimationManager();

    private AnimationController moveController = new EntityAnimationController(this, "moveController", 10F, this::moveController);

    public int attackTimer = 0;

    public DragourdEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
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
        if (attackTimer > 0) {
            moveController.setAnimation(new AnimationBuilder().addAnimation("attack", true));
            attackTimer--;
        }
        else if (event.isWalking()) {
            moveController.setAnimation(new AnimationBuilder().addAnimation("walk", true));
            return true;
        }
        return false;


    }
    public boolean attackEntityAsMob(Entity entityIn) {
        attackTimer = 60;
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
                .func_233815_a_(Attributes.field_233818_a_, 20.0D) //health
                .func_233815_a_(Attributes.field_233821_d_, 0.3D) //movement speed
                .func_233815_a_(Attributes.field_233823_f_, 3.0D) //attack damage
                .func_233815_a_(Attributes.field_233824_g_, 0.2D); //attack knockback
    }

    public SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.BLOCK_WOOD_BREAK;
    }

    public SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_WOOD_BREAK;
    }

}
