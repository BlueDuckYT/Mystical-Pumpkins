package blueduck.mysticalpumpkins.client.renderer;

import blueduck.mysticalpumpkins.client.model.FriendlyPumpkinionModel;
import blueduck.mysticalpumpkins.client.model.PumpkinionModel;
import blueduck.mysticalpumpkins.entity.EnemyPumpkinionEntity;
import blueduck.mysticalpumpkins.entity.FriendlyPumpkinionEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class FriendlyPumpkinionRenderer extends MobRenderer<FriendlyPumpkinionEntity, FriendlyPumpkinionModel> {
    public FriendlyPumpkinionRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn,  new FriendlyPumpkinionModel(), 0.2F);
    }

    @Override
    public ResourceLocation getEntityTexture(FriendlyPumpkinionEntity entity) {
        return new ResourceLocation("mystical_pumpkins", "textures/entity/pumpkinion.png");
    }
}
