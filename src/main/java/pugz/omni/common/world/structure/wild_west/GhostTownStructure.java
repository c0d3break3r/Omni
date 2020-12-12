package pugz.omni.common.world.structure.wild_west;

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
import pugz.omni.core.Omni;

import javax.annotation.Nonnull;

public class GhostTownStructure extends JigsawStructure {
    public static final IStructureProcessorType<GhostTownPools.GhostTownLootProcessor> GHOST_TOWN_PROCESSOR = () -> GhostTownPools.GhostTownLootProcessor.CODEC;

    public GhostTownStructure(Codec<VillageConfig> p_i231927_1_) {
        super(p_i231927_1_, 33, false, false);
    }

    protected boolean func_230363_a_(ChunkGenerator generator, BiomeProvider provider, long seed, SharedSeedRandom random, int p_230363_6_, int p_230363_7_, Biome biome, ChunkPos chunkPos, VillageConfig config) {
        return random.nextInt(5) >= 2;
    }

    public static void registerShit() {
        Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(Omni.MOD_ID, "ghost_town"), GHOST_TOWN_PROCESSOR);
    }

    @Nonnull
    @Override
    public GenerationStage.Decoration getDecorationStage() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }
}