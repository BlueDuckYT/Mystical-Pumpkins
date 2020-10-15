package blueduck.mysticalpumpkins.client.renderer;

import blueduck.mysticalpumpkins.client.model.SludgeModel;
import blueduck.mysticalpumpkins.entity.SludgeEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class SludgeRenderer extends MobRenderer<SludgeEntity, SludgeModel> {
    public SludgeRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new SludgeModel(16), 0.25F);
        this.addLayer(new SludgeGelLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(SludgeEntity entity) {
        return new ResourceLocation("mystical_pumpkins", "textures/entity/pumpkin_sludge.png");
    }

    @Override
    public void render(SludgeEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        this.shadowSize = 0.25F * entityIn.getSlimeSize();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    protected void preRenderCallback(SludgeEntity living, MatrixStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(0.999F, 0.999F, 0.999F);
        matrixStackIn.translate(0.0D, 0.001F, 0.0D);
        float size = living.getSlimeSize();
        float f = 1.0F / (MathHelper.lerp(partialTickTime, living.prevSquishFactor, living.squishFactor) / (size * 0.5F + 1.0F) + 1.0F);
        matrixStackIn.scale(f * size, 1.0F / f * size, f * size);
    }
}
