package blueduck.mysticalpumpkins.block;

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

    @Nullable
    private BlockPattern snowmanBasePattern;
    @Nullable
    private BlockPattern snowmanPattern;


    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!oldState.isIn(state.getBlock())) {
            this.trySpawnGolem(worldIn, pos);
        }
    }
    private void trySpawnGolem(World p_196358_1_, BlockPos pos) {

        if (p_196358_1_.getBlockState(pos.down()).isStickyBlock()) {
            SnowGolemEntity snowgolementity = EntityType.SNOW_GOLEM.create(p_196358_1_);
            BlockPos blockpos1 = pos.down();
            snowgolementity.setLocationAndAngles((double) blockpos1.getX() + 0.5D, (double) blockpos1.getY() + 0.05D, (double) blockpos1.getZ() + 0.5D, 0.0F, 0.0F);
            p_196358_1_.addEntity(snowgolementity);

            for (ServerPlayerEntity serverplayerentity : p_196358_1_.getEntitiesWithinAABB(ServerPlayerEntity.class, snowgolementity.getBoundingBox().grow(5.0D))) {
                CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayerentity, snowgolementity);
            }

        }
        p_196358_1_.setBlockState(pos, Blocks.AIR.getDefaultState());
        p_196358_1_.setBlockState(pos.down(), Blocks.AIR.getDefaultState());

    }

}
