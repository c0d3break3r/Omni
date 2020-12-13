package pugz.omni.core.registry;

import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;
import pugz.omni.core.util.RegistryUtil;

public class OmniSurfaceBuilders {
    public static RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> JUNGLE_BADLANDS;
    public static RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> JUNGLE_DESERT;
    public static RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> WOODED_BADLANDS;
    public static RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> WOODED_DESERT;

    public static class Configured {
        public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> JUNGLE_BADLANDS = RegistryUtil.createConfiguredSurfaceBuilder("jungle_badlands", OmniSurfaceBuilders.JUNGLE_BADLANDS.get().func_242929_a(SurfaceBuilder.GRASS_DIRT_SAND_CONFIG));
        public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> JUNGLE_DESERT = RegistryUtil.createConfiguredSurfaceBuilder("jungle_desert", OmniSurfaceBuilders.JUNGLE_DESERT.get().func_242929_a(SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG));
        public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> WOODED_BADLANDS = RegistryUtil.createConfiguredSurfaceBuilder("wooded_badlands", OmniSurfaceBuilders.WOODED_BADLANDS.get().func_242929_a(SurfaceBuilder.GRASS_DIRT_SAND_CONFIG));
        public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> WOODED_DESERT = RegistryUtil.createConfiguredSurfaceBuilder("wooded_desert", OmniSurfaceBuilders.WOODED_DESERT.get().func_242929_a(SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG));
    }
}