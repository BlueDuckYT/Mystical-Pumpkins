package blueduck.mysticalpumpkins.client.renderer;

import blueduck.mysticalpumpkins.client.model.SludgeModel;
import blueduck.mysticalpumpkins.entity.SludgeEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SludgeGelLayer extends LayerRenderer<SludgeEntity, SludgeModel> {
	private final SludgeModel slimeModel = new SludgeModel(0);

	public SludgeGelLayer(IEntityRenderer<SludgeEntity, SludgeModel> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, SludgeEntity living, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!living.isInvisible()) {
			this.getEntityModel().copyModelAttributesTo(this.slimeModel);
			this.slimeModel.setLivingAnimations(living, limbSwing, limbSwingAmount, partialTicks);
			this.slimeModel.setRotationAngles(living, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityTranslucent(this.getEntityTexture(living)));
			this.slimeModel.render(matrixStackIn, ivertexbuilder, packedLightIn, LivingRenderer.getPackedOverlay(living, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}