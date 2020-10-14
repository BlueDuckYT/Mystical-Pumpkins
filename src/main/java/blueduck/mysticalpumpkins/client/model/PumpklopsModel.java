// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.12.2 or 1.15.2 (same format for both) for entity models animated with GeckoLib
// Paste this class into your mod and follow the documentation for GeckoLib to use animations. You can find the documentation here: https://github.com/bernie-g/geckolib
// Blockbench plugin created by Gecko
package blueduck.mysticalpumpkins.client.model;

import blueduck.mysticalpumpkins.entity.PumpklopsEntity;
import blueduck.mysticalpumpkins.utils.SpecialConstants;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib.animation.model.AnimatedEntityModel;
import software.bernie.geckolib.animation.render.AnimatedModelRenderer;

public class PumpklopsModel extends AnimatedEntityModel<PumpklopsEntity> {

    private final AnimatedModelRenderer body;
	private final AnimatedModelRenderer heart;
	private final AnimatedModelRenderer left_arm;
	private final AnimatedModelRenderer right_arm;
	private final AnimatedModelRenderer scepter;
	private final AnimatedModelRenderer left_leg;
	private final AnimatedModelRenderer right_leg;
	private final AnimatedModelRenderer head;
	private final AnimatedModelRenderer cape;

    public PumpklopsModel()
    {
        textureWidth = 64;
    textureHeight = 64;
    body = new AnimatedModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.setTextureOffset(28, 28).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
		body.setModelRendererName("body");
		this.registerModelRenderer(body);

		heart = new AnimatedModelRenderer(this);
		heart.setRotationPoint(0.0F, 4.0F, 0.0F);
		body.addChild(heart);
		heart.setTextureOffset(0, 46).addBox(-2.0F, -2.5F, -1.5F, 4.0F, 6.0F, 3.0F, 0.0F, false);
		heart.setModelRendererName("heart");
		this.registerModelRenderer(heart);

		left_arm = new AnimatedModelRenderer(this);
		left_arm.setRotationPoint(5.0F, 1.0F, 0.0F);
		body.addChild(left_arm);
		left_arm.setTextureOffset(0, 32).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, true);
		left_arm.setModelRendererName("left_arm");
		this.registerModelRenderer(left_arm);

		right_arm = new AnimatedModelRenderer(this);
		right_arm.setRotationPoint(-5.0F, 1.0F, 0.0F);
		body.addChild(right_arm);
		setRotationAngle(right_arm, -0.1745F, 0.0F, 0.0F);
		right_arm.setTextureOffset(0, 32).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
		right_arm.setModelRendererName("right_arm");
		this.registerModelRenderer(right_arm);

		scepter = new AnimatedModelRenderer(this);
		scepter.setRotationPoint(0.0F, 10.0F, 0.0F);
		right_arm.addChild(scepter);
		scepter.setTextureOffset(24, 4).addBox(-0.5F, -1.0F, -9.0F, 1.0F, 1.0F, 12.0F, 0.0F, false);
		scepter.setTextureOffset(24, 17).addBox(-2.0F, -2.5F, -8.0F, 4.0F, 4.0F, 3.0F, 0.0F, false);
		scepter.setModelRendererName("scepter");
		this.registerModelRenderer(scepter);

		left_leg = new AnimatedModelRenderer(this);
		left_leg.setRotationPoint(2.0F, 12.0F, 0.1F);
		body.addChild(left_leg);
		left_leg.setTextureOffset(8, 0).addBox(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, 0.0F, false);
		left_leg.setModelRendererName("left_leg");
		this.registerModelRenderer(left_leg);

		right_leg = new AnimatedModelRenderer(this);
		right_leg.setRotationPoint(-2.0F, 12.0F, 0.1F);
		body.addChild(right_leg);
		right_leg.setTextureOffset(8, 0).addBox(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, 0.0F, true);
		right_leg.setModelRendererName("right_leg");
		this.registerModelRenderer(right_leg);

		head = new AnimatedModelRenderer(this);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(head);
		head.setTextureOffset(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
		head.setModelRendererName("head");
		this.registerModelRenderer(head);

		cape = new AnimatedModelRenderer(this);
		cape.setRotationPoint(0.0F, 0.0F, 2.0F);
		body.addChild(cape);
		setRotationAngle(cape, 0.1745F, 0.0F, 0.0F);
		cape.setTextureOffset(16, 0).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 16.0F, 0.0F, 0.0F, false);
		cape.setModelRendererName("cape");
		this.registerModelRenderer(cape);

    this.rootBones.add(body);
  }


    @Override
    public ResourceLocation getAnimationFileLocation()
    {
        return new ResourceLocation(SpecialConstants.MODID, "animations/pumpklops.json");
    }
}