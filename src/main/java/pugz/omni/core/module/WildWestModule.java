package pugz.omni.core.module;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import org.apache.commons.lang3.StringUtils;
import pugz.omni.client.render.TumbleweedRenderer;
import pugz.omni.common.block.VerticalSlabBlock;
import pugz.omni.common.block.wild_west.*;
import pugz.omni.common.entity.wild_west.TumbleweedEntity;
import pugz.omni.common.world.biome.WoodedBadlandsBiome;
import pugz.omni.common.world.biome.WoodedDesertBiome;
import pugz.omni.common.world.feature.ExposedOreFeatureConfig;
import pugz.omni.common.world.feature.wild_west.SaguaroCactusFeature;
import pugz.omni.common.world.structure.wild_west.GhostTownPools;
import pugz.omni.common.world.structure.wild_west.GhostTownStructure;
import pugz.omni.common.world.surface.WoodedBadlandsSurfaceBuilder;
import pugz.omni.common.world.surface.WoodedDesertSurfaceBuilder;
import pugz.omni.core.registry.*;
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
        GhostTownStructure.setup();
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
    protected void registerBiomes() {
        OmniBiomes.WOODED_BADLANDS = RegistryUtil.createBiome(new WoodedBadlandsBiome());
        OmniBiomes.WOODED_DESERT = RegistryUtil.createBiome(new WoodedDesertBiome());
    }

    @Override
    protected void registerSurfaceBuilders() {
        OmniSurfaceBuilders.WOODED_BADLANDS = RegistryUtil.createSurfaceBuilder("wooded_badlands", () -> new WoodedBadlandsSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
        OmniSurfaceBuilders.WOODED_DESERT = RegistryUtil.createSurfaceBuilder("wooded_desert", () -> new WoodedDesertSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
    }

    @Override
    protected void registerFeatures() {
        OmniFeatures.SAGUARO_CACTUS = RegistryUtil.createFeature("saguaro_cactus", () -> new SaguaroCactusFeature(NoFeatureConfig.field_236558_a_));
    }

    @Override
    protected void registerConfiguredFeatures() {
        OmniFeatures.Configured.RED_ROCK = RegistryUtil.createConfiguredFeature("red_rock", OmniFeatures.EXPOSED_ORE.get().withConfiguration(new ExposedOreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, OmniBlocks.RED_ROCK.get().getDefaultState(), null, CoreModule.Configuration.COMMON.RED_ROCK_GEN_SIZE.get(), ExposedOreFeatureConfig.CaveFace.ALL)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 128)).chance(1)).range(80).square().func_242731_b(10));
        OmniFeatures.Configured.SAGUARO_CACTUS = RegistryUtil.createConfiguredFeature("saguaro_cacti", OmniFeatures.SAGUARO_CACTUS.get().withConfiguration(new NoFeatureConfig()).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(12)).chance(12);

        OmniFeatures.Configured.DENSE_SAVANNA_TREES = RegistryUtil.createConfiguredFeature("dense_savanna_trees", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Features.ACACIA.withChance(0.8F)), Features.OAK)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
    }

    @Override
    protected void registerStructures() {
        OmniStructures.GHOST_TOWN = RegistryUtil.createStructure("ghost_town", () -> new GhostTownStructure(VillageConfig.field_236533_a_));
    }

    @Override
    protected void registerStructureFeatures() {
        OmniStructures.Features.GHOST_TOWN = RegistryUtil.createStructureFeature("ghost_town", OmniStructures.GHOST_TOWN.get().withConfiguration(new VillageConfig(() -> {
            return GhostTownPools.field_244090_a;
        }, 6)));
    }

    protected void onBiomeLoading(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder gen = event.getGeneration();
        ResourceLocation name = event.getName();

        if (event.getCategory() == Biome.Category.MESA || StringUtils.contains(name.getPath(), "badland")) {
            BiomeFeatures.addRedRock(gen);
            BiomeFeatures.addTerracottaCave(gen);
            BiomeFeatures.addSaguaroCacti(gen);
            BiomeFeatures.addGhostTowns(gen);

            if (name != null) {
                event.getSpawns().withSpawner(EntityClassification.MISC, new MobSpawnInfo.Spawners(OmniEntities.TUMBLEWEED.get(), 10, 1, 3));
            }
        }

        if (name.equals(OmniBiomes.WOODED_BADLANDS.getRegistryName().getPath())) {
            gen.withSurfaceBuilder(OmniSurfaceBuilders.Configured.WOODED_BADLANDS);
            BiomeFeatures.addDenseSavannaTrees(gen);
            BiomeFeatures.addTerracottaRocks(gen);
        }

        if (name.getPath().equals(OmniBiomes.WOODED_DESERT.getRegistryName().getPath())) {
            gen.withSurfaceBuilder(OmniSurfaceBuilders.Configured.WOODED_DESERT);
            BiomeFeatures.addDenseSavannaTrees(gen);
        }
    }

    protected void onServerTick(TickEvent.ServerTickEvent event) {
        TumbleweedEntity.updateWind();
    }
}