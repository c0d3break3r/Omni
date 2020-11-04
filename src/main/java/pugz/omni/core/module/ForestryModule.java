package pugz.omni.core.module;

import net.minecraft.block.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import pugz.omni.core.event.FireTickEvent;
import pugz.omni.core.registry.OmniSoundEvents;
import pugz.omni.core.util.RegistryUtil;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraftforge.common.MinecraftForge;
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
    protected void registerBlocks() {
        //RegistryObject<Block> BEAVER_DAM;

        //RegistryObject<Block> CARVED_OAK_PLANKS;
        //RegistryObject<Block> CARVED_OAK_LOG;

        //CHARRED_LOG
    }

    @Override
    protected void registerItems() {
        //RegistryObject<Item> RAW_BIRD;
        //RegistryObject<Item> COOKED_BIRD;

        //RegistryObject<Item> HEARTBEET;

        //RegistryObject<Item> HONEY_CHESTPLATE;
        //RegistryObject<Item> ROYAL_JELLY;

        //RegistryObject<Item> ENCHANTED_GOLDEN_CARROT;
    }

    @Override
    protected void registerEntities() {
        //RegistryObject<EntityType<?>> SONGBIRD;
        //RegistryObject<EntityType<?>> CROPLINGS;
        //RegistryObject<EntityType<?>> PUG;
        //RegistryObject<EntityType<?>> BEAVER;
    }

    @Override
    protected void registerBiomes() {
        //RegistryObject<Biome> ERODED_FOREST;
        //RegistryObject<Biome> ERODED_TAIGA_FOREST;
        //RegistryObject<Biome> ERODED_JUNGLE;

        //RegistryObject<Biome> TALL_FOREST;
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
        OmniSoundEvents.AMBIENT_FOREST = RegistryUtil.createSoundEvent("ambient.forest");
        OmniSoundEvents.AMBIENT_JUNGLE = RegistryUtil.createSoundEvent("ambient.jungle");
        OmniSoundEvents.AMBIENT_PLAINS = RegistryUtil.createSoundEvent("ambient.plains");
        OmniSoundEvents.AMBIENT_SWAMP = RegistryUtil.createSoundEvent("ambient.swamp");
    }

    public void onBiomeLoading(BiomeLoadingEvent event) {
        BiomeAmbience effects = event.getEffects();

        if (event.getCategory() == Biome.Category.FOREST) {
            event.setEffects((new BiomeAmbience.Builder()).setWaterColor(effects.getWaterColor()).setWaterFogColor(effects.getWaterFogColor()).setFogColor(effects.getFogColor()).withSkyColor(effects.getSkyColor()).setAmbientSound(OmniSoundEvents.AMBIENT_FOREST.get()).setMoodSound(effects.getMoodSound().get()).build());
        } else if (event.getCategory() == Biome.Category.JUNGLE) {
            event.setEffects((new BiomeAmbience.Builder()).setWaterColor(effects.getWaterColor()).setWaterFogColor(effects.getWaterFogColor()).setFogColor(effects.getFogColor()).withSkyColor(effects.getSkyColor()).setAmbientSound(OmniSoundEvents.AMBIENT_JUNGLE.get()).setMoodSound(effects.getMoodSound().get()).build());
        } else if (event.getCategory() == Biome.Category.PLAINS) {
            event.setEffects((new BiomeAmbience.Builder()).setWaterColor(effects.getWaterColor()).setWaterFogColor(effects.getWaterFogColor()).setFogColor(effects.getFogColor()).withSkyColor(effects.getSkyColor()).setAmbientSound(OmniSoundEvents.AMBIENT_PLAINS.get()).setMoodSound(effects.getMoodSound().get()).build());
        } else if (event.getCategory() == Biome.Category.SWAMP) {
            event.setEffects((new BiomeAmbience.Builder()).setWaterColor(effects.getWaterColor()).setWaterFogColor(effects.getWaterFogColor()).setFogColor(effects.getFogColor()).withSkyColor(effects.getSkyColor()).setAmbientSound(OmniSoundEvents.AMBIENT_SWAMP.get()).setMoodSound(effects.getMoodSound().get()).build());
        }
    }
}