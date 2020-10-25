package com.pugz.omni.common.world.feature.cavier_caves;

import com.mojang.serialization.Codec;
import com.pugz.omni.common.block.cavier_caves.SpeleothemBlock;
import com.pugz.omni.core.util.CaveGenUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.*;

public class SpeleothemFeature extends Feature<SpeleothemFeatureConfig> {
    public SpeleothemFeature(Codec<SpeleothemFeatureConfig> config) {
        super(config);
    }

    @Override
    public boolean func_241855_a(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, SpeleothemFeatureConfig config) {
        //floor
        if (random.nextBoolean() && config.variant != SpeleothemFeatureConfig.Variant.END_STONE && config.variant != SpeleothemFeatureConfig.Variant.ICE) {
            BlockPos.Mutable lowerStart = CaveGenUtils.getCaveFloorPosition(world, pos, config.variant);
            int lowerLength = CaveGenUtils.getCaveHeight(world, lowerStart);
            if (lowerLength == 0) return false;

            lowerLength = MathHelper.clamp(random.nextInt(lowerLength), 3, lowerLength);
            int third = Math.round(lowerLength / 3.0F);

            int runs = 0;
            boolean run1 = false;
            boolean run2 = false;
            for (int y = lowerStart.getY(); y <= lowerStart.getY() + lowerLength; ++y) {
                if (y > (lowerStart.getY() + (third)) && !run1) {
                    run1 = true;
                    ++runs;
                }
                if (y > (lowerStart.getY() + (third * 2)) && !run2) {
                    run2 = true;
                    ++runs;
                }
                BlockPos place = new BlockPos(lowerStart.getX(), y, lowerStart.getZ());
                placeFullSpeleothem(world, place, SpeleothemBlock.Half.LOWER, config, runs);
            }
            return true;
        }
        //ceiling
        else {
            BlockPos.Mutable upperStart = CaveGenUtils.getCaveFloorPosition(world, pos, config.variant);
            int upperLength = CaveGenUtils.getCaveHeight(world, upperStart);
            if (upperLength == 0 || CaveGenUtils.checkCavePos(world.getBlockState(upperStart.up(upperLength)).getBlock(), config.variant)) return false;

            upperStart.setY(upperStart.getY() + (upperLength - 1));
            upperLength = MathHelper.clamp(random.nextInt(upperLength), 3, upperLength);

            int runs = 0;
            int third = Math.round(upperLength / 3.0F);
            boolean run1 = false;
            boolean run2 = false;
            for (int y = upperStart.getY(); y >= (upperStart.getY()) - upperLength; --y) {
                if (y < (upperStart.getY() - (third)) && !run1) {
                    run1 = true;
                    ++runs;
                }
                if (y < (upperStart.getY() - (third * 2)) && !run2) {
                    run2 = true;
                    ++runs;
                }
                BlockPos place = new BlockPos(upperStart.getX(), y, upperStart.getZ());
                placeFullSpeleothem(world, place, SpeleothemBlock.Half.UPPER, config, runs);
            }
            return true;
        }
    }

    private void placeSpeleothem(ISeedReader world, BlockPos pos, SpeleothemFeatureConfig config, int length) {
        int fifth = Math.round(length / 5.0F);
        BlockPos.Mutable pos$mutable = pos.toMutable();

        for (int y = pos.getY(); y <= pos.getY() + length; ++y) {
            if (y > pos.getY() + (fifth * 4)) {
                placeFullSpeleothem(world, pos$mutable, SpeleothemBlock.Half.FULL, config, 0);
                continue;
            }
            else if (y > pos.getY() + (fifth * 3)) {
                placeFullSpeleothem(world, pos$mutable, SpeleothemBlock.Half.FULL, config, 1);
                continue;
            }
            else if (y > pos.getY() + (fifth * 2)) {
                placeFullSpeleothem(world, pos$mutable, SpeleothemBlock.Half.FULL, config, 2);
                continue;
            }
            else if (y > pos.getY() + fifth) {
                placeFullSpeleothem(world, pos$mutable, SpeleothemBlock.Half.FULL, config, 1);
                continue;
            }
            else if (y > pos.getY()) {
                placeFullSpeleothem(world, pos$mutable, SpeleothemBlock.Half.FULL, config, 0);
            }

            pos$mutable.setPos(pos.getX(), y, pos.getZ());
        }
    }

    public static void placeSpeleothem(ISeedReader world, BlockPos pos, SpeleothemBlock.Size size, SpeleothemBlock.Half half, SpeleothemFeatureConfig config) {
        Block block = world.getBlockState(pos).getBlock();
        if (block == Blocks.CAVE_AIR || block == Blocks.WATER) world.setBlockState(pos, config.variant.getState().with(SpeleothemBlock.SIZE, size).with(SpeleothemBlock.HALF, half).with(SpeleothemBlock.WATERLOGGED, world.getBlockState(pos).getBlock() == Blocks.WATER), 0);
    }

    public static void placeFullSpeleothem(ISeedReader world, BlockPos pos, SpeleothemBlock.Half half, SpeleothemFeatureConfig config, int i) {
        switch (i) {
            case 0:
                if (config.variant == SpeleothemFeatureConfig.Variant.ICE) placeSpeleothem(world, pos, SpeleothemBlock.Size.ICE_LARGE, half, config);
                else placeSpeleothem(world, pos, SpeleothemBlock.Size.LARGE, half, config);
            case 1:
                placeSpeleothem(world, pos, SpeleothemBlock.Size.MEDIUM, half, config);
            case 2:
                placeSpeleothem(world, pos, SpeleothemBlock.Size.SMALL, half, config);
        }
    }
}