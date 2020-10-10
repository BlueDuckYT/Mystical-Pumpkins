// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.12.2 or 1.15.2 (same format for both) for entity models animated with GeckoLib
// Paste this class into your mod and follow the documentation for GeckoLib to use animations. You can find the documentation here: https://github.com/bernie-g/geckolib
// Blockbench plugin created by Gecko
package blueduck.mysticalpumpkins.client.model;

import blueduck.mysticalpumpkins.entity.DragourdEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib.animation.model.AnimatedEntityModel;
import software.bernie.geckolib.animation.render.AnimatedModelRenderer;

public class DragourdModel extends AnimatedEntityModel<DragourdEntity> {

    private final AnimatedModelRenderer body;
	private final AnimatedModelRenderer neck;
	private final AnimatedModelRenderer head;
	private final AnimatedModelRenderer jaw;
	private final AnimatedModelRenderer lhorn;
	private final AnimatedModelRenderer rhorn;
	private final AnimatedModelRenderer leg1;
	private final AnimatedModelRenderer leg4;
	private final AnimatedModelRenderer leg2;
	private final AnimatedModelRenderer leg3;
	private final AnimatedModelRenderer tail1;
	private final AnimatedModelRenderer tail2;
	private final AnimatedModelRenderer tail3;

    public DragourdModel()
    {
        textureWidth = 96;
    textureHeight = 96;
    body = new AnimatedModelRenderer(this);
		body.setRotationPoint(0.0F, 11.0F, 0.0F);
		body.setTextureOffset(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
		body.setTextureOffset(8, 8).addBox(0.0F, -10.0F, -7.0F, 0.0F, 2.0F, 2.0F, 0.0F, false);
		body.setTextureOffset(0, 6).addBox(0.0F, -12.0F, -3.0F, 0.0F, 4.0F, 2.0F, 0.0F, false);
		body.setTextureOffset(4, 6).addBox(0.0F, -11.0F, 1.0F, 0.0F, 3.0F, 2.0F, 0.0F, false);
		body.setTextureOffset(8, 8).addBox(0.0F, -10.0F, 5.0F, 0.0F, 2.0F, 2.0F, 0.0F, false);
		body.setModelRendererName("body");
		this.registerModelRenderer(body);

		neck = new AnimatedModelRenderer(this);
		neck.setRotationPoint(0.0F, -1.0F, -8.0F);
		body.addChild(neck);
		setRotationAngle(neck, -0.3927F, 0.0F, 0.0F);
		neck.setTextureOffset(30, 38).addBox(-4.0F, -4.0F, -9.0F, 8.0F, 8.0F, 12.0F, 0.0F, false);
		neck.setModelRendererName("neck");
		this.registerModelRenderer(neck);

		head = new AnimatedModelRenderer(this);
		head.setRotationPoint(0.0F, 0.0F, -9.0F);
		neck.addChild(head);
		setRotationAngle(head, 0.5236F, 0.0F, 0.0F);
		head.setTextureOffset(0, 32).addBox(-4.5F, -6.0F, -9.0F, 9.0F, 6.0F, 12.0F, 0.0F, false);
		head.setModelRendererName("head");
		this.registerModelRenderer(head);

		jaw = new AnimatedModelRenderer(this);
		jaw.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(jaw);
		jaw.setTextureOffset(48, 0).addBox(-4.5F, 0.0F, -9.0F, 9.0F, 3.0F, 12.0F, -0.01F, false);
		jaw.setModelRendererName("jaw");
		this.registerModelRenderer(jaw);

		lhorn = new AnimatedModelRenderer(this);
		lhorn.setRotationPoint(4.0F, -6.0F, 3.0F);
		head.addChild(lhorn);
		setRotationAngle(lhorn, 0.2618F, 0.2618F, 0.0F);
		lhorn.setTextureOffset(57, 58).addBox(-2.0F, -0.5F, -1.0F, 3.0F, 3.0F, 6.0F, 0.0F, false);
		lhorn.setModelRendererName("lhorn");
		this.registerModelRenderer(lhorn);

		rhorn = new AnimatedModelRenderer(this);
		rhorn.setRotationPoint(-4.0F, -6.0F, 3.0F);
		head.addChild(rhorn);
		setRotationAngle(rhorn, 0.2618F, -0.2618F, 0.0F);
		rhorn.setTextureOffset(57, 58).addBox(-1.0F, -0.5F, -1.0F, 3.0F, 3.0F, 6.0F, 0.0F, false);
		rhorn.setModelRendererName("rhorn");
		this.registerModelRenderer(rhorn);

		leg1 = new AnimatedModelRenderer(this);
		leg1.setRotationPoint(6.0F, 8.0F, -6.0F);
		body.addChild(leg1);
		leg1.setTextureOffset(0, 0).addBox(-2.0F, 0.0F, -1.0F, 3.0F, 5.0F, 3.0F, 0.0F, false);
		leg1.setModelRendererName("leg1");
		this.registerModelRenderer(leg1);

		leg4 = new AnimatedModelRenderer(this);
		leg4.setRotationPoint(6.0F, 8.0F, 5.0F);
		body.addChild(leg4);
		leg4.setTextureOffset(0, 0).addBox(-2.0F, 0.0F, -1.0F, 3.0F, 5.0F, 3.0F, 0.0F, false);
		leg4.setModelRendererName("leg4");
		this.registerModelRenderer(leg4);

		leg2 = new AnimatedModelRenderer(this);
		leg2.setRotationPoint(-6.0F, 8.0F, -6.0F);
		body.addChild(leg2);
		leg2.setTextureOffset(0, 0).addBox(-1.0F, 0.0F, -1.0F, 3.0F, 5.0F, 3.0F, 0.0F, true);
		leg2.setModelRendererName("leg2");
		this.registerModelRenderer(leg2);

		leg3 = new AnimatedModelRenderer(this);
		leg3.setRotationPoint(-6.0F, 8.0F, 5.0F);
		body.addChild(leg3);
		leg3.setTextureOffset(0, 0).addBox(-1.0F, 0.0F, -1.0F, 3.0F, 5.0F, 3.0F, 0.0F, true);
		leg3.setModelRendererName("leg3");
		this.registerModelRenderer(leg3);

		tail1 = new AnimatedModelRenderer(this);
		tail1.setRotationPoint(0.0F, 0.0F, 8.0F);
		body.addChild(tail1);
		setRotationAngle(tail1, -0.2618F, 0.0F, 0.0F);
		tail1.setTextureOffset(0, 50).addBox(-5.0F, -6.0F, -3.0F, 10.0F, 12.0F, 8.0F, 0.0F, false);
		tail1.setTextureOffset(8, 8).addBox(0.0F, -8.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, false);
		tail1.setTextureOffset(8, 8).addBox(0.0F, -8.0F, 3.0F, 0.0F, 2.0F, 2.0F, 0.0F, false);
		tail1.setModelRendererName("tail1");
		this.registerModelRenderer(tail1);

		tail2 = new AnimatedModelRenderer(this);
		tail2.setRotationPoint(0.0F, 0.0F, 5.0F);
		tail1.addChild(tail2);
		setRotationAngle(tail2, 0.0873F, 0.0F, 0.0F);
		tail2.setTextureOffset(36, 58).addBox(-3.0F, -4.0F, -2.0F, 6.0F, 8.0F, 9.0F, 0.0F, false);
		tail2.setTextureOffset(8, 8).addBox(0.0F, -6.0F, 2.0F, 0.0F, 2.0F, 2.0F, 0.0F, false);
		tail2.setTextureOffset(0, 0).addBox(0.0F, -5.0F, 6.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
		tail2.setModelRendererName("tail2");
		this.registerModelRenderer(tail2);

		tail3 = new AnimatedModelRenderer(this);
		tail3.setRotationPoint(0.0F, 0.0F, 7.0F);
		tail2.addChild(tail3);
		setRotationAngle(tail3, 0.0873F, 0.0F, 0.0F);
		tail3.setTextureOffset(55, 23).addBox(-2.0F, -2.5F, -1.0F, 4.0F, 5.0F, 9.0F, 0.0F, false);
		tail3.setModelRendererName("tail3");
		this.registerModelRenderer(tail3);


    this.rootBones.add(body);
  }

    @Override
    public ResourceLocation getAnimationFileLocation()
    {
        return new ResourceLocation("mystical_pumpkins", "animations/dragourd.json");
    }
}