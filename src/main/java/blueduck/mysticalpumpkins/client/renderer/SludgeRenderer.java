package blueduck.mysticalpumpkins.client.renderer;

import blueduck.mysticalpumpkins.client.model.DragourdModel;
import blueduck.mysticalpumpkins.client.model.SludgeModel;
import blueduck.mysticalpumpkins.entity.DragourdEntity;
import blueduck.mysticalpumpkins.entity.SludgeEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class SludgeRenderer extends MobRenderer<SludgeEntity, SludgeModel> {
    public SludgeRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn,  new SludgeModel(0), 0.7F);
    }

    @Override
    public ResourceLocation getEntityTexture(SludgeEntity entity) {
        return new ResourceLocation("mystical_pumpkins", "textures/entity/pumpkin_sludge.png");
    }
}
