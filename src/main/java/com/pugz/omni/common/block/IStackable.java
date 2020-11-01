package com.pugz.omni.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;

public interface IStackable {
    VoxelShape getStackedShape();

    Block getBase();

    Block getBlock();

    Property<Integer> getCountProperty();

    void removeOne(World world, BlockPos pos, BlockState state);
}