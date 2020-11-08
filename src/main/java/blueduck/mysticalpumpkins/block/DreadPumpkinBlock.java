package blueduck.mysticalpumpkins.block;

import blueduck.mysticalpumpkins.entity.PumpklopsEntity;
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

public class DreadPumpkinBlock extends MysticalPumpkinBlock {

    @Nullable
    private BlockPattern snowmanBasePattern;
    @Nullable
    private BlockPattern snowmanPattern;


    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!oldState.isIn(state.getBlock())) {
            this.trySpawnGolem(worldIn, pos);
        }
    }
    private void trySpawnGolem(World world, BlockPos pos) {

        if (world.getBlockState(pos.down()).equals(Blocks.BONE_BLOCK.getDefaultState()) && world.getBlockState(pos.down(2)).equals(Blocks.BONE_BLOCK.getDefaultState())) {
            PumpklopsEntity snowgolementity = RegisterHandler.PUMPKLOPS.get().create(world);
            BlockPos blockpos1 = pos.down();
            snowgolementity.setLocationAndAngles((double) blockpos1.getX() + 0.5D, (double) blockpos1.getY() + 0.05D, (double) blockpos1.getZ() + 0.5D, 0.0F, 0.0F);
            world.addEntity(snowgolementity);
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            world.setBlockState(pos.down(), Blocks.AIR.getDefaultState());
            world.setBlockState(pos.down(2), Blocks.AIR.getDefaultState());

            for (ServerPlayerEntity serverplayerentity : world.getEntitiesWithinAABB(ServerPlayerEntity.class, snowgolementity.getBoundingBox().grow(5.0D))) {
                CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayerentity, snowgolementity);
            }

        }

    }

}
