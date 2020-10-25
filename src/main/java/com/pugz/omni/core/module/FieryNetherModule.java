package com.pugz.omni.core.module;

public class FieryNetherModule extends AbstractModule {
    public static final FieryNetherModule instance = new FieryNetherModule();

    public FieryNetherModule() {
        super("Fiery Nether");
    }

    @Override
    protected void onInitialize() {
    }

    @Override
    protected void registerBlocks() {
        //RegistryObject<Block> MOLTEN_MAGMA;
        //RegistryObject<Block> PUMICE;

        //RegistryObject<Block> FOOLS_NETHERITE;

        //RegistryObject<Block> MORTAR;

        //RegistryObject<Block> BASALT_VENT;

        //RegistryObject<Block> NETHERITE_GONG;

        //RegistryObject<Block> MAGMISS;
    }

    @Override
    protected void registerItems() {
        //RegistryObject<Item> ERUPTION_SHARD;
    }

    @Override
    protected void registerFeatures() {
        //RegistryObject<Feature<?>> MORTAR;
        //RegistryObject<Feature<?>> BASALT_VENT;
        //RegistryObject<Feature<?>> MAGMISS;
        //RegistryObject<Feature<?>> CHUNK;
    }

    @Override
    protected void registerEffects() {
        //RegistryObject<Effect> BLAZEFIRE;
    }

    @Override
    protected void registerParticles() {
        //RegistryObject<ParticleType<?>> MOLTEN_MAGMA;
        //RegistryObject<ParticleType<?>> MAGMISS;
    }

    @Override
    protected void registerSounds() {
    }
}