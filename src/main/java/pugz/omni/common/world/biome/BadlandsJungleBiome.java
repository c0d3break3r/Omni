package pugz.omni.common.world.biome;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import pugz.omni.core.registry.OmniSurfaceBuilders;

import javax.annotation.Nonnull;

public class BadlandsJungleBiome extends AbstractBiome {
    public BadlandsJungleBiome() {
        super("badlands_jungle");
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
        return 2;
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
        builder.withSurfaceBuilder(() -> OmniSurfaceBuilders.Configured.JUNGLE_BADLANDS);
        builder.withStructure(StructureFeatures.RUINED_PORTAL_JUNGLE);
        builder.withStructure(StructureFeatures.JUNGLE_PYRAMID);
        DefaultBiomeFeatures.withBadlandsStructures(builder);
        DefaultBiomeFeatures.withCavesAndCanyons(builder);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder);
        DefaultBiomeFeatures.withMonsterRoom(builder);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder);
        DefaultBiomeFeatures.withOverworldOres(builder);
        DefaultBiomeFeatures.withExtraGoldOre(builder);
        DefaultBiomeFeatures.withDisks(builder);
        DefaultBiomeFeatures.withWarmFlowers(builder);
        DefaultBiomeFeatures.withBadlandsGrassAndBush(builder);
        DefaultBiomeFeatures.withBadlandsVegetation(builder);
        DefaultBiomeFeatures.withJungleGrass(builder);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder);
        DefaultBiomeFeatures.withMelonPatchesAndVines(builder);
        DefaultBiomeFeatures.withFrozenTopLayer(builder);
        DefaultBiomeFeatures.withJungleTrees(builder);
        DefaultBiomeFeatures.withLightBambooVegetation(builder);
        return builder.build();
    }

    @Nonnull
    @Override
    MobSpawnInfo getMobSpawns() {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PARROT, 10, 1, 1)).withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.OCELOT, 2, 1, 1)).withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PANDA, 1, 1, 2));
        builder.isValidSpawnBiomeForPlayer();
        DefaultBiomeFeatures.withBatsAndHostiles(builder);
        DefaultBiomeFeatures.withSpawnsWithExtraChickens(builder);
        return builder.copy();
    }
}