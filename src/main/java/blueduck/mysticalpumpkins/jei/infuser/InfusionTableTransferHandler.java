package blueduck.mysticalpumpkins.jei.infuser;

import blueduck.mysticalpumpkins.container.InfusionTableContainer;
import blueduck.mysticalpumpkins.tileentity.InfusionTableRecipe;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.entity.player.PlayerEntity;

public class InfusionTableTransferHandler implements IRecipeTransferHandler<InfusionTableContainer> {

	public InfusionTableTransferHandler() {}

	@Override
	public IRecipeTransferError transferRecipe(InfusionTableContainer container, Object recipe, IRecipeLayout recipeLayout, PlayerEntity player, boolean maxTransfer, boolean doTransfer) {
		InfusionTableRecipe infusionTableRecipe = (InfusionTableRecipe) recipe;
		recipeLayout.getItemStacks();
		return null;
	}

	@Override
	public Class<InfusionTableContainer> getContainerClass() {
		return InfusionTableContainer.class;
	}
}
