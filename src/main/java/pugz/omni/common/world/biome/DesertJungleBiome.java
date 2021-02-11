package pugz.omni.common.world.biome;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import pugz.omni.core.module.CoreModule;
import pugz.omni.core.registry.OmniSurfaceBuilders;

import javax.annotation.Nonnull;

public class DesertJungleBiome extends AbstractBiome {
    public DesertJungleBiome() {
        super("desert_jungle");
    }

    @Override
    public Biome.Category getCategory() {
        return Biome.Category.JUNGLE;
    }

    @Override
    public float getDepth() {
        return 0.125F;
    }

    @Override
    public float getScale() {
        return 0.1F;
    }

    @Override
    public float getTemperature() {
        return 1.0F;
    }

    @Override
    public float getDownfall() {
        return 0.7F;
    }

    @Nonnull
    @Override
    BiomeManager.BiomeType getBiomeType() {
        return BiomeManager.BiomeType.WARM;
    }

    @Override
    int getWeight() {
        return CoreModule.Configuration.COMMON.DESERT_JUNGLE_SPAWN_WEIGHT.get();
    }

    @Nonnull
    @Override
    BiomeDictionary.Type[] getBiomeDictionaryTypes() {
        return new BiomeDictionary.Type[]{
                BiomeDictionary.Type.MESA,
                BiomeDictionary.Type.HOT,
                BiomeDictionary.Type.WET,
                BiomeDictionary.Type.JUNGLE,
                BiomeDictionary.Type.OVERWORLD,
                BiomeDictionary.Type.DENSE,
                BiomeDictionary.Type.LUSH
        };
    }

    @Nonnull
    @Override
    BiomeGenerationSettings getGenerationSettings() {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder();
        builder.withSurfaceBuilder(() -> OmniSurfaceBuilders.Configured.JUNGLE_DESERT);
        DefaultBiomeFeatures.withStrongholdAndMineshaft(builder);
        builder.withStructure(StructureFeatures.RUINED_PORTAL_DESERT);
        builder.withStructure(StructureFeatures.DESERT_PYRAMID);
        DefaultBiomeFeatures.withFossils(builder);
        builder.withStructure(StructureFeatures.VILLAGE_DESERT);
        builder.withStructure(StructureFeatures.PILLAGER_OUTPOST);
        DefaultBiomeFeatures.withCavesAndCanyons(builder);
        DefaultBiomeFeatures.withLavaLakes(builder);
        DefaultBiomeFeatures.withMonsterRoom(builder);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder);
        DefaultBiomeFeatures.withOverworldOres(builder);
        DefaultBiomeFeatures.withDisks(builder);
        DefaultBiomeFeatures.withDefaultFlowers(builder);
        DefaultBiomeFeatures.withBadlandsGrass(builder);
        DefaultBiomeFeatures.withDesertDeadBushes(builder);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder);
        DefaultBiomeFeatures.withDesertVegetation(builder);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder);
        DefaultBiomeFeatures.withDesertWells(builder);
        DefaultBiomeFeatures.withFrozenTopLayer(builder);
        DefaultBiomeFeatures.withJungleTrees(builder);
        DefaultBiomeFeatures.withLightBambooVegetation(builder);
        return builder.build();
    }

    @Nonnull
    @Override
    MobSpawnInfo getMobSpawns() {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withDesertMobs(builder);
        builder.isValidSpawnBiomeForPlayer();
        return builder.copy();
    }
}