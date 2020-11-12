package pugz.omni.common.block.cavier_caves;

import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import pugz.omni.core.module.CavierCavesModule;
import pugz.omni.core.module.CoreModule;
import pugz.omni.core.registry.OmniBlocks;

import javax.annotation.Nonnull;

public class MalachiteTotemBlock extends Block {
    private static final VoxelShape PART_BASE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 3.0D, 15.0D);
    private static final VoxelShape PART_TOTEM = Block.makeCuboidShape(6.0D, 3.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    private static final VoxelShape SHAPE = VoxelShapes.or(PART_BASE, PART_TOTEM);

    public MalachiteTotemBlock() {
        super(AbstractBlock.Properties.from(OmniBlocks.MALACHITE_BLOCK.get()).setLightLevel((state) -> {
            return CoreModule.Configuration.CLIENT.MALACHITE_TOTEM_LIGHT_LEVEL.get();
        }));
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @SuppressWarnings("deprecation")
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }
}