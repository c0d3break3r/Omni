package pugz.omni.core.module;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import pugz.omni.common.block.forestry.CarvedLogBlock;
import pugz.omni.common.block.forestry.GoldenOakLeavesBlock;
import pugz.omni.core.registry.OmniBlocks;
import pugz.omni.core.registry.OmniFeatures;
import pugz.omni.core.util.RegistryUtil;

public class ForestryModule extends AbstractModule {
    public static final ForestryModule instance = new ForestryModule();

    public ForestryModule() {
        super("Forestry");
    }

    @Override
    protected void sendInitMessage() {
        System.out.println("Relaxing within all the Forestry.");
    }

    @Override
    protected void onInitialize() {
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
        //RegistryObject<Block> CARVED_OAK_PLANKS;

        OmniBlocks.CARVED_DARK_OAK_WOOD = RegistryUtil.createBlock("carved_dark_oak_wood", () -> new CarvedLogBlock(MaterialColor.ADOBE), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.CARVED_ACACIA_WOOD = RegistryUtil.createBlock("carved_acacia_wood", () -> new CarvedLogBlock(MaterialColor.BROWN), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.CARVED_JUNGLE_WOOD = RegistryUtil.createBlock("carved_jungle_wood", () -> new CarvedLogBlock(MaterialColor.DIRT), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.CARVED_BIRCH_WOOD = RegistryUtil.createBlock("carved_birch_wood", () -> new CarvedLogBlock(MaterialColor.SAND), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.CARVED_SPRUCE_WOOD = RegistryUtil.createBlock("carved_spruce_wood", () -> new CarvedLogBlock(MaterialColor.OBSIDIAN), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.CARVED_OAK_WOOD = RegistryUtil.createBlock("carved_oak_wood", () -> new CarvedLogBlock(MaterialColor.WOOD), ItemGroup.BUILDING_BLOCKS);

        OmniBlocks.GOLDEN_OAK_LEAVES = RegistryUtil.createBlock("golden_oak_leaves", GoldenOakLeavesBlock::new, ItemGroup.DECORATIONS);

        //CHARRED_LOG

        //REDWOOD
    }

    @Override
    protected void registerItems() {
        //RegistryObject<Item> RAW_BIRD;
        //RegistryObject<Item> COOKED_BIRD;

        //RegistryObject<Item> HONEY_CHESTPLATE;
        //RegistryObject<Item> ROYAL_JELLY;
    }

    @Override
    protected void registerEntities() {
        //RegistryObject<EntityType<?>> SONGBIRD;
        //RegistryObject<EntityType<?>> PUG;
    }

    @Override
    protected void registerBiomes() {
        //RegistryObject<Biome> ERODED_FOREST;
        //RegistryObject<Biome> ERODED_TAIGA_FOREST;
        //RegistryObject<Biome> ERODED_JUNGLE;

        //OmniBiomes.TALL_FOREST = RegistryUtil.createBiome("tall_forest", OmniBiomes.createTallForestBiome(), BiomeManager.BiomeType.COOL, 6, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.RARE);
        //RegistryObject<Biome> TALL_TAIGA_FOREST;
        //RegistryObject<Biome> TALL_DARK_FOREST;
        //RegistryObject<Biome> TALL_JUNGLE;
    }

    @Override
    protected void registerFeatures() {
        //RegistryObject<Feature<?>> FALLEN_TREES;
    }

    @Override
    protected void registerConfiguredFeatures() {
        OmniFeatures.Configured.TALL_OAK_TREE = RegistryUtil.createConfiguredFeature("oak_tall", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(5, 2, 6), new TwoLayerFeature(1, 0, 1))).setIgnoreVines().setDecorators(ImmutableList.of(Features.Placements.BEES_0002_PLACEMENT)).build()));
        OmniFeatures.Configured.GOLDEN_APPLE_TREE = RegistryUtil.createConfiguredFeature("oak_tall", Feature.TREE.withConfiguration((new net.minecraft.world.gen.feature.BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()), new SimpleBlockStateProvider(OmniBlocks.GOLDEN_OAK_LEAVES.get().getDefaultState()), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1))).setIgnoreVines().build())).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, CoreModule.Configuration.COMMON.GOLDEN_OAK_SPAWN_CHANCE.get().floatValue(), 1)));
    }

    @Override
    protected void registerSounds() {
    }
}