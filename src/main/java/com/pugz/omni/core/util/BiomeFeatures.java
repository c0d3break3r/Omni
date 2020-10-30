package com.pugz.omni.core.util;

import com.pugz.omni.common.world.feature.cavier_caves.SpeleothemFeatureConfig;
import com.pugz.omni.core.registry.OmniFeatures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.CaveEdgeConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import java.util.Set;

public class BiomeFeatures {
    public static BlockClusterFeatureConfig scatteredBlockConfig(BlockState state, Set<Block> placers, int freq) {
        return (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(state), SimpleBlockPlacer.PLACER)).tries(freq).whitelist(placers).func_227317_b_().build();
    }

    public static void addScatteredBlock(BiomeGenerationSettingsBuilder biome, BlockState state, Set<Block> placers, int freq, int chance) {
        biome.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> Feature.RANDOM_PATCH.withConfiguration(scatteredBlockConfig(state, placers, freq)).func_242730_a(FeatureSpread.func_242253_a(-1, 4)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(5).chance(chance));
    }

    public static void addSpeleothems(BiomeGenerationSettingsBuilder biome, SpeleothemFeatureConfig.Variant variant) {
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> OmniFeatures.SPELEOTHEM.get().withConfiguration(new SpeleothemFeatureConfig(variant)).withPlacement(new ConfiguredPlacement<>(Placement.CARVING_MASK, new CaveEdgeConfig(GenerationStage.Carving.AIR, 0.004F))));
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> OmniFeatures.SPELEOTHEM.get().withConfiguration(new SpeleothemFeatureConfig(variant)).withPlacement(new ConfiguredPlacement<>(Placement.CARVING_MASK, new CaveEdgeConfig(GenerationStage.Carving.LIQUID, 0.004F))));
    }
}