package pugz.omni.core.module;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BushFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.commons.lang3.StringUtils;
import pugz.omni.common.block.*;
import pugz.omni.common.block.wild_west.*;
import pugz.omni.common.item.OmniBoatItem;
import pugz.omni.common.item.OmniSignItem;
import pugz.omni.common.world.biome.WoodedBadlandsBiome;
import pugz.omni.common.world.biome.WoodedDesertBiome;
import pugz.omni.common.world.feature.ExposedOreFeatureConfig;
import pugz.omni.common.world.feature.wild_west.placers.PaloVerdeTrunkPlacer;
import pugz.omni.common.world.feature.wild_west.tree.PaloVerdeTree;
import pugz.omni.common.world.feature.wild_west.SaguaroCactusFeature;
import pugz.omni.common.world.surface.WoodedBadlandsSurfaceBuilder;
import pugz.omni.common.world.surface.WoodedDesertSurfaceBuilder;
import pugz.omni.core.Omni;
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
        System.out.println("Saguaros and tumbleweeds coming soon woahh");
    }

    @Override
    protected void onInitialize() {
        MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoading);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void onClientInitialize() {
        RenderTypeLookup.setRenderLayer(OmniBlocks.PALO_VERDE_DOOR.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(OmniBlocks.PALO_VERDE_TRAPDOOR.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(OmniBlocks.PALO_VERDE_LADDER.get(), RenderType.getCutout());

        RegistryUtil.sprites.add(new RenderMaterial(Atlases.SIGN_ATLAS, new ResourceLocation(Omni.MOD_ID, "entity/sign/palo_verde")));
    }

    @Override
    protected void onPostInitialize(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            TileEntityType.SIGN.validBlocks.add(OmniBlocks.PALO_VERDE_SIGN.get());
            TileEntityType.SIGN.validBlocks.add(OmniBlocks.PALO_VERDE_WALL_SIGN.get());
        });
    }

    @Override
    @SuppressWarnings("deprecation")
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

        //if (ModList.get().isLoaded("quark")) {
            OmniBlocks.RED_ROCK_VERTICAL_SLAB = RegistryUtil.createBlock("red_rock_vertical_slab", () -> new VerticalSlabBlock(AbstractBlock.Properties.from(OmniBlocks.RED_ROCK.get())), ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.RED_ROCK_BRICK_VERTICAL_SLAB = RegistryUtil.createBlock("red_rock_brick_vertical_slab", () -> new VerticalSlabBlock(AbstractBlock.Properties.from(OmniBlocks.RED_ROCK.get())), ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.RED_ROCK_PAVEMENT = RegistryUtil.createBlock("red_rock_pavement", () -> new Block(AbstractBlock.Properties.from(OmniBlocks.RED_ROCK_BRICKS.get())), ItemGroup.BUILDING_BLOCKS);
        //}

        OmniBlocks.SAGUARO_CACTUS = RegistryUtil.createBlock("saguaro_cactus", SaguaroCactusBlock::new, ItemGroup.DECORATIONS);
        OmniBlocks.CACTUS_BLOOM = RegistryUtil.createBlock("cactus_bloom", CactusBloomBlock::new, ItemGroup.DECORATIONS);

        OmniBlocks.STRIPPED_PALO_VERDE_LOG = RegistryUtil.createBlock("stripped_palo_verde_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.GREEN_TERRACOTTA).hardnessAndResistance(2.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.PALO_VERDE_LOG = RegistryUtil.createBlock("palo_verde_log", () -> new OmniLogBlock((RotatedPillarBlock) OmniBlocks.STRIPPED_PALO_VERDE_LOG.get()), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.STRIPPED_PALO_VERDE_WOOD = RegistryUtil.createBlock("stripped_palo_verde_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.LIME_TERRACOTTA).hardnessAndResistance(2.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.PALO_VERDE_WOOD = RegistryUtil.createBlock("palo_verde_wood", () -> new OmniLogBlock((RotatedPillarBlock) OmniBlocks.STRIPPED_PALO_VERDE_WOOD.get()), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.PALO_VERDE_PLANKS = RegistryUtil.createBlock("palo_verde_planks", () -> new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.GREEN_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.PALO_VERDE_STAIRS = RegistryUtil.createBlock("palo_verde_stairs", () -> new StairsBlock(() -> OmniBlocks.PALO_VERDE_PLANKS.get().getDefaultState(), AbstractBlock.Properties.from(OmniBlocks.PALO_VERDE_PLANKS.get())), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.PALO_VERDE_SLAB = RegistryUtil.createBlock("palo_verde_slab", () -> new SlabBlock(AbstractBlock.Properties.from(OmniBlocks.PALO_VERDE_PLANKS.get())), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.PALO_VERDE_FENCE = RegistryUtil.createBlock("palo_verde_fence", () -> new FenceBlock(AbstractBlock.Properties.from(OmniBlocks.PALO_VERDE_PLANKS.get())), ItemGroup.DECORATIONS);
        OmniBlocks.PALO_VERDE_FENCE_GATE = RegistryUtil.createBlock("palo_verde_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.from(OmniBlocks.PALO_VERDE_PLANKS.get())), ItemGroup.REDSTONE);
        OmniBlocks.PALO_VERDE_DOOR = RegistryUtil.createDoor("palo_verde_door", () -> new DoorBlock(AbstractBlock.Properties.create(Material.WOOD, OmniBlocks.PALO_VERDE_PLANKS.get().getMaterialColor()).hardnessAndResistance(3.0F).sound(SoundType.WOOD).notSolid()), ItemGroup.REDSTONE);
        OmniBlocks.PALO_VERDE_TRAPDOOR = RegistryUtil.createBlock("palo_verde_trapdoor", () -> new TrapDoorBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.GREEN_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid().setAllowsSpawn((BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) -> false)), ItemGroup.REDSTONE);
        OmniBlocks.PALO_VERDE_BUTTON = RegistryUtil.createBlock("palo_verde_button", () -> new WoodButtonBlock(AbstractBlock.Properties.from(Blocks.OAK_BUTTON)), ItemGroup.REDSTONE);
        OmniBlocks.PALO_VERDE_PRESSURE_PLATE = RegistryUtil.createBlock("palo_verde_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.from(Blocks.OAK_PRESSURE_PLATE)), ItemGroup.REDSTONE);
        OmniBlocks.PALO_VERDE_BOOKSHELF = RegistryUtil.createBlock("palo_verde_bookshelf", () -> new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.GREEN_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.PALO_VERDE_CHEST = RegistryUtil.createBlock("palo_verde_chest", () -> new OmniChestBlock(AbstractBlock.Properties.from(Blocks.CHEST), "palo_verde"), ItemGroup.DECORATIONS);
        OmniBlocks.PALO_VERDE_TRAPPED_CHEST = RegistryUtil.createBlock("palo_verde_trapped_chest", () -> new OmniTrappedChestBlock(AbstractBlock.Properties.from(Blocks.TRAPPED_CHEST), "palo_verde"), ItemGroup.REDSTONE);
        OmniBlocks.PALO_VERDE_LEAVES = RegistryUtil.createBlock("palo_verde_leaves", OmniLeavesBlock::new, ItemGroup.DECORATIONS);
        OmniBlocks.PALO_VERDE_SAPLING = RegistryUtil.createBlock("palo_verde_sapling", () -> new OmniSaplingBlock(new PaloVerdeTree()), ItemGroup.DECORATIONS);
        OmniBlocks.PALO_VERDE_SIGN = RegistryUtil.createBlock("palo_verde_sign", () -> new OmniStandingSignBlock(AbstractBlock.Properties.from(Blocks.OAK_SIGN), "palo_verde"));
        OmniBlocks.PALO_VERDE_WALL_SIGN = RegistryUtil.createBlock("palo_verde_wall_sign", () -> new OmniWallSignBlock(AbstractBlock.Properties.from(Blocks.OAK_WALL_SIGN).lootFrom(OmniBlocks.PALO_VERDE_SIGN.get()), "palo_verde"));
        OmniBlocks.PALO_VERDE_LADDER = RegistryUtil.createBlock("palo_verde_ladder", () -> new LadderBlock(AbstractBlock.Properties.from(Blocks.LADDER)), ItemGroup.DECORATIONS);
        OmniBlocks.PALO_VERDE_BEEHIVE = RegistryUtil.createBlock("palo_verde_beehive", OmniBeehiveBlock::new, ItemGroup.DECORATIONS);
        //if (ModList.get().isLoaded("quark")) {
            OmniBlocks.VERTICAL_PALO_VERDE_PLANKS = RegistryUtil.createBlock("vertical_palo_verde_planks", () -> new Block(AbstractBlock.Properties.from(OmniBlocks.PALO_VERDE_PLANKS.get())), ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.PALO_VERDE_VERTICAL_SLAB = RegistryUtil.createBlock("palo_verde_vertical_slab", () -> new VerticalSlabBlock(AbstractBlock.Properties.from(OmniBlocks.PALO_VERDE_PLANKS.get())), ItemGroup.BUILDING_BLOCKS);
        //}
    }

    @Override
    protected void registerItems() {
        OmniItems.PALO_VERDE_BOAT = RegistryUtil.createItem("palo_verde_boat", () -> new OmniBoatItem(new Item.Properties().group(ItemGroup.TRANSPORTATION).maxStackSize(1), "palo_verde"));
        OmniItems.PALO_VERDE_SIGN = RegistryUtil.createItem("palo_verde_sign", () -> new OmniSignItem(new Item.Properties().group(ItemGroup.DECORATIONS).maxStackSize(16), OmniBlocks.PALO_VERDE_SIGN.get(), OmniBlocks.PALO_VERDE_WALL_SIGN.get()));
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

        OmniFeatures.Configured.PALO_VERDE_TREE = RegistryUtil.createConfiguredFeature("palo_verde", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(OmniBlocks.PALO_VERDE_LOG.get().getDefaultState()), new SimpleBlockStateProvider(OmniBlocks.PALO_VERDE_LEAVES.get().getDefaultState()), new BushFoliagePlacer(FeatureSpread.func_242252_a(1), FeatureSpread.func_242252_a(0), 1), new PaloVerdeTrunkPlacer(3, 0, 1), new TwoLayerFeature(2, 0, 1))).setIgnoreVines().setDecorators(ImmutableList.of(Features.Placements.BEES_0002_PLACEMENT)).build()));
        OmniFeatures.Configured.PALO_VERDE_TREES = RegistryUtil.createConfiguredFeature("palo_verde_trees", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(OmniFeatures.Configured.PALO_VERDE_TREE.withChance(0.8F)), OmniFeatures.Configured.PALO_VERDE_TREE)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(5, 0.5F, 1))));

        OmniFeatures.Configured.DENSE_SAVANNA_TREES = RegistryUtil.createConfiguredFeature("dense_savanna_trees", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Features.ACACIA.withChance(0.8F)), Features.OAK)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
    }

    @Override
    protected void registerPlacers() {
        OmniFeatures.Placers.PALO_VERDE_TRUNK_PLACER = RegistryUtil.createTrunkPlacer("palo_verde_trunk_placer", PaloVerdeTrunkPlacer.CODEC);
    }

    protected void onBiomeLoading(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder gen = event.getGeneration();
        ResourceLocation name = event.getName();

        if (event.getCategory() == Biome.Category.MESA || StringUtils.contains(name.getPath(), "badland")) {
            BiomeFeatures.addRedRock(gen);
            BiomeFeatures.addTerracottaCaves(gen);
            BiomeFeatures.addSaguaroCacti(gen);
        }

        if (name.getPath().equals(OmniBiomes.WOODED_BADLANDS.getRegistryName().getPath())) {
            gen.withSurfaceBuilder(OmniSurfaceBuilders.Configured.WOODED_BADLANDS);
            BiomeFeatures.addDenseSavannaTrees(gen);
            BiomeFeatures.addTerracottaRocks(gen);
            BiomeFeatures.addPaloVerdeTrees(gen);
        }

        if (name.getPath().equals(OmniBiomes.WOODED_DESERT.getRegistryName().getPath())) {
            gen.withSurfaceBuilder(OmniSurfaceBuilders.Configured.WOODED_DESERT);
            BiomeFeatures.addDenseSavannaTrees(gen);
            BiomeFeatures.addPaloVerdeTrees(gen);
        }
    }
}