package blueduck.mysticalpumpkins.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class NightVisionUnderwaterEffectPumpkinItem extends EffectPumpkinItem {
    public NightVisionUnderwaterEffectPumpkinItem(Block blockIn, Properties p_i48534_3_, Effect effectIn) {
        super(blockIn, p_i48534_3_, effectIn);
    }

    public void tick(ItemStack stack, World world, PlayerEntity player) {
        if (player.isInWater()) {
            player.addPotionEffect(new EffectInstance(effect, 20));
            player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 20));
        }
    }
}
