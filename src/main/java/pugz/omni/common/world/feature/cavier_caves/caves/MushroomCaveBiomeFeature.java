package pugz.omni.common.world.feature.cavier_caves.caves;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import pugz.omni.common.block.cavier_caves.CaveMushroomBlock;
import pugz.omni.core.registry.OmniBlocks;

import java.util.BitSet;
import java.util.Random;

public class MushroomCaveBiomeFeature extends Feature<CaveBiomeFeatureConfig> {
    public MushroomCaveBiomeFeature(Codec<CaveBiomeFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, CaveBiomeFeatureConfig config) {
        float f = rand.nextFloat() * (float)Math.PI;
        float f1 = (float)config.size / 8.0F;
        int i = MathHelper.ceil(((float)config.size / 16.0F * 2.0F + 1.0F) / 2.0F);
        double d0 = (double)pos.getX() + Math.sin(f) * (double)f1;
        double d1 = (double)pos.getX() - Math.sin(f) * (double)f1;
        double d2 = (double)pos.getZ() + Math.cos(f) * (double)f1;
        double d3 = (double)pos.getZ() - Math.cos(f) * (double)f1;
        double d4 = (pos.getY() + rand.nextInt(3) - 2);
        double d5 = (pos.getY() + rand.nextInt(3) - 2);
        int k = pos.getX() - MathHelper.ceil(f1) - i;
        int l = pos.getY() - 2 - i;
        int i1 = pos.getZ() - MathHelper.ceil(f1) - i;
        int j1 = 2 * (MathHelper.ceil(f1) + i);
        int k1 = 2 * (2 + i);

        for(int l1 = k; l1 <= k + j1; ++l1) {
            for(int i2 = i1; i2 <= i1 + j1; ++i2) {
                if (l <= reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, l1, i2)) {
                    return this.func_207803_a(reader, generator, rand, config, d0, d1, d2, d3, d4, d5, k, l, i1, j1, k1);
                }
            }
        }
        return false;    }

    protected boolean func_207803_a(ISeedReader worldIn, ChunkGenerator generator, Random random, CaveBiomeFeatureConfig config, double p_207803_4_, double p_207803_6_, double p_207803_8_, double p_207803_10_, double p_207803_12_, double p_207803_14_, int p_207803_16_, int p_207803_17_, int p_207803_18_, int p_207803_19_, int p_207803_20_) {
        int i = 0;
        BitSet bitset = new BitSet(p_207803_19_ * p_207803_20_ * p_207803_19_);
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        int j = config.size;
        double[] adouble = new double[j * 4];

        for(int k = 0; k < j; ++k) {
            float f = (float)k / (float)j;
            double d0 = MathHelper.lerp(f, p_207803_4_, p_207803_6_);
            double d2 = MathHelper.lerp(f, p_207803_12_, p_207803_14_);
            double d4 = MathHelper.lerp(f, p_207803_8_, p_207803_10_);
            double d6 = random.nextDouble() * (double)j / 16.0D;
            double d7 = ((double)(MathHelper.sin((float)Math.PI * f) + 1.0F) * d6 + 1.0D) / 2.0D;
            adouble[k * 4] = d0;
            adouble[k * 4 + 1] = d2;
            adouble[k * 4 + 2] = d4;
            adouble[k * 4 + 3] = d7;
        }

        for(int i3 = 0; i3 < j - 1; ++i3) {
            if (!(adouble[i3 * 4 + 3] <= 0.0D)) {
                for(int k3 = i3 + 1; k3 < j; ++k3) {
                    if (!(adouble[k3 * 4 + 3] <= 0.0D)) {
                        double d12 = adouble[i3 * 4] - adouble[k3 * 4];
                        double d13 = adouble[i3 * 4 + 1] - adouble[k3 * 4 + 1];
                        double d14 = adouble[i3 * 4 + 2] - adouble[k3 * 4 + 2];
                        double d15 = adouble[i3 * 4 + 3] - adouble[k3 * 4 + 3];
                        if (d15 * d15 > d12 * d12 + d13 * d13 + d14 * d14) {
                            if (d15 > 0.0D) {
                                adouble[k3 * 4 + 3] = -1.0D;
                            } else {
                                adouble[i3 * 4 + 3] = -1.0D;
                            }
                        }
                    }
                }
            }
        }

        for(int j3 = 0; j3 < j; ++j3) {
            double d11 = adouble[j3 * 4 + 3];
            if (!(d11 < 0.0D)) {
                double d1 = adouble[j3 * 4];
                double d3 = adouble[j3 * 4 + 1];
                double d5 = adouble[j3 * 4 + 2];
                int l = Math.max(MathHelper.floor(d1 - d11), p_207803_16_);
                int l3 = Math.max(MathHelper.floor(d3 - d11), p_207803_17_);
                int i1 = Math.max(MathHelper.floor(d5 - d11), p_207803_18_);
                int j1 = Math.max(MathHelper.floor(d1 + d11), l);
                int k1 = Math.max(MathHelper.floor(d3 + d11), l3);
                int l1 = Math.max(MathHelper.floor(d5 + d11), i1);

                for(int i2 = l; i2 <= j1; ++i2) {
                    double d8 = ((double)i2 + 0.5D - d1) / d11;
                    if (d8 * d8 < 1.0D) {
                        for(int j2 = l3; j2 <= k1; ++j2) {
                            double d9 = ((double)j2 + 0.5D - d3) / d11;
                            if (d8 * d8 + d9 * d9 < 1.0D) {
                                for(int k2 = i1; k2 <= l1; ++k2) {
                                    double d10 = ((double)k2 + 0.5D - d5) / d11;
                                    if (d8 * d8 + d9 * d9 + d10 * d10 < 1.0D) {
                                        int l2 = i2 - p_207803_16_ + (j2 - p_207803_17_) * p_207803_19_ + (k2 - p_207803_18_) * p_207803_19_ * p_207803_20_;
                                        if (!bitset.get(l2)) {
                                            bitset.set(l2);
                                            blockpos$mutable.setPos(i2, j2, k2);
                                            if (config.target.test(worldIn.getBlockState(blockpos$mutable), random)) {
                                                for (Direction direction : Direction.values()) {
                                                    if (direction == Direction.DOWN) {
                                                        if (worldIn.getBlockState(blockpos$mutable.offset(direction)).getBlock() == Blocks.CAVE_AIR && worldIn.getBlockState(blockpos$mutable.offset(direction.getOpposite())).getBlock() != Blocks.CAVE_AIR) {
                                                            if (config.ceilingState != null) worldIn.setBlockState(blockpos$mutable, config.ceilingState, 2);
                                                            if (config.fillerState != null) worldIn.setBlockState(blockpos$mutable.offset(direction.getOpposite()), config.fillerState, 2);
                                                        }
                                                    }
                                                    if (direction != Direction.UP && direction != Direction.DOWN) {
                                                        if (config.ridged) {
                                                            if (worldIn.getBlockState(blockpos$mutable.offset(direction)).getBlock() == Blocks.CAVE_AIR) {
                                                                if (config.wallState != null)
                                                                    worldIn.setBlockState(blockpos$mutable, config.wallState, 2);
                                                                if (config.fillerState != null)
                                                                    worldIn.setBlockState(blockpos$mutable.offset(direction.getOpposite()), config.fillerState, 2);
                                                            }
                                                        } else {
                                                            if (worldIn.getBlockState(blockpos$mutable.offset(direction)).getBlock() == Blocks.CAVE_AIR && worldIn.getBlockState(blockpos$mutable.offset(Direction.UP)).getBlock() != Blocks.CAVE_AIR) {
                                                                if (config.wallState != null)
                                                                    worldIn.setBlockState(blockpos$mutable, config.wallState, 2);
                                                                if (config.fillerState != null)
                                                                    worldIn.setBlockState(blockpos$mutable.offset(direction.getOpposite()), config.fillerState, 2);
                                                            }
                                                        }
                                                    }
                                                    if (direction == Direction.UP) {
                                                        if (worldIn.getBlockState(blockpos$mutable.offset(direction)).getBlock() == Blocks.CAVE_AIR && worldIn.getBlockState(blockpos$mutable.offset(direction.getOpposite())).getBlock() != Blocks.CAVE_AIR) {
                                                            if (config.floorState != null) worldIn.setBlockState(blockpos$mutable, config.floorState, 2);
                                                            if (config.fillerState != null) worldIn.setBlockState(blockpos$mutable.offset(direction.getOpposite()), config.fillerState, 2);

                                                            if (random.nextFloat() <= config.featureChance) this.generateFeatures(worldIn, generator, random, blockpos$mutable.up(), config);
                                                        }
                                                    }
                                                }
                                                ++i;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return i > 0;
    }

    public void generateFeatures(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, CaveBiomeFeatureConfig config) {
        if (world.getBlockState(pos.down()).getBlock() == Blocks.MYCELIUM) {
            if (rand.nextInt(3) == 0) {
                world.setBlockState(pos, getRandomSmallState(rand).with(CaveMushroomBlock.WATERLOGGED, world.getFluidState(pos).isTagged(FluidTags.WATER)), 2);
                return;
            } else if (rand.nextInt(5) == 0) {
                if (rand.nextBoolean()) Features.PATCH_RED_MUSHROOM.generate(world, generator, rand, pos);
                else Features.PATCH_BROWN_MUSHROOM.generate(world, generator, rand, pos);
            }

            int height = rand.nextInt(4) + 1;
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