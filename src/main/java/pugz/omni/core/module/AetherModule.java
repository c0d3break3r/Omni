package pugz.omni.core.module;

public class AetherModule extends AbstractModule {
    public AetherModule() {
        super("Aether");
    }

    @Override
    protected void onInitialize() {
    }

    @Override
    protected void onClientInitialize() {
    }

    @Override
    protected void onPostInitialize() {
    }

    @Override
    protected void sendInitMessage() {
        System.out.println("Reached the Dawn of the Aether!");
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
        // aurora crystals
    }

    @Override
    protected void registerItems() {
        // empyrean steel
        // sky agate
        // aurora crystal
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
        // aurora crystal
        // skyris trees
        // clouds
        // vesper
    }

    @Override
    protected void registerStructures() {
        // palace
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