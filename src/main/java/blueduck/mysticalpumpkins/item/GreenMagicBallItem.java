package blueduck.mysticalpumpkins.item;

import blueduck.mysticalpumpkins.entity.GreenMagicBallEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;

public class GreenMagicBallItem extends Item {

	public GreenMagicBallItem(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
		world.addEntity(new GreenMagicBallEntity(context.getPlayer(), 0, 0, 0, world));
		context.getItem().shrink(1);
		return ActionResultType.func_233537_a_(world.isRemote);
	}
}
