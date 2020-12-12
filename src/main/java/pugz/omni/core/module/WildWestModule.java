package pugz.omni.core.module;

import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import pugz.omni.client.render.TumbleweedRenderer;
import pugz.omni.common.block.VerticalSlabBlock;
import pugz.omni.common.block.wild_west.*;
import pugz.omni.common.entity.wild_west.TumbleweedEntity;
import pugz.omni.common.world.feature.ExposedOreFeatureConfig;
import pugz.omni.common.world.feature.wild_west.SaguaroCactusFeature;
import pugz.omni.core.registry.OmniBlocks;
import pugz.omni.core.registry.OmniEntities;
import pugz.omni.core.registry.OmniFeatures;
import pugz.omni.core.util.BiomeFeatures;
import pugz.omni.core.util.RegistryUtil;
import net.minecraft.block.*;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class WildWestModule extends AbstractModule {
    public static final WildWestModule instance = new WildWestModule();

    public WildWestModule() {
        super("Wild West");
    }

    @Override
    protected void sendInitMessage() {
        System.out.println("Wandering the Wild West...");
    }

    @Override
    protected void onInitialize() {
        MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoading);
        MinecraftForge.EVENT_BUS.addListener(this::onServerTick);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void onClientInitialize() {
        RenderingRegistry.registerEntityRenderingHandler(OmniEntities.TUMBLEWEED.get(), TumbleweedRenderer::new);
    }

    @Override
    protected void onPostInitialize() {
    }

    @Override
    protected void registerBlocks() {
        OmniBlocks.RED_ROCK = RegistryUtil.createBlock("red_rock", () -> new Block(AbstractBlock.Properties.from(Blocks.SANDSTONE)), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.RED_ROCK_STAIRS = RegistryUtil.createBlock("red_rock_stairs", () -> new StairsBlock(() -> OmniBlocks.RED_ROCK.get().getDefaultState(), AbstractBlock.Properties.from(Blocks.COBBLESTONE)), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.RED_ROCK_SLAB = RegistryUtil.createBlock("red_rock_slab", () -> new SlabBlock(AbstractBlock.Properties.from(OmniBlocks.RED_ROCK.get())), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.RED_ROCK_WALL = RegistryUtil.createBlock("red_rock_wall", () -> new WallBlock(AbstractBlock.Properties.from(OmniBlocks.RED_ROCK.get())), ItemGroup.DECORATIONS);
        OmniBlocks.RED_ROCK_BRICKS = RegistryUtil.createBlock("red_rock_bricks", () -> new Block(AbstractBlock.Properties.from(OmniBlocks.RED_ROCK.get())), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.RED_ROCK_BRICK_STAIRS = RegistryUtil.createBlock("red_rock_brick_stairs", () -> new StairsBlock(() -> OmniBlocks.RED_ROCK_BRICKS.get().getDefaultState(), AbstractBlock.Properties.from(Blocks.STONE_BRICKS)), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.RED_ROCK_BRICK_SLAB = RegistryUtil.createBlock("red_rock_brick_slab", () -> new SlabBlock(AbstractBlock.Properties.from(OmniBlocks.RED_ROCK_BRICKS.get())), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.RED_ROCK_BRICK_WALL = RegistryUtil.createBlock("red_rock_brick_wall", () -> new WallBlock(AbstractBlock.Properties.from(OmniBlocks.RED_ROCK_BRICKS.get())), ItemGroup.DECORATIONS);
        OmniBlocks.CHISELED_RED_ROCK_BRICKS = RegistryUtil.createBlock("chiseled_red_rock_bricks", () -> new Block(AbstractBlock.Properties.from(OmniBlocks.RED_ROCK_BRICKS.get())), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.CRACKED_RED_ROCK_BRICKS = RegistryUtil.createBlock("cracked_red_rock_bricks", () -> new Block(AbstractBlock.Properties.from(OmniBlocks.RED_ROCK_BRICKS.get())), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.RED_ROCK_PILLAR = RegistryUtil.createBlock("red_rock_pillar", () -> new RotatedPillarBlock(AbstractBlock.Properties.from(OmniBlocks.RED_ROCK_BRICKS.get())), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.RED_ROCK_BRICK_BUTTON = RegistryUtil.createBlock("red_rock_brick_button", RedRockBrickButton::new, ItemGroup.REDSTONE);
        OmniBlocks.RED_ROCK_BRICK_PRESSURE_PLATE = RegistryUtil.createBlock("red_rock_brick_pressure_plate", RedRockBrickPressurePlate::new, ItemGroup.REDSTONE);

        if (ModList.get().isLoaded("quark")) {
            OmniBlocks.RED_ROCK_VERTICAL_SLAB = RegistryUtil.createBlock("red_rock_vertical_slab", () -> new VerticalSlabBlock(AbstractBlock.Properties.from(OmniBlocks.RED_ROCK.get())), ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.RED_ROCK_BRICK_VERTICAL_SLAB = RegistryUtil.createBlock("red_rock_brick_vertical_slab", () -> new VerticalSlabBlock(AbstractBlock.Properties.from(OmniBlocks.RED_ROCK.get())), ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.RED_ROCK_PAVEMENT = RegistryUtil.createBlock("red_rock_pavement", () -> new Block(AbstractBlock.Properties.from(OmniBlocks.RED_ROCK_BRICKS.get())), ItemGroup.BUILDING_BLOCKS);
        }

        OmniBlocks.SAGUARO_CACTUS = RegistryUtil.createBlock("saguaro_cactus", SaguaroCactusBlock::new, ItemGroup.DECORATIONS);
        OmniBlocks.CACTUS_BLOOM = RegistryUtil.createBlock("cactus_bloom", CactusBloomBlock::new, ItemGroup.DECORATIONS);
        OmniBlocks.TUMBLEWEED = RegistryUtil.createBlock("tumbleweed", TumbleweedBlock::new, ItemGroup.DECORATIONS);
    }

    @Override
    protected void registerEntities() {
        OmniEntities.TUMBLEWEED = RegistryUtil.createEntity("tumbleweed", OmniEntities::createTumbleweedEntity);
    }

    @Override
    protected void registerFeatures() {
        OmniFeatures.SAGUARO_CACTUS = RegistryUtil.createFeature("saguaro_cactus", () -> new SaguaroCactusFeature(NoFeatureConfig.field_236558_a_));
    }

    @Override
    protected void registerConfiguredFeatures() {
        OmniFeatures.Configured.RED_ROCK = RegistryUtil.createConfiguredFeature("red_rock", OmniFeatures.EXPOSED_ORE.get().withConfiguration(new ExposedOreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, OmniBlocks.RED_ROCK.get().getDefaultState(), null, CoreModule.Configuration.COMMON.RED_ROCK_GEN_SIZE.get(), ExposedOreFeatureConfig.CaveFace.ALL)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 128)).chance(1)).range(80).square().func_242731_b(10));
        OmniFeatures.Configured.SAGUARO_CACTUS = RegistryUtil.createConfiguredFeature("saguaro_cacti", OmniFeatures.SAGUARO_CACTUS.get().withConfiguration(new NoFeatureConfig()).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(12)).chance(12);
        OmniFeatures.Configured.TUMBLEWEEDS = RegistryUtil.createConfiguredFeature("tumbleweeds", Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(OmniBlocks.TUMBLEWEED.get().getDefaultState()), SimpleBlockPlacer.PLACER).build()).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(3).chance(8)).chance(16);
    }

    protected void onBiomeLoading(BiomeLoadingEvent event) {
        Biome.Category category = event.getCategory();
        BiomeGenerationSettingsBuilder gen = event.getGeneration();

        if (category == Biome.Category.MESA) {
            BiomeFeatures.addRedRock(gen);
            BiomeFeatures.addTerracottaCave(gen);
            BiomeFeatures.addSaguaroCacti(gen);
            BiomeFeatures.addTumbleweeds(gen);
        }
    }

    protected void onServerTick(TickEvent.ServerTickEvent event) {
        TumbleweedEntity.updateWind();
    }
}