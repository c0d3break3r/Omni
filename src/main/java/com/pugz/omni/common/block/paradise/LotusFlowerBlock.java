package com.pugz.omni.common.block.paradise;

import net.minecraft.block.*;
import net.minecraft.potion.Effect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class LotusFlowerBlock extends FlowerBlock {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);

    public LotusFlowerBlock(Effect effect, int duration) {
        super(effect, duration, AbstractBlock.Properties.from(Blocks.DANDELION));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public AbstractBlock.OffsetType getOffsetType() {
        return OffsetType.NONE;
    }
}