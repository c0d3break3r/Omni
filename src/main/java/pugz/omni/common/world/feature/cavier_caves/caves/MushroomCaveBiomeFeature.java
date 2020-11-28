package pugz.omni.common.world.feature.cavier_caves.caves;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Features;
import pugz.omni.common.block.cavier_caves.CaveMushroomBlock;
import pugz.omni.core.registry.OmniBlocks;

import java.util.Random;

public class MushroomCaveBiomeFeature extends AbstractCaveBiomeFeature {
    public MushroomCaveBiomeFeature(Codec<CaveBiomeFeatureConfig> codec) {
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
        if (world.getBlockState(pos.offset(direction)).getBlock() == Blocks.CAVE_AIR && world.getBlockState(pos.offset(Direction.UP)).getBlock() != Blocks.CAVE_AIR) {
            if (config.wallState != null)
                world.setBlockState(pos, config.wallState, 2);
            if (config.fillerState != null)
                world.setBlockState(pos.offset(direction.getOpposite()), config.fillerState, 2);
        }
    }

    @Override
    public void placeFloor(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, Direction direction, CaveBiomeFeatureConfig config) {
        if (world.getBlockState(pos.offset(direction)).getBlock() == Blocks.CAVE_AIR && world.getBlockState(pos.offset(direction.getOpposite())).getBlock() != Blocks.CAVE_AIR) {
            if (config.floorState != null) world.setBlockState(pos, config.floorState, 2);
            if (config.fillerState != null) world.setBlockState(pos.offset(direction.getOpposite()), config.fillerState, 2);

            if (rand.nextFloat() <= config.featureChance) {
                if (world.getBlockState(pos).getBlock() == Blocks.MYCELIUM) {
                    if (rand.nextInt(3) == 0) {
                        world.setBlockState(pos.up(), getRandomSmallState(rand).with(CaveMushroomBlock.WATERLOGGED, world.getFluidState(pos).isTagged(FluidTags.WATER)), 2);
                        return;
                    } else if (rand.nextInt(5) == 0) {
                        if (rand.nextBoolean()) Features.PATCH_RED_MUSHROOM.generate(world, generator, rand, pos.up());
                        else Features.PATCH_BROWN_MUSHROOM.generate(world, generator, rand, pos.up());
                    }

                    int height = rand.nextInt(4) + 1;
                    BlockState state = getRandomState(rand);

                    for (int y = pos.up().getY(); y <= pos.up().getY() + height; ++y) {
                        BlockPos place1 = new BlockPos(pos.up().getX(), y, pos.up().getZ());
                        world.setBlockState(place1, OmniBlocks.CAVE_MUSHROOM_STEM.get().getDefaultState(), 2);

                        if (y == pos.up().getY() + height) {
                            for (Direction d : Direction.values()) {
                                if (!world.getBlockState(place1.offset(d)).isSolid())
                                    world.setBlockState(place1.offset(d), state, 2);
                            }
                        }
                    }
                }
            }
        }
    }

    private BlockState getRandomState(Random random) {
        int i = random.nextInt(4);

        switch (i) {
            case 3:
                return OmniBlocks.PURPLE_CAVE_MUSHROOM_BLOCK.get().getDefaultState();
            case 2:
                return OmniBlocks.BLUE_CAVE_MUSHROOM_BLOCK.get().getDefaultState();
            case 1:
                return OmniBlocks.GREEN_CAVE_MUSHROOM_BLOCK.get().getDefaultState();
            default:
                return OmniBlocks.YELLOW_CAVE_MUSHROOM_BLOCK.get().getDefaultState();
        }
    }

    private BlockState getRandomSmallState(Random random) {
        int i = random.nextInt(4);

        switch (i) {
            case 3:
                return OmniBlocks.PURPLE_CAVE_MUSHROOM.get().getDefaultState();
            case 2:
                return OmniBlocks.BLUE_CAVE_MUSHROOM.get().getDefaultState();
            case 1:
                return OmniBlocks.GREEN_CAVE_MUSHROOM.get().getDefaultState();
            default:
                return OmniBlocks.YELLOW_CAVE_MUSHROOM.get().getDefaultState();
        }
    }
}