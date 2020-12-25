package pugz.omni.core.module;

import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.apache.commons.lang3.tuple.Pair;
import pugz.omni.common.tileentity.OmniBeehiveTileEntity;
import pugz.omni.common.tileentity.OmniChestTileEntity;
import pugz.omni.common.tileentity.OmniSignTileEntity;
import pugz.omni.common.tileentity.OmniTrappedChestTileEntity;
import pugz.omni.common.world.feature.ExposedOreFeature;
import pugz.omni.common.world.feature.ExposedOreFeatureConfig;
import pugz.omni.core.registry.OmniFeatures;
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
    @OnlyIn(Dist.CLIENT)
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

    @Override
    protected void registerFeatures() {
        OmniFeatures.EXPOSED_ORE = RegistryUtil.createFeature("exposed_ore", () -> new ExposedOreFeature(ExposedOreFeatureConfig.CODEC));
    }

    public static class Configuration {
        //cavier caves
        public final ForgeConfigSpec.IntValue MALACHITE_GEODE_SPAWN_CHANCE;
        public final ForgeConfigSpec.ConfigValue<String> GEODE_SHELL_OUTER_BLOCK;
        public final ForgeConfigSpec.ConfigValue<String> GEODE_SHELL_INNER_BLOCK;
        public final ForgeConfigSpec.IntValue BUDDING_MALACHITE_GROWTH_CHANCE;
        public final ForgeConfigSpec.ConfigValue<Double> SPELEOTHEMS_SPAWN_PROBABILITY;
        public final ForgeConfigSpec.BooleanValue SPELEOTHEMS_FALL;
        public final ForgeConfigSpec.BooleanValue SPELEOTHEMS_FALL_BY_PROJECTILES;
        public final ForgeConfigSpec.BooleanValue SPELEOTHEMS_FILL_CAULDRONS;
        public final ForgeConfigSpec.IntValue MUSHROOM_CAVE_CHANCE;
        public final ForgeConfigSpec.IntValue GREEN_CAVE_MUSHROOM_BOUNCE_MODIFIER;
        public final ForgeConfigSpec.IntValue ICY_CAVE_CHANCE;
        public final ForgeConfigSpec.IntValue ARCTISS_FREEZE_DURATION;
        public final ForgeConfigSpec.BooleanValue ICY_CAVE_ICE_WINDOWS;
        public final ForgeConfigSpec.IntValue TERRACOTTA_CAVE_CHANCE;

        //colormatic
        public final ForgeConfigSpec.BooleanValue CONNECTABLE_QUILTED_CARPETS;
        public final ForgeConfigSpec.IntValue TRADERS_WOOL_TRADE_PRICE;
        public final ForgeConfigSpec.BooleanValue STACK_FLOWERS;
        public final ForgeConfigSpec.IntValue FLOWER_FIELD_SPAWN_WEIGHT;
        public final ForgeConfigSpec.BooleanValue LAYER_CONCRETE;
        public final ForgeConfigSpec.BooleanValue CONCRETE_POWDER_FALLS;

        //deserted
        public final ForgeConfigSpec.IntValue RED_ROCK_GEN_SIZE;

        //forestry
        public final ForgeConfigSpec.ConfigValue<Double> GOLDEN_OAK_SPAWN_CHANCE;

        //miscellaneous
        public final ForgeConfigSpec.ConfigValue<Double> ENCHANTED_GOLDEN_CARROT_SPAWN_CHANCE;
        public final ForgeConfigSpec.BooleanValue ZOMBIE_HORSE_TRANSMUTATION;

        //paradise
        public final ForgeConfigSpec.ConfigValue<Double> SEAHORSE_SPAWN_CHANCE;
        public final ForgeConfigSpec.IntValue SEAHORSE_TAME_CHANCE;
        public final ForgeConfigSpec.BooleanValue RIDEABLE_SEAHORSES;
        public final ForgeConfigSpec.ConfigValue<String> SEAHORSE_SPAWN_BIOMES;
        public final ForgeConfigSpec.IntValue LARGE_SEAHORSE_SPAWN_CHANCE;
        public final ForgeConfigSpec.IntValue TROPICAL_PLAINS_SPAWN_WEIGHT;
        public final ForgeConfigSpec.IntValue LOTUS_FLOWER_SPAWN_CHANCE;
        public final ForgeConfigSpec.IntValue LOTUS_FLOWER_TRADE_PRICE;

        //wintertime
        public final ForgeConfigSpec.IntValue POLAR_BEAR_JOCKEY_CHANCE;

        public Configuration(ForgeConfigSpec.Builder builder) {
            builder.comment("Omni Common Configuration");

            builder.push(CavierCavesModule.instance.getName());
            MALACHITE_GEODE_SPAWN_CHANCE = builder.defineInRange("malachite_geode_spawn_chance", 48, 0, 1000);
            GEODE_SHELL_OUTER_BLOCK = builder.define("geode_shell_outer_block", "minecraft:granite");
            GEODE_SHELL_INNER_BLOCK = builder.define("geode_shell_inner_block", "minecraft:diorite");
            BUDDING_MALACHITE_GROWTH_CHANCE = builder.defineInRange("budding_malachite_growth_chance", 5, 0, 1000);
            SPELEOTHEMS_SPAWN_PROBABILITY = builder.define("speleothems_spawn_probability", 0.04D);
            SPELEOTHEMS_FALL = builder.define("speleothems_fall", true);
            SPELEOTHEMS_FALL_BY_PROJECTILES = builder.define("speleothems_fall_by_projectiles", true);
            SPELEOTHEMS_FILL_CAULDRONS = builder.define("speleothems_fill_cauldrons", true);
            MUSHROOM_CAVE_CHANCE = builder.defineInRange("mushroom_cave_chance", 15, 0, 1000);
            GREEN_CAVE_MUSHROOM_BOUNCE_MODIFIER = builder.defineInRange("green_cave_mushroom_bounce_modifier", 1, 0, 1000);
            ICY_CAVE_CHANCE = builder.defineInRange("icy_cave_chance", 18, 0, 1000);
            ARCTISS_FREEZE_DURATION = builder.defineInRange("arctiss_freeze_duration", 120, 0, 9600);
            ICY_CAVE_ICE_WINDOWS = builder.define("icy_cave_ice_windows", true);
            TERRACOTTA_CAVE_CHANCE = builder.defineInRange("terracotta_cave_chance", 18, 0, 1000);
            builder.pop();

            builder.push(ColormaticModule.instance.getName());
            CONNECTABLE_QUILTED_CARPETS = builder.define("connectable_quilted_carpets", true);
            TRADERS_WOOL_TRADE_PRICE = builder.defineInRange("traders_wool_trade_price", 1, 0, 64);
            STACK_FLOWERS = builder.define("stack_flowers", true);
            FLOWER_FIELD_SPAWN_WEIGHT = builder.defineInRange("flower_field_spawn_weight", 1, 0, 100);
            LAYER_CONCRETE = builder.define("layer_concrete", true);
            CONCRETE_POWDER_FALLS = builder.define("concrete_powder_falls", true);
            builder.pop();

            builder.push(ForestryModule.instance.getName());
            GOLDEN_OAK_SPAWN_CHANCE = builder.define("golden_oak_spawn_chance", 0.005D);
            builder.pop();

            builder.push(MiscellaneousModule.instance.getName());
            ENCHANTED_GOLDEN_CARROT_SPAWN_CHANCE = builder.define("enchanted_golden_carrot_spawn_chance", 0.03D);
            ZOMBIE_HORSE_TRANSMUTATION = builder.define("zombie_horse_transmutation", true);
            builder.pop();

            builder.push(ParadiseModule.instance.getName());
            SEAHORSE_SPAWN_CHANCE = builder.define("seahorse_spawn_chance", 0.75D);
            SEAHORSE_TAME_CHANCE = builder.defineInRange("seahorse_tame_chance", 5, 0, 1000);
            RIDEABLE_SEAHORSES = builder.define("rideable_seahorses", true);
            SEAHORSE_SPAWN_BIOMES = builder.define("seahorse_spawn_biomes", "minecraft:warm_ocean,minecraft:deep_warm_ocean");
            LARGE_SEAHORSE_SPAWN_CHANCE = builder.defineInRange("large_seahorse_spawn_chance", 25, 0, 1000);
            TROPICAL_PLAINS_SPAWN_WEIGHT = builder.defineInRange("tropical_plains_spawn_weight", 2, 0, 100);
            LOTUS_FLOWER_SPAWN_CHANCE = builder.defineInRange("lotus_flower_spawn_chance", 8, 0, 1000);
            LOTUS_FLOWER_TRADE_PRICE = builder.defineInRange("lotus_flower_trade_price", 1, 0, 64);
            builder.pop();

            builder.push(WildWestModule.instance.getName());
            RED_ROCK_GEN_SIZE = builder.defineInRange("red_rock_gen_size", 36, 0, 128);
            builder.pop();

            builder.push(WintertimeModule.instance.getName());
            POLAR_BEAR_JOCKEY_CHANCE = builder.defineInRange("polar_bear_jockey_chance", 40, 0, 1000);
            builder.pop();
        }

        public static final ForgeConfigSpec COMMON_SPEC;
        public static final Configuration COMMON;

        static {
            final Pair<Configuration, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Configuration::new);
            COMMON_SPEC = specPair.getRight();
            COMMON = specPair.getLeft();
        }
    }
}