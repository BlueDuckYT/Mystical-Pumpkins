package blueduck.mysticalpumpkins.item;

import blueduck.mysticalpumpkins.entity.GreenMagicBallEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class GreenMagicBallItem extends Item {

	public GreenMagicBallItem(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		worldIn.addEntity(new GreenMagicBallEntity(playerIn, 0, 0, 0, worldIn));
		playerIn.getActiveItemStack().shrink(1);
		return ActionResult.resultPass(playerIn.getHeldItem(handIn));
	}
}
