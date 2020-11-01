package com.pugz.omni.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IStackable {
    Block getBase();

    Block getBlock();

    void removeOne(World world, BlockPos pos, BlockState state);
}