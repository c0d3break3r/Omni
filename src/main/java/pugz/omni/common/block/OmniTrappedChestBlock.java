package pugz.omni.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.TrappedChestBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import pugz.omni.common.tileentity.OmniTrappedChestTileEntity;

public class OmniTrappedChestBlock extends TrappedChestBlock {
    public OmniTrappedChestBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new OmniTrappedChestTileEntity();
    }
}