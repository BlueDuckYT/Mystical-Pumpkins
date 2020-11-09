package blueduck.mysticalpumpkins.block;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class ChromaticPumpkinBlock extends RedstoneLampBlock {

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public ChromaticPumpkinBlock() {
        super(Properties.from(Blocks.CARVED_PUMPKIN));
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (state.get(LIT) && !worldIn.isBlockPowered(pos)) {
            worldIn.setBlockState(pos, state.func_235896_a_(LIT), 2);
        }
        else if (state.get(LIT).booleanValue()) {
            for (LivingEntity e : worldIn.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos.getX() - 16, pos.getY() - 16, pos.getZ() - 16, pos.getX() + 16, pos.getY() + 16, pos.getZ() + 16))) {
                e.addPotionEffect(new EffectInstance(Effects.GLOWING, 20, 0));
            }
        }


    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite()).with(LIT, Boolean.valueOf(context.getWorld().isBlockPowered(context.getPos())));
    }

    public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT).add(FACING);
    }
}
