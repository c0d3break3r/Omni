package pugz.omni.common.world.feature.cavier_caves;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.registries.ForgeRegistries;
import pugz.omni.common.block.cavier_caves.BuddingMalachiteBlock;
import pugz.omni.common.world.noise.NormalNoise;
import pugz.omni.common.world.noise.WorldGenRandom;
import pugz.omni.core.module.CavierCavesModule;
import pugz.omni.core.module.CoreModule;
import pugz.omni.core.registry.OmniBlocks;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GeodeFeature extends Feature<GeodeFeatureConfig> {
    private static final Direction[] directions = Direction.values();

    public GeodeFeature(Codec<GeodeFeatureConfig> config) {
        super(config);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, GeodeFeatureConfig config) {
        int minGenOffset = config.minGenOffset;
        int maxGenOffset = config.maxGenOffset;
        if (world.getBlockState(pos.add(0, maxGenOffset / 3, 0)).isAir()) {
            return false;
        } else {
            List<Pair<BlockPos, Integer>> var8 = Lists.newLinkedList();
            int var9 = config.minDistribution + rand.nextInt(config.maxDistribution - config.minDistribution);
            WorldGenRandom worldGenRandom = new WorldGenRandom(world.getSeed());
            NormalNoise noise = NormalNoise.a(worldGenRandom, -4, 1.0D);
            List<BlockPos> var12 = Lists.newLinkedList();
            double var13 = (double)var9 / (double)config.maxOuterDistance;
            double var18 = 1.0D / Math.sqrt(1.7D);
            double var20 = 1.0D / Math.sqrt(2.2D + var13);
            double var22 = 1.0D / Math.sqrt(3.2D + var13);
            double var24 = 1.0D / Math.sqrt(4.2D + var13);
            double var26 = 1.0D / Math.sqrt(2.0D + rand.nextDouble() / 2.0D + (var9 > 3 ? var13 : 0.0D));
            boolean var28 = (double)rand.nextFloat() < 0.95D;

            int var29;
            int var30;
            for(var29 = 0; var29 < var9; ++var29) {
                var30 = config.minOuterDistance + rand.nextInt(config.maxOuterDistance - config.minOuterDistance);
                int var31 = config.minOuterDistance + rand.nextInt(config.maxOuterDistance - config.minOuterDistance);
                int var32 = config.minOuterDistance + rand.nextInt(config.maxOuterDistance - config.minOuterDistance);
                var8.add(Pair.of(pos.add(var30, var31, var32), config.minOffset + rand.nextInt(config.maxOffset - config.minOffset)));
            }

            if (var28) {
                var29 = rand.nextInt(4);
                var30 = var9 * 2 + 1;
                if (var29 == 0) {
                    var12.add(pos.add(var30, 7, 0));
                    var12.add(pos.add(var30, 5, 0));
                    var12.add(pos.add(var30, 1, 0));
                } else if (var29 == 1) {
                    var12.add(pos.add(0, 7, var30));
                    var12.add(pos.add(0, 5, var30));
                    var12.add(pos.add(0, 1, var30));
                } else if (var29 == 2) {
                    var12.add(pos.add(var30, 7, var30));
                    var12.add(pos.add(var30, 5, var30));
                    var12.add(pos.add(var30, 1, var30));
                } else {
                    var12.add(pos.add(0, 7, 0));
                    var12.add(pos.add(0, 5, 0));
                    var12.add(pos.add(0, 1, 0));
                }
            }

            List<BlockPos> var40 = Lists.newArrayList();
            Iterator var41 = BlockPos.getAllInBox(pos.add(minGenOffset, minGenOffset, minGenOffset), pos.add(maxGenOffset, maxGenOffset, maxGenOffset)).iterator();

            if (world.getBlockState(pos.down()).getFluidState().isTagged(FluidTags.WATER) && world.getBiome(pos).getCategory() == Biome.Category.OCEAN) return false;

            while(true) {
                while(true) {
                    double var34;
                    double var36;
                    BlockPos var43;
                    do {
                        if (!var41.hasNext()) {
                            List<BlockState> var42 = ImmutableList.of(OmniBlocks.SMALL_MALACHITE_BUD.get().getDefaultState(), OmniBlocks.MEDIUM_MALACHITE_BUD.get().getDefaultState(), OmniBlocks.LARGE_MALACHITE_BUD.get().getDefaultState(), OmniBlocks.MALACHITE_CLUSTER.get().getDefaultState());
                            Iterator var44 = var40.iterator();

                            while(true) {
                                while(var44.hasNext()) {
                                    BlockPos var46 = (BlockPos)var44.next();
                                    BlockState crystalBudState = (BlockState)var42.get(rand.nextInt(var42.size()));
                                    Direction[] var47 = directions;
                                    int var35 = var47.length;

                                    for (Direction var37 : var47) {
                                        if (crystalBudState.hasProperty(BlockStateProperties.FACING)) {
                                            crystalBudState = (BlockState) crystalBudState.with(BlockStateProperties.FACING, var37);
                                        }

                                        BlockPos var50 = var46.offset(var37);
                                        BlockState var52 = world.getBlockState(var50);
                                        if (crystalBudState.hasProperty(BlockStateProperties.WATERLOGGED)) {
                                            crystalBudState = (BlockState) crystalBudState.with(BlockStateProperties.WATERLOGGED, var52.getFluidState().isTagged(FluidTags.WATER));
                                        }

                                        if (BuddingMalachiteBlock.g(var52)) {
                                            world.setBlockState(var50, crystalBudState, 2);
                                            break;
                                        }
                                    }
                                }

                                return true;
                            }
                        }

                        var43 = (BlockPos)var41.next();
                        double var45 = noise.a((double)var43.getX(), (double)var43.getY(), (double)var43.getZ()) * config.noiseMultiplier;
                        var34 = 0.0D;
                        var36 = 0.0D;

                        Iterator var38;
                        Pair var39;
                        for(var38 = var8.iterator(); var38.hasNext(); var34 += MathHelper.fastInvSqrt(var43.distanceSq((Vector3i)var39.getFirst()) + (double)(Integer)var39.getSecond()) + var45) {
                            var39 = (Pair)var38.next();
                        }

                        BlockPos var51;
                        for(var38 = var12.iterator(); var38.hasNext(); var36 += MathHelper.fastInvSqrt(var43.distanceSq(var51) + (double)2) + var45) {
                            var51 = (BlockPos)var38.next();
                        }
                    } while(var34 < var24);

                    if (var28 && var36 >= var26 && var34 < var18) {
                        if (world.getBlockState(var43).isSolid()) {
                            world.setBlockState(var43, Blocks.AIR.getDefaultState(), 3);
                        }
                    } else if (var34 >= var18) {
                        world.setBlockState(var43, Blocks.AIR.getDefaultState(), 3);
                    } else if (var34 >= var20) {
                        boolean var49 = (double)rand.nextFloat() < config.alternateLayer0Chance;
                        if (var49) {
                            world.setBlockState(var43, OmniBlocks.BUDDING_MALACHITE.get().getDefaultState(), 2);
                        } else {
                            world.setBlockState(var43, OmniBlocks.MALACHITE_BLOCK.get().getDefaultState(), 2);
                        }

                        if ((!config.requireLayer0Alternate || var49) && (double)rand.nextFloat() < config.potentialPlacementsChance) {
                            var40.add(var43.toImmutable());
                        }
                    } else if (var34 >= var22) {
                        world.setBlockState(var43, ForgeRegistries.BLOCKS.getValue(new ResourceLocation(CoreModule.Configuration.CLIENT.GEODE_SHELL_INNER_BLOCK.get())).getDefaultState(), 3);
                    } else if (var34 >= var24) {
                        world.setBlockState(var43, ForgeRegistries.BLOCKS.getValue(new ResourceLocation(CoreModule.Configuration.CLIENT.GEODE_SHELL_OUTER_BLOCK.get())).getDefaultState(), 3);
                    }
                }
            }
        }
    }
}