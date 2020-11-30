package blueduck.mysticalpumpkins.client.model;

import blueduck.mysticalpumpkins.entity.FriendlyPumpkinionEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FriendlyPumpkinionModel extends AnimatedGeoModel<FriendlyPumpkinionEntity> {

	public FriendlyPumpkinionModel() {
	}

	@Override
	public ResourceLocation getModelLocation(FriendlyPumpkinionEntity object) {
		return new ResourceLocation("mystical_pumpkins", "geo/pumpkinion.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(FriendlyPumpkinionEntity object) {
		return new ResourceLocation("mystical_pumpkins", "textures/entity/pumpkinion.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(FriendlyPumpkinionEntity object) {
		return new ResourceLocation("mystical_pumpkins", "animations/pumpkinion.json");
	}
}