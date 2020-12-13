package pugz.omni.core.util;

import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;

import java.util.function.Supplier;

public class BiomeGenUtils {
    public static void removeFeature(BiomeGenerationSettingsBuilder biome, Supplier<ConfiguredFeature<?, ?>> feature, GenerationStage.Decoration stage) {
        biome.getFeatures(stage).remove(feature);
    }
}