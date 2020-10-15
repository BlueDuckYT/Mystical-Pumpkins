package blueduck.mysticalpumpkins.client.model;

import blueduck.mysticalpumpkins.entity.SludgeEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SludgeModel extends SegmentedModel<SludgeEntity> {
    private final ModelRenderer slimeBodies;
    private final ModelRenderer slimeRightEye;
    private final ModelRenderer slimeLeftEye;
    private final ModelRenderer slimeMouth;

    public SludgeModel(int slimeBodyTexOffY) {
        this.slimeBodies = new ModelRenderer(this, 0, slimeBodyTexOffY);
        this.slimeRightEye = new ModelRenderer(this, 32, 0);
        this.slimeLeftEye = new ModelRenderer(this, 32, 4);
        this.slimeMouth = new ModelRenderer(this, 32, 8);
        if (slimeBodyTexOffY > 0) {
            this.slimeBodies.addBox(-3.0F, 17.0F, -3.0F, 6.0F, 6.0F, 6.0F);
            this.slimeRightEye.addBox(-3.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F);
            this.slimeLeftEye.addBox(1.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F);
            this.slimeMouth.addBox(0.0F, 21.0F, -3.5F, 1.0F, 1.0F, 1.0F);
        } else {
            this.slimeBodies.addBox(-4.0F, 16.0F, -4.0F, 8.0F, 8.0F, 8.0F);
        }

    }

    @Override
    public void setRotationAngles(SludgeEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.slimeBodies, this.slimeRightEye, this.slimeLeftEye, this.slimeMouth);
    }
}