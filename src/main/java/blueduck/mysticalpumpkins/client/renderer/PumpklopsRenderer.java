package blueduck.mysticalpumpkins.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import blueduck.mysticalpumpkins.client.model.PumpklopsModel;
import blueduck.mysticalpumpkins.entity.PumpklopsEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PumpklopsRenderer extends GeoEntityRenderer<PumpklopsEntity> {

	public PumpklopsRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new PumpklopsModel());
	}

	@Override
	public RenderType getRenderType(PumpklopsEntity animatable, float partialTicks, MatrixStack stack,
			IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.getEntityTranslucent(getTextureLocation(animatable));
	}
}
