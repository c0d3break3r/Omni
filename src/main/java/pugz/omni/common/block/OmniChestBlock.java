package pugz.omni.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import pugz.omni.common.tileentity.OmniChestTileEntity;
import pugz.omni.core.registry.OmniTileEntities;
import pugz.omni.core.util.IOmniChest;

public class OmniChestBlock extends ChestBlock implements IOmniChest {
    private final String wood;

    public OmniChestBlock(AbstractBlock.Properties properties, String wood) {
        super(properties, () -> OmniTileEntities.CHEST.get());
        this.wood = wood;
    }

    @Override
    public String getWood() {
        return this.wood;
    }

    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new OmniChestTileEntity();
    }
}