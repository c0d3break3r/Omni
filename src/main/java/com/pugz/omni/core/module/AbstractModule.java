package com.pugz.omni.core.module;

public abstract class AbstractModule {
    private String name;

    public AbstractModule(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    protected void onInitialize() {

    }

    public void initialize() {
        System.out.println("Initialized " + name + " for Omni");
        registerBlocks();
        registerItems();
        registerTileEntities();
        registerEntities();
        registerBiomes();
        registerSurfaceBuilders();
        registerFeatures();
        registerStructures();
        registerEnchantments();
        registerEffects();
        registerSounds();
        registerParticles();
        registerStats();
        onInitialize();
    }

    protected void registerBlocks() {
    }

    protected void registerItems() {
    }

    protected void registerTileEntities() {
    }

    protected void registerEntities() {
    }

    protected void registerBiomes() {
    }

    protected void registerSurfaceBuilders() {
    }

    protected void registerFeatures() {
    }

    protected void registerStructures() {
    }

    protected void registerEnchantments() {
    }

    protected void registerEffects() {
    }

    protected void registerParticles() {
    }

    protected void registerSounds() {
    }

    protected void registerStats() {
    }
}