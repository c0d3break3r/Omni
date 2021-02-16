package pugz.omni.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import org.apache.commons.lang3.StringUtils;
import pugz.omni.common.tileentity.OmniChestTileEntity;
import pugz.omni.core.registry.OmniTileEntities;

public class OmniChestBlock extends ChestBlock {
    public OmniChestBlock(AbstractBlock.Properties properties) {
        super(properties, () -> OmniTileEntities.CHEST.get());
    }

    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new OmniChestTileEntity(StringUtils.remove(this.getRegistryName().getPath(), "_chest"));
    }
}