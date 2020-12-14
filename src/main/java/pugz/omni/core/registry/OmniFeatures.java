package pugz.omni.core.registry;

import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import pugz.omni.common.world.feature.ExposedOreFeatureConfig;
import pugz.omni.common.world.feature.SizedBlockBlobConfig;
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
    public static RegistryObject<Feature<CaveBiomeFeatureConfig>> TERRACOTTA_CAVE;

    //core
    public static RegistryObject<Feature<ExposedOreFeatureConfig>> EXPOSED_ORE;

    //paradise
    public static RegistryObject<Feature<SizedBlockBlobConfig>> SIZED_BLOCK_BLOB;

    //wild west
    public static RegistryObject<Feature<NoFeatureConfig>> SAGUARO_CACTUS;

    public static class Configured {
        //cavier caves
        public static ConfiguredFeature<?, ?> STONE_SPELEOTHEM;
        public static ConfiguredFeature<?, ?> ICICLE;
        public static ConfiguredFeature<?, ?> NETHERRACK_SPELEOTHEM;
        public static ConfiguredFeature<?, ?> MALACHITE_GEODE;
        public static ConfiguredFeature<?, ?> MUSHROOM_CAVE;
        public static ConfiguredFeature<?, ?> ICY_CAVE;
        public static ConfiguredFeature<?, ?> TERRACOTTA_CAVE;

        //forestry
        public static ConfiguredFeature<?, ?> TALL_OAK_TREE;
        public static ConfiguredFeature<?, ?> TALL_OAK_TREES;
        public static ConfiguredFeature<?, ?> GOLDEN_APPLE_TREE;

        //paradise
        public static ConfiguredFeature<?, ?> RED_LOTUS_FLOWER;
        public static ConfiguredFeature<?, ?> ORANGE_LOTUS_FLOWER;
        public static ConfiguredFeature<?, ?> YELLOW_LOTUS_FLOWER;
        public static ConfiguredFeature<?, ?> BLUE_LOTUS_FLOWER;
        public static ConfiguredFeature<?, ?> PINK_LOTUS_FLOWER;
        public static ConfiguredFeature<?, ?> PURPLE_LOTUS_FLOWER;
        public static ConfiguredFeature<?, ?> BLACK_LOTUS_FLOWER;
        public static ConfiguredFeature<?, ?> WHITE_LOTUS_FLOWER;

        public static ConfiguredFeature<?, ?> TERRACOTTA_ROCK;

        //wild west
        public static ConfiguredFeature<?, ?> RED_ROCK;
        public static ConfiguredFeature<?, ?> SAGUARO_CACTUS;

        public static ConfiguredFeature<?, ?> DENSE_SAVANNA_TREES;
    }
}