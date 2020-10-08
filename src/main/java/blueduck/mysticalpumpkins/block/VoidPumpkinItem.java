package blueduck.mysticalpumpkins.block;

import com.sun.jna.platform.KeyboardUtils;
import net.java.games.input.Keyboard;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class VoidPumpkinItem extends MysticalPumpkinItem {



    public VoidPumpkinItem(Block blockIn, Properties p_i48534_3_) {
        super(blockIn, p_i48534_3_);
    }

    public void tick(ItemStack stack, World world, PlayerEntity player) {
        if (player.getPersistentData().getBoolean("isPressingSpace")) {
            player.addPotionEffect(new EffectInstance(Effects.LEVITATION, 20));
        }
        else {
            player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 20));
        }
    }
}
