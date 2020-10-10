package blueduck.mysticalpumpkins.jei;

import blueduck.mysticalpumpkins.client.gui.InfusionTableScreen;
import blueduck.mysticalpumpkins.container.InfusionTableContainer;
import blueduck.mysticalpumpkins.jei.infuser.InfusionTableCategory;
import blueduck.mysticalpumpkins.jei.infuser.InfusionTableTransferHandler;
import blueduck.mysticalpumpkins.registry.InfusionTableRecipeRegistry;
import blueduck.mysticalpumpkins.registry.RegisterHandler;
import blueduck.mysticalpumpkins.utils.SpecialConstants;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.*;
import mezz.jei.transfer.BasicRecipeTransferInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class MysticalPumpkinsJeiPlugin implements IModPlugin {

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(SpecialConstants.MODID, "mystical_pumpkins");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		IJeiHelpers jeiHelpers = registration.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registration.addRecipeCategories(new InfusionTableCategory(guiHelper));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		registration.addRecipes(InfusionTableRecipeRegistry.recipes, SpecialConstants.INFUSION_TABLE_UID);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(InfusionTableScreen.class, 69, 29, 73   , 20, SpecialConstants.INFUSION_TABLE_UID);
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		InfusionTableTransferHandler handler = new InfusionTableTransferHandler(registration.getJeiHelpers().getStackHelper(), registration.getTransferHelper(), new BasicRecipeTransferInfo<>(InfusionTableContainer.class, SpecialConstants.INFUSION_TABLE_UID, 0, 3, 4, 36));
		registration.addRecipeTransferHandler(handler, SpecialConstants.INFUSION_TABLE_UID);
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(RegisterHandler.INFUSION_TABLE.get()), SpecialConstants.INFUSION_TABLE_UID);
	}
}
