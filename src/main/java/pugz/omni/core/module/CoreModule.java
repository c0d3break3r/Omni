package pugz.omni.core.module;

import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.apache.commons.lang3.tuple.Pair;
import pugz.omni.common.tileentity.OmniBeehiveTileEntity;
import pugz.omni.common.tileentity.OmniChestTileEntity;
import pugz.omni.common.tileentity.OmniSignTileEntity;
import pugz.omni.common.tileentity.OmniTrappedChestTileEntity;
import pugz.omni.core.registry.OmniTileEntities;
import pugz.omni.core.util.RegistryUtil;

import java.util.ArrayList;
import java.util.List;

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
        public ForgeConfigSpec.ConfigValue<Integer> MALACHITE_GEODE_SPAWN_CHANCE;
        public ForgeConfigSpec.ConfigValue<String> GEODE_SHELL_OUTER_BLOCK;
        public ForgeConfigSpec.ConfigValue<String> GEODE_SHELL_INNER_BLOCK;
        public ForgeConfigSpec.ConfigValue<Integer> BUDDING_MALACHITE_GROWTH_CHANCE;
        public ForgeConfigSpec.ConfigValue<Integer> MALACHITE_TOTEM_RANGE;
        public ForgeConfigSpec.ConfigValue<Integer> MALACHITE_TOTEM_LIGHT_LEVEL;
        public ForgeConfigSpec.ConfigValue<Double> SPELEOTHEMS_SPAWN_PROBABILITY;
        public ForgeConfigSpec.ConfigValue<Boolean> SPELEOTHEMS_FALL;
        public ForgeConfigSpec.ConfigValue<Boolean> SPELEOTHEMS_FALL_BY_PROJECTILES;
        public ForgeConfigSpec.ConfigValue<Boolean> SPELEOTHEMS_FILL_CAULDRONS;

        //colormatic
        public ForgeConfigSpec.ConfigValue<Boolean> CONNECTABLE_QUILTED_CARPETS;
        public ForgeConfigSpec.ConfigValue<Integer> TRADERS_WOOL_TRADE_PRICE;
        public ForgeConfigSpec.ConfigValue<Integer> FLOWER_FIELD_SPAWN_WEIGHT;

        //deserted
        public ForgeConfigSpec.ConfigValue<Integer> RED_ROCK_GEN_SIZE;

        //forestry
        public ForgeConfigSpec.ConfigValue<Boolean> AMBIENT_SOUNDS;

        //paradise
        public ForgeConfigSpec.ConfigValue<Double> SEAHORSE_SPAWN_CHANCE;
        public ForgeConfigSpec.ConfigValue<Integer> SEAHORSE_TAME_CHANCE;
        public ForgeConfigSpec.ConfigValue<Boolean> RIDEABLE_SEAHORSES;
        public ForgeConfigSpec.ConfigValue<String> SEAHORSE_SPAWN_BIOMES;
        public ForgeConfigSpec.ConfigValue<Integer> SEAHORSE_CORAL_GROWTH_RATE;
        public ForgeConfigSpec.ConfigValue<Integer> LARGE_SEAHORSE_SPAWN_CHANCE;
        public ForgeConfigSpec.ConfigValue<Integer> SEAHORSE_CORAL_GROWTH_DISTANCE;
        public ForgeConfigSpec.ConfigValue<Integer> TROPICAL_PLAINS_SPAWN_WEIGHT;
        public ForgeConfigSpec.ConfigValue<Integer> LOTUS_FLOWER_SPAWN_CHANCE;
        public ForgeConfigSpec.ConfigValue<Integer> LOTUS_FLOWER_TRADE_PRICE;

        //miscellaneous
        public ForgeConfigSpec.ConfigValue<Float> ENCHANTED_GOLDEN_CARROT_SPAWN_CHANCE;

        public Configuration(ForgeConfigSpec.Builder builder) {
            builder.comment("Omni Configuration");

            builder.push(CavierCavesModule.instance.getName());
            MALACHITE_GEODE_SPAWN_CHANCE = builder.define("malachite_geode_spawn_chance", 48);
            GEODE_SHELL_OUTER_BLOCK = builder.define("geode_shell_outer_block", "minecraft:granite");
            GEODE_SHELL_INNER_BLOCK = builder.define("geode_shell_inner_block", "minecraft:diorite");
            BUDDING_MALACHITE_GROWTH_CHANCE = builder.define("budding_malachite_growth_chance", 5);
            MALACHITE_TOTEM_RANGE = builder.define("malachite_totem_range", 12);
            MALACHITE_TOTEM_LIGHT_LEVEL = builder.defineInRange("malachite_totem_light_level", 10, 0, 16);
            SPELEOTHEMS_SPAWN_PROBABILITY = builder.define("speleothems_spawn_probability", 0.004D);
            SPELEOTHEMS_FALL = builder.define("speleothems_fall", true);
            SPELEOTHEMS_FALL_BY_PROJECTILES = builder.define("speleothems_fall_by_projectiles", true);
            SPELEOTHEMS_FILL_CAULDRONS = builder.define("speleothems_fill_cauldrons", true);
            builder.pop();

            builder.push(ColormaticModule.instance.getName());
            CONNECTABLE_QUILTED_CARPETS = builder.define("connectable_quilted_carpets", true);
            TRADERS_WOOL_TRADE_PRICE = builder.defineInRange("traders_wool_trade_price", 1, 0, 64);
            FLOWER_FIELD_SPAWN_WEIGHT = builder.define("flower_field_spawn_weight", 1);
            builder.pop();

            builder.push(DesertedModule.instance.getName());
            RED_ROCK_GEN_SIZE = builder.define("red_rock_gen_size", 36);
            builder.pop();

            builder.push(ForestryModule.instance.getName());
            AMBIENT_SOUNDS = builder.define("red_rock_gen_size", true);
            builder.pop();

            builder.push(ParadiseModule.instance.getName());
            SEAHORSE_SPAWN_CHANCE = builder.define("seahorse_spawn_chance", 0.05D);
            SEAHORSE_TAME_CHANCE = builder.define("seahorse_tame_chance", 5);
            RIDEABLE_SEAHORSES = builder.define("rideable_seahorses", true);
            SEAHORSE_SPAWN_BIOMES = builder.define("seahorse_spawn_biomes", "minecraft:warm_ocean,minecraft:deep_warm_ocean");
            LARGE_SEAHORSE_SPAWN_CHANCE = builder.define("large_seahorse_spawn_chance", 25);
            SEAHORSE_CORAL_GROWTH_RATE = builder.define("seahorse_coral_growth_rate", 25);
            SEAHORSE_CORAL_GROWTH_DISTANCE = builder.define("seahorse_coral_growth_distance", 4);
            TROPICAL_PLAINS_SPAWN_WEIGHT = builder.define("tropical_plains_spawn_weight", 2);
            LOTUS_FLOWER_SPAWN_CHANCE = builder.define("lotus_flower_spawn_chance", 8);
            LOTUS_FLOWER_TRADE_PRICE = builder.defineInRange("lotus_flower_trade_price", 1, 0, 64);
            builder.pop();

            builder.push(MiscellaneousModule.instance.getName());
            ENCHANTED_GOLDEN_CARROT_SPAWN_CHANCE = builder.define("enchanted_golden_carrot_spawn_chance", 0.03F);
            builder.pop();
        }

        public static final ForgeConfigSpec CLIENT_SPEC;
        public static final Configuration CLIENT;
        static {
            final Pair<Configuration, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Configuration::new);
            CLIENT_SPEC = specPair.getRight();
            CLIENT = specPair.getLeft();
        }
    }
}