package pugz.omni.core.module;

public abstract class AbstractModule {
    private final String name;

    public AbstractModule(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    protected abstract void onInitialize();

    protected abstract void onClientInitialize();

    protected abstract void onPostInitialize();

    protected abstract void sendInitMessage();

    public void initialize() {
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
        sendInitMessage();
    }

    public void initializeClient() {
        onClientInitialize();
    }

    public void initializePost() {
        onPostInitialize();
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