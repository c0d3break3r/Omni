package pugz.omni.core.module;

public class WintertimeModule extends AbstractModule {
    public static final WintertimeModule instance = new WintertimeModule();

    public WintertimeModule() {
        super("Wintertime");
    }

    @Override
    protected void sendInitMessage() {
        System.out.println("Drinking hot chocolate by the fire during Wintertime!");
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
    protected void registerBlocks() {
        //RegistryObject<Block> ANTLER_BLOCK;

        //RegistryObject<Block> ARCTISS_BLOCK;

        //RegistryObject<Block> MOONSTONE_BLOCK;
        //RegistryObject<Block> CREOSTONE;
        //RegistryObject<Block> CREOSTONE_STAIRS;
        //RegistryObject<Block> CREOSTONE_SLAB;
        //RegistryObject<Block> CREOSTONE_WALL;

        //RegistryObject<Block> MISTLETOE;
    }

    @Override
    protected void registerItems() {
        //RegistryObject<Item> BLIZZARD_SHARD;
        //RegistryObject<Item> ANTLER;
        //RegistryObject<Item> MOONSTONES;
    }

    @Override
    protected void registerEntities() {
        //RegistryObject<EntityType<?>> BLIZZARD;
        //RegistryObject<EntityType<?>> REINDEER;
    }

    @Override
    protected void registerStructures() {
        //RegistryObject<Structure<?>> BLIZZARD_MONUMENT;
    }

    @Override
    protected void registerEnchantments() {
        //RegistryObject<Enchantment> ICY_TOUCH;
    }

    @Override
    protected void registerParticles() {
        //RegistryObject<ParticleType<?>> MOON;
        //RegistryObject<ParticleType<?>> ARCTISS;
    }

    @Override
    protected void registerSounds() {
    }
}