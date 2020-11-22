package pugz.omni.core.module;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import pugz.omni.common.block.VerticalSlabBlock;
import pugz.omni.common.block.deserted.RedRockBrickButton;
import pugz.omni.common.block.deserted.RedRockBrickPressurePlate;
import pugz.omni.common.world.feature.CaveOreFeatureConfig;
import pugz.omni.core.registry.OmniBlocks;
import pugz.omni.core.util.BiomeFeatures;
import pugz.omni.core.util.RegistryUtil;
import net.minecraft.block.*;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class DesertedModule extends AbstractModule {
    public static final DesertedModule instance = new DesertedModule();

    public DesertedModule() {
        super("Deserted");
    }

    @Override
    protected void sendInitMessage() {
        System.out.println("Wandering the Deserted Desert...");
    }

    @Override
    protected void onInitialize() {
        MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoading);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void onClientInitialize() {
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
            OmniBlocks.RED_ROCK_BRICK_VERICAL_SLAB = RegistryUtil.createBlock("red_rock_brick_vertical_slab", () -> new VerticalSlabBlock(AbstractBlock.Properties.from(OmniBlocks.RED_ROCK.get())), ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.RED_ROCK_PAVEMENT = RegistryUtil.createBlock("red_rock_pavement", () -> new Block(AbstractBlock.Properties.from(OmniBlocks.RED_ROCK_BRICKS.get())), ItemGroup.BUILDING_BLOCKS);
        }

        //RegistryObject<Block> QUICKSAND;
        //RegistryObject<Block> RED_QUICKSAND;
        //RegistryObject<Block> WHITE_QUICKSAND;
        //RegistryObject<Block> ARID_QUICKSAND;
        //RegistryObject<Block> RED_ARID_QUICKSAND;
        //RegistryObject<Block> SOUL_QUICKSAND;

        //RegistryObject<Block> BLEACHED_LOG;
    }

    @Override
    protected void registerTileEntities() {
        //RegistryObject<TileEntityType<?>> OASSELISK;
    }

    @Override
    protected void registerEntities() {
        //RegistryObject<EntityType<?>> CAMEL;
        //RegistryObject<EntityType<?>> SANDSTORM;
        //RegistryObject<EntityType<?>> OASSIGER;
    }

    @Override
    protected void registerBiomes() {
        //RegistryObject<Biome> OASIS;

        //RegistryObject<Biome> BADLANDS_ARCHES;
        //RegistryObject<Biome> PAINTED_BADLANDS;

        //RegistryObject<Biome> RED_DESERT;
        //RegistryObject<Biome> ROCKY_DESERT;
        //RegistryObject<Biome> ROCKY_RED_DESERT;
        //RegistryObject<Biome> RED_ROCK_MOUNTAINS;

        //RegistryObject<Biome> BADLANDS_FOREST;
        //RegistryObject<Biome> BADLANDS_FOREST_HILLS;
    }

    @Override
    protected void registerSurfaceBuilders() {
        //RegistryObject<SurfaceBuilder<?>> BADLANDS_FOREST_SURFACE_BUILDER;
    }

    @Override
    protected void registerFeatures() {
        //RegistryObject<Feature<?>> ARCH;
        //RegistryObject<Feature<?>> BLEACHED_TREE;
        //RegistryObject<Feature<?>> QUICKSAND;
        //RegistryObject<Feature<?>> RED_ROCK;
    }

    @Override
    protected void registerStructures() {
        //RegistryObject<Structure<?>> DESERT_RUINS;
    }

    @Override
    protected void registerEffects() {
        //RegistryObject<Effect> CONFUSED;
    }

    @Override
    protected void registerSounds() {
    }

    @Override
    protected void registerStats() {
    }

    protected void onBiomeLoading(BiomeLoadingEvent event) {
        Biome.Category category = event.getCategory();
        BiomeGenerationSettingsBuilder gen = event.getGeneration();

        if (category == Biome.Category.MESA) {
            BiomeFeatures.addCaveOreCluster(gen, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, OmniBlocks.RED_ROCK.get().getDefaultState(), CaveOreFeatureConfig.CaveFace.ALL, CoreModule.Configuration.CLIENT.RED_ROCK_GEN_SIZE.get(), 0, 0, 100, 10, 80, 1);
        }
    }
}