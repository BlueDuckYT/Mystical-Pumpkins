package blueduck.mysticalpumpkins.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import blueduck.mysticalpumpkins.client.model.FriendlyPumpkinionModel;
import blueduck.mysticalpumpkins.entity.FriendlyPumpkinionEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class FriendlyPumpkinionRenderer extends GeoEntityRenderer<FriendlyPumpkinionEntity> {

	public FriendlyPumpkinionRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new FriendlyPumpkinionModel());
	}

	@Override
	public ResourceLocation getEntityTexture(FriendlyPumpkinionEntity entity) {
		return new ResourceLocation("mystical_pumpkins", "textures/entity/pumpkinion.png");
	}

	@Override
	public RenderType getRenderType(FriendlyPumpkinionEntity animatable, float partialTicks, MatrixStack stack,
			IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.getEntityTranslucent(getTextureLocation(animatable));
	}
}
