package pugz.omni.core.module;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import pugz.omni.common.block.forestry.CarvedLogBlock;
import pugz.omni.core.registry.OmniBlocks;
import pugz.omni.core.registry.OmniFeatures;
import pugz.omni.core.registry.OmniSoundEvents;
import pugz.omni.core.util.RegistryUtil;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraftforge.event.world.BiomeLoadingEvent;

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
        //RegistryObject<Block> CARVED_OAK_PLANKS;

        OmniBlocks.CARVED_DARK_OAK_WOOD = RegistryUtil.createBlock("carved_dark_oak_wood", () -> new CarvedLogBlock(MaterialColor.ADOBE), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.CARVED_ACACIA_WOOD = RegistryUtil.createBlock("carved_acacia_wood", () -> new CarvedLogBlock(MaterialColor.BROWN), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.CARVED_JUNGLE_WOOD = RegistryUtil.createBlock("carved_jungle_wood", () -> new CarvedLogBlock(MaterialColor.DIRT), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.CARVED_BIRCH_WOOD = RegistryUtil.createBlock("carved_birch_wood", () -> new CarvedLogBlock(MaterialColor.SAND), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.CARVED_SPRUCE_WOOD = RegistryUtil.createBlock("carved_spruce_wood", () -> new CarvedLogBlock(MaterialColor.OBSIDIAN), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.CARVED_OAK_WOOD = RegistryUtil.createBlock("carved_oak_wood", () -> new CarvedLogBlock(MaterialColor.WOOD), ItemGroup.BUILDING_BLOCKS);

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

        OmniFeatures.TALL_OAK_TREE = RegistryUtil.createConfiguredFeature("oak_tall", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(5, 2, 6), new TwoLayerFeature(1, 0, 1))).setIgnoreVines().setDecorators(ImmutableList.of(Features.Placements.BEES_0002_PLACEMENT)).build()));
        //RegistryObject<Feature<?>> TALL_SPRUCE_TREE;
        //RegistryObject<Feature<?>> TALL_JUNGLE_TREE;
        //RegistryObject<Feature<?>> TALL_DARK_OAK_TREE;
    }

    @Override
    protected void registerSounds() {
        OmniSoundEvents.AMBIENT_FOREST = RegistryUtil.createSoundEvent("ambient.forest");
        OmniSoundEvents.AMBIENT_JUNGLE = RegistryUtil.createSoundEvent("ambient.jungle");
        OmniSoundEvents.AMBIENT_PLAINS = RegistryUtil.createSoundEvent("ambient.plains");
        OmniSoundEvents.AMBIENT_SWAMP = RegistryUtil.createSoundEvent("ambient.swamp");
    }

    protected void onBiomeLoading(BiomeLoadingEvent event) {
        Biome.Category category = event.getCategory();
        BiomeAmbience effects = event.getEffects();
        BiomeAmbience.Builder ambience = (new BiomeAmbience.Builder())
                .withGrassColorModifier(effects.getGrassColorModifier())
                .setWaterColor(effects.getWaterColor())
                .setWaterFogColor(effects.getWaterFogColor())
                .setFogColor(effects.getFogColor())
                .withSkyColor(effects.getSkyColor());
        if (effects.getFoliageColor().isPresent())
            ambience = ambience.withFoliageColor(effects.getFoliageColor().get());
        if (effects.getGrassColor().isPresent()) ambience = ambience.withGrassColor(effects.getGrassColor().get());
        if (effects.getMoodSound().isPresent()) ambience = ambience.setMoodSound(effects.getMoodSound().get());
        if (effects.getAdditionsSound().isPresent())
            ambience = ambience.setAdditionsSound(effects.getAdditionsSound().get());
        if (effects.getParticle().isPresent()) ambience = ambience.setParticle(effects.getParticle().get());

        if (category == Biome.Category.FOREST) {
            event.setEffects(ambience.setAmbientSound(OmniSoundEvents.AMBIENT_FOREST.get()).build());
        } else if (category == Biome.Category.JUNGLE) {
            event.setEffects(ambience.setAmbientSound(OmniSoundEvents.AMBIENT_JUNGLE.get()).build());
        } else if (category == Biome.Category.PLAINS) {
            event.setEffects(ambience.setAmbientSound(OmniSoundEvents.AMBIENT_PLAINS.get()).build());
        } else if (category == Biome.Category.SWAMP) {
            event.setEffects(ambience.setAmbientSound(OmniSoundEvents.AMBIENT_SWAMP.get()).build());
        }
    }
}