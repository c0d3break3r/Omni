package pugz.omni.common.world.feature.cavier_caves;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import pugz.omni.common.block.cavier_caves.CaveMushroomBlock;
import pugz.omni.core.registry.OmniBlocks;
import pugz.omni.core.util.BaseGenUtils;
import pugz.omni.core.util.CaveGenUtils;

import java.util.Random;

public class SmallMushroomFeature extends Feature<SmallMushroomFeatureConfig> {
    public SmallMushroomFeature(Codec<SmallMushroomFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, SmallMushroomFeatureConfig config) {
        BlockPos place = CaveGenUtils.getCaveFloorPosition(world, pos, null);
        if (world.getBlockState(place.down()).getBlock() != Blocks.MYCELIUM) return false;

        if (rand.nextBoolean()) {
            world.setBlockState(place, config.smallState.with(CaveMushroomBlock.WATERLOGGED, world.getFluidState(place).isTagged(FluidTags.WATER)), 2);
            return true;
        }

        if (rand.nextInt(6) == 0) {
            if (rand.nextBoolean()) Features.PATCH_RED_MUSHROOM.generate(world, generator, rand, place);
            else Features.PATCH_BROWN_MUSHROOM.generate(world, generator, rand, place);
        }

        int height = rand.nextInt(3) + 1;

        if (BaseGenUtils.isAirPresent(world, new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ() - 1), new BlockPos(pos.getX() + 1, pos.getY() + height + 1, pos.getZ() + 1), 1.0F)) {
            for (int y = place.getY(); y <= place.getY() + height; ++y) {
                BlockPos place1 = new BlockPos(place.getX(), y, place.getZ());
                world.setBlockState(place1, OmniBlocks.CAVE_MUSHROOM_STEM.get().getDefaultState(), 2);

                if (y == place.getY() + height) {
                    for (Direction direction : Direction.values()) {
                        if (!world.getBlockState(place1.offset(direction)).isSolid())
                            world.setBlockState(place1.offset(direction), config.state, 2);
                    }
                }
            }
            return true;
        }
        return false;
    }
}