package pugz.omni.common.world.biome;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import pugz.omni.core.module.CoreModule;

import javax.annotation.Nonnull;

public class TropicalPlainsBiome extends AbstractBiome {
    public TropicalPlainsBiome() {
        super("tropical_plains");
    }

    @Override
    public Biome.Category getCategory() {
        return Biome.Category.JUNGLE;
    }

    @Override
    public float getDepth() {
        return 0.1F;
    }

    @Override
    public float getScale() {
        return 0.2F;
    }

    @Override
    public float getTemperature() {
        return 0.95F;
    }

    @Override
    public float getDownfall() {
        return 0.8F;
    }

    @Nonnull
    @Override
    BiomeManager.BiomeType getBiomeType() {
        return BiomeManager.BiomeType.WARM;
    }

    @Override
    int getWeight() {
        return CoreModule.Configuration.COMMON.TROPICAL_PLAINS_SPAWN_WEIGHT.get();
    }

    @Nonnull
    @Override
    BiomeDictionary.Type[] getBiomeDictionaryTypes() {
        return new BiomeDictionary.Type[]{
                BiomeDictionary.Type.PLAINS,
                BiomeDictionary.Type.HOT,
                BiomeDictionary.Type.WET,
                BiomeDictionary.Type.JUNGLE,
                BiomeDictionary.Type.OVERWORLD
        };
    }

    @Nonnull
    @Override
    BiomeGenerationSettings getGenerationSettings() {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder();
        builder.withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j);
        DefaultBiomeFeatures.withStrongholdAndMineshaft(builder);
        builder.withStructure(StructureFeatures.RUINED_PORTAL_JUNGLE);
        DefaultBiomeFeatures.withCavesAndCanyons(builder);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder);
        DefaultBiomeFeatures.withMonsterRoom(builder);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder);
        DefaultBiomeFeatures.withOverworldOres(builder);
        DefaultBiomeFeatures.withDisks(builder);
        DefaultBiomeFeatures.withJungleEdgeTrees(builder);
        DefaultBiomeFeatures.withWarmFlowers(builder);
        DefaultBiomeFeatures.withJungleGrass(builder);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder);
        DefaultBiomeFeatures.withMelonPatchesAndVines(builder);
        DefaultBiomeFeatures.withFrozenTopLayer(builder);
        return builder.build();
    }

    @Nonnull
    @Override
    MobSpawnInfo getMobSpawns() {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withSpawnsWithExtraChickens(builder);
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PARROT, 10, 1, 1)).withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.OCELOT, 2, 1, 1)).withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PANDA, 1, 1, 2));
        DefaultBiomeFeatures.withBatsAndHostiles(builder);
        return builder.copy();
    }
}