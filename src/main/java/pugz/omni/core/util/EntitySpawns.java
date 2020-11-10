package pugz.omni.core.util;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

import java.util.function.Predicate;

public class EntitySpawns {
    protected static Predicate<Biome> warmOceanCondition() {
        return biome -> biome.getRegistryName() == Biomes.WARM_OCEAN.getRegistryName() || biome.getRegistryName() == Biomes.DEEP_WARM_OCEAN.getRegistryName();
    }
}