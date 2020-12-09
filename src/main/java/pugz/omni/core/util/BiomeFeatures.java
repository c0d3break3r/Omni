package pugz.omni.core.util;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.*;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import pugz.omni.common.world.feature.ExposedOreFeatureConfig;
import pugz.omni.common.world.feature.cavier_caves.*;
import pugz.omni.common.world.feature.cavier_caves.caves.CaveBiomeFeatureConfig;
import pugz.omni.core.module.CoreModule;
import pugz.omni.core.registry.OmniBlocks;
import pugz.omni.core.registry.OmniFeatures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;

import javax.annotation.Nullable;
import java.util.Set;

public class BiomeFeatures {
    public static void addOreCluster(BiomeGenerationSettingsBuilder biome, RuleTest filler, BlockState state, int size, int bottom, int top, int maxHeight, int spread, int range) {
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> Feature.ORE.withConfiguration(new OreFeatureConfig(filler, state, size)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(bottom, top, maxHeight))).range(range).square().func_242731_b(spread));
    }

    public static void addExposedOreCluster(BiomeGenerationSettingsBuilder biome, RuleTest filler, BlockState state, @Nullable BlockState fillerState, ExposedOreFeatureConfig.CaveFace face, int size, int bottom, int top, int maxHeight, int spread, int range, int chance) {
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> OmniFeatures.EXPOSED_ORE.get().withConfiguration(new ExposedOreFeatureConfig(filler, state, fillerState, size, face)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(bottom, top, maxHeight)).chance(chance)).range(range).square().func_242731_b(spread));
    }

    public static void addRedRock(BiomeGenerationSettingsBuilder biome) {
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> OmniFeatures.Configured.RED_ROCK);
    }

    public static void addScatteredBlock(BiomeGenerationSettingsBuilder biome, BlockState state, Set<Block> placers, int tries, int chance) {
        biome.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(state), SimpleBlockPlacer.PLACER)).tries(tries).whitelist(placers).func_227317_b_().build()).func_242730_a(FeatureSpread.func_242253_a(-1, 4)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(5).chance(chance));
    }

    public static void addLotuses(BiomeGenerationSettingsBuilder biome) {
        biome.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> OmniFeatures.Configured.RED_LOTUS_FLOWER);
        biome.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> OmniFeatures.Configured.ORANGE_LOTUS_FLOWER);
        biome.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> OmniFeatures.Configured.YELLOW_LOTUS_FLOWER);
        biome.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> OmniFeatures.Configured.BLUE_LOTUS_FLOWER);
        biome.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> OmniFeatures.Configured.PINK_LOTUS_FLOWER);
        biome.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> OmniFeatures.Configured.PURPLE_LOTUS_FLOWER);
        biome.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> OmniFeatures.Configured.BLACK_LOTUS_FLOWER);
        biome.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> OmniFeatures.Configured.WHITE_LOTUS_FLOWER);
    }

    public static void addStoneSpeleothems(BiomeGenerationSettingsBuilder biome) {
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> OmniFeatures.Configured.STONE_SPELEOTHEM.withPlacement(new ConfiguredPlacement<>(Placement.CARVING_MASK, new CaveEdgeConfig(GenerationStage.Carving.AIR, CoreModule.Configuration.COMMON.SPELEOTHEMS_SPAWN_PROBABILITY.get().floatValue()))));
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> OmniFeatures.Configured.STONE_SPELEOTHEM.withPlacement(new ConfiguredPlacement<>(Placement.CARVING_MASK, new CaveEdgeConfig(GenerationStage.Carving.LIQUID, CoreModule.Configuration.COMMON.SPELEOTHEMS_SPAWN_PROBABILITY.get().floatValue()))));
    }

    public static void addIcicles(BiomeGenerationSettingsBuilder biome) {
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> OmniFeatures.Configured.ICICLE.withPlacement(new ConfiguredPlacement<>(Placement.CARVING_MASK, new CaveEdgeConfig(GenerationStage.Carving.AIR, CoreModule.Configuration.COMMON.SPELEOTHEMS_SPAWN_PROBABILITY.get().floatValue() * 1.5F))));
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> OmniFeatures.Configured.ICICLE.withPlacement(new ConfiguredPlacement<>(Placement.CARVING_MASK, new CaveEdgeConfig(GenerationStage.Carving.LIQUID, CoreModule.Configuration.COMMON.SPELEOTHEMS_SPAWN_PROBABILITY.get().floatValue() * 1.5F))));
    }

    public static void addNetherrackSpeleothems(BiomeGenerationSettingsBuilder biome) {
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> OmniFeatures.Configured.NETHERRACK_SPELEOTHEM.withPlacement(new ConfiguredPlacement<>(Placement.CARVING_MASK, new CaveEdgeConfig(GenerationStage.Carving.AIR, CoreModule.Configuration.COMMON.SPELEOTHEMS_SPAWN_PROBABILITY.get().floatValue() * 2.0F))));
    }

    public static void addMalachiteGeodes(BiomeGenerationSettingsBuilder biome) {
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> OmniFeatures.Configured.MALACHITE_GEODE);
    }

    public static void addMushroomCave(BiomeGenerationSettingsBuilder biome) {
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> OmniFeatures.Configured.MUSHROOM_CAVE);
    }

    public static void addIcyCave(BiomeGenerationSettingsBuilder biome) {
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> OmniFeatures.Configured.ICY_CAVE);
    }

    public static void addTerracottaCave(BiomeGenerationSettingsBuilder biome) {
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> OmniFeatures.Configured.TERRACOTTA_CAVE);
    }

    public static void addGoldenOakTrees(BiomeGenerationSettingsBuilder biome) {
        biome.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()), new SimpleBlockStateProvider(OmniBlocks.GOLDEN_OAK_LEAVES.get().getDefaultState()), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1))).setIgnoreVines().build()).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, CoreModule.Configuration.COMMON.GOLDEN_OAK_SPAWN_CHANCE.get().floatValue(), 1))));
    }
}