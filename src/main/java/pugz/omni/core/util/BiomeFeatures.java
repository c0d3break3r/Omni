package pugz.omni.core.util;

import net.minecraft.world.gen.placement.*;
import pugz.omni.common.world.feature.CaveOreFeatureConfig;
import pugz.omni.common.world.feature.cavier_caves.*;
import pugz.omni.core.module.CoreModule;
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
    private static final GeodeFeatureConfig geodeFeatureConfig = new GeodeFeatureConfig(0.35D, 0.083D, true, 4, 7, 3, 5, 1, 3, -16, 16, 0.05D);

    public static void addOreCluster(BiomeGenerationSettingsBuilder biome, RuleTest filler, BlockState state, int size, int bottom, int top, int maxHeight, int spread, int range) {
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> Feature.ORE.withConfiguration(new OreFeatureConfig(filler, state, size)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(bottom, top, maxHeight))).range(range).square().func_242731_b(spread));
    }

    public static void addCaveOreCluster(BiomeGenerationSettingsBuilder biome, RuleTest filler, BlockState state, CaveOreFeatureConfig.CaveFace face, int size, int bottom, int top, int maxHeight, int spread, int range, int chance) {
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> OmniFeatures.CAVE_ORE.get().withConfiguration(new CaveOreFeatureConfig(filler, state, size, face)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(bottom, top, maxHeight)).chance(chance)).range(range).square().func_242731_b(spread));
    }

    public static void addScatteredBlock(BiomeGenerationSettingsBuilder biome, BlockState state, Set<Block> placers, int tries, int chance) {
        biome.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(state), SimpleBlockPlacer.PLACER)).tries(tries).whitelist(placers).func_227317_b_().build()).func_242730_a(FeatureSpread.func_242253_a(-1, 4)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(5).chance(chance));
    }

    public static void addScatteredCaveBlock(BiomeGenerationSettingsBuilder biome, BlockState state, Set<Block> placers, int tries, int chance, float probability) {
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(state), SimpleBlockPlacer.PLACER)).tries(tries).whitelist(placers).func_227317_b_().build()).func_242730_a(FeatureSpread.func_242253_a(-1, 4)).withPlacement(new ConfiguredPlacement<>(Placement.CARVING_MASK, new CaveEdgeConfig(GenerationStage.Carving.LIQUID, probability))).func_242731_b(5).chance(chance));
    }

    public static void addSpeleothems(BiomeGenerationSettingsBuilder biome, SpeleothemFeatureConfig.Variant variant, float probability, int chance) {
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> OmniFeatures.SPELEOTHEM.get().withConfiguration(new SpeleothemFeatureConfig(variant)).withPlacement(new ConfiguredPlacement<>(Placement.CARVING_MASK, new CaveEdgeConfig(GenerationStage.Carving.AIR, probability)).chance(chance)));
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> OmniFeatures.SPELEOTHEM.get().withConfiguration(new SpeleothemFeatureConfig(variant)).withPlacement(new ConfiguredPlacement<>(Placement.CARVING_MASK, new CaveEdgeConfig(GenerationStage.Carving.LIQUID, probability)).chance(chance)));
    }

    public static void addMalachiteGeodes(BiomeGenerationSettingsBuilder biome) {
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> OmniFeatures.GEODE.get().withConfiguration(geodeFeatureConfig).withPlacement((DecoratedPlacement.RANGE.configure(new TopSolidRangeConfig(6, 0, 47)).chance(CoreModule.Configuration.COMMON.MALACHITE_GEODE_SPAWN_CHANCE.get()))));
    }

    public static void addSmallMushrooms(BiomeGenerationSettingsBuilder biome, BlockState state, BlockState smallState, float probability, int chance) {
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> OmniFeatures.CAVE_MUSHROOM.get().withConfiguration(new CaveMushroomFeatureConfig(state, smallState)).withPlacement(new ConfiguredPlacement<>(Placement.CARVING_MASK, new CaveEdgeConfig(GenerationStage.Carving.AIR, probability)).chance(chance)));
    }
}