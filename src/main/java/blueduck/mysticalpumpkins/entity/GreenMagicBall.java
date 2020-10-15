package blueduck.mysticalpumpkins.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.world.World;

public class GreenMagicBall extends DamagingProjectileEntity {
	
	protected GreenMagicBall(EntityType<? extends DamagingProjectileEntity> type, World world) {
		super(type, world);
	}
}
