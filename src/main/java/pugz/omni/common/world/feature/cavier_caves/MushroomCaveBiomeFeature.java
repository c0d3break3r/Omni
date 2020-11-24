package pugz.omni.common.world.feature.cavier_caves;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
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

    public void generateFeature(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, CaveBiomeFeatureConfig config) {
        if (rand.nextInt(3) == 0) {
            world.setBlockState(pos, getRandomSmallState(rand).with(CaveMushroomBlock.WATERLOGGED, world.getFluidState(pos).isTagged(FluidTags.WATER)), 2);
            return;
        } else if (rand.nextInt(5) == 0) {
            if (rand.nextBoolean()) Features.PATCH_RED_MUSHROOM.generate(world, generator, rand, pos);
            else Features.PATCH_BROWN_MUSHROOM.generate(world, generator, rand, pos);
        }

        int height = rand.nextInt(3) + 1;
        BlockState state = getRandomState(rand);

        for (int y = pos.getY(); y <= pos.getY() + height; ++y) {
            BlockPos place1 = new BlockPos(pos.getX(), y, pos.getZ());
            world.setBlockState(place1, OmniBlocks.CAVE_MUSHROOM_STEM.get().getDefaultState(), 2);

            if (y == pos.getY() + height) {
                for (Direction direction : Direction.values()) {
                    if (!world.getBlockState(place1.offset(direction)).isSolid())
                        world.setBlockState(place1.offset(direction), state, 2);
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