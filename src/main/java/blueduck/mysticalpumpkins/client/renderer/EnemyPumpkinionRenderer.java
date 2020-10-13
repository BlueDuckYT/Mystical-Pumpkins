package blueduck.mysticalpumpkins.client.renderer;

import blueduck.mysticalpumpkins.client.model.DragourdModel;
import blueduck.mysticalpumpkins.client.model.PumpkinionModel;
import blueduck.mysticalpumpkins.entity.DragourdEntity;
import blueduck.mysticalpumpkins.entity.EnemyPumpkinionEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class EnemyPumpkinionRenderer extends MobRenderer<EnemyPumpkinionEntity, PumpkinionModel> {
    public EnemyPumpkinionRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn,  new PumpkinionModel(), 0.2F);
    }

    @Override
    public ResourceLocation getEntityTexture(EnemyPumpkinionEntity entity) {
        return new ResourceLocation("mystical_pumpkins", "textures/entity/pumpkinion.png");
    }
}
