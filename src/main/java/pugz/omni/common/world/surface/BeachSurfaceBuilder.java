package pugz.omni.common.world.surface;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;
import java.util.stream.IntStream;

public class BeachSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
    private static final BlockState STONE = Blocks.STONE.getDefaultState();
    private static final BlockState AIR = Blocks.AIR.getDefaultState();
    private static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
    private static final BlockState SAND = Blocks.SAND.getDefaultState();
    private static final BlockState SANDSTONE = Blocks.SANDSTONE.getDefaultState();

    protected long seed;
    protected OctavesNoiseGenerator noise;

    public BeachSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void buildSurface(Random random, IChunk chunk, Biome biome, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        int relX = x & 0xF;
        int relZ = z & 0xF;

        double sandThreshold = 0.0D;

        double sandNoise = this.noise.func_205563_a(x * 0.03125D, z * 0.03125D, 0.0D) * 75.0D + random.nextDouble();
        double gravelNoise = this.noise.func_205563_a(x * 0.03125D, 109.0D, z * 0.03125D) * 75.0D + random.nextDouble();

        boolean genSand = sandNoise > sandThreshold;
        boolean genGravel = gravelNoise > 10.0D;

        int genStone = (int)(noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);

        BlockPos.Mutable pos = new BlockPos.Mutable();
        int flag = -1;

        BlockState topBlock = config.getTop();
        BlockState fillerBlock = config.getUnder();

        for (int y = startHeight; y >= 0; --y) {
            pos.setPos(relX, y, relZ);
            BlockState thisBlock = chunk.getBlockState(pos);

            if (thisBlock.isAir()) {
                flag = -1;
            }

            else if (thisBlock.isIn(defaultBlock.getBlock())) {
                if (flag == -1) {
                    if (genStone <= 0) {
                        topBlock = AIR;
                        fillerBlock = STONE;
                    }

                    else if (y >= seaLevel - 4 && y <= seaLevel + 1) {
                        topBlock = config.getTop();
                        fillerBlock = config.getUnder();

                        if (genGravel) {
                            topBlock = AIR;
                            fillerBlock = GRAVEL;
                        }

                        if (genSand) {
                            topBlock = SAND;
                            fillerBlock = SAND;
                        }
                    }
                    if (y < seaLevel && topBlock.isAir()) {
                        topBlock = defaultFluid;
                    }

                    flag = genStone;
                    if (y >= seaLevel - 1) {
                        chunk.setBlockState(pos, topBlock, false);
                    }
                    else {
                        chunk.setBlockState(pos, fillerBlock, false);
                    }
                }

                else if (flag > 0) {
                    --flag;
                    chunk.setBlockState(pos, fillerBlock, false);

                    // Generates layer of sandstone starting at lowest block of sand, of height 1 to 4.
                    if (flag == 0 && fillerBlock.equals(SAND)) {
                        flag = random.nextInt(4);
                        fillerBlock = SANDSTONE;
                    }
                }
            }
        }
    }

    @Override
    public void setSeed(long seed) {
        if (this.seed != seed || this.noise == null) {
            this.noise = new OctavesNoiseGenerator(new SharedSeedRandom(seed), IntStream.rangeClosed(-3, 0));
        }
        this.seed = seed;
    }
}