package blueduck.mysticalpumpkins.jei;

import blueduck.mysticalpumpkins.MysticalPumpkinsMod;
import blueduck.mysticalpumpkins.jei.infusing.InfusingRecipeCategory;
import blueduck.mysticalpumpkins.registry.InfuserRecipeRegistry;
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
		registration.addRecipes(InfuserRecipeRegistry.recipes, this.getPluginUid());
	}

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(MysticalPumpkinsMod.MODID, "jei_plugin");
	}
}
