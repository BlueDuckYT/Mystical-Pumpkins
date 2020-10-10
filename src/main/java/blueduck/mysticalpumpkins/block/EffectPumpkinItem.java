package blueduck.mysticalpumpkins.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class EffectPumpkinItem extends MysticalPumpkinItem {

    public Effect effect;

    public EffectPumpkinItem(Block blockIn, Properties p_i48534_3_, Effect effectIn) {
        super(blockIn, p_i48534_3_);
        effect = effectIn;
    }

    public void tick(ItemStack stack, World world, PlayerEntity player) {
        player.addPotionEffect(new EffectInstance(effect, 20));
    }
}
