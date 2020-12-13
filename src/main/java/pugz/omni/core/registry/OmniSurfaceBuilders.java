package pugz.omni.core.registry;

import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;
import pugz.omni.core.util.RegistryUtil;

public class OmniSurfaceBuilders {
    public static RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> JUNGLE_BADLANDS;

    public static class Configured {
        public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> JUNGLE_BADLANDS = RegistryUtil.createConfiguredSurfaceBuilder("jungle_badlands", OmniSurfaceBuilders.JUNGLE_BADLANDS.get().func_242929_a(SurfaceBuilder.GRASS_DIRT_SAND_CONFIG));
    }
}