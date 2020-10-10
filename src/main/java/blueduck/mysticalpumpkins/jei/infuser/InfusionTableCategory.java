package blueduck.mysticalpumpkins.jei.infuser;

import blueduck.mysticalpumpkins.registry.RegisterHandler;
import blueduck.mysticalpumpkins.tileentity.InfusionTableRecipe;
import blueduck.mysticalpumpkins.utils.SpecialConstants;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.util.Translator;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class InfusionTableCategory implements IRecipeCategory<InfusionTableRecipe> {

	private final IDrawable background;
	private final IDrawable icon;
	private final String localizedName;
	private final IDrawableAnimated arrow;

	protected static final int inputSlot = 0;
	protected static final int essenceSlot = 1;
	protected static final int secondarySlot = 2;
	protected static final int outputSlot = 3;

	public InfusionTableCategory(IGuiHelper guiHelper) {
		this.background = guiHelper.createDrawable(SpecialConstants.PUMPKIN_INFUSER_JEI, 0, 0, 176, 76);
		this.icon = guiHelper.createDrawableIngredient(new ItemStack(RegisterHandler.INFUSION_TABLE.get()));
		this.localizedName = Translator.translateToLocal("gui.jei.category.pumpkin_infusion_table");
		int totalCookTime = 200;
		this.arrow = guiHelper.drawableBuilder(SpecialConstants.PUMPKIN_INFUSER_TEX, 176, 0, 70, 17)
				                    .buildAnimated(totalCookTime, IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public ResourceLocation getUid() {
		return SpecialConstants.INFUSION_TABLE_UID;
	}

	@Override
	public Class<? extends InfusionTableRecipe> getRecipeClass() {
		return InfusionTableRecipe.class;
	}

	@Override
	public String getTitle() {
		return localizedName;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public void draw(InfusionTableRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
		arrow.draw(matrixStack, 71, 30);
	}

	@Override
	public void setIngredients(InfusionTableRecipe recipe, IIngredients ingredients) {
		NonNullList<Ingredient> inputs = NonNullList.create();
		inputs.add(Ingredient.fromStacks(recipe.getInput()));
		inputs.add(Ingredient.fromStacks(new ItemStack(RegisterHandler.PUMPKIN_ESSENCE.get(), recipe.getEssenceAmount())));
		inputs.add(Ingredient.fromStacks(recipe.getSecondary()));
		ingredients.setInputIngredients(inputs);
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, InfusionTableRecipe recipe, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(inputSlot, true, 53, 12);
		guiItemStacks.init(essenceSlot, true, 17, 30);
		guiItemStacks.init(secondarySlot, true, 53, 48);
		guiItemStacks.init(outputSlot, false, 143, 30);

		guiItemStacks.set(ingredients);
	}
}
