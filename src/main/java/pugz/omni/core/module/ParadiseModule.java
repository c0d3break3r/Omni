package pugz.omni.core.module;

import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import org.apache.commons.lang3.StringUtils;
import pugz.omni.client.render.SeahorseRenderer;
import pugz.omni.common.block.paradise.LotusFlowerBlock;
import pugz.omni.common.entity.paradise.SeahorseEntity;
import pugz.omni.common.item.paradise.SeahorseBucketItem;
import pugz.omni.common.item.OmniSpawnEggItem;
import pugz.omni.common.world.biome.BadlandsJungleBiome;
import pugz.omni.common.world.biome.DesertJungleBiome;
import pugz.omni.common.world.biome.TropicalPlainsBiome;
import pugz.omni.common.world.feature.SizedBlockBlobConfig;
import pugz.omni.common.world.feature.SizedBlockBlobFeature;
import pugz.omni.common.world.surface.LushBadlandsSurfaceBuilder;
import pugz.omni.common.world.surface.LushDesertSurfaceBuilder;
import pugz.omni.core.registry.*;
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

        EntitySpawnPlacementRegistry.register(OmniEntities.SEAHORSE.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SeahorseEntity::canSeahorseSpawn);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void onClientInitialize() {
        RenderingRegistry.registerEntityRenderingHandler(OmniEntities.SEAHORSE.get(), SeahorseRenderer::new);

        MinecraftForge.EVENT_BUS.addListener(this::onItemColorHandler);

        //Minecraft.getInstance().particles.registerFactory(OmniParticles.RED_LOTUS_LEAF.get(), LotusPetalParticle.Factory::new);
    }

    @Override
    protected void onPostInitialize() {
        GlobalEntityTypeAttributes.put(OmniEntities.SEAHORSE.get(), SeahorseEntity.registerAttributes().create());
    }

    @Override
    protected void registerBlocks() {
        OmniBlocks.RED_LOTUS_FLOWER = RegistryUtil.createBlock("red_lotus_flower", () -> new LotusFlowerBlock(Effects.NAUSEA, 9), ItemGroup.DECORATIONS);
        OmniBlocks.YELLOW_LOTUS_FLOWER = RegistryUtil.createBlock("yellow_lotus_flower", () -> new LotusFlowerBlock(Effects.NAUSEA, 9), ItemGroup.DECORATIONS);
        OmniBlocks.ORANGE_LOTUS_FLOWER = RegistryUtil.createBlock("orange_lotus_flower", () -> new LotusFlowerBlock(Effects.NAUSEA, 9), ItemGroup.DECORATIONS);
        OmniBlocks.BLUE_LOTUS_FLOWER = RegistryUtil.createBlock("blue_lotus_flower", () -> new LotusFlowerBlock(Effects.NAUSEA, 9), ItemGroup.DECORATIONS);
        OmniBlocks.PINK_LOTUS_FLOWER = RegistryUtil.createBlock("pink_lotus_flower", () -> new LotusFlowerBlock(Effects.NAUSEA, 9), ItemGroup.DECORATIONS);
        OmniBlocks.PURPLE_LOTUS_FLOWER = RegistryUtil.createBlock("purple_lotus_flower", () -> new LotusFlowerBlock(Effects.NAUSEA, 9), ItemGroup.DECORATIONS);
        OmniBlocks.BLACK_LOTUS_FLOWER = RegistryUtil.createBlock("black_lotus_flower", () -> new LotusFlowerBlock(Effects.BLINDNESS, 8), ItemGroup.DECORATIONS);
        OmniBlocks.WHITE_LOTUS_FLOWER = RegistryUtil.createBlock("white_lotus_flower", () -> new LotusFlowerBlock(Effects.NIGHT_VISION, 8), ItemGroup.DECORATIONS);
    }

    @Override
    protected void registerItems() {
        OmniItems.SEAHORSE_SPAWN_EGG = RegistryUtil.createItem("seahorse_spawn_egg", () -> new OmniSpawnEggItem(() -> OmniEntities.SEAHORSE.get(), 3966437, 14827318, new Item.Properties().group(ItemGroup.MISC)));
        OmniItems.SEAHORSE_BUCKET = RegistryUtil.createItem("seahorse_bucket", SeahorseBucketItem::new);
    }

    @Override
    protected void registerEntities() {
        OmniEntities.SEAHORSE = RegistryUtil.createEntity("seahorse", () -> OmniEntities.createLivingEntity(SeahorseEntity::new, EntityClassification.CREATURE, "seahorse",0.3F, 0.85F));
    }

    @Override
    protected void registerBiomes() {
        OmniBiomes.TROPICAL_PLAINS = RegistryUtil.createBiome(new TropicalPlainsBiome());
        OmniBiomes.JUNGLE_BADLANDS = RegistryUtil.createBiome(new BadlandsJungleBiome());
        OmniBiomes.JUNGLE_DESERT = RegistryUtil.createBiome(new DesertJungleBiome());
    }

    @Override
    protected void registerSurfaceBuilders() {
        OmniSurfaceBuilders.JUNGLE_BADLANDS = RegistryUtil.createSurfaceBuilder("badlands_jungle", () -> new LushBadlandsSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
        OmniSurfaceBuilders.JUNGLE_DESERT = RegistryUtil.createSurfaceBuilder("desert_jungle", () -> new LushDesertSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
    }

    @Override
    protected void registerFeatures() {
        OmniFeatures.SIZED_BLOCK_BLOB = RegistryUtil.createFeature("sized_block_blob", () -> new SizedBlockBlobFeature(SizedBlockBlobConfig.CODEC));
    }

    @Override
    protected void registerConfiguredFeatures() {
        OmniFeatures.Configured.RED_LOTUS_FLOWER = RegistryUtil.createConfiguredFeature("red_lotus_flower", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(OmniBlocks.RED_LOTUS_FLOWER.get().getDefaultState()), SimpleBlockPlacer.PLACER)).tries(6).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL)).func_227317_b_().build()).func_242730_a(FeatureSpread.func_242253_a(-1, 4)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(5).chance(CoreModule.Configuration.COMMON.LOTUS_FLOWER_SPAWN_CHANCE.get()));
        OmniFeatures.Configured.ORANGE_LOTUS_FLOWER = RegistryUtil.createConfiguredFeature("orange_lotus_flower", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(OmniBlocks.ORANGE_LOTUS_FLOWER.get().getDefaultState()), SimpleBlockPlacer.PLACER)).tries(6).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL)).func_227317_b_().build()).func_242730_a(FeatureSpread.func_242253_a(-1, 4)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(5).chance(CoreModule.Configuration.COMMON.LOTUS_FLOWER_SPAWN_CHANCE.get()));
        OmniFeatures.Configured.YELLOW_LOTUS_FLOWER = RegistryUtil.createConfiguredFeature("yellow_lotus_flower", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(OmniBlocks.YELLOW_LOTUS_FLOWER.get().getDefaultState()), SimpleBlockPlacer.PLACER)).tries(6).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL)).func_227317_b_().build()).func_242730_a(FeatureSpread.func_242253_a(-1, 4)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(5).chance(CoreModule.Configuration.COMMON.LOTUS_FLOWER_SPAWN_CHANCE.get()));
        OmniFeatures.Configured.BLUE_LOTUS_FLOWER = RegistryUtil.createConfiguredFeature("blue_lotus_flower", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(OmniBlocks.BLUE_LOTUS_FLOWER.get().getDefaultState()), SimpleBlockPlacer.PLACER)).tries(6).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL)).func_227317_b_().build()).func_242730_a(FeatureSpread.func_242253_a(-1, 4)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(5).chance(CoreModule.Configuration.COMMON.LOTUS_FLOWER_SPAWN_CHANCE.get()));
        OmniFeatures.Configured.PINK_LOTUS_FLOWER = RegistryUtil.createConfiguredFeature("pink_lotus_flower", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(OmniBlocks.PINK_LOTUS_FLOWER.get().getDefaultState()), SimpleBlockPlacer.PLACER)).tries(6).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL)).func_227317_b_().build()).func_242730_a(FeatureSpread.func_242253_a(-1, 4)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(5).chance(CoreModule.Configuration.COMMON.LOTUS_FLOWER_SPAWN_CHANCE.get()));
        OmniFeatures.Configured.PURPLE_LOTUS_FLOWER = RegistryUtil.createConfiguredFeature("purple_lotus_flower", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(OmniBlocks.PURPLE_LOTUS_FLOWER.get().getDefaultState()), SimpleBlockPlacer.PLACER)).tries(6).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL)).func_227317_b_().build()).func_242730_a(FeatureSpread.func_242253_a(-1, 4)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(5).chance(CoreModule.Configuration.COMMON.LOTUS_FLOWER_SPAWN_CHANCE.get()));
        OmniFeatures.Configured.BLACK_LOTUS_FLOWER = RegistryUtil.createConfiguredFeature("black_lotus_flower", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(OmniBlocks.BLACK_LOTUS_FLOWER.get().getDefaultState()), SimpleBlockPlacer.PLACER)).tries(3).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL)).func_227317_b_().build()).func_242730_a(FeatureSpread.func_242253_a(-1, 4)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(5).chance(Math.round(CoreModule.Configuration.COMMON.LOTUS_FLOWER_SPAWN_CHANCE.get() * 1.5F)));
        OmniFeatures.Configured.WHITE_LOTUS_FLOWER = RegistryUtil.createConfiguredFeature("white_lotus_flower", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(OmniBlocks.WHITE_LOTUS_FLOWER.get().getDefaultState()), SimpleBlockPlacer.PLACER)).tries(3).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL)).func_227317_b_().build()).func_242730_a(FeatureSpread.func_242253_a(-1, 4)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(5).chance(Math.round(CoreModule.Configuration.COMMON.LOTUS_FLOWER_SPAWN_CHANCE.get() * 1.5F)));

        OmniFeatures.Configured.TERRACOTTA_ROCK = RegistryUtil.createConfiguredFeature("terracotta_rock", OmniFeatures.SIZED_BLOCK_BLOB.get().withConfiguration(new SizedBlockBlobConfig(Blocks.TERRACOTTA.getDefaultState(), 1)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).chance(2).func_242732_c(2));
    }

    @Override
    protected void registerParticles() {
        //OmniParticles.RED_LOTUS_LEAF = RegistryUtil.createParticle("red_lotus_leaf");
    }

    public void onWandererTrades(WandererTradesEvent event) {
        event.getGenericTrades().addAll(ImmutableSet.of(
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.RED_LOTUS_FLOWER.get()), CoreModule.Configuration.COMMON.LOTUS_FLOWER_TRADE_PRICE.get(), 1, 12, 1),
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.BLUE_LOTUS_FLOWER.get()), CoreModule.Configuration.COMMON.LOTUS_FLOWER_TRADE_PRICE.get(), 1, 12, 1),
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.PINK_LOTUS_FLOWER.get()), CoreModule.Configuration.COMMON.LOTUS_FLOWER_TRADE_PRICE.get(), 1, 12, 1),
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.BLACK_LOTUS_FLOWER.get()), CoreModule.Configuration.COMMON.LOTUS_FLOWER_TRADE_PRICE.get(), 1, 12, 1),
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.WHITE_LOTUS_FLOWER.get()), CoreModule.Configuration.COMMON.LOTUS_FLOWER_TRADE_PRICE.get(), 1, 12, 1),
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.PURPLE_LOTUS_FLOWER.get()), CoreModule.Configuration.COMMON.LOTUS_FLOWER_TRADE_PRICE.get(), 1, 12, 1),
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.ORANGE_LOTUS_FLOWER.get()), CoreModule.Configuration.COMMON.LOTUS_FLOWER_TRADE_PRICE.get(), 1, 12, 1),
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.YELLOW_LOTUS_FLOWER.get()), CoreModule.Configuration.COMMON.LOTUS_FLOWER_TRADE_PRICE.get(), 1, 12, 1)
        ));
    }

    protected void onBiomeLoading(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder gen = event.getGeneration();
        ResourceLocation name = event.getName();
        //Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());

        if (event.getCategory() == Biome.Category.JUNGLE) {
            BiomeFeatures.addLotuses(gen);
        }

        if (name != null) {
            if (StringUtils.contains(name.getPath(), "warm_ocean")) {
                event.getSpawns().withSpawner(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(OmniEntities.SEAHORSE.get(), 100, 1, 1));
            }

            if (name.getPath().equals(OmniBiomes.JUNGLE_BADLANDS.getRegistryName().getPath())) {
                gen.withSurfaceBuilder(OmniSurfaceBuilders.Configured.JUNGLE_BADLANDS);
                BiomeFeatures.addTerracottaRocks(gen);
            }

            if (name.getPath().equals(OmniBiomes.JUNGLE_DESERT.getRegistryName().getPath())) {
                gen.withSurfaceBuilder(OmniSurfaceBuilders.Configured.JUNGLE_DESERT);
            }
        }
    }

    public void onItemColorHandler(ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, tintIndex) -> ((OmniSpawnEggItem) OmniItems.SEAHORSE_SPAWN_EGG.get()).getColor(tintIndex), OmniItems.SEAHORSE_SPAWN_EGG.get());
    }
}