package pugz.omni.core.module;

public class MiscellaneousModule extends AbstractModule {
    public static final MiscellaneousModule instance = new MiscellaneousModule();

    public MiscellaneousModule() {
        super("Miscellaneous");
    }

    @Override
    protected void sendInitMessage() {
        System.out.println("Miscellaneous, random stuff initialized!");
    }

    @Override
    protected void onInitialize() {
    }

    @Override
    protected void registerBlocks() {
        //RegistryObject<Block> PALISADE;

        //RegistryObject<Block> REDSTONE_RECEPTOR;

        //RegistryObject<Block> GOLD_WOOL;
        //RegistryObject<Block> GOLD_CARPET;

        //RegistryObject<Block> CHORMICE;
        //RegistryObject<Block> CHORMICE_BRICKS;
        //RegistryObject<Block> CHORMICE_BRICK_STAIRS;
        //RegistryObject<Block> CHORMICE_BRICK_SLAB;
        //RegistryObject<Block> CHORMICE_BRICK_WALL;

        //RegistryObject<Block> PAPER_LANTERNS;

        //RegistryObject<Block> COW_HEAD;
        //RegistryObject<Block> PIG_HEAD;
        //RegistryObject<Block> SHEEP_HEAD;
        //RegistryObject<Block> CHICKEN_HEAD;
        //RegistryObject<Block> DEER_HEAD;
        //RegistryObject<Block> YAK_HEAD;
    }

    @Override
    protected void registerItems() {
        //RegistryObject<Item> FARMER_HAT;

        //RegistryObject<Item> CHORCOMB;
        //RegistryObject<Item> CHORUS_FLUTE;
        //RegistryObject<Item> CHORUS_PEARL;

        //RegistryObject<Item> AURA_POTION;
        //RegistryObject<Item> PHOENIX_FEATHER;
        //RegistryObject<Item> GOLD_STRING;
    }

    @Override
    protected void registerTileEntities() {
        //RegistryObject<TileEntityType<?>> WOODEN_PISTON;
        //RegistryObject<TileEntityType<?>> BALLISTA;
    }

    @Override
    protected void registerEntities() {
        //RegistryObject<EntityType<?>> FLAMINGO;
        //RegistryObject<EntityType<?>> PHOENIX;
        //RegistryObject<EntityType<?>> CHORUS;
        //RegistryObject<EntityType<?>> GOLDEN_RAM;
    }

    @Override
    protected void registerFeatures() {
        //RegistryObject<Feature<?>> CHORUS_NEST;
    }

    @Override
    protected void registerEnchantments() {
        //RegistryObject<Enchantment> KINETIC_PROTECTION;
        //RegistryObject<Enchantment> MAGIC_PROTECTION;
        //RegistryObject<Enchantment> ARMOR_BREAKING;
        //RegistryObject<Enchantment> ARMOR_PIERCING;
        //RegistryObject<Enchantment> STEADFAST;
        //RegistryObject<Enchantment> VITALITY;
        //RegistryObject<Enchantment> SHEPHERDS_TOUCH;
        //RegistryObject<Enchantment> BUTCHERING;
        //RegistryObject<Enchantment> WISDOM;
        //RegistryObject<Enchantment> SLASHING_EDGE;
    }

    @Override
    protected void registerParticles() {
        //RegistryObject<ParticleType<?>> GOLDEN_STRING;
    }

    @Override
    protected void registerSounds() {
    }
}