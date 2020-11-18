package pugz.omni.core.module;

import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;
import pugz.omni.common.tileentity.OmniBeehiveTileEntity;
import pugz.omni.common.tileentity.OmniChestTileEntity;
import pugz.omni.common.tileentity.OmniSignTileEntity;
import pugz.omni.common.tileentity.OmniTrappedChestTileEntity;
import pugz.omni.core.registry.OmniTileEntities;
import pugz.omni.core.util.RegistryUtil;

public class CoreModule extends AbstractModule {
    public static final CoreModule instance = new CoreModule();

    public CoreModule() {
        super("Core");
    }

    @Override
    protected void sendInitMessage() {
        System.out.println("Booted up the Core Module.");
    }

    @Override
    protected void onInitialize() {
    }

    @Override
    protected void onClientInitialize() {
        ClientRegistry.bindTileEntityRenderer(OmniTileEntities.CHEST.get(), ChestTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(OmniTileEntities.TRAPPED_CHEST.get(), ChestTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(OmniTileEntities.SIGN.get(), SignTileEntityRenderer::new);
    }

    @Override
    protected void onPostInitialize() {
    }

    @Override
    protected void registerTileEntities() {
        OmniTileEntities.BEEHIVE = RegistryUtil.createTileEntity("beehive", OmniBeehiveTileEntity::new, () -> OmniTileEntities.collectBlocks(OmniBeehiveTileEntity.class));
        OmniTileEntities.SIGN = RegistryUtil.createTileEntity("sign", OmniSignTileEntity::new, () -> OmniTileEntities.collectBlocks(OmniSignTileEntity.class));
        OmniTileEntities.CHEST = RegistryUtil.createTileEntity("chest", OmniChestTileEntity::new, () -> OmniTileEntities.collectBlocks(OmniChestTileEntity.class));
        OmniTileEntities.TRAPPED_CHEST = RegistryUtil.createTileEntity("trapped_chest", OmniTrappedChestTileEntity::new, () -> OmniTileEntities.collectBlocks(OmniTrappedChestTileEntity.class));
    }

    public static class Configuration {
        //cavier caves
        final ForgeConfigSpec.BooleanValue MALACHITE;
        final ForgeConfigSpec.IntValue MALACHITE_GEODE_SPAWN_CHANCE;
        final ForgeConfigSpec.ConfigValue<String> GEODE_SHELL_OUTER_BLOCK;
        final ForgeConfigSpec.ConfigValue<String> GEODE_SHELL_INNER_BLOCK;
        final ForgeConfigSpec.IntValue BUDDING_MALACHITE_GROWTH_CHANCE;
        final ForgeConfigSpec.BooleanValue SPELEOTHEMS;
        final ForgeConfigSpec.ConfigValue<Float> SPELEOTHEMS_SPAWN_PROBABILITY;
        final ForgeConfigSpec.BooleanValue SPELEOTHEMS_FALL;
        final ForgeConfigSpec.BooleanValue SPELEOTHEMS_FALL_BY_PROJECTILES;
        final ForgeConfigSpec.BooleanValue SPELEOTHEMS_FILL_CAULDRONS;

        //colormatic
        final ForgeConfigSpec.BooleanValue QUILTED_CARPETS;
        final ForgeConfigSpec.BooleanValue CONNECTABLE_QUILTED_CARPETS;
        final ForgeConfigSpec.IntValue TRADERS_WOOL_TRADE_PRICE;
        final ForgeConfigSpec.BooleanValue STACKABLE_FLOWERS;
        final ForgeConfigSpec.IntValue FLOWER_FIELD_SPAWN_WEIGHT;
        final ForgeConfigSpec.BooleanValue IMPROVED_CONCRETE_POWDER;
        final ForgeConfigSpec.BooleanValue CONCRETE_POWDER_FALLS;

        //deserted
        final ForgeConfigSpec.BooleanValue RED_ROCK;
        final ForgeConfigSpec.IntValue RED_ROCK_GEN_SIZE;

        //fiery nether
        final ForgeConfigSpec.BooleanValue COBBLED_BASALT;

        //forestry
        final ForgeConfigSpec.BooleanValue AMBIENT_SOUNDS;
        final ForgeConfigSpec.BooleanValue CARVED_WOOD;

        //miscellaneous
        final ForgeConfigSpec.BooleanValue ENCHANTED_GOLDEN_CARROTS;
        final ForgeConfigSpec.ConfigValue<Float> ENCHANTED_GOLDEN_CARROT_SPAWN_CHANCE;
        final ForgeConfigSpec.BooleanValue ZOMBIE_HORSE_TRANSMUTATION;

        //paradise
        final ForgeConfigSpec.BooleanValue SEAHORSES;
        final ForgeConfigSpec.ConfigValue<Float> SEAHORSE_SPAWN_CHANCE;
        final ForgeConfigSpec.IntValue SEAHORSE_TAME_CHANCE;
        final ForgeConfigSpec.BooleanValue RIDEABLE_SEAHORSES;
        final ForgeConfigSpec.ConfigValue<String> SEAHORSE_SPAWN_BIOMES;
        final ForgeConfigSpec.IntValue LARGE_SEAHORSE_SPAWN_CHANCE;
        final ForgeConfigSpec.IntValue TROPICAL_PLAINS_SPAWN_WEIGHT;
        final ForgeConfigSpec.BooleanValue LOTUS_FLOWERS;
        final ForgeConfigSpec.IntValue LOTUS_FLOWER_SPAWN_CHANCE;
        final ForgeConfigSpec.IntValue LOTUS_FLOWER_TRADE_PRICE;

        //wintertime
        final ForgeConfigSpec.IntValue POLAR_BEAR_JOCKEY_CHANCE;

        public Configuration(ForgeConfigSpec.Builder builder) {
            builder.comment("Omni Configuration");

            builder.push(CavierCavesModule.instance.getName());
            MALACHITE = builder.define("malachite", true);
            MALACHITE_GEODE_SPAWN_CHANCE = builder.defineInRange("malachite_geode_spawn_chance", 48, 0, 1000);
            GEODE_SHELL_OUTER_BLOCK = builder.define("geode_shell_outer_block", "minecraft:granite");
            GEODE_SHELL_INNER_BLOCK = builder.define("geode_shell_inner_block", "minecraft:diorite");
            BUDDING_MALACHITE_GROWTH_CHANCE = builder.defineInRange("budding_malachite_growth_chance", 5, 0, 1000);
            SPELEOTHEMS = builder.define("speleothems", true);
            SPELEOTHEMS_SPAWN_PROBABILITY = builder.define("speleothems_spawn_probability", 0.003F);
            SPELEOTHEMS_FALL = builder.define("speleothems_fall", true);
            SPELEOTHEMS_FALL_BY_PROJECTILES = builder.define("speleothems_fall_by_projectiles", true);
            SPELEOTHEMS_FILL_CAULDRONS = builder.define("speleothems_fill_cauldrons", true);
            builder.pop();

            builder.push(ColormaticModule.instance.getName());
            QUILTED_CARPETS = builder.define("quilted_carpets", true);
            CONNECTABLE_QUILTED_CARPETS = builder.define("connectable_quilted_carpets", true);
            TRADERS_WOOL_TRADE_PRICE = builder.defineInRange("traders_wool_trade_price", 1, 0, 64);
            STACKABLE_FLOWERS = builder.define("stackable_flowers", true);
            FLOWER_FIELD_SPAWN_WEIGHT = builder.defineInRange("flower_field_spawn_weight", 1, 0, 100);
            IMPROVED_CONCRETE_POWDER = builder.define("improved_concrete_powder", true);
            CONCRETE_POWDER_FALLS = builder.define("concrete_powder_falls", true);
            builder.pop();

            builder.push(DesertedModule.instance.getName());
            RED_ROCK = builder.define("red_rock", true);
            RED_ROCK_GEN_SIZE = builder.defineInRange("red_rock_gen_size", 36, 0, 128);
            builder.pop();

            builder.push(FieryNetherModule.instance.getName());
            COBBLED_BASALT = builder.define("cobbled_basalt", true);
            builder.pop();

            builder.push(ForestryModule.instance.getName());
            AMBIENT_SOUNDS = builder.define("ambient_sounds", true);
            CARVED_WOOD = builder.define("carved_wood", true);
            builder.pop();


            builder.push(MiscellaneousModule.instance.getName());
            ENCHANTED_GOLDEN_CARROTS = builder.define("enchanted_golden_carrots", true);
            ENCHANTED_GOLDEN_CARROT_SPAWN_CHANCE = builder.define("enchanted_golden_carrot_spawn_chance", 0.03F);
            ZOMBIE_HORSE_TRANSMUTATION = builder.define("zombie_horse_transmutation", true);
            builder.pop();

            builder.push(ParadiseModule.instance.getName());
            SEAHORSES = builder.define("seahorses", true);
            SEAHORSE_SPAWN_CHANCE = builder.define("seahorse_spawn_chance", 0.75F);
            SEAHORSE_TAME_CHANCE = builder.defineInRange("seahorse_tame_chance", 5, 0, 1000);
            RIDEABLE_SEAHORSES = builder.define("rideable_seahorses", true);
            SEAHORSE_SPAWN_BIOMES = builder.define("seahorse_spawn_biomes", "minecraft:warm_ocean,minecraft:deep_warm_ocean");
            LARGE_SEAHORSE_SPAWN_CHANCE = builder.defineInRange("large_seahorse_spawn_chance", 25, 0, 1000);
            TROPICAL_PLAINS_SPAWN_WEIGHT = builder.defineInRange("tropical_plains_spawn_weight", 2, 0, 100);
            LOTUS_FLOWERS = builder.define("lotus_flowers", true);
            LOTUS_FLOWER_SPAWN_CHANCE = builder.defineInRange("lotus_flower_spawn_chance", 8, 0, 1000);
            LOTUS_FLOWER_TRADE_PRICE = builder.defineInRange("lotus_flower_trade_price", 1, 0, 64);
            builder.pop();

            builder.push(WintertimeModule.instance.getName());
            POLAR_BEAR_JOCKEY_CHANCE = builder.defineInRange("polar_bear_jockey_chance", 100, 0, 1000);
            builder.pop();
        }

        public static final ForgeConfigSpec CLIENT_SPEC;
        public static final Configuration CLIENT;

        public static void bakeConfig(final ModConfig config) {
            //cavier caves
            CavierCavesModule.malachite = true;
            CavierCavesModule.malachiteGeodeSpawnChance = CLIENT.MALACHITE_GEODE_SPAWN_CHANCE.get();
            CavierCavesModule.geodeShellOuterBlock = CLIENT.GEODE_SHELL_OUTER_BLOCK.get();
            CavierCavesModule.geodeShellInnerBlock = CLIENT.GEODE_SHELL_INNER_BLOCK.get();
            CavierCavesModule.buddingMalachiteGrowthChance = CLIENT.BUDDING_MALACHITE_GROWTH_CHANCE.get();
            CavierCavesModule.speleothems = true;
            CavierCavesModule.speleothemsSpawnProbability = CLIENT.SPELEOTHEMS_SPAWN_PROBABILITY.get();
            CavierCavesModule.speleothemsFall = CLIENT.SPELEOTHEMS_FALL.get();
            CavierCavesModule.speleothemsFallByProjectiles = CLIENT.SPELEOTHEMS_FALL_BY_PROJECTILES.get();
            CavierCavesModule.speleothemsFillCauldrons = CLIENT.SPELEOTHEMS_FILL_CAULDRONS.get();

            //colormatic
            ColormaticModule.quiltedCarpets = true;
            ColormaticModule.connectableQuiltedCarpets = CLIENT.CONNECTABLE_QUILTED_CARPETS.get();
            ColormaticModule.tradersWoolTradePrice = CLIENT.TRADERS_WOOL_TRADE_PRICE.get();
            ColormaticModule.stackableFlowers = true;
            ColormaticModule.flowerFieldSpawnWeight = CLIENT.FLOWER_FIELD_SPAWN_WEIGHT.get();
            ColormaticModule.improvedConcretePowder = true;
            ColormaticModule.concretePowderFalls = CLIENT.CONCRETE_POWDER_FALLS.get();

            //deserted
            DesertedModule.redRock = true;
            DesertedModule.redRockGenSize = CLIENT.RED_ROCK_GEN_SIZE.get();

            //fiery nether
            FieryNetherModule.cobbledBasalt = true;

            //forestry
            ForestryModule.ambientSounds = true;
            ForestryModule.carvedWood = true;

            //miscellaneous
            MiscellaneousModule.enchantedGoldenCarrots = true;
            MiscellaneousModule.enchantedGoldenCarrotSpawnChance = CLIENT.ENCHANTED_GOLDEN_CARROT_SPAWN_CHANCE.get();
            MiscellaneousModule.zombieHorseTransmutation = CLIENT.ZOMBIE_HORSE_TRANSMUTATION.get();

            //paradise
            ParadiseModule.seahorses = true;
            ParadiseModule.seahorseSpawnChance = CLIENT.SEAHORSE_SPAWN_CHANCE.get();
            ParadiseModule.seahorseTameChance = CLIENT.SEAHORSE_TAME_CHANCE.get();
            ParadiseModule.rideableSeahorses = CLIENT.RIDEABLE_SEAHORSES.get();
            ParadiseModule.seahorseSpawnBiomes = CLIENT.SEAHORSE_SPAWN_BIOMES.get();
            ParadiseModule.largeSeahorseSpawnChance = CLIENT.LARGE_SEAHORSE_SPAWN_CHANCE.get();
            ParadiseModule.tropicalPlainsSpawnWeight = CLIENT.TROPICAL_PLAINS_SPAWN_WEIGHT.get();
            ParadiseModule.lotusFlowers = true;
            ParadiseModule.lotusFlowerSpawnChance = CLIENT.LOTUS_FLOWER_SPAWN_CHANCE.get();
            ParadiseModule.lotusFlowerTradePrice = CLIENT.LOTUS_FLOWER_TRADE_PRICE.get();

            //wintertime
            WintertimeModule.polarBearJockeyChance = CLIENT.POLAR_BEAR_JOCKEY_CHANCE.get();
        }

        static {
            final Pair<Configuration, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Configuration::new);
            CLIENT_SPEC = specPair.getRight();
            CLIENT = specPair.getLeft();
        }
    }
}