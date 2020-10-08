package blueduck.mysticalpumpkins.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;

public class LuminousMysticalPumpkinBlock extends MysticalPumpkinBlock {
    int l;
    public LuminousMysticalPumpkinBlock(int light) {
        l = light;
    }

    public int getLightValue() {
        return l;
    }
}
