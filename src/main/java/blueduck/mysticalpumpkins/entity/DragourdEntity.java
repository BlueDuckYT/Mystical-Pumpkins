package blueduck.mysticalpumpkins.entity;

import blueduck.mysticalpumpkins.MysticalPumpkinsMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;

public class DragourdEntity extends MonsterEntity implements IAnimatable {

    public AnimationFactory animationManager = new AnimationFactory(this);

    private AnimationController moveController = new AnimationController(this, "moveController", 10F, this::moveController);
    private AnimationController headController = new AnimationController(this, "headController", 10F, this::moveController);
    private AnimationController tailController = new AnimationController(this, "tailController", 10F, this::moveController);

    public int attackTimer = 0;

    public DragourdEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        registerAnimationControllers(animationManager.getOrCreateAnimationData(1));
    }
    public void registerAnimationControllers(AnimationData data)
    {
        if(world.isRemote)
        {
            data.addAnimationController(moveController);
            data.addAnimationController(headController);
            data.addAnimationController(tailController);
        }
    }



    private <E extends IAnimatable> PlayState moveController(AnimationEvent<E> event)
    {
        boolean b = false;
        if (attackTimer > 0) {
            headController.setAnimation(new AnimationBuilder().addAnimation("attackhead", true));
            tailController.setAnimation(new AnimationBuilder().addAnimation("attacktail", true));
            attackTimer--;
            b = true;
            return PlayState.STOP;
        }
        else if (event.isMoving()) {
            moveController.setAnimation(new AnimationBuilder().addAnimation("walklegs", true));
            if (!b) {
                headController.setAnimation(new AnimationBuilder().addAnimation("walkhead", true));
                tailController.setAnimation(new AnimationBuilder().addAnimation("walktail", true));
            }
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;


    }
    public boolean attackEntityAsMob(Entity entityIn) {
        attackTimer = 60;
        if(world.isRemote) {
            headController.setAnimation(new AnimationBuilder().addAnimation("attackhead", true));
            tailController.setAnimation(new AnimationBuilder().addAnimation("attacktail", true));
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
                .createMutableAttribute(Attributes.MAX_HEALTH, 20.0D) //health
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3D) //movement speed
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 3.0D) //attack damage
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 0.2D); //attack knockback
    }

    public SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.BLOCK_WOOD_BREAK;
    }

    public static boolean canSpawn(EntityType<DragourdEntity> type, IWorld world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return (MysticalPumpkinsMod.CONFIG.DRAGOURD_SPAWN_EVERYWHERE_ON_FULL_MOON.get() && world.getMoonFactor() == 1.0F && world.getDimensionType().equals(DimensionType.OVERWORLD)) || MysticalPumpkinsMod.CONFIG.DRAGOURD_SPAWN_BIOMES.get().contains(world.getBiome(pos).toString());
    }

    public SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_WOOD_BREAK;
    }

    @Override
    public void registerControllers(AnimationData animationData) {

    }

    @Override
    public AnimationFactory getFactory() {
        return animationManager;
    }
}
