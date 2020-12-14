package pugz.omni.common.world.biome;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

import javax.annotation.Nonnull;

public class FlowerFieldBiome extends AbstractBiome {
    public FlowerFieldBiome() {
        super("flower_field");
    }

    @Override
    public Biome.Category getCategory() {
        return Biome.Category.PLAINS;
    }

    @Override
    public float getDepth() {
        return 0.125F;
    }

    @Override
    public float getScale() {
        return 0.05F;
    }

    @Override
    public float getTemperature() {
        return 0.8F;
    }

    @Override
    public float getDownfall() {
        return 0.4F;
    }

    @Nonnull
    @Override
    BiomeManager.BiomeType getBiomeType() {
        return BiomeManager.BiomeType.COOL;
    }

    @Override
    int getWeight() {
        return 3;
    }

    @Nonnull
    @Override
    BiomeDictionary.Type[] getBiomeDictionaryTypes() {
        return new BiomeDictionary.Type[]{
                BiomeDictionary.Type.FOREST,
                BiomeDictionary.Type.DENSE,
                BiomeDictionary.Type.RARE,
                BiomeDictionary.Type.OVERWORLD
        };
    }

    @Nonnull
    @Override
    BiomeGenerationSettings getGenerationSettings() {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder();
        builder.withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j);
        builder.withStructure(StructureFeatures.VILLAGE_PLAINS).withStructure(StructureFeatures.PILLAGER_OUTPOST);
        DefaultBiomeFeatures.withStrongholdAndMineshaft(builder);
        builder.withStructure(StructureFeatures.RUINED_PORTAL);
        DefaultBiomeFeatures.withCavesAndCanyons(builder);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder);
        DefaultBiomeFeatures.withMonsterRoom(builder);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.FOREST_FLOWER_VEGETATION_COMMON);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.FLOWER_FOREST);
        DefaultBiomeFeatures.withBadlandsGrass(builder);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_SUNFLOWER);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder);
        DefaultBiomeFeatures.withOverworldOres(builder);
        DefaultBiomeFeatures.withDisks(builder);
        DefaultBiomeFeatures.withPlainGrassVegetation(builder);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder);
        DefaultBiomeFeatures.withFrozenTopLayer(builder);
        return builder.build();
    }

    @Nonnull
    @Override
    MobSpawnInfo getMobSpawns() {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withPassiveMobs(builder);
        DefaultBiomeFeatures.withBatsAndHostiles(builder);
        DefaultBiomeFeatures.withSpawnsWithHorseAndDonkey(builder);
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.RABBIT, 4, 2, 3));
        builder.isValidSpawnBiomeForPlayer();
        return builder.copy();
    }
}