// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.12.2 or 1.15.2 (same format for both) for entity models animated with GeckoLib
// Paste this class into your mod and follow the documentation for GeckoLib to use animations. You can find the documentation here: https://github.com/bernie-g/geckolib
// Blockbench plugin created by Gecko
package blueduck.mysticalpumpkins.client.model;

import blueduck.mysticalpumpkins.entity.DragourdEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib.animation.model.AnimatedEntityModel;
import software.bernie.geckolib.animation.render.AnimatedModelRenderer;

public class PumpkinionModel extends AnimatedEntityModel<DragourdEntity> {

    private final AnimatedModelRenderer main;
	private final AnimatedModelRenderer body;
	private final AnimatedModelRenderer stalk;
	private final AnimatedModelRenderer lleg;
	private final AnimatedModelRenderer rleg;

    public PumpkinionModel()
    {
        textureWidth = 64;
    textureHeight = 64;
    main = new AnimatedModelRenderer(this);
		main.setRotationPoint(0.0F, 9.0F, 0.0F);
		
		main.setModelRendererName("main");
		this.registerModelRenderer(main);

		body = new AnimatedModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		main.addChild(body);
		body.setTextureOffset(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
		body.setModelRendererName("body");
		this.registerModelRenderer(body);

		stalk = new AnimatedModelRenderer(this);
		stalk.setRotationPoint(0.0F, -8.0F, 0.0F);
		body.addChild(stalk);
		setRotationAngle(stalk, -0.3927F, 0.0F, 0.0F);
		stalk.setTextureOffset(16, 32).addBox(-1.5F, -4.0F, -1.5F, 3.0F, 5.0F, 3.0F, 0.0F, false);
		stalk.setModelRendererName("stalk");
		this.registerModelRenderer(stalk);

		lleg = new AnimatedModelRenderer(this);
		lleg.setRotationPoint(4.0F, 7.0F, 0.0F);
		main.addChild(lleg);
		lleg.setTextureOffset(0, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);
		lleg.setTextureOffset(28, 32).addBox(-2.0F, 5.0F, -4.0F, 4.0F, 3.0F, 2.0F, 0.0F, false);
		lleg.setModelRendererName("lleg");
		this.registerModelRenderer(lleg);

		rleg = new AnimatedModelRenderer(this);
		rleg.setRotationPoint(-4.0F, 7.0F, 0.0F);
		main.addChild(rleg);
		rleg.setTextureOffset(0, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, true);
		rleg.setTextureOffset(28, 32).addBox(-2.0F, 5.0F, -4.0F, 4.0F, 3.0F, 2.0F, 0.0F, true);
		rleg.setModelRendererName("rleg");
		this.registerModelRenderer(rleg);

    this.rootBones.add(main);
  }


    @Override
    public ResourceLocation getAnimationFileLocation()
    {
        return new ResourceLocation("MODID", "animations/ANIMATIONFILE.json");
    }
}