package pugz.omni.common.world.structure.wild_west;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import pugz.omni.core.Omni;
import pugz.omni.core.registry.OmniStructures;

import javax.annotation.Nonnull;

public class GhostTownStructure extends JigsawStructure {
    public static final IStructureProcessorType<GhostTownPools.GhostTownLootProcessor> GHOST_TOWN_PROCESSOR = () -> GhostTownPools.GhostTownLootProcessor.CODEC;

    public GhostTownStructure(Codec<VillageConfig> p_i231927_1_) {
        super(p_i231927_1_, 33, false, false);
    }

    protected boolean func_230363_a_(ChunkGenerator generator, BiomeProvider provider, long seed, SharedSeedRandom random, int p_230363_6_, int p_230363_7_, Biome biome, ChunkPos chunkPos, VillageConfig config) {
        return random.nextInt(5) >= 2;
    }

    public static void setup() {
        Structure.NAME_STRUCTURE_BIMAP.put(OmniStructures.GHOST_TOWN.get().getRegistryName().toString(), OmniStructures.GHOST_TOWN.get());
        Structure.field_236384_t_ = ImmutableList.<Structure<?>>builder().addAll(Structure.field_236384_t_).add(OmniStructures.GHOST_TOWN.get()).build();
        DimensionStructuresSettings.field_236191_b_ = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder().putAll(DimensionStructuresSettings.field_236191_b_).put(OmniStructures.GHOST_TOWN.get(), new StructureSeparationSettings(10, 5, 1928563917)).build();
    }

    @Nonnull
    @Override
    public GenerationStage.Decoration getDecorationStage() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }
}