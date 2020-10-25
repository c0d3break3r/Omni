package com.pugz.omni.core.module;

public class AetherModule extends AbstractModule {
    public AetherModule() {
        super("Aether");
    }

    @Override
    protected void onInitialize() {
    }

    @Override
    protected void registerBlocks() {
        // vesper
        // skyris wood
        // sky stone
        // empyrean steel block and ore
        // cloud block
        // nimbus blocks
        // mystical wood
        // sky agate block and ore
    }

    @Override
    protected void registerItems() {
        // empyrean steel
        // sky agate
    }

    @Override
    protected void registerEntities() {
        // nimbus
        // myst
    }

    @Override
    protected void registerBiomes() {
        // sky isles
        // mystical cliffs
        // skyris woods
    }

    @Override
    protected void registerSurfaceBuilders() {
    }

    @Override
    protected void registerFeatures() {
    }

    @Override
    protected void registerStructures() {
    }

    @Override
    protected void registerEnchantments() {
        //RegistryObject<Enchantment> SUN_AFFINITY;
        //RegistryObject<Enchantment> MOON_AFFINITY;
    }

    @Override
    protected void registerEffects() {
    }

    @Override
    protected void registerParticles() {
        // myst
        // cloud
    }

    @Override
    protected void registerSounds() {
    }

    @Override
    protected void registerStats() {
    }
}