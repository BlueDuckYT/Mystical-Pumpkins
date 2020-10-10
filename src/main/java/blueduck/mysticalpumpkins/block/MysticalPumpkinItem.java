package blueduck.mysticalpumpkins.block;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SkullItem;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MysticalPumpkinItem extends BlockItem {


    public MysticalPumpkinItem(Block blockIn, Properties p_i48534_3_) {
        super(blockIn, p_i48534_3_);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        this.tick(stack, world, player);
    }

    public void tick(ItemStack stack, World world, PlayerEntity player) {

    }

    public EquipmentSlotType getEquipmentSlot() {
        return EquipmentSlotType.HEAD;
    }
    public EquipmentSlotType.Group getSlotType() {
        return EquipmentSlotType.Group.ARMOR;
    }

    public boolean equals(Object object) {
        return object instanceof SkullItem;
    }
    public boolean canEquip(ItemStack stack, EquipmentSlotType armorType, Entity entity)
    {
        return armorType == EquipmentSlotType.HEAD;
    }
    @OnlyIn(Dist.CLIENT)
    public void renderHelmetOverlay(ItemStack stack, PlayerEntity player, int width, int height, float partialTicks)
    {

    }

}
