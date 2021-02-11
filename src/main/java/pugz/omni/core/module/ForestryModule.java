package pugz.omni.core.module;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import pugz.omni.common.block.forestry.CarvedLogBlock;
import pugz.omni.common.block.OmniLeavesBlock;
import pugz.omni.common.world.biome.TallForestBiome;
import pugz.omni.core.registry.OmniBiomes;
import pugz.omni.core.registry.OmniBlocks;
import pugz.omni.core.registry.OmniFeatures;
import pugz.omni.core.util.BiomeFeatures;
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
        OmniBlocks.CARVED_DARK_OAK_WOOD = RegistryUtil.createBlock("carved_dark_oak_wood", () -> new CarvedLogBlock(MaterialColor.ADOBE), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.CARVED_ACACIA_WOOD = RegistryUtil.createBlock("carved_acacia_wood", () -> new CarvedLogBlock(MaterialColor.BROWN), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.CARVED_JUNGLE_WOOD = RegistryUtil.createBlock("carved_jungle_wood", () -> new CarvedLogBlock(MaterialColor.DIRT), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.CARVED_BIRCH_WOOD = RegistryUtil.createBlock("carved_birch_wood", () -> new CarvedLogBlock(MaterialColor.SAND), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.CARVED_SPRUCE_WOOD = RegistryUtil.createBlock("carved_spruce_wood", () -> new CarvedLogBlock(MaterialColor.OBSIDIAN), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.CARVED_OAK_WOOD = RegistryUtil.createBlock("carved_oak_wood", () -> new CarvedLogBlock(MaterialColor.WOOD), ItemGroup.BUILDING_BLOCKS);

        OmniBlocks.GOLDEN_OAK_LEAVES = RegistryUtil.createBlock("golden_oak_leaves", OmniLeavesBlock::new, ItemGroup.DECORATIONS);
    }

    @Override
    protected void registerBiomes() {
        OmniBiomes.TALL_FOREST = RegistryUtil.createBiome(new TallForestBiome());
    }

    @Override
    protected void registerConfiguredFeatures() {
        OmniFeatures.Configured.TALL_OAK_TREE = RegistryUtil.createConfiguredFeature("oak_tall", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(4, 2, 4), new TwoLayerFeature(1, 0, 1))).setIgnoreVines().setDecorators(ImmutableList.of(Features.Placements.BEES_0002_PLACEMENT)).build()).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
        OmniFeatures.Configured.TALL_OAK_TREES = RegistryUtil.createConfiguredFeature("dense_savanna_trees", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(OmniFeatures.Configured.TALL_OAK_TREE.withChance(0.8F), Features.BIRCH_TALL.withChance(0.25F)), Features.FANCY_OAK_BEES_005)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(12, 0.1F, 1))));
        OmniFeatures.Configured.GOLDEN_APPLE_TREE = RegistryUtil.createConfiguredFeature("golden_tree", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()), new SimpleBlockStateProvider(OmniBlocks.GOLDEN_OAK_LEAVES.get().getDefaultState()), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1))).setIgnoreVines().build())).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, CoreModule.Configuration.COMMON.GOLDEN_OAK_SPAWN_CHANCE.get().floatValue(), 1)));
    }

    protected void onBiomeLoading(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder gen = event.getGeneration();

        if (event.getCategory() == Biome.Category.FOREST) {
            BiomeFeatures.addGoldenOakTrees(gen);

            if (event.getName().getPath().equals(OmniBiomes.TALL_FOREST.getRegistryName().getPath())) {
                BiomeFeatures.addTallOakTrees(gen);
            }
        }
    }
}