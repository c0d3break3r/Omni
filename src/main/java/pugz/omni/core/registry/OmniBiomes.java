package pugz.omni.core.registry;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import pugz.omni.core.util.BiomeFeatures;

public class OmniBiomes {
    //colormatic
    public static RegistryKey<Biome> FLOWER_FIELD;

    //forestry
    public static RegistryKey<Biome> TALL_FOREST;

    //paradise
    public static RegistryKey<Biome> TROPICAL_PLAINS;

    public static Biome createFlowerFieldBiome() {
        MobSpawnInfo.Builder mobspawninfo$builder = getStandardMobSpawnBuilder().withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.RABBIT, 4, 2, 3));
        DefaultBiomeFeatures.withSpawnsWithHorseAndDonkey(mobspawninfo$builder);
        mobspawninfo$builder.isValidSpawnBiomeForPlayer();
        BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j);
        biomegenerationsettings$builder.withStructure(StructureFeatures.VILLAGE_PLAINS).withStructure(StructureFeatures.PILLAGER_OUTPOST);
        DefaultBiomeFeatures.withStrongholdAndMineshaft(biomegenerationsettings$builder);
        biomegenerationsettings$builder.withStructure(StructureFeatures.RUINED_PORTAL);
        DefaultBiomeFeatures.withCavesAndCanyons(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withLavaAndWaterLakes(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withMonsterRoom(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withNoiseTallGrass(biomegenerationsettings$builder);
        biomegenerationsettings$builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_SUNFLOWER);
        DefaultBiomeFeatures.withCommonOverworldBlocks(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withOverworldOres(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withDisks(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withPlainGrassVegetation(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withNormalMushroomGeneration(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withLavaAndWaterSprings(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withFrozenTopLayer(biomegenerationsettings$builder);
        return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).category(Biome.Category.PLAINS).depth(0.125F).scale(0.05F).temperature(0.8F).downfall(0.4F).setEffects((new BiomeAmbience.Builder()).setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(getSkyColorWithTemperatureModifier(0.8F)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(mobspawninfo$builder.copy()).withGenerationSettings(biomegenerationsettings$builder.build()).build();
    }

    public static Biome createTallForestBiome() {
        BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j);
        MobSpawnInfo.Builder mobspawninfo$builder = getStandardMobSpawnBuilder().withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.WOLF, 5, 4, 4)).isValidSpawnBiomeForPlayer();
        DefaultBiomeFeatures.withStrongholdAndMineshaft(biomegenerationsettings$builder);
        biomegenerationsettings$builder.withStructure(StructureFeatures.RUINED_PORTAL);
        DefaultBiomeFeatures.withCavesAndCanyons(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withLavaAndWaterLakes(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withMonsterRoom(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withAllForestFlowerGeneration(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withCommonOverworldBlocks(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withOverworldOres(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withDisks(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withDefaultFlowers(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withForestGrass(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withNormalMushroomGeneration(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withLavaAndWaterSprings(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withFrozenTopLayer(biomegenerationsettings$builder);
        return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).category(Biome.Category.FOREST).depth(0.1F).scale(0.4F).temperature(0.7F).downfall(0.8F).setEffects((new BiomeAmbience.Builder()).setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(getSkyColorWithTemperatureModifier(0.7F)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(mobspawninfo$builder.copy()).withGenerationSettings(biomegenerationsettings$builder.build()).build();
    }

    public static int getSkyColorWithTemperatureModifier(float temperature) {
        float lvt_1_1_ = temperature / 3.0F;
        lvt_1_1_ = MathHelper.clamp(lvt_1_1_, -1.0F, 1.0F);
        return MathHelper.hsvToRGB(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
    }

    public static MobSpawnInfo.Builder getStandardMobSpawnBuilder() {
        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withPassiveMobs(mobspawninfo$builder);
        DefaultBiomeFeatures.withBatsAndHostiles(mobspawninfo$builder);
        return mobspawninfo$builder;
    }
}