package pugz.omni.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockReader;
import pugz.omni.common.tileentity.OmniBeehiveTileEntity;
import pugz.omni.core.util.IBaseBlock;

import javax.annotation.Nullable;

public class OmniBeehiveBlock extends BeehiveBlock implements IBaseBlock {
    public OmniBeehiveBlock() {
        super(AbstractBlock.Properties.from(Blocks.BEEHIVE));
        this.setDefaultState(this.stateContainer.getBaseState().with(HONEY_LEVEL, 0).with(FACING, Direction.NORTH));
    }

    @Override
    public int getFireEncouragement() {
        return 5;
    }

    @Override
    public int getFireFlammability() {
        return 20;
    }

    @Nullable
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new OmniBeehiveTileEntity();
    }
}