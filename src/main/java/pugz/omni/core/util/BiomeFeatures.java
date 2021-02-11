package pugz.omni.core.util;

import net.minecraft.world.gen.placement.*;
import pugz.omni.common.world.feature.ExposedOreFeatureConfig;
import pugz.omni.core.module.CoreModule;
import pugz.omni.core.registry.OmniFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;

import javax.annotation.Nullable;

public class BiomeFeatures {
    public static void addExposedOreCluster(BiomeGenerationSettingsBuilder biome, RuleTest filler, BlockState state, @Nullable BlockState fillerState, ExposedOreFeatureConfig.CaveFace face, int size, int bottom, int top, int maxHeight, int spread, int range, int chance) {
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> OmniFeatures.EXPOSED_ORE.get().withConfiguration(new ExposedOreFeatureConfig(filler, state, fillerState, size, face)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(bottom, top, maxHeight)).chance(chance)).range(range).square().func_242731_b(spread));
    }

    public static void addRedRock(BiomeGenerationSettingsBuilder biome) {
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> OmniFeatures.Configured.RED_ROCK);
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
        biome.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> OmniFeatures.Configured.GOLDEN_APPLE_TREE);
    }

    public static void addSaguaroCacti(BiomeGenerationSettingsBuilder biome) {
        biome.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> OmniFeatures.Configured.SAGUARO_CACTUS);
    }

    public static void addTerracottaRocks(BiomeGenerationSettingsBuilder biome) {
        biome.getFeatures(GenerationStage.Decoration.LOCAL_MODIFICATIONS).add(() -> OmniFeatures.Configured.TERRACOTTA_ROCK);
    }

    public static void addTallOakTrees(BiomeGenerationSettingsBuilder biome) {
        biome.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> OmniFeatures.Configured.TALL_OAK_TREES);
    }

    public static void addDenseSavannaTrees(BiomeGenerationSettingsBuilder biome) {
        biome.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> OmniFeatures.Configured.DENSE_SAVANNA_TREES);
    }

    public static void addPaloVerdeTrees(BiomeGenerationSettingsBuilder biome) {
        biome.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> OmniFeatures.Configured.PALO_VERDE_TREES);
    }
}