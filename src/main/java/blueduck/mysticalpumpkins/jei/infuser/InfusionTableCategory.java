package blueduck.mysticalpumpkins.jei.infuser;

import blueduck.mysticalpumpkins.registry.RegisterHandler;
import blueduck.mysticalpumpkins.tileentity.InfusionTableRecipe;
import blueduck.mysticalpumpkins.utils.SpecialConstants;
import com.google.common.cache.LoadingCache;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class InfusionTableCategory implements IRecipeCategory<InfusionTableRecipe> {

	private final IDrawable background;
	private final int regularCookTime = 200;
	private final IDrawable icon;
	private final String localizedName;
	private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;

	protected static final int inputSlot = 0;
	protected static final int essenceSlot = 1;
	protected static final int secondarySlot = 2;
	protected static final int outputSlot = 3;

	public InfusionTableCategory(IGuiHelper guiHelper) {
		this.background = guiHelper.createDrawable(SpecialConstants.PUMPKIN_INFUSER_TEX, 0, 0, 176, 166);
		this.icon = guiHelper.createDrawableIngredient(new ItemStack(RegisterHandler.INFUSION_TABLE.get()));
	}

	@Override
	public ResourceLocation getUid() {
		return new ResourceLocation(SpecialConstants.MODID, "infusion_table");
	}

	@Override
	public Class<? extends InfusionTableRecipe> getRecipeClass() {
		return InfusionTableRecipe.class;
	}

	@Override
	public String getTitle() {
		return null;
	}

	@Override
	public IDrawable getBackground() {
		return null;
	}

	@Override
	public IDrawable getIcon() {
		return null;
	}

	@Override
	public void setIngredients(InfusionTableRecipe recipe, IIngredients ingredients) {

	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, InfusionTableRecipe recipe, IIngredients ingredients) {

	}
}
