package pugz.omni.core.module;

import net.minecraft.loot.*;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;
import pugz.omni.common.item.forestry.EnchantedGoldenCarrotItem;
import pugz.omni.core.Omni;
import pugz.omni.core.registry.OmniItems;
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
        MinecraftForge.EVENT_BUS.addListener(this::onLootTableLoad);
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
        //RegistryObject<Block> BEAVER_DAM;

        //RegistryObject<Block> CARVED_OAK_PLANKS;
        //RegistryObject<Block> CARVED_OAK_LOG;

        //CHARRED_LOG
    }

    @Override
    protected void registerItems() {
        //RegistryObject<Item> RAW_BIRD;
        //RegistryObject<Item> COOKED_BIRD;

        //RegistryObject<Item> HONEY_CHESTPLATE;
        //RegistryObject<Item> ROYAL_JELLY;

        OmniItems.ENCHANTED_GOLDEN_CARROT = RegistryUtil.createItem("enchanted_golden_carrot", EnchantedGoldenCarrotItem::new);
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
        BiomeAmbience.Builder ambience = (new BiomeAmbience.Builder())
                .withGrassColorModifier(effects.getGrassColorModifier())
                .setWaterColor(effects.getWaterColor())
                .setWaterFogColor(effects.getWaterFogColor())
                .setFogColor(effects.getFogColor())
                .withSkyColor(effects.getSkyColor());
        if (effects.getFoliageColor().isPresent()) ambience = ambience.withFoliageColor(effects.getFoliageColor().get());
        if (effects.getGrassColor().isPresent()) ambience = ambience.withGrassColor(effects.getGrassColor().get());
        if (effects.getMoodSound().isPresent()) ambience = ambience.setMoodSound(effects.getMoodSound().get());
        if (effects.getAdditionsSound().isPresent()) ambience = ambience.setAdditionsSound(effects.getAdditionsSound().get());
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

    public void onLootTableLoad(LootTableLoadEvent event) {
        LootTable table = event.getTable();
        ResourceLocation name = event.getName();

        if (name.toString().equals(LootTables.CHESTS_ABANDONED_MINESHAFT.toString()) || name.toString().equals(LootTables.CHESTS_SIMPLE_DUNGEON.toString()) || name.toString().equals(LootTables.BASTION_TREASURE.toString()) || name.toString().equals(LootTables.CHESTS_DESERT_PYRAMID.toString()) || name.toString().equals(LootTables.RUINED_PORTAL.toString()) || name.toString().equals(LootTables.CHESTS_WOODLAND_MANSION.toString())) {
            LootPool pool = new LootPool.Builder().addEntry(TableLootEntry.builder(new ResourceLocation(Omni.MOD_ID, "injects/enchanted_golden_carrot"))).acceptCondition(RandomChance.builder(0.03F)).build();
            table.addPool(pool);
        }
    }
}