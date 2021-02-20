package pugz.omni.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.TrappedChestBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import pugz.omni.common.tileentity.OmniTrappedChestTileEntity;
import pugz.omni.core.util.IOmniChest;

import javax.annotation.Nullable;

public class OmniTrappedChestBlock extends TrappedChestBlock implements IOmniChest {
    private final String wood;

    public OmniTrappedChestBlock(AbstractBlock.Properties properties, String wood) {
        super(properties);
        this.wood = wood;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new OmniTrappedChestTileEntity();
    }

    @Override
    public String getWood() {
        return this.wood;
    }

    @Override
    public boolean isTrapped() {
        return true;
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