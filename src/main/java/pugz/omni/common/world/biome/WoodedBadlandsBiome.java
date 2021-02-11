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
import pugz.omni.core.module.CoreModule;
import pugz.omni.core.registry.OmniSurfaceBuilders;

import javax.annotation.Nonnull;

public class WoodedBadlandsBiome extends AbstractBiome {
    public WoodedBadlandsBiome() {
        super("wooded_badlands");
    }

    @Override
    public Biome.Category getCategory() {
        return Biome.Category.MESA;
    }

    @Override
    public Biome.RainType getRainType() {
        return Biome.RainType.NONE;
    }

    @Override
    public float getDepth() {
        return 0.125F;
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
        return 0.0F;
    }

    @Nonnull
    @Override
    BiomeManager.BiomeType getBiomeType() {
        return BiomeManager.BiomeType.DESERT;
    }

    @Override
    int getWeight() {
        return CoreModule.Configuration.COMMON.WOODED_BADLANDS_SPAWN_WEIGHT.get();
    }

    @Nonnull
    @Override
    BiomeDictionary.Type[] getBiomeDictionaryTypes() {
        return new BiomeDictionary.Type[]{
                BiomeDictionary.Type.DRY,
                BiomeDictionary.Type.OVERWORLD,
                BiomeDictionary.Type.SPARSE,
                BiomeDictionary.Type.MESA,
                BiomeDictionary.Type.HOT
        };
    }

    @Nonnull
    @Override
    BiomeGenerationSettings getGenerationSettings() {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder();
        builder.withSurfaceBuilder(() -> OmniSurfaceBuilders.Configured.WOODED_BADLANDS);
        builder.withStructure(StructureFeatures.RUINED_PORTAL_DESERT);
        DefaultBiomeFeatures.withBadlandsStructures(builder);
        DefaultBiomeFeatures.withFossils(builder);
        builder.withStructure(StructureFeatures.VILLAGE_SAVANNA);
        builder.withStructure(StructureFeatures.PILLAGER_OUTPOST);
        DefaultBiomeFeatures.withCavesAndCanyons(builder);
        DefaultBiomeFeatures.withLavaLakes(builder);
        DefaultBiomeFeatures.withMonsterRoom(builder);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder);
        DefaultBiomeFeatures.withOverworldOres(builder);
        DefaultBiomeFeatures.withDisks(builder);
        DefaultBiomeFeatures.withWarmFlowers(builder);
        DefaultBiomeFeatures.withSavannaGrass(builder);
        DefaultBiomeFeatures.withBadlandsGrassAndBush(builder);
        DefaultBiomeFeatures.withBadlandsVegetation(builder);
        DefaultBiomeFeatures.withDesertDeadBushes(builder);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder);
        DefaultBiomeFeatures.withDesertVegetation(builder);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder);
        DefaultBiomeFeatures.withFrozenTopLayer(builder);
        return builder.build();
    }

    @Nonnull
    @Override
    MobSpawnInfo getMobSpawns() {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withDesertMobs(builder);
        DefaultBiomeFeatures.withPassiveMobs(builder);
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.HORSE, 1, 2, 6)).withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.DONKEY, 1, 1, 1));
        builder.isValidSpawnBiomeForPlayer();
        return builder.copy();
    }
}