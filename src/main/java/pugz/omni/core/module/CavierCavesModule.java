package pugz.omni.core.module;

import net.minecraft.block.SoundType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.Item;
import pugz.omni.common.block.HorizontalFacingBlock;
import pugz.omni.common.block.cavier_caves.*;
import pugz.omni.common.world.feature.cavier_caves.GeodeFeature;
import pugz.omni.common.world.feature.cavier_caves.GeodeFeatureConfig;
import pugz.omni.common.world.feature.cavier_caves.SpeleothemFeature;
import pugz.omni.common.world.feature.cavier_caves.SpeleothemFeatureConfig;
import pugz.omni.core.registry.OmniBlocks;
import pugz.omni.core.registry.OmniFeatures;
import pugz.omni.core.registry.OmniItems;
import pugz.omni.core.registry.OmniSoundEvents;
import pugz.omni.core.util.BiomeFeatures;
import pugz.omni.core.util.RegistryUtil;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class CavierCavesModule extends AbstractModule {
    public static final CavierCavesModule instance = new CavierCavesModule();

    public CavierCavesModule() {
        super("Cavier Caves");
    }

    @Override
    protected void sendInitMessage() {
        System.out.println("Explored the Depths of the Cavier Caves!");
    }

    @Override
    protected void onInitialize() {
        MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoading);
    }

    @Override
    protected void onClientInitialize() {
        if (CoreModule.Configuration.CLIENT.MALACHITE.get()) {
            RenderTypeLookup.setRenderLayer(OmniBlocks.MALACHITE_CLUSTER.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(OmniBlocks.LARGE_MALACHITE_BUD.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(OmniBlocks.MEDIUM_MALACHITE_BUD.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(OmniBlocks.SMALL_MALACHITE_BUD.get(), RenderType.getCutout());
        }
    }

    @Override
    protected void onPostInitialize() {
    }

    @Override
    protected void registerBlocks() {
        //RegistryObject<Block> SULFUR_BLOCK;

        //RegistryObject<Block> PETRIFIED_PLANKS;

        //RegistryObject<Block> CAVE_PAINTING;

        //RegistryObject<Block> CAVE_MUSHROOM;
        //RegistryObject<Block> RED_SHELF_MUSHROOM;
        //RegistryObject<Block> BROWN_SHELF_MUSHROOM;
        //RegistryObject<Block> CAVE_SHELF_MUSHROOM;
        //RegistryObject<Block> SHELF_GLOWSHROOM;

        //RegistryObject<Block> WEB_BLOCK;
        //RegistryObject<Block> SPIDER_SAC;

        //RegistryObject<Block> WEAK_STONE;

        //RegistryObject<Block> SLIME;

        //RegistryObject<Block> THIN_ICE;
        //RegistryObject<Block> PERMAFROST;

        //RegistryObject<Block> STONE_SIGN;
        //RegistryObject<Block> BLACKSTONE_SIGN;

        if (CoreModule.Configuration.CLIENT.SPELEOTHEMS.get()) {
            OmniBlocks.STONE_SPELEOTHEM = RegistryUtil.createBlock("stone_speleothem", () -> new SpeleothemBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).setRequiresTool().hardnessAndResistance(1.25F, 4.5F)), ItemGroup.DECORATIONS);
            OmniBlocks.ICE_SPELEOTHEM = RegistryUtil.createBlock("ice_speleothem", () -> new SpeleothemBlock(AbstractBlock.Properties.create(Material.PACKED_ICE).slipperiness(0.98F).hardnessAndResistance(0.4F).sound(SoundType.GLASS)), ItemGroup.DECORATIONS);
            OmniBlocks.NETHERRACK_SPELEOTHEM = RegistryUtil.createBlock("netherrack_speleothem", () -> new SpeleothemBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.NETHERRACK).setRequiresTool().hardnessAndResistance(0.3F)), ItemGroup.DECORATIONS);
        }

        if (CoreModule.Configuration.CLIENT.MALACHITE.get()) {
            OmniBlocks.MALACHITE_BLOCK = RegistryUtil.createBlock("malachite_block", MalachiteBlock::new, ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.BUDDING_MALACHITE = RegistryUtil.createBlock("budding_malachite", BuddingMalachiteBlock::new, ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.MALACHITE_CLUSTER = RegistryUtil.createBlock("malachite_cluster", () -> new MalachiteBudBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(4.5F, 9.5F), 3), ItemGroup.DECORATIONS);
            OmniBlocks.LARGE_MALACHITE_BUD = RegistryUtil.createBlock("large_malachite_bud", () -> new MalachiteBudBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(4.0F, 9.0F), 2), ItemGroup.DECORATIONS);
            OmniBlocks.MEDIUM_MALACHITE_BUD = RegistryUtil.createBlock("medium_malachite_bud", () -> new MalachiteBudBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.5F, 8.5F), 1), ItemGroup.DECORATIONS);
            OmniBlocks.SMALL_MALACHITE_BUD = RegistryUtil.createBlock("small_malachite_bud", () -> new MalachiteBudBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 8.0F), 0), ItemGroup.DECORATIONS);
            OmniBlocks.CARVED_MALACHITE = RegistryUtil.createBlock("carved_malachite", () -> new HorizontalFacingBlock(AbstractBlock.Properties.from(OmniBlocks.MALACHITE_BLOCK.get())), ItemGroup.BUILDING_BLOCKS);
        }
    }

    @Override
    protected void registerItems() {
        //RegistryObject<Item> SULFUR_DUST;

        //RegistryObject<Item> CRYSTAL_MELON;

        if (CoreModule.Configuration.CLIENT.MALACHITE.get()) OmniItems.MALACHITE_SHARD = RegistryUtil.createItem("malachite_shard", () -> new Item(new Item.Properties().group(ItemGroup.MATERIALS)));
    }

    @Override
    protected void registerEntities() {
        //RegistryObject<EntityType<?>> SPIDERLING;
    }

    @Override
    protected void registerFeatures() {
        //RegistryObject<Feature<?>> CEILING_ORE;
        //RegistryObject<Feature<?>> EXPOSED_ORE;
        //RegistryObject<Feature<?>> FLOORED_ORE;
        //RegistryObject<Feature<?>> CAVE_FLOWER;

        if (CoreModule.Configuration.CLIENT.SPELEOTHEMS.get()) OmniFeatures.SPELEOTHEM = RegistryUtil.createFeature("speleothem", () -> new SpeleothemFeature(SpeleothemFeatureConfig.codec));
        //RegistryObject<Feature<?>> SLIME;
        //RegistryObject<Feature<?>> CAVE_CARVING;
        //RegistryObject<Feature<?>> THIN_ICE;
        //RegistryObject<Feature<?>> SPIDER_NEST;
        //RegistryObject<Feature<?>> WALL_MUSHROOM;
        //RegistryObject<Feature<?>> SULFUR;

        //RegistryObject<Feature<?>> PETRIFIED_WOOD_REPLACEMENT;

        if (CoreModule.Configuration.CLIENT.MALACHITE.get()) OmniFeatures.GEODE = RegistryUtil.createFeature("geode", () -> new GeodeFeature(GeodeFeatureConfig.b));
    }

    @Override
    protected void registerEffects() {
        //RegistryObject<Effect> DEAFENED;
    }

    @Override
    protected void registerParticles() {
        //RegistryObject<ParticleType<?>> SULFUR;
    }

    @Override
    protected void registerSounds() {
        if (CoreModule.Configuration.CLIENT.MALACHITE.get()) {
            OmniSoundEvents.CRYSTAL_PLACE = RegistryUtil.createSoundEvent("block.crystal.place");
            OmniSoundEvents.CRYSTAL_BREAK = RegistryUtil.createSoundEvent("block.crystal.break");
            OmniSoundEvents.CRYSTAL_STEP = RegistryUtil.createSoundEvent("block.crystal.step");
            OmniSoundEvents.CRYSTAL_SHIMMER = RegistryUtil.createSoundEvent("block.crystal.shimmer");
            OmniSoundEvents.CRYSTAL_BUD_PLACE = RegistryUtil.createSoundEvent("block.crystal_bud.place");
            OmniSoundEvents.CRYSTAL_BUD_BREAK = RegistryUtil.createSoundEvent("block.crystal_bud.break");
        }
    }

    @Override
    protected void registerStats() {
    }

    public void onBiomeLoading(BiomeLoadingEvent event) {
        Biome.Category category = event.getCategory();
        BiomeGenerationSettingsBuilder gen = event.getGeneration();

        if (category != Biome.Category.NETHER && category != Biome.Category.THEEND) {
            if (CoreModule.Configuration.CLIENT.SPELEOTHEMS.get()) BiomeFeatures.addSpeleothems(gen, SpeleothemFeatureConfig.Variant.STONE, CoreModule.Configuration.CLIENT.SPELEOTHEMS_SPAWN_PROBABILITY.get().floatValue());
            if (CoreModule.Configuration.CLIENT.MALACHITE.get()) BiomeFeatures.addMalachiteGeodes(gen);
        }
        if (category == Biome.Category.ICY) {
            if (CoreModule.Configuration.CLIENT.SPELEOTHEMS.get()) BiomeFeatures.addSpeleothems(gen, SpeleothemFeatureConfig.Variant.ICE, CoreModule.Configuration.CLIENT.SPELEOTHEMS_SPAWN_PROBABILITY.get().floatValue() * 1.5F);
        }
        if (category == Biome.Category.NETHER) {
            if (CoreModule.Configuration.CLIENT.SPELEOTHEMS.get()) BiomeFeatures.addSpeleothems(gen, SpeleothemFeatureConfig.Variant.NETHERRACK, CoreModule.Configuration.CLIENT.SPELEOTHEMS_SPAWN_PROBABILITY.get().floatValue() * 2.0F);
        }
    }
}