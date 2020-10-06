package blueduck.mysticalpumpkins.block;

import blueduck.mysticalpumpkins.registry.RegisterHandler;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEquipable;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

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
