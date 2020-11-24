package pugz.omni.common.world.feature.cavier_caves.caves;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import pugz.omni.common.world.feature.cavier_caves.CaveBiomeFeatureConfig;
import pugz.omni.core.registry.OmniBlocks;

import java.util.Random;

public class IcyCaveBiomeFeature extends AbstractCaveBiomeFeature {
    public IcyCaveBiomeFeature(Codec<CaveBiomeFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public void generateFeature(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, CaveBiomeFeatureConfig config) {
        if (rand.nextInt(5) == 0) {
            Feature.RANDOM_PATCH.generate(world, generator, rand, pos, new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(OmniBlocks.ARCTISS.get().getDefaultState()), SimpleBlockPlacer.PLACER).tries(8).build());
        } else {
            Feature.RANDOM_PATCH.generate(world, generator, rand, pos, new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.SNOW.getDefaultState()), SimpleBlockPlacer.PLACER).tries(12).build());
        }

        if (world.isAirBlock(pos.down()) || world.isAirBlock(pos.down(2))) {
            world.setBlockState(pos.down(), Blocks.ICE.getDefaultState(), 3);
            if (world.isAirBlock(pos.down(2))) world.setBlockState(pos.down(2), Blocks.ICE.getDefaultState(), 3);
        }

        BlockState state = rand.nextInt(8) < 7 ? Blocks.PACKED_ICE.getDefaultState() : Blocks.BLUE_ICE.getDefaultState();

        if (rand.nextInt(10) == 0) {
            for (int y = pos.getY(); y <= pos.getY() + 3; ++y) {
                BlockPos place1 = new BlockPos(pos.getX(), y, pos.getZ());
                world.setBlockState(place1, state, 2);

                if (y == pos.getY()) {
                    for (Direction direction : Direction.values()) {
                        if (!world.getBlockState(place1.offset(direction)).isSolid())
                            world.setBlockState(place1.offset(direction), state, 2);
                    }
                }
            }
        }
    }
}