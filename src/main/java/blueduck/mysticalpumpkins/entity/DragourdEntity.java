package blueduck.mysticalpumpkins.entity;

import java.util.Random;

import blueduck.mysticalpumpkins.MysticalPumpkinsMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class DragourdEntity extends MonsterEntity implements IAnimatable {

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (this.getLastAttackedEntityTime() + 30 > this.ticksExisted && this.getLastAttackedEntityTime() != 0) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("attackfull", true));
			return PlayState.CONTINUE;
		} else if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walkfull", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<DragourdEntity>(this, "controller", 0.1F, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	public DragourdEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 20.0D) // health
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3D) // movement speed
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 3.0D) // attack damage
				.createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 0.2D); // attack knockback
	}


	public SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.BLOCK_WOOD_BREAK;
	}

	public static boolean canSpawn(EntityType<DragourdEntity> type, IWorld world, SpawnReason spawnReason, BlockPos pos,
			Random random) {
		return (MysticalPumpkinsMod.CONFIG.DRAGOURD_SPAWN_EVERYWHERE_ON_FULL_MOON.get()
				&& world.getMoonFactor() == 1.0F && !world.getDimensionType().doesFixedTimeExist())
				|| MysticalPumpkinsMod.CONFIG.DRAGOURD_SPAWN_BIOMES.get().contains(world.getBiome(pos).toString());
	}

	public SoundEvent getDeathSound() {
		return SoundEvents.BLOCK_WOOD_BREAK;
	}

}
