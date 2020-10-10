package blueduck.mysticalpumpkins.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class WrathPumpkinItem extends MysticalPumpkinItem {



    public WrathPumpkinItem(Block blockIn, Properties p_i48534_3_) {
        super(blockIn, p_i48534_3_);
    }

    public void tick(ItemStack stack, World world, PlayerEntity player) {
        if (player.getHealth() <= 6) {
            player.addPotionEffect(new EffectInstance(Effects.STRENGTH, 20, 2));
        }
        else if (player.getHealth() <= 10) {
            player.addPotionEffect(new EffectInstance(Effects.STRENGTH, 20, 1));
        }
        else if (player.getHealth() <= 20) {
            player.addPotionEffect(new EffectInstance(Effects.STRENGTH, 20, 0));
        }
    }
}
