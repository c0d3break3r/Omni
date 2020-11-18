package pugz.omni.core.module;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ForgeConfigSpec;
import pugz.omni.core.registry.OmniBlocks;
import pugz.omni.core.registry.OmniSoundEvents;
import pugz.omni.core.util.RegistryUtil;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class ForestryModule extends AbstractModule {
    public static final ForestryModule instance = new ForestryModule();
    public static boolean ambientSounds = true;
    public static boolean carvedWood = true;

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
    protected void onClientInitialize() {
    }

    @Override
    protected void onPostInitialize() {
    }

    @Override
    protected void registerBlocks() {
        //RegistryObject<Block> CARVED_OAK_PLANKS;

        if (carvedWood) {
            OmniBlocks.CARVED_OAK_WOOD = RegistryUtil.createBlock("carved_oak_wood", () -> new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.CARVED_SPRUCE_WOOD = RegistryUtil.createBlock("carved_spruce_wood", () -> new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.OBSIDIAN).hardnessAndResistance(2.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.CARVED_BIRCH_WOOD = RegistryUtil.createBlock("carved_birch_wood", () -> new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(2.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.CARVED_JUNGLE_WOOD = RegistryUtil.createBlock("carved_jungle_wood", () -> new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.DIRT).hardnessAndResistance(2.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.CARVED_DARK_OAK_WOOD = RegistryUtil.createBlock("carved_dark_oak_wood", () -> new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.ADOBE).hardnessAndResistance(2.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.CARVED_ACACIA_WOOD = RegistryUtil.createBlock("carved_acacia_wood", () -> new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
        }

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

        //RegistryObject<Feature<?>> TALL_OAK_TREE;
        //RegistryObject<Feature<?>> TALL_SPRUCE_TREE;
        //RegistryObject<Feature<?>> TALL_JUNGLE_TREE;
        //RegistryObject<Feature<?>> TALL_DARK_OAK_TREE;
    }

    @Override
    protected void registerSounds() {
        if (ambientSounds) {
            OmniSoundEvents.AMBIENT_FOREST = RegistryUtil.createSoundEvent("ambient.forest");
            OmniSoundEvents.AMBIENT_JUNGLE = RegistryUtil.createSoundEvent("ambient.jungle");
            OmniSoundEvents.AMBIENT_PLAINS = RegistryUtil.createSoundEvent("ambient.plains");
            OmniSoundEvents.AMBIENT_SWAMP = RegistryUtil.createSoundEvent("ambient.swamp");
        }
    }

    public void onBiomeLoading(BiomeLoadingEvent event) {
        if (ambientSounds) {
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

            if (event.getCategory() == Biome.Category.FOREST) {
                event.setEffects(ambience.setAmbientSound(OmniSoundEvents.AMBIENT_FOREST.get()).build());
            } else if (event.getCategory() == Biome.Category.JUNGLE) {
                event.setEffects(ambience.setAmbientSound(OmniSoundEvents.AMBIENT_JUNGLE.get()).build());
            } else if (event.getCategory() == Biome.Category.PLAINS) {
                event.setEffects(ambience.setAmbientSound(OmniSoundEvents.AMBIENT_PLAINS.get()).build());
            } else if (event.getCategory() == Biome.Category.SWAMP) {
                event.setEffects(ambience.setAmbientSound(OmniSoundEvents.AMBIENT_SWAMP.get()).build());
            }
        }
    }
}