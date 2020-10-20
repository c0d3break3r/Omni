package com.pugz.omni.common.block.colormatic;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.PushReaction;

public class GlazedTerracottaPillarBlock extends RotatedPillarBlock {
    public GlazedTerracottaPillarBlock() {
        super(AbstractBlock.Properties.from(Blocks.GRAY_GLAZED_TERRACOTTA));
    }

    @Override
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.PUSH_ONLY;
    }
}