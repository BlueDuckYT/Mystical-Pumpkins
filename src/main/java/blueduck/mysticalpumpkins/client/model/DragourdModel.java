package blueduck.mysticalpumpkins.client.model;

import blueduck.mysticalpumpkins.entity.DragourdEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DragourdModel extends AnimatedGeoModel<DragourdEntity> {

	public DragourdModel() {
	}

	@Override
	public ResourceLocation getModelLocation(DragourdEntity object) {
		return new ResourceLocation("mystical_pumpkins", "geo/dragourd.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(DragourdEntity object) {
		return new ResourceLocation("mystical_pumpkins", "textures/entity/dragourd.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(DragourdEntity object) {
		return new ResourceLocation("mystical_pumpkins", "animations/dragourd.json");
	}
}