// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.12.2 or 1.15.2 (same format for both) for entity models animated with GeckoLib
// Paste this class into your mod and follow the documentation for GeckoLib to use animations. You can find the documentation here: https://github.com/bernie-g/geckolib
// Blockbench plugin created by Gecko
package blueduck.mysticalpumpkins.client.model;

import blueduck.mysticalpumpkins.entity.EnemyPumpkinionEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PumpkinionModel extends AnimatedGeoModel<EnemyPumpkinionEntity> {

	public PumpkinionModel() {
	}

	@Override
	public ResourceLocation getModelLocation(EnemyPumpkinionEntity object) {
		return new ResourceLocation("mystical_pumpkins", "geo/pumpkinion.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(EnemyPumpkinionEntity object) {
		return new ResourceLocation("mystical_pumpkins", "textures/entity/pumpkinion.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(EnemyPumpkinionEntity object) {
		return new ResourceLocation("mystical_pumpkins", "animations/pumpkinion.json");
	}
}