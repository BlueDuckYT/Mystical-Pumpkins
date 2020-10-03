package blueduck.client.gui;

import blueduck.container.InfuserContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

// I kinda copied Brewing Stand Screen
// also bc I don't have a texture and brewing stand TE, container etc are similar to the infuser
public class InfuserScreen extends ContainerScreen<InfuserContainer> {

	private static final ResourceLocation PLACEHOLDER = new ResourceLocation("textures/gui/container/brewing_stand.png");

	public InfuserScreen(InfuserContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}

	@Override
	protected void func_231160_c_() {
		super.func_231160_c_();
		this.field_238742_p_ = (this.xSize - this.field_230712_o_.func_238414_a_(this.field_230704_d_)) / 2;
	}

	public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
		this.func_230446_a_(p_230430_1_);
		super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
		this.func_230459_a_(p_230430_1_, p_230430_2_, p_230430_3_);
	}

	@Override
	protected void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.field_230706_i_.getTextureManager().bindTexture(PLACEHOLDER);
		int i = (this.field_230708_k_ - this.xSize) / 2;
		int j = (this.field_230709_l_ - this.ySize) / 2;
		this.func_238474_b_(p_230450_1_, i, j, 0, 0, this.xSize, this.ySize);
		//TODO Understand render
	}
}
