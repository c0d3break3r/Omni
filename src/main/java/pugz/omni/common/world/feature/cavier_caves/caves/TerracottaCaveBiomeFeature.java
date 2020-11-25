package pugz.omni.common.world.feature.cavier_caves.caves;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import pugz.omni.common.world.feature.ExposedOreFeatureConfig;
import pugz.omni.core.module.CoreModule;
import pugz.omni.core.registry.OmniBlocks;
import pugz.omni.core.registry.OmniFeatures;

import java.util.Random;

public class TerracottaCaveBiomeFeature extends AbstractCaveBiomeFeature {
    public TerracottaCaveBiomeFeature(Codec<CaveBiomeFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public void placeCeiling(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, Direction direction, CaveBiomeFeatureConfig config) {
        if (world.getBlockState(pos.offset(direction)).getBlock() == Blocks.CAVE_AIR && world.getBlockState(pos.offset(direction.getOpposite())).getBlock() != Blocks.CAVE_AIR) {
            if (config.ceilingState != null) world.setBlockState(pos, config.ceilingState, 2);
            if (config.fillerState != null) world.setBlockState(pos.offset(direction.getOpposite()), config.fillerState, 2);
        }
    }

    @Override
    public void placeWall(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, Direction direction, CaveBiomeFeatureConfig config) {
        if (world.getBlockState(pos.offset(direction)).getBlock() == Blocks.CAVE_AIR) {
            if (config.wallState != null) {
                BlockState state1 = Blocks.TERRACOTTA.getDefaultState();
                BlockState state2 = Blocks.BROWN_TERRACOTTA.getDefaultState();

                if (rand.nextInt(3) == 0) {
                    state1 = Blocks.WHITE_TERRACOTTA.getDefaultState();
                    state2 = Blocks.LIGHT_GRAY_TERRACOTTA.getDefaultState();
                }

                world.setBlockState(pos, config.wallState, 2);
                if (world.getBlockState(pos.down()).getShape(world, pos.down()) == VoxelShapes.fullCube()) world.setBlockState(pos.down(), state1, 2);
                if (world.getBlockState(pos.down(2)).getShape(world, pos.down(2)) == VoxelShapes.fullCube()) world.setBlockState(pos.down(3), state2, 2);
                if (world.getBlockState(pos.down(3)).getShape(world, pos.down(3)) == VoxelShapes.fullCube()) world.setBlockState(pos.down(4), Blocks.RED_SANDSTONE.getDefaultState(), 2);
            }
        }
    }

    @Override
    public void placeFloor(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, Direction direction, CaveBiomeFeatureConfig config) {
        if (world.getBlockState(pos.offset(direction)).getBlock() == Blocks.CAVE_AIR && world.getBlockState(pos.offset(direction.getOpposite())).getBlock() != Blocks.CAVE_AIR) {
            if (config.floorState != null) {
                BlockState state1 = Blocks.TERRACOTTA.getDefaultState();
                BlockState state2 = Blocks.BROWN_TERRACOTTA.getDefaultState();

                if (rand.nextInt(3) == 0) {
                    state1 = Blocks.WHITE_TERRACOTTA.getDefaultState();
                    state2 = Blocks.LIGHT_GRAY_TERRACOTTA.getDefaultState();
                }

                world.setBlockState(pos, config.floorState, 2);
                if (world.getBlockState(pos.down()).getShape(world, pos.down()) == VoxelShapes.fullCube()) world.setBlockState(pos.down(), state1, 2);
                if (world.getBlockState(pos.down(2)).getShape(world, pos.down(2)) == VoxelShapes.fullCube()) world.setBlockState(pos.down(2), state2, 2);
                if (world.getBlockState(pos.down(3)).getShape(world, pos.down(3)) == VoxelShapes.fullCube()) world.setBlockState(pos.down(4), Blocks.RED_SANDSTONE.getDefaultState(), 2);
            }

            if (rand.nextFloat() <= config.featureChance) {
                if (rand.nextBoolean()) world.setBlockState(pos.up(), Blocks.DEAD_BUSH.getDefaultState(), 2);
                else Features.PATCH_CACTUS_DECORATED.generate(world, generator, rand, pos.up());
            }
        }
    }
}