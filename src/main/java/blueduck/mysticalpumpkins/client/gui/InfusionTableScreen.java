package blueduck.mysticalpumpkins.client.gui;

import blueduck.mysticalpumpkins.container.InfusionTableContainer;
import blueduck.mysticalpumpkins.utils.SpecialConstants;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class InfusionTableScreen extends ContainerScreen<InfusionTableContainer> {

	public InfusionTableScreen(InfusionTableContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}

	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(stack);
		super.render(stack, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(stack, mouseX, mouseY);
	}

	@Override
	public void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(SpecialConstants.PUMPKIN_INFUSER_TEX);

		this.blit(matrixStack, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		int infusingScale = this.container.infusingScaled();
		this.blit(
				matrixStack,
				this.guiLeft + 71,  // x of starting replacing point (up)
				this.guiTop + 30,   // y of starting replacing point (left)
				176,                // x of thing that replaces (up)
				0,                  // y of thing that replaces (left)
				infusingScale,
				17
		);
	}
}
