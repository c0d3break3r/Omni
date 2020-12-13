package pugz.omni.common.world.biome;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.*;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.registries.ForgeRegistries;
import pugz.omni.core.Omni;
import pugz.omni.core.util.IBaseBiome;

import javax.annotation.Nonnull;

public abstract class OmniBiome implements IBaseBiome {
    private final String name;
    private final Biome biome;
    private final RegistryKey<Biome> key;

    public OmniBiome(String name) {
        this.name = name;
        this.biome = new Biome.Builder()
                .category(this.getCategory())
                .depth(this.getDepth())
                .downfall(this.getDownfall())
                .precipitation(this.getRainType())
                .scale(this.getScale())
                .temperature(this.getTemperature())
                .setEffects(new BiomeAmbience.Builder()
                        .setFogColor(this.getFogColor())
                        .setWaterColor(this.getWaterColor())
                        .setWaterFogColor(this.getWaterFogColor())
                        .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE)
                        .withSkyColor(this.getSkyColorWithTemperatureModifier(this.getTemperature()))
                        .build())
                .withGenerationSettings(this.getGenerationSettings())
                .withMobSpawnSettings(this.getMobSpawns())
                .build();

        BiomeManager.addBiome(this.getBiomeType(), new BiomeManager.BiomeEntry(this.getKey(), this.getWeight()));
        BiomeDictionary.addTypes(this.getKey(), this.getBiomeDictionaryTypes());

        this.key = RegistryKey.getOrCreateKey(ForgeRegistries.Keys.BIOMES, new ResourceLocation(Omni.MOD_ID, this.name));
    }

    public String getName() {
        return name;
    }

    public Biome getBiome() {
        return biome;
    }

    public RegistryKey<Biome> getKey() {
        return key;
    }

    @Nonnull
    abstract BiomeManager.BiomeType getBiomeType();

    abstract int getWeight();

    @Nonnull
    abstract BiomeDictionary.Type[] getBiomeDictionaryTypes();

    @Nonnull
    abstract BiomeGenerationSettings getGenerationSettings();

    @Nonnull
    abstract MobSpawnInfo getMobSpawns();

    public int getSkyColorWithTemperatureModifier(float temperature) {
        float lvt_1_1_ = temperature / 3.0F;
        lvt_1_1_ = MathHelper.clamp(lvt_1_1_, -1.0F, 1.0F);
        return MathHelper.hsvToRGB(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
    }
}