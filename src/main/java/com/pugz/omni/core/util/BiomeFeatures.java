package com.pugz.omni.core.util;

import com.google.common.collect.ImmutableSet;
import com.pugz.omni.common.world.feature.cavier_caves.SpeleothemFeatureConfig;
import com.pugz.omni.core.registry.OmniBlocks;
import com.pugz.omni.core.registry.OmniFeatures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.Set;

public class BiomeFeatures {
    public static BlockClusterFeatureConfig scatteredBlockConfig(BlockState state, Set<Block> placers, int freq) {
        return (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(state), SimpleBlockPlacer.PLACER)).tries(freq).whitelist(placers).func_227317_b_().build();
    }

    public static void addScatteredBlock(BiomeGenerationSettingsBuilder biome, BlockState state, Set<Block> placers, int freq) {
        biome.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> Feature.RANDOM_PATCH.withConfiguration(scatteredBlockConfig(state, placers, freq)).withPlacement(Placement.field_242905_i.configure(new NoPlacementConfig())));
    }

    public static void addSpeleothems(BiomeGenerationSettingsBuilder biome, SpeleothemFeatureConfig.Variant variant) {
        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> OmniFeatures.SPELEOTHEM.get().withConfiguration(new SpeleothemFeatureConfig(variant)).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242729_a(1));
    }

    public static void onBiomeLoading(BiomeLoadingEvent event) {
        Biome.Category category = event.getCategory();
        BiomeGenerationSettingsBuilder biome = event.getGeneration();

        if (category == Biome.Category.JUNGLE) {
            addScatteredBlock(biome, OmniBlocks.RED_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 64);
            addScatteredBlock(biome, OmniBlocks.BLUE_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 64);
            addScatteredBlock(biome, OmniBlocks.PINK_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 64);
            addScatteredBlock(biome, OmniBlocks.BLACK_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 24);
            addScatteredBlock(biome, OmniBlocks.WHITE_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 24);
        }

        if (category == Biome.Category.ICY) {
            addSpeleothems(biome, SpeleothemFeatureConfig.Variant.ICE);
        }
        if (category == Biome.Category.NETHER) {
            addSpeleothems(biome, SpeleothemFeatureConfig.Variant.NETHERRACK);
        }
        if (category == Biome.Category.THEEND) {
            addSpeleothems(biome, SpeleothemFeatureConfig.Variant.END_STONE);
        }
        else addSpeleothems(biome, SpeleothemFeatureConfig.Variant.STONE);
    }
}