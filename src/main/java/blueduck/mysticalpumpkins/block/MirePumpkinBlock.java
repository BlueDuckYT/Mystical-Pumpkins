package blueduck.mysticalpumpkins.block;

import blueduck.mysticalpumpkins.entity.SludgeEntity;
import blueduck.mysticalpumpkins.registry.RegisterHandler;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MirePumpkinBlock extends MysticalPumpkinBlock {



    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!oldState.isIn(state.getBlock())) {
            this.trySpawnGolem(worldIn, pos);
        }
    }
    private void trySpawnGolem(World world, BlockPos pos) {

        if (world.getBlockState(pos.down()).isStickyBlock()) {
            SludgeEntity snowgolementity = RegisterHandler.PUMPKIN_SLUDGE.get().create(world);
            BlockPos blockpos1 = pos.down();
            snowgolementity.setLocationAndAngles((double) blockpos1.getX() + 0.5D, (double) blockpos1.getY() + 0.05D, (double) blockpos1.getZ() + 0.5D, 0.0F, 0.0F);
            world.addEntity(snowgolementity);

            for (ServerPlayerEntity serverplayerentity : world.getEntitiesWithinAABB(ServerPlayerEntity.class, snowgolementity.getBoundingBox().grow(5.0D))) {
                CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayerentity, snowgolementity);
            }

        }
        world.setBlockState(pos, Blocks.AIR.getDefaultState());
        world.setBlockState(pos.down(), Blocks.AIR.getDefaultState());

    }

}
