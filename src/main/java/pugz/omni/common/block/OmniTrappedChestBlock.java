package pugz.omni.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.TrappedChestBlock;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import pugz.omni.common.tileentity.OmniTrappedChestTileEntity;
import pugz.omni.core.registry.OmniTileEntities;
import pugz.omni.core.util.IOmniChest;

import javax.annotation.Nonnull;

public class OmniTrappedChestBlock extends ChestBlock implements IOmniChest {
    private final String wood;

    public OmniTrappedChestBlock(AbstractBlock.Properties properties, String wood) {
        super(properties, () -> OmniTileEntities.TRAPPED_CHEST.get());
        this.wood = wood;
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

    @Nonnull
    @Override
    protected Stat<ResourceLocation> getOpenStat() {
        return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canProvidePower(BlockState state) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getWeakPower(BlockState state, IBlockReader world, BlockPos pos, Direction direction) {
        return MathHelper.clamp(ChestTileEntity.getPlayersUsing(world, pos), 0, 15);
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getStrongPower(BlockState state, IBlockReader world, BlockPos pos, Direction direction) {
        return direction == Direction.UP ? state.getWeakPower(world, pos, direction) : 0;
    }
}