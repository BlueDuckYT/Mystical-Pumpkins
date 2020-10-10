package blueduck.mysticalpumpkins.jei_old.infusing;

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
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class InfusingRecipeCategory implements IRecipeCategory<InfusionTableRecipe> {

	private static final int inputSlot = 0;
	private static final int essenceSlot = 1;
	private static final int secondarySlot = 2;
	private static final int outputSlot = 3;

	private final IDrawable background;
	private final IDrawable icon;
	private final IDrawable slotDrawable;
	private final String localizedName;
	private final IDrawableAnimated arrow;

	public InfusingRecipeCategory(IGuiHelper guiHelper) {
		ResourceLocation location = SpecialConstants.PUMPKIN_INFUSER_TEX;
		background = guiHelper
				             .drawableBuilder(location, 0, 0, 176, 166)
				             .addPadding(1, 0, 0, 50)
				             .build();
		icon = guiHelper.createDrawableIngredient(new ItemStack(RegisterHandler.INFUSION_TABLE.get()));
		localizedName = Translator.translateToLocal("gui.jei.category.infusing");

		arrow = guiHelper
				        .drawableBuilder(location, 176, 0, 71, 30)
				        .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);

		slotDrawable = guiHelper.getSlotDrawable();
	}

	@Override
	public ResourceLocation getUid() {
		return new ResourceLocation(SpecialConstants.MODID, "mystical_infusing");
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
	public void setIngredients(InfusionTableRecipe recipe, IIngredients iIngredients) {
		List<ItemStack> inputs = new ArrayList<>();
		inputs.add(recipe.getInput());
		inputs.add(new ItemStack(RegisterHandler.PUMPKIN_ESSENCE.get(), recipe.getEssenceAmount()));
		inputs.add(recipe.getSecondary());
		iIngredients.setInputs(VanillaTypes.ITEM, inputs);
		iIngredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
	}

	@Override
	public void draw(InfusionTableRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
		arrow.draw(matrixStack, 71, 30);
	}

	@Override
	public void setRecipe(IRecipeLayout iRecipeLayout, InfusionTableRecipe InfuserRecipe, IIngredients iIngredients) {
		IGuiItemStackGroup itemStacks = iRecipeLayout.getItemStacks();
		itemStacks.init(inputSlot, true, 53, 12);
		itemStacks.init(essenceSlot, true, 17, 30);
		itemStacks.init(secondarySlot, true, 53, 48);
		itemStacks.init(outputSlot, false, 143, 30);
		itemStacks.setBackground(outputSlot, slotDrawable);
		itemStacks.set(iIngredients);
	}
}
