package pugz.omni.core.util;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.foliageplacer.*;
import net.minecraft.world.gen.placement.*;
import net.minecraft.world.gen.trunkplacer.DarkOakTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.ForkyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import pugz.omni.common.world.feature.cavier_caves.SpeleothemFeatureConfig;
import pugz.omni.core.registry.OmniFeatures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;

import java.util.Set;

public class BiomeFeatures {
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> TALL_OAK = Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(6, 3, 0), new TwoLayerFeature(1, 0, 1))).setIgnoreVines().build());
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> TALL_SPRUCE = Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.SPRUCE_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.SPRUCE_LEAVES.getDefaultState()), new SpruceFoliagePlacer(FeatureSpread.func_242253_a(2, 1), FeatureSpread.func_242253_a(0, 2), FeatureSpread.func_242253_a(1, 1)), new StraightTrunkPlacer(7, 3, 1), new TwoLayerFeature(2, 0, 2))).setIgnoreVines().build());
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> TALL_PINE = Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.SPRUCE_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.SPRUCE_LEAVES.getDefaultState()), new PineFoliagePlacer(FeatureSpread.func_242252_a(1), FeatureSpread.func_242252_a(1), FeatureSpread.func_242253_a(3, 1)), new StraightTrunkPlacer(8, 4, 0), new TwoLayerFeature(2, 0, 2))).setIgnoreVines().build());
    public static final ConfiguredFeature<?, ?> TAIGA_VEGETATION = Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(TALL_PINE.withChance(0.33333334F)), TALL_SPRUCE)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1)));
    public static final ConfiguredFeature<?, ?> BIRCH_OTHER = Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Features.BIRCH_TALL.withChance(0.2F), Features.FANCY_OAK_BEES_0002.withChance(0.1F)), TALL_OAK)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1)));

    public static void addOreCluster(BiomeGenerationSettingsBuilder biome, RuleTest filler, BlockState state, int size, int bottom, int top, int maxHeight, int spread, int range) {
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> Feature.ORE.withConfiguration(new OreFeatureConfig(filler, state, size)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(bottom, top, maxHeight))).range(range).square().func_242731_b(spread));
    }

    public static void addScatteredBlock(BiomeGenerationSettingsBuilder biome, BlockState state, Set<Block> placers, int tries, int chance) {
        biome.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(state), SimpleBlockPlacer.PLACER)).tries(tries).whitelist(placers).func_227317_b_().build()).func_242730_a(FeatureSpread.func_242253_a(-1, 4)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(5).chance(chance));
    }

    public static void addSpeleothems(BiomeGenerationSettingsBuilder biome, SpeleothemFeatureConfig.Variant variant, float probability) {
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> OmniFeatures.SPELEOTHEM.get().withConfiguration(new SpeleothemFeatureConfig(variant)).withPlacement(new ConfiguredPlacement<>(Placement.CARVING_MASK, new CaveEdgeConfig(GenerationStage.Carving.AIR, probability))));
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> OmniFeatures.SPELEOTHEM.get().withConfiguration(new SpeleothemFeatureConfig(variant)).withPlacement(new ConfiguredPlacement<>(Placement.CARVING_MASK, new CaveEdgeConfig(GenerationStage.Carving.LIQUID, probability))));
    }
}