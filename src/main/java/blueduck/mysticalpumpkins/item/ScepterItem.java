package blueduck.mysticalpumpkins.item;

import blueduck.mysticalpumpkins.registry.RegisterHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.AbstractSpawner;

import java.util.Objects;

import net.minecraft.item.Item.Properties;

public class ScepterItem extends Item {
    public ScepterItem(Properties properties) {
        super(properties);
    }
    @Override
    public int getItemEnchantability() {
        return 15;
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        if (!(world instanceof ServerWorld)) {
            return ActionResultType.SUCCESS;
        } else {
            ItemStack itemstack = context.getItem();
            BlockPos blockpos = context.getPos();
            Direction direction = context.getFace();
            BlockState blockstate = world.getBlockState(blockpos);
            if (blockstate.isIn(Blocks.SPAWNER)) {
                TileEntity tileentity = world.getTileEntity(blockpos);
                if (tileentity instanceof MobSpawnerTileEntity) {
                    AbstractSpawner abstractspawner = ((MobSpawnerTileEntity)tileentity).getSpawnerBaseLogic();
                    EntityType<?> entitytype1 = RegisterHandler.FRIENDLY_PUMPKINION.get();
                    abstractspawner.setEntityType(entitytype1);
                    tileentity.markDirty();
                    world.notifyBlockUpdate(blockpos, blockstate, blockstate, 3);
                    context.getPlayer().getCooldownTracker().setCooldown(itemstack.getItem(), 400);
                    if (EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, itemstack) == 0 || (EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, itemstack) > 0 && context.getPlayer().getEntityWorld().getRandom().nextDouble() < (EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, itemstack)/3))) {
                        itemstack.damageItem(1, context.getPlayer(), (entity) -> {
                            entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                        });
                    }

                    return ActionResultType.CONSUME;
                }
            }

            BlockPos blockpos1;
            if (blockstate.getCollisionShape(world, blockpos).isEmpty()) {
                blockpos1 = blockpos;
            } else {
                blockpos1 = blockpos.offset(direction);
            }

            EntityType<?> entitytype = RegisterHandler.FRIENDLY_PUMPKINION.get();
            if (entitytype.spawn((ServerWorld)world, itemstack, context.getPlayer(), blockpos1, SpawnReason.BUCKET, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP) != null) {
                context.getPlayer().getCooldownTracker().setCooldown(itemstack.getItem(), 400);
                if (EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, itemstack) == 0 || (EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, itemstack) > 0 && context.getPlayer().getEntityWorld().getRandom().nextDouble() < (EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, itemstack)/3))) {
                    itemstack.damageItem(1, context.getPlayer(), (entity) -> {
                        entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                    });
                }
            }

            return ActionResultType.CONSUME;
        }
    }
}
