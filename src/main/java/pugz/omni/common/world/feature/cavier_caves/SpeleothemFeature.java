package pugz.omni.common.world.feature.cavier_caves;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.feature.Feature;
import pugz.omni.common.block.cavier_caves.SpeleothemBlock;
import pugz.omni.core.util.CaveGenUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

import java.util.*;

public class SpeleothemFeature extends Feature<SpeleothemFeatureConfig> {
    public SpeleothemFeature(Codec<SpeleothemFeatureConfig> config) {
        super(config);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, SpeleothemFeatureConfig config) {
        //floor
        if (random.nextBoolean() && config.variant != SpeleothemFeatureConfig.Variant.ICE) {
            BlockPos.Mutable lowerStart = CaveGenUtils.getCaveFloorPosition(world, pos, config.variant);
            if (world.getBlockState(lowerStart.up()).getBlock() instanceof SpeleothemBlock || world.getBlockState(lowerStart.down()).getBlock() instanceof SpeleothemBlock) return false;

            int lowerLength = CaveGenUtils.getCaveHeight(world, lowerStart, config.variant);
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
                placeFullSpeleothem(world, place, config, runs, false);
            }
            return true;
        }
        //ceiling
        else {
            BlockPos.Mutable upperStart = CaveGenUtils.getCaveFloorPosition(world, pos, config.variant);
            if (world.getBlockState(upperStart.up()).getBlock() instanceof SpeleothemBlock || world.getBlockState(upperStart.down()).getBlock() instanceof SpeleothemBlock) return false;

            int upperLength = CaveGenUtils.getCaveHeight(world, upperStart, config.variant);
            if (upperLength == 0 || !CaveGenUtils.isValidCavePos(world.getBlockState(upperStart.up(upperLength + 1)).getBlock(), config.variant)) return false;

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
                placeFullSpeleothem(world, place, config, runs, true);
            }
            return true;
        }
    }

    public static void placeSpeleothem(ISeedReader world, BlockPos pos, SpeleothemBlock.Size size, SpeleothemFeatureConfig config, boolean part) {
        if (!world.isRemote()) {
            Block block = world.getBlockState(pos).getBlock();
            if (block == Blocks.CAVE_AIR || block == Blocks.WATER)
                world.setBlockState(pos, config.variant.getState().with(SpeleothemBlock.SIZE, size).with(SpeleothemBlock.PART, part ? SpeleothemBlock.Part.UPPER : SpeleothemBlock.Part.LOWER).with(SpeleothemBlock.STATIC, true).with(SpeleothemBlock.WATERLOGGED, world.getBlockState(pos).getBlock() == Blocks.WATER), 0);
        }
    }

    public static void placeFullSpeleothem(ISeedReader world, BlockPos pos, SpeleothemFeatureConfig config, int i, boolean part) {
        switch (i) {
            case 0:
                if (config.variant == SpeleothemFeatureConfig.Variant.ICE) placeSpeleothem(world, pos, SpeleothemBlock.Size.ICE_LARGE, config, part);
                else placeSpeleothem(world, pos, SpeleothemBlock.Size.LARGE, config, part);
            case 1:
                placeSpeleothem(world, pos, SpeleothemBlock.Size.MEDIUM, config, part);
            case 2:
                placeSpeleothem(world, pos, SpeleothemBlock.Size.SMALL, config, part);
        }
    }
}