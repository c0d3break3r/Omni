package com.pugz.omni.core.module;

public class ForestryModule extends AbstractModule {
    public static final ForestryModule instance = new ForestryModule();

    public ForestryModule() {
        super("Forestry");
    }

    @Override
    protected void registerBlocks() {
    }

    @Override
    protected void registerItems() {
        //RegistryObject<Item> RAW_BIRD;
        //RegistryObject<Item> COOKED_BIRD;

        //RegistryObject<Item> HEARTBEET;

        //RegistryObject<Item> HONEY_CHESTPLATE;
        //RegistryObject<Item> ROYAL_JELLY;

        //RegistryObject<Item> ENCHANTED_GOLDEN_CARROT;
    }

    @Override
    protected void registerEntities() {
        //RegistryObject<EntityType<?>> SONGBIRD;
        //RegistryObject<EntityType<?>> CROPLINGS;
        //RegistryObject<EntityType<?>> PUG;
    }

    @Override
    protected void registerBiomes() {
        //RegistryObject<Biome> ERODED_FOREST;
        //RegistryObject<Biome> ERODED_TAIGA_FOREST;
        //RegistryObject<Biome> ERODED_JUNGLE;

        //RegistryObject<Biome> TALL_FOREST;
        //RegistryObject<Biome> TALL_TAIGA_FOREST;
        //RegistryObject<Biome> TALL_DARK_FOREST;
        //RegistryObject<Biome> TALL_JUNGLE;
    }

    @Override
    protected void registerFeatures() {
        //RegistryObject<Feature<?>> FALLEN_TREES;
        //RegistryObject<Feature<?>> TALL_OAK_TREE;
        //RegistryObject<Feature<?>> TALL_SPRUCE_TREE;
        //RegistryObject<Feature<?>> TALL_JUNGLE_TREE;
        //RegistryObject<Feature<?>> TALL_DARK_OAK_TREE;
    }

    @Override
    protected void registerSounds() {
    }
}