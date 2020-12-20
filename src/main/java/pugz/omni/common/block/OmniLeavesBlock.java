package pugz.omni.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import pugz.omni.core.util.IBaseBlock;

public class OmniLeavesBlock extends Block implements IBaseBlock {
    public OmniLeavesBlock() {
        super(AbstractBlock.Properties.from(Blocks.OAK_LEAVES));
    }

    @Override
    public int getFireEncouragement() {
        return 30;
    }

    @Override
    public int getFireFlammability() {
        return 60;
    }

    @Override
    public RenderType getRenderType() {
        return RenderType.getCutoutMipped();
    }

    @SuppressWarnings("deprecation")
    public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1;
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }
}