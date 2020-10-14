package blueduck.mysticalpumpkins.client.renderer;

import blueduck.mysticalpumpkins.client.model.PumpklopsModel;
import blueduck.mysticalpumpkins.entity.PumpklopsEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class PumpklopsRenderer extends MobRenderer<PumpklopsEntity, PumpklopsModel> {
    public PumpklopsRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn,  new PumpklopsModel(), 0.7F);
    }

    @Override
    public ResourceLocation getEntityTexture(PumpklopsEntity entity) {
        return new ResourceLocation("mystical_pumpkins", "textures/entity/pumpklops_rotten.png");
    }
}
