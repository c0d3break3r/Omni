package pugz.omni.core.registry;

import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import pugz.omni.core.util.RegistryUtil;

public class OmniSurfaceBuilders {
    public static SurfaceBuilder<SurfaceBuilderConfig> JUNGLE_BADLANDS;

    public static class Configured {
        public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> JUNGLE_BADLANDS = RegistryUtil.createConfiguredSurfaceBuilder("jungle_badlands", new ConfiguredSurfaceBuilder<>(OmniSurfaceBuilders.JUNGLE_BADLANDS, SurfaceBuilder.RED_SAND_WHITE_TERRACOTTA_GRAVEL_CONFIG));
    }
}