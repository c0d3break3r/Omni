package pugz.omni.core.module;

import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import pugz.omni.client.render.SeahorseRenderer;
import pugz.omni.common.block.paradise.LotusFlowerBlock;
import pugz.omni.common.entity.paradise.SeahorseEntity;
import pugz.omni.common.item.paradise.SeahorseBucketItem;
import pugz.omni.common.item.OmniSpawnEggItem;
import pugz.omni.core.registry.OmniBiomes;
import pugz.omni.core.registry.OmniBlocks;
import pugz.omni.core.registry.OmniEntities;
import pugz.omni.core.registry.OmniItems;
import pugz.omni.core.util.BiomeFeatures;
import pugz.omni.core.util.RegistryUtil;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.Effects;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import pugz.omni.core.util.TradeUtils;

public class ParadiseModule extends AbstractModule {
    public static final ParadiseModule instance = new ParadiseModule();

    public ParadiseModule() {
        super("Paradise");
    }

    @Override
    protected void sendInitMessage() {
        System.out.println("Ah, yes. Vibing in Paradise...");
    }

    @Override
    protected void onInitialize() {
        MinecraftForge.EVENT_BUS.addListener(this::onWandererTrades);
        MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoading);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void onClientInitialize() {
        RenderingRegistry.registerEntityRenderingHandler(OmniEntities.SEAHORSE.get(), SeahorseRenderer::new);
    }

    @Override
    protected void onPostInitialize() {
        GlobalEntityTypeAttributes.put(OmniEntities.SEAHORSE.get(), SeahorseEntity.registerAttributes().create());
        EntitySpawnPlacementRegistry.register(OmniEntities.SEAHORSE.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SeahorseEntity::canSeahorseSpawn);
    }

    @Override
    protected void registerBlocks() {
        //RegistryObject<Block> SAND_LAYER;
        //RegistryObject<Block> RED_SAND_LAYER;
        //RegistryObject<Block> WHITE_SAND_LAYER;
        //RegistryObject<Block> SOUL_SAND_LAYER;
        //RegistryObject<Block> ARID_SAND_LAYER;
        //RegistryObject<Block> RED_ARID_SAND_LAYER;

        //RegistryObject<Block> NAUTILUS_SHELL_BLOCK;

        //RegistryObject<Block> WHITE_SAND;
        //RegistryObject<Block> WHITE_SANDSTONE;
        //RegistryObject<Block> WHITE_SANDSTONE_STAIRS;
        //RegistryObject<Block> WHITE_SANDSTONE_SLAB;
        //RegistryObject<Block> WHITE_SANDSTONE_WALL;

        //RegistryObject<Block> PALM_LOG;
        //RegistryObject<Block> PALM_PLANKS;

        //RegistryObject<Block> COCONUT;
        //RegistryObject<Block> COCONUT_BLOCK;

        OmniBlocks.RED_LOTUS_FLOWER = RegistryUtil.createBlock("red_lotus_flower", () -> new LotusFlowerBlock(Effects.NAUSEA, 9), ItemGroup.DECORATIONS);
        OmniBlocks.YELLOW_LOTUS_FLOWER = RegistryUtil.createBlock("yellow_lotus_flower", () -> new LotusFlowerBlock(Effects.NAUSEA, 9), ItemGroup.DECORATIONS);
        OmniBlocks.ORANGE_LOTUS_FLOWER = RegistryUtil.createBlock("orange_lotus_flower", () -> new LotusFlowerBlock(Effects.NAUSEA, 9), ItemGroup.DECORATIONS);
        OmniBlocks.BLUE_LOTUS_FLOWER = RegistryUtil.createBlock("blue_lotus_flower", () -> new LotusFlowerBlock(Effects.NAUSEA, 9), ItemGroup.DECORATIONS);
        OmniBlocks.PINK_LOTUS_FLOWER = RegistryUtil.createBlock("pink_lotus_flower", () -> new LotusFlowerBlock(Effects.NAUSEA, 9), ItemGroup.DECORATIONS);
        OmniBlocks.PURPLE_LOTUS_FLOWER = RegistryUtil.createBlock("purple_lotus_flower", () -> new LotusFlowerBlock(Effects.NAUSEA, 9), ItemGroup.DECORATIONS);
        OmniBlocks.BLACK_LOTUS_FLOWER = RegistryUtil.createBlock("black_lotus_flower", () -> new LotusFlowerBlock(Effects.BLINDNESS, 8), ItemGroup.DECORATIONS);
        OmniBlocks.WHITE_LOTUS_FLOWER = RegistryUtil.createBlock("white_lotus_flower", () -> new LotusFlowerBlock(Effects.NIGHT_VISION, 8), ItemGroup.DECORATIONS);

        //RegistryObject<Block> TROPICAL_FERN;
    }

    @Override
    protected void registerItems() {
        //RegistryObject<Item> COCONUT;
        //RegistryObject<Item> COCONUT_MILK;
        //RegistryObject<Item> COCONUT_BOMB;

        //OmniItems.BAMBOO_SPEAR = RegistryUtil.createItem("bamboo_spear", () -> new SpearItem(new Item.Properties().group(ItemGroup.COMBAT).maxDamage(240)));
        //OmniItems.LIT_BAMBOO_SPEAR = RegistryUtil.createItem("lit_bamboo_spear", () -> new LitSpearItem(new Item.Properties().group(ItemGroup.COMBAT).maxDamage(240)));

        //RegistryObject<Item> NEPTFIN;

        //RegistryObject<Item> TIKI_MASK;

        //RegistryObject<Item> KIWI;

        //RegistryObject<Item> SHELLS;

        OmniItems.SEAHORSE_SPAWN_EGG = RegistryUtil.createItem("seahorse_spawn_egg", () -> new OmniSpawnEggItem(() -> OmniEntities.SEAHORSE.get(), 3966437, 14827318, new Item.Properties().group(ItemGroup.MISC)));
        OmniItems.SEAHORSE_BUCKET = RegistryUtil.createItem("seahorse_bucket", SeahorseBucketItem::new);
    }

    @Override
    protected void registerEntities() {
        //RegistryObject<EntityType<?>> KIWI;
        //RegistryObject<EntityType<?>> TIKI;
        OmniEntities.SEAHORSE = RegistryUtil.createEntity("seahorse", () -> OmniEntities.createLivingEntity(SeahorseEntity::new, EntityClassification.CREATURE, "seahorse",0.3F, 0.85F));
        //RegistryObject<EntityType<?>> GOLIATH;
        //RegistryObject<EntityType<?>> HERMIT_CRAB;
        //RegistryObject<EntityType<?>> SEAGULL;

        //OmniEntities.BAMBOO_SPEAR = RegistryUtil.createEntity("bamboo_spear", () -> OmniEntities.createBambooSpearEntity(BambooSpearEntity::new));
    }

    @Override
    protected void registerBiomes() {
        OmniBiomes.TROPICAL_PLAINS = RegistryUtil.createBiome("tropical_plains", BiomeMaker.makeJungleEdgeBiome(), BiomeManager.BiomeType.WARM, CoreModule.Configuration.CLIENT.TROPICAL_PLAINS_SPAWN_WEIGHT.get(), BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.HOT, BiomeDictionary.Type.WET, BiomeDictionary.Type.JUNGLE, BiomeDictionary.Type.OVERWORLD);
        //RegistryObject<Biome> TROPICAL_ISLANDS;
    }

    @Override
    protected void registerSurfaceBuilders() {
        //RegistryObject<SurfaceBuilder<?>> TROPICAL_ISLAND_SURFACE_BUILDER;
    }

    @Override
    protected void registerFeatures() {
        //RegistryObject<Feature<?>> WHITE_SAND_REPLACEMENT;
    }

    @Override
    protected void registerSounds() {
    }

    public void onWandererTrades(WandererTradesEvent event) {
        event.getGenericTrades().addAll(ImmutableSet.of(
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.RED_LOTUS_FLOWER.get()), CoreModule.Configuration.CLIENT.LOTUS_FLOWER_TRADE_PRICE.get(), 1, 12, 1),
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.BLUE_LOTUS_FLOWER.get()), CoreModule.Configuration.CLIENT.LOTUS_FLOWER_TRADE_PRICE.get(), 1, 12, 1),
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.PINK_LOTUS_FLOWER.get()), CoreModule.Configuration.CLIENT.LOTUS_FLOWER_TRADE_PRICE.get(), 1, 12, 1),
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.BLACK_LOTUS_FLOWER.get()), CoreModule.Configuration.CLIENT.LOTUS_FLOWER_TRADE_PRICE.get(), 1, 12, 1),
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.WHITE_LOTUS_FLOWER.get()), CoreModule.Configuration.CLIENT.LOTUS_FLOWER_TRADE_PRICE.get(), 1, 12, 1),
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.PURPLE_LOTUS_FLOWER.get()), CoreModule.Configuration.CLIENT.LOTUS_FLOWER_TRADE_PRICE.get(), 1, 12, 1),
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.ORANGE_LOTUS_FLOWER.get()), CoreModule.Configuration.CLIENT.LOTUS_FLOWER_TRADE_PRICE.get(), 1, 12, 1),
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.YELLOW_LOTUS_FLOWER.get()), CoreModule.Configuration.CLIENT.LOTUS_FLOWER_TRADE_PRICE.get(), 1, 12, 1)
        ));
    }

    protected void onBiomeLoading(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder gen = event.getGeneration();
        MobSpawnInfoBuilder spawns = event.getSpawns();

        MobSpawnInfo seahorse = new MobSpawnInfo.Builder().withCreatureSpawnProbability(CoreModule.Configuration.CLIENT.SEAHORSE_SPAWN_CHANCE.get().floatValue()).withSpawner(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(OmniEntities.SEAHORSE.get(), 15, 1, 4)).copy();
        seahorse.getSpawners(EntityClassification.WATER_AMBIENT).forEach((s) -> {
            spawns.getSpawner(EntityClassification.WATER_AMBIENT).add(s);
        });

        if (event.getCategory() == Biome.Category.JUNGLE) {
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.RED_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 6, CoreModule.Configuration.CLIENT.LOTUS_FLOWER_SPAWN_CHANCE.get());
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.ORANGE_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 6, CoreModule.Configuration.CLIENT.LOTUS_FLOWER_SPAWN_CHANCE.get());
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.YELLOW_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 6, CoreModule.Configuration.CLIENT.LOTUS_FLOWER_SPAWN_CHANCE.get());
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.BLUE_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 6, CoreModule.Configuration.CLIENT.LOTUS_FLOWER_SPAWN_CHANCE.get());
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.PINK_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 6, CoreModule.Configuration.CLIENT.LOTUS_FLOWER_SPAWN_CHANCE.get());
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.PURPLE_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 6, CoreModule.Configuration.CLIENT.LOTUS_FLOWER_SPAWN_CHANCE.get());
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.BLACK_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 3, Math.round(CoreModule.Configuration.CLIENT.LOTUS_FLOWER_SPAWN_CHANCE.get() * 1.5F));
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.WHITE_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 3, Math.round(CoreModule.Configuration.CLIENT.LOTUS_FLOWER_SPAWN_CHANCE.get() * 1.5F));
        }
    }
}