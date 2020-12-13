package pugz.omni.core.util;

import net.minecraft.world.biome.Biome;

public interface IBaseBiome {
    default Biome.Category getCategory() {
        return Biome.Category.PLAINS;
    }

    default Biome.RainType getRainType() {
        return Biome.RainType.RAIN;
    }

    default float getTemperature() {
        return 0.7F;
    }

    default float getDownfall() {
        return 0.8F;
    }

    default float getDepth() {
        return 0.125F;
    }

    default float getScale() {
        return 0.05F;
    }

    default int getWaterColor() {
        return 4159204;
    }

    default int getWaterFogColor() {
        return 329011;
    }

    default int getFogColor() {
        return 12638463;
    }
}