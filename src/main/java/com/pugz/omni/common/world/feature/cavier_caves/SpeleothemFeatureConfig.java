package com.pugz.omni.common.world.feature.cavier_caves;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pugz.omni.core.registry.OmniBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class SpeleothemFeatureConfig implements IFeatureConfig {
    public static final Codec<SpeleothemFeatureConfig> codec = RecordCodecBuilder.create((builder) -> {
        return builder.group(SpeleothemFeatureConfig.Variant.codec.fieldOf("variant").forGetter((p_236570_0_) -> {
            return p_236570_0_.variant;
        })).apply(builder, SpeleothemFeatureConfig::new);
    });

    public final SpeleothemFeatureConfig.Variant variant;

    public SpeleothemFeatureConfig(SpeleothemFeatureConfig.Variant variantIn) {
        variant = variantIn;
    }

    public enum Variant implements IStringSerializable {
        STONE("stone", OmniBlocks.STONE_SPELEOTHEM.get().getDefaultState(), Blocks.STONE, Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE, Blocks.OBSIDIAN),
        ICE("ice", OmniBlocks.ICE_SPELEOTHEM.get().getDefaultState(), Blocks.STONE, Blocks.PACKED_ICE, Blocks.ICE),
        NETHERRACK("netherrack", OmniBlocks.NETHERRACK_SPELEOTHEM.get().getDefaultState(), Blocks.NETHERRACK, Blocks.NETHER_BRICKS),
        END_STONE("end_stone", OmniBlocks.END_STONE_SPELEOTHEM.get().getDefaultState(), Blocks.END_STONE);

        public static final Codec<SpeleothemFeatureConfig.Variant> codec = IStringSerializable.createEnumCodec(SpeleothemFeatureConfig.Variant::values, SpeleothemFeatureConfig.Variant::byName);
        private final String name;
        private final BlockState state;
        private final Block[] spawnableBlocks;
        private static final Map<String, Variant> VALUES_MAP = Arrays.stream(values()).collect(Collectors.toMap(SpeleothemFeatureConfig.Variant::getName, (p_236573_0_) -> {
            return p_236573_0_;
        }));

        Variant(String name, BlockState state, Block... spawnableBlocks) {
            this.name = name;
            this.state = state;
            this.spawnableBlocks = spawnableBlocks;
        }

        public String getName() {
            return this.name;
        }

        public BlockState getState() {
            return this.state;
        }

        public Block[] getSpawnableBlocks() {
            return this.spawnableBlocks;
        }

        public static SpeleothemFeatureConfig.Variant byName(String nameIn) {
            return VALUES_MAP.get(nameIn);
        }

        public String getString() {
            return this.name;
        }
    }
}