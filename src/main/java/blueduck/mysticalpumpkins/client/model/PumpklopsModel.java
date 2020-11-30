package blueduck.mysticalpumpkins.client.model;

import blueduck.mysticalpumpkins.entity.PumpklopsEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PumpklopsModel extends AnimatedGeoModel<PumpklopsEntity> {

	public PumpklopsModel() {
	}

	@Override
	public ResourceLocation getModelLocation(PumpklopsEntity object) {
		return new ResourceLocation("mystical_pumpkins", "geo/pumpklops.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(PumpklopsEntity object) {
		return new ResourceLocation("mystical_pumpkins", "textures/entity/pumpklops_rotten.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(PumpklopsEntity object) {
		return new ResourceLocation("mystical_pumpkins", "animations/pumpklops.json");
	}
}