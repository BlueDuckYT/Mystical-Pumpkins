package blueduck.mysticalpumpkins.block;

import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class PumpkinMineBlock extends PressurePlateBlock {
    public PumpkinMineBlock() {
        super(Sensitivity.MOBS, Properties.from(Blocks.STONE_PRESSURE_PLATE));
    }

    protected void updateState(World worldIn, BlockPos pos, BlockState state, int oldRedstoneStrength) {
        int i = this.computeRedstoneStrength(worldIn, pos);
        boolean flag = oldRedstoneStrength > 0;
        boolean flag1 = i > 0;
        if (oldRedstoneStrength != i) {
            BlockState blockstate = this.setRedstoneStrength(state, i);
            worldIn.setBlockState(pos, blockstate, 2);
            this.updateNeighbors(worldIn, pos);
            worldIn.markBlockRangeForRenderUpdate(pos, state, blockstate);
        }

        if (!flag1 && flag) {
            this.playClickOffSound(worldIn, pos);
            worldIn.createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 5, Explosion.Mode.BREAK);
        } else if (flag1 && !flag) {
            this.playClickOnSound(worldIn, pos);
        }

        if (flag1) {
            worldIn.getPendingBlockTicks().scheduleTick(new BlockPos(pos), this, this.getPoweredDuration());
        }
    }
}
