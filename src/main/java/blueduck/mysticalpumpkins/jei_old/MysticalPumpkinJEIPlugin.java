package blueduck.mysticalpumpkins.jei_old;

import blueduck.mysticalpumpkins.jei_old.infusing.InfusingRecipeCategory;
import blueduck.mysticalpumpkins.registry.InfusionTableRecipeRegistry;
import blueduck.mysticalpumpkins.utils.SpecialConstants;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class MysticalPumpkinJEIPlugin implements IModPlugin {

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new InfusingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		registration.addRecipes(InfusionTableRecipeRegistry.recipes, this.getPluginUid());
	}

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(SpecialConstants.MODID, "jei_plugin");
	}
}
