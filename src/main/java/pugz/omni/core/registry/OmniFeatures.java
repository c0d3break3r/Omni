package pugz.omni.core.registry;

import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import pugz.omni.common.world.feature.ExposedOreFeatureConfig;
import pugz.omni.common.world.feature.cavier_caves.caves.CaveBiomeFeatureConfig;
import pugz.omni.common.world.feature.cavier_caves.GeodeFeatureConfig;
import pugz.omni.common.world.feature.cavier_caves.SpeleothemFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;

public class OmniFeatures {
    //cavier caves
    public static RegistryObject<Feature<SpeleothemFeatureConfig>> SPELEOTHEM;
    public static RegistryObject<Feature<GeodeFeatureConfig>> GEODE;
    public static RegistryObject<Feature<CaveBiomeFeatureConfig>> MUSHROOM_CAVE;
    public static RegistryObject<Feature<CaveBiomeFeatureConfig>> ICY_CAVE;

    //core
    public static RegistryObject<Feature<ExposedOreFeatureConfig>> EXPOSED_ORE;

    //forestry
    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> TALL_OAK_TREE;
}