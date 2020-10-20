package com.pugz.omni.common.world.feature.cavier_caves;

import com.mojang.serialization.Codec;
import com.pugz.omni.core.util.CaveGenUtils;
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

            System.out.println("Generated Speleothem at " + lowerStart.getX() + ", " + lowerStart.getY() + ", " + lowerStart.getZ());

            int lowerLength = CaveGenUtils.getCaveHeight(world, lowerStart);
            if (lowerLength == 0) return false;

            lowerLength = MathHelper.clamp(random.nextInt(lowerLength - 1), 3, lowerLength);
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
                placeSpeleothem(world, place, config, runs);
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
                placeSpeleothem(world, place, config, runs);
            }
            return true;
        }
    }

    private void placeSpeleothem(ISeedReader world, BlockPos pos, SpeleothemFeatureConfig config, int length) {
        int fifth = Math.round(length / 5.0F);
        BlockPos.Mutable pos$mutable = pos.toMutable();

        for (int y = pos.getY(); y <= pos.getY() + length; ++y) {
            if (y > pos.getY() + (fifth * 4)) {
                CaveGenUtils.placeFullSpeleothem(world, pos$mutable, config, 0);
                continue;
            }
            else if (y > pos.getY() + (fifth * 3)) {
                CaveGenUtils.placeFullSpeleothem(world, pos$mutable, config, 1);
                continue;
            }
            else if (y > pos.getY() + (fifth * 2)) {
                CaveGenUtils.placeFullSpeleothem(world, pos$mutable, config, 2);
                continue;
            }
            else if (y > pos.getY() + fifth) {
                CaveGenUtils.placeFullSpeleothem(world, pos$mutable, config, 1);
                continue;
            }
            else if (y > pos.getY()) {
                CaveGenUtils.placeFullSpeleothem(world, pos$mutable, config, 0);
            }

            pos$mutable.setPos(pos.getX(), y, pos.getZ());
        }
    }
}