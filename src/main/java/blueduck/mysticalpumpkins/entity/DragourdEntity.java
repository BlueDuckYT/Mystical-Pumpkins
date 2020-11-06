package blueduck.mysticalpumpkins.entity;

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
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib.animation.builder.AnimationBuilder;
import software.bernie.geckolib.animation.controller.AnimationController;
import software.bernie.geckolib.animation.controller.EntityAnimationController;
import software.bernie.geckolib.entity.IAnimatedEntity;
import software.bernie.geckolib.event.AnimationTestEvent;
import software.bernie.geckolib.manager.EntityAnimationManager;

import java.util.Random;

public class DragourdEntity extends MonsterEntity implements IAnimatedEntity {

    public EntityAnimationManager animationManager = new EntityAnimationManager();

    private AnimationController moveController = new EntityAnimationController(this, "moveController", 10F, this::moveController);
    private AnimationController headController = new EntityAnimationController(this, "headController", 10F, this::moveController);
    private AnimationController tailController = new EntityAnimationController(this, "tailController", 10F, this::moveController);

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
            this.animationManager.addAnimationController(headController);
            this.animationManager.addAnimationController(tailController);
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
            headController.setAnimation(new AnimationBuilder().addAnimation("attackhead", true));
            tailController.setAnimation(new AnimationBuilder().addAnimation("attacktail", true));
            attackTimer--;
            b = true;
            return true;
        }
        else if (event.isWalking()) {
            moveController.setAnimation(new AnimationBuilder().addAnimation("walklegs", true));
            if (!b) {
                headController.setAnimation(new AnimationBuilder().addAnimation("walkhead", true));
                tailController.setAnimation(new AnimationBuilder().addAnimation("walktail", true));
            }
            return true;
        }
        return false;


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
                .func_233815_a_(Attributes.field_233818_a_, 20.0D) //health
                .func_233815_a_(Attributes.field_233821_d_, 0.3D) //movement speed
                .func_233815_a_(Attributes.field_233823_f_, 3.0D) //attack damage
                .func_233815_a_(Attributes.field_233824_g_, 0.2D); //attack knockback
    }

    public SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.BLOCK_WOOD_BREAK;
    }

    public static boolean canSpawn(EntityType<DragourdEntity> type, IWorld world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBiome(pos).getRegistryName().equals(Biomes.DARK_FOREST.func_240901_a_()) || world.getBiome(pos).getRegistryName().equals(Biomes.DARK_FOREST_HILLS.func_240901_a_()) || (pos.getY() > world.getSeaLevel() && world.func_242413_ae() == 1.0F);
    }

    public SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_WOOD_BREAK;
    }

}
