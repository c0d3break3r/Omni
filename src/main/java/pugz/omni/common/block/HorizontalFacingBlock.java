package pugz.omni.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import pugz.omni.core.base.IBaseBlock;

public class HorizontalFacingBlock extends HorizontalBlock implements IBaseBlock {
    public HorizontalFacingBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        if (context.getFace() != Direction.UP && context.getFace() != Direction.DOWN) {
            return this.getDefaultState().with(HORIZONTAL_FACING, context.getFace().getOpposite());
        } else return getDefaultState();
    }
}