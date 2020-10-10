package blueduck.mysticalpumpkins.client.renderer;

import blueduck.mysticalpumpkins.client.model.DragourdModel;
import blueduck.mysticalpumpkins.entity.DragourdEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class DragourdRenderer extends MobRenderer<DragourdEntity, DragourdModel> {
    public DragourdRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn,  new DragourdModel(), 0.7F);
    }

    @Override
    public ResourceLocation getEntityTexture(DragourdEntity entity) {
        return new ResourceLocation("mystical_pumpkins", "textures/entity/dragourd.png");
    }
}
