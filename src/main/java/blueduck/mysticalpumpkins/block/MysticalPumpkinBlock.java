package blueduck.mysticalpumpkins.block;

import net.minecraft.block.Blocks;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.FourWayBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.util.Direction;

public class MysticalPumpkinBlock extends HorizontalBlock {

    public MysticalPumpkinBlock() {
        super(Properties.from(Blocks.CARVED_PUMPKIN));
    }
}
