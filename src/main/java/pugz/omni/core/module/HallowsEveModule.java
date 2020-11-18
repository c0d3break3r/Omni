package pugz.omni.core.module;

public class HallowsEveModule extends AbstractModule {
    public static final HallowsEveModule instance = new HallowsEveModule();

    public HallowsEveModule() {
        super("Hallows Eve");
    }

    @Override
    protected void sendInitMessage() {
        System.out.println("BOO! Happy Hallow's Eve!");
    }

    @Override
    protected void onInitialize() {
        registerHexes();
    }

    @Override
    protected void onClientInitialize() {
    }

    @Override
    protected void onPostInitialize() {
    }

    @Override
    protected void registerBlocks() {
        //RegistryObject<Block> HALLOWED_DIRT;

        //RegistryObject<Block> PUMPKIN_PLANKS;

        //RegistryObject<Block> SOUL_GLASS;

        //RegistryObject<Block> MORTIS;
        //RegistryObject<Block> MORTIS_STAIRS;
        //RegistryObject<Block> MORTIS_SLAB;
        //RegistryObject<Block> MORTIS_WALL;
        //RegistryObject<Block> MORTIS_BRICKS;
        //RegistryObject<Block> MORTIS_BRICK_STAIRS;
        //RegistryObject<Block> MORTIS_BRICK_SLAB;
        //RegistryObject<Block> MORTIS_BRICK_WALL;
        //RegistryObject<Block> HALLOWED_MORTIS_BRICKS;
        //RegistryObject<Block> HALLOWED_MORTIS_BRICK_STAIRS;
        //RegistryObject<Block> HALLOWED_MORTIS_BRICK_SLAB;
        //RegistryObject<Block> HALLOWED_MORTIS_BRICK_WALL;

        //RegistryObject<Block> ASPHODEL_LOG;
        //RegistryObject<Block> ASPHODEL_PLANKS;

        //RegistryObject<Block> PALE_BUSH;

        //RegistryObject<Block> SOUL_URN;
        //RegistryObject<Block> SMOKY_QUARTZ;
        //RegistryObject<Block> SMOKY_QUARTZ_STAIRS;
        //RegistryObject<Block> SMOKY_QUARTZ_SLAB;
        //RegistryObject<Block> SMOKY_QUARTZ_WALL;
        //RegistryObject<Block> SMOKY_QUARTZ_PILLAR;
        //RegistryObject<Block> SMOKY_SOULSAND;

        //RegistryObject<Block> THORNWOOD_LOG;
        //RegistryObject<Block> THORNWOOD_PLANKS;
        //RegistryObject<Block> THORNBRUSH;

        //RegistryObject<Block> WROUGHT_IRON_BLOCK;
        //RegistryObject<Block> WROUGHT_IRON_BARS;

        //RegistryObject<Block> CORN_STALK;
        //RegistryObject<Block> CORN_CRATE;
    }

    @Override
    protected void registerItems() {
        //RegistryObject<Item> CORN;
        //RegistryObject<Item> CORNBREAD;

        //RegistryObject<Item> GHOST_CLOTH;
        //RegistryObject<Item> GHOST_DRAPE;

        //RegistryObject<Item> PUMPKIN_PIECE;
        //RegistryObject<Item> PUMPKIN_SHIELD;

        //RegistryObject<Item> BOTTLED_SOUL_SMOKE;

        //RegistryObject<Item> WITCHS_BREW;
    }

    @Override
    protected void registerTileEntities() {
        //RegistryObject<TileEntityType<?>> SOUL_URN;
    }

    @Override
    protected void registerEntities() {
        //RegistryObject<EntityType<?>> GHOST;
        //RegistryObject<EntityType<?>> PUMPKENT;
    }

    @Override
    protected void registerBiomes() {
        //RegistryObject<Biome> PUMPKIN_PLAINS;
        //RegistryObject<Biome> PERISHED_PLAINS;
        //RegistryObject<Biome> WITHERED_WOODS;

        //RegistryObject<Biome> DEAD_OCEAN;
        //RegistryObject<Biome> DEEP_SWAMP;
        //RegistryObject<Biome> DARK_OAK_SWAMP;
    }

    @Override
    protected void registerFeatures() {
        //RegistryObject<Feature<?>> MORTIS_ROCK;
        //RegistryObject<Feature<?>> PALE_BUSH;
        //RegistryObject<Feature<?>> ASPHODEL_TREE;
        //RegistryObject<Feature<?>> SOUL_URN;
        //RegistryObject<Feature<?>> THORNWOOD_TREE;
        //RegistryObject<Feature<?>> THORNBRUSH;
        //RegistryObject<Feature<?>> GIANT_PUMPKIN;
        //RegistryObject<Feature<?>> SMOKY_SOULSAND;
        //RegistryObject<Feature<?>> GRAVESTONE;
        //RegistryObject<Feature<?>> CORN_PATCH;
    }

    @Override
    protected void registerStructures() {
        //RegistryObject<Structure<?>> WITCH_TOWER;
    }

    @Override
    protected void registerDimensions() {
    }

    private void registerHexes() {
        //animal fear
        //illusions
        //hide status
        //insomnia
        //drop items
        //fire trail
        //no drops
        //monster spawning
    }

    @Override
    protected void registerEnchantments() {
        //RegistryObject<Enchantment> LIFESTEAL;
    }

    @Override
    protected void registerParticles() {
        //RegistryObject<ParticleType<?>> SOUL_SMOKE;
        //RegistryObject<ParticleType<?>> SOUL;
        //RegistryObject<ParticleType<?>> WITCH_BREW;
    }

    @Override
    protected void registerSounds() {
    }
}