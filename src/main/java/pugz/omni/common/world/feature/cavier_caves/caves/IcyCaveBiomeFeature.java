package pugz.omni.common.world.feature.cavier_caves.caves;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import pugz.omni.core.module.CoreModule;
import pugz.omni.core.registry.OmniBlocks;

import java.util.Random;

public class IcyCaveBiomeFeature extends AbstractCaveBiomeFeature {
    public IcyCaveBiomeFeature(Codec<CaveBiomeFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public void place(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, CaveBiomeFeatureConfig config) {
        for (Direction direction : Direction.values()) {
            if (direction == Direction.DOWN) {
                if (world.getBlockState(pos.offset(direction)).getBlock() == Blocks.CAVE_AIR && world.getBlockState(pos.offset(direction.getOpposite())).getBlock() != Blocks.CAVE_AIR) {
                    if (config.ceilingState != null) {
                        if (world.isAirBlock(pos.offset(direction.getOpposite())) && CoreModule.Configuration.COMMON.ICY_CAVE_ICE_WINDOWS.get()) {
                            world.setBlockState(pos, Blocks.ICE.getDefaultState(), 2);
                            if (!world.canBlockSeeSky(pos.offset(direction.getOpposite()))) world.setBlockState(pos.offset(direction.getOpposite()), Blocks.ICE.getDefaultState(), 2);
                            continue;
                        }
                        else world.setBlockState(pos, config.ceilingState, 2);
                    }
                    if (config.fillerState != null) world.setBlockState(pos.offset(direction.getOpposite()), config.fillerState, 2);
                }
            }
            if (direction != Direction.UP && direction != Direction.DOWN) {
                if (world.getBlockState(pos.offset(direction)).getBlock() == Blocks.CAVE_AIR) {
                    if (config.wallState != null) {
                        if (world.isAirBlock(pos.offset(direction.getOpposite(), 2)) && CoreModule.Configuration.COMMON.ICY_CAVE_ICE_WINDOWS.get()) {
                            world.setBlockState(pos, Blocks.ICE.getDefaultState(), 2);
                            if (!world.canBlockSeeSky(pos.offset(direction.getOpposite()))) world.setBlockState(pos.offset(direction.getOpposite()), Blocks.ICE.getDefaultState(), 2);
                            continue;
                        }
                        else world.setBlockState(pos, config.wallState, 2);
                    }
                    if (config.fillerState != null)
                        world.setBlockState(pos.offset(direction.getOpposite()), config.fillerState, 2);
                }
            }
            if (direction == Direction.UP) {
                if (world.getBlockState(pos.offset(direction)).getBlock() == Blocks.CAVE_AIR && world.getBlockState(pos.offset(direction.getOpposite())).getBlock() != Blocks.CAVE_AIR) {
                    if (config.floorState != null) {
                        if (world.isAirBlock(pos.offset(direction.getOpposite(), 2)) && CoreModule.Configuration.COMMON.ICY_CAVE_ICE_WINDOWS.get()) {
                            world.setBlockState(pos, Blocks.ICE.getDefaultState(), 2);
                            if (!world.canBlockSeeSky(pos.offset(direction.getOpposite()))) world.setBlockState(pos.offset(direction.getOpposite()), Blocks.ICE.getDefaultState(), 2);
                            continue;
                        }
                        else world.setBlockState(pos, config.floorState, 2);
                    }
                    if (config.fillerState != null) world.setBlockState(pos.offset(direction.getOpposite()), config.fillerState, 2);

                    if (rand.nextFloat() <= config.featureChance) {
                        if (rand.nextInt(4) == 0 && !world.canBlockSeeSky(pos.up())) {
                            world.setBlockState(pos.up(), OmniBlocks.ARCTISS.get().getDefaultState(), 2);
                        } else if (rand.nextInt(4) == 0) {
                            Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.SNOW.getDefaultState()), SimpleBlockPlacer.PLACER)).tries(64).xSpread(5).ySpread(5).zSpread(5).func_227317_b_().build()).generate(world, generator, rand, pos.up());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void placeCeiling(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, Direction direction, CaveBiomeFeatureConfig config) {
    }

    @Override
    public void placeWall(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, Direction direction, CaveBiomeFeatureConfig config) {
    }

    @Override
    public void placeFloor(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, Direction direction, CaveBiomeFeatureConfig config) {
    }
}