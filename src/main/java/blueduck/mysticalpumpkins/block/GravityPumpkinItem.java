package blueduck.mysticalpumpkins.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class GravityPumpkinItem extends MysticalPumpkinItem {



    public GravityPumpkinItem(Block blockIn, Properties builderIn) {
        super(blockIn, builderIn);
    }

    public void tick(ItemStack stack, World world, PlayerEntity player) {
        if (player.getPersistentData().getBoolean("isPressingSpace")) {
            player.addPotionEffect(new EffectInstance(Effects.LEVITATION, 5, 7));
            //player.setMotion(player.getLookVec());
        }

    }
}
