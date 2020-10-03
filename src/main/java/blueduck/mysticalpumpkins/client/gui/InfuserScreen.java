package blueduck.mysticalpumpkins.client.gui;

import blueduck.mysticalpumpkins.MysticalPumpkinsMod;
import blueduck.mysticalpumpkins.container.InfuserContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class InfuserScreen extends ContainerScreen<InfuserContainer> {

	private static final ResourceLocation PUMPKIN_INFUSER_TEX = new ResourceLocation(MysticalPumpkinsMod.MODID, "textures/gui/container/pinfuser.png");

	public InfuserScreen(InfuserContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}

	//Setup GuiLeft and GuiTop (I guess)
	@Override
	protected void func_231160_c_() {
		super.func_231160_c_();
		this.field_238742_p_ = (this.xSize - this.field_230712_o_.func_238414_a_(this.field_230704_d_)) / 2;
	}

	// Draw Background
	@Override
	public void func_230430_a_(MatrixStack matrixStack, int mouseX, int mouseY, float p_230430_4_) {
		this.func_230446_a_(matrixStack);
		super.func_230430_a_(matrixStack, mouseX, mouseY, p_230430_4_);
		this.func_230459_a_(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void func_230450_a_(MatrixStack matrixStack, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.field_230706_i_.getTextureManager().bindTexture(PUMPKIN_INFUSER_TEX);
		int i = (this.field_230708_k_ - this.xSize) / 2;
		int j = (this.field_230709_l_ - this.ySize) / 2;
		this.func_238474_b_(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
		int actualTime = this.container.getTimeArray().get(0);
		int totalTime = this.container.getTimeArray().get(1);
		if (actualTime > 0) {
			int ratio = (28 * (1 - actualTime / totalTime));
			if (ratio > 0) {
				this.func_238474_b_(matrixStack, i + 97, j + 16, 176, 0, 9, ratio);
			}
		}
	}
}
