package pugz.omni.core.registry;

import pugz.omni.common.world.feature.cavier_caves.GeodeFeatureConfig;
import pugz.omni.common.world.feature.cavier_caves.SpeleothemFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;

public class OmniFeatures {
    public static RegistryObject<Feature<SpeleothemFeatureConfig>> SPELEOTHEM;

    public static RegistryObject<Feature<GeodeFeatureConfig>> GEODE;
}