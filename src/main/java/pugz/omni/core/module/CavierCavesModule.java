package pugz.omni.core.module;

import net.minecraft.block.*;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.*;
import net.minecraft.stats.IStatFormatter;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.commons.lang3.StringUtils;
import pugz.omni.client.render.SizedCaveSpiderRenderer;
import pugz.omni.client.render.SpeleothemRenderer;
import pugz.omni.common.block.*;
import pugz.omni.common.block.cavier_caves.*;
import pugz.omni.common.entity.cavier_caves.SizedCaveSpiderEntity;
import pugz.omni.common.item.OmniBoatItem;
import pugz.omni.common.item.OmniSignItem;
import pugz.omni.common.world.feature.cavier_caves.*;
import pugz.omni.common.world.feature.cavier_caves.caves.*;
import pugz.omni.core.Omni;
import pugz.omni.core.registry.*;
import pugz.omni.core.util.BiomeFeatures;
import pugz.omni.core.util.RegistryUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
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
        MinecraftForge.EVENT_BUS.addListener(this::onLivingJump);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void onClientInitialize() {
        RenderingRegistry.registerEntityRenderingHandler(OmniEntities.CAVE_SPIDER.get(), SizedCaveSpiderRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(OmniEntities.SPELEOTHEM.get(), SpeleothemRenderer::new);

        RenderTypeLookup.setRenderLayer(OmniBlocks.CAVE_MUSHROOM_DOOR.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(OmniBlocks.CAVE_MUSHROOM_TRAPDOOR.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(OmniBlocks.CAVE_MUSHROOM_LADDER.get(), RenderType.getCutout());

        RegistryUtil.sprites.add(new RenderMaterial(Atlases.SIGN_ATLAS, new ResourceLocation(Omni.MOD_ID, "entity/sign/cave_mushroom")));
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void onPostInitialize(final FMLCommonSetupEvent event) {
        GlobalEntityTypeAttributes.put(OmniEntities.CAVE_SPIDER.get(), SizedCaveSpiderEntity.registerAttributes().create());

        event.enqueueWork(() -> {
            TileEntityType.SIGN.validBlocks.add(OmniBlocks.CAVE_MUSHROOM_SIGN.get());
            TileEntityType.SIGN.validBlocks.add(OmniBlocks.CAVE_MUSHROOM_WALL_SIGN.get());
        });
    }

    @Override
    protected void registerBlocks() {
        OmniBlocks.YELLOW_CAVE_MUSHROOM = RegistryUtil.createBlock("yellow_cave_mushroom", YellowCaveMushroomBlock::new, ItemGroup.DECORATIONS);
        OmniBlocks.GREEN_CAVE_MUSHROOM = RegistryUtil.createBlock("green_cave_mushroom", () -> new CaveMushroomBlock(MaterialColor.GREEN), ItemGroup.DECORATIONS);
        OmniBlocks.BLUE_CAVE_MUSHROOM = RegistryUtil.createBlock("blue_cave_mushroom", BlueCaveMushroomBlock::new, ItemGroup.DECORATIONS);
        OmniBlocks.PURPLE_CAVE_MUSHROOM = RegistryUtil.createBlock("purple_cave_mushroom", PurpleCaveMushroomBlock::new, ItemGroup.DECORATIONS);
        OmniBlocks.YELLOW_CAVE_MUSHROOM_BLOCK = RegistryUtil.createBlock("yellow_cave_mushroom_block", () -> new Block(AbstractBlock.Properties.from(Blocks.RED_MUSHROOM_BLOCK)), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.GREEN_CAVE_MUSHROOM_BLOCK = RegistryUtil.createBlock("green_cave_mushroom_block", () -> new Block(AbstractBlock.Properties.from(Blocks.RED_MUSHROOM_BLOCK)), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.BLUE_CAVE_MUSHROOM_BLOCK = RegistryUtil.createBlock("blue_cave_mushroom_block", () -> new Block(AbstractBlock.Properties.from(Blocks.RED_MUSHROOM_BLOCK)), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.PURPLE_CAVE_MUSHROOM_BLOCK = RegistryUtil.createBlock("purple_cave_mushroom_block", () -> new Block(AbstractBlock.Properties.from(Blocks.RED_MUSHROOM_BLOCK)), ItemGroup.BUILDING_BLOCKS);

        //if (ModList.get().isLoaded("enhanced_mushrooms")) {
            OmniBlocks.STRIPPED_CAVE_MUSHROOM_STEM = RegistryUtil.createBlock("stripped_cave_mushroom_stem", () -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.OAK_LOG)), ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.CAVE_MUSHROOM_STEM = RegistryUtil.createBlock("cave_mushroom_stem", () -> new OmniLogBlock((RotatedPillarBlock) OmniBlocks.STRIPPED_CAVE_MUSHROOM_STEM.get()), ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.STRIPPED_CAVE_MUSHROOM_HYPHAE = RegistryUtil.createBlock("stripped_cave_mushroom_hyphae", () -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.OAK_LOG)), ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.CAVE_MUSHROOM_HYPHAE = RegistryUtil.createBlock("cave_mushroom_hyphae", () -> new OmniLogBlock((RotatedPillarBlock) OmniBlocks.STRIPPED_CAVE_MUSHROOM_HYPHAE.get()), ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.CAVE_MUSHROOM_PLANKS = RegistryUtil.createBlock("cave_mushroom_planks", () -> new Block(AbstractBlock.Properties.from(Blocks.OAK_PLANKS)), ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.CAVE_MUSHROOM_STAIRS = RegistryUtil.createBlock("cave_mushroom_stairs", () -> new StairsBlock(OmniBlocks.CAVE_MUSHROOM_PLANKS.get()::getDefaultState, AbstractBlock.Properties.from(OmniBlocks.CAVE_MUSHROOM_PLANKS.get())), ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.CAVE_MUSHROOM_SLAB = RegistryUtil.createBlock("cave_mushroom_slab", () -> new SlabBlock(AbstractBlock.Properties.from(OmniBlocks.CAVE_MUSHROOM_PLANKS.get())), ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.CAVE_MUSHROOM_FENCE = RegistryUtil.createBlock("cave_mushroom_fence", () -> new FenceBlock(AbstractBlock.Properties.from(OmniBlocks.CAVE_MUSHROOM_PLANKS.get())), ItemGroup.DECORATIONS);
            OmniBlocks.CAVE_MUSHROOM_FENCE_GATE = RegistryUtil.createBlock("cave_mushroom_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.from(OmniBlocks.CAVE_MUSHROOM_PLANKS.get())), ItemGroup.REDSTONE);
            OmniBlocks.CAVE_MUSHROOM_BUTTON = RegistryUtil.createBlock("cave_mushroom_button", () -> new WoodButtonBlock(AbstractBlock.Properties.from(Blocks.OAK_BUTTON)), ItemGroup.REDSTONE);
            OmniBlocks.CAVE_MUSHROOM_PRESSURE_PLATE = RegistryUtil.createBlock("cave_mushroom_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.from(Blocks.OAK_PRESSURE_PLATE)), ItemGroup.REDSTONE);
            OmniBlocks.CAVE_MUSHROOM_DOOR = RegistryUtil.createDoor("cave_mushroom_door", () -> new DoorBlock(AbstractBlock.Properties.from(Blocks.OAK_DOOR)), ItemGroup.REDSTONE);
            OmniBlocks.CAVE_MUSHROOM_TRAPDOOR = RegistryUtil.createBlock("cave_mushroom_trapdoor", () -> new TrapDoorBlock(AbstractBlock.Properties.from(Blocks.OAK_TRAPDOOR)), ItemGroup.REDSTONE);
            OmniBlocks.CAVE_MUSHROOM_LADDER = RegistryUtil.createBlock("cave_mushroom_ladder", () -> new LadderBlock(AbstractBlock.Properties.from(Blocks.LADDER)), ItemGroup.DECORATIONS);
            OmniBlocks.CAVE_MUSHROOM_BOOKSHELF = RegistryUtil.createBlock("cave_mushroom_bookshelf", () -> new Block(AbstractBlock.Properties.from(Blocks.OAK_DOOR)), ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.CAVE_MUSHROOM_CHEST = RegistryUtil.createBlock("cave_mushroom_chest", () -> new OmniChestBlock(AbstractBlock.Properties.from(Blocks.OAK_DOOR)), ItemGroup.DECORATIONS);
            OmniBlocks.CAVE_MUSHROOM_TRAPPED_CHEST = RegistryUtil.createBlock("cave_mushroom_trapped_chest", () -> new OmniTrappedChestBlock(AbstractBlock.Properties.from(Blocks.OAK_DOOR)), ItemGroup.REDSTONE);
            OmniBlocks.CAVE_MUSHROOM_SIGN = RegistryUtil.createBlock("cave_mushroom_sign", () -> new OmniStandingSignBlock(AbstractBlock.Properties.from(Blocks.OAK_SIGN), "palo_verde"));
            OmniBlocks.CAVE_MUSHROOM_WALL_SIGN = RegistryUtil.createBlock("cave_mushroom_wall_sign", () -> new OmniWallSignBlock(AbstractBlock.Properties.from(Blocks.OAK_WALL_SIGN).lootFrom(OmniBlocks.CAVE_MUSHROOM_SIGN.get()), "palo_verde"));
            OmniBlocks.CAVE_MUSHROOM_BEEHIVE = RegistryUtil.createBlock("cave_mushroom_beehive", OmniBeehiveBlock::new, ItemGroup.DECORATIONS);
            //if (ModList.get().isLoaded("quark")) {
                OmniBlocks.VERTICAL_CAVE_MUSHROOM_PLANKS = RegistryUtil.createBlock("vertical_cave_mushroom_planks", () -> new Block(AbstractBlock.Properties.from(OmniBlocks.CAVE_MUSHROOM_PLANKS.get())), ItemGroup.BUILDING_BLOCKS);
                OmniBlocks.CAVE_MUSHROOM_VERTICAL_SLAB = RegistryUtil.createBlock("cave_mushroom_vertical_slab", () -> new VerticalSlabBlock(AbstractBlock.Properties.from(OmniBlocks.CAVE_MUSHROOM_PLANKS.get())), ItemGroup.BUILDING_BLOCKS);
            //}
        //}

        OmniBlocks.STONE_SPELEOTHEM = RegistryUtil.createBlock("stone_speleothem", () -> new SpeleothemBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).setRequiresTool().hardnessAndResistance(1.25F, 0.1F)), ItemGroup.DECORATIONS);
        OmniBlocks.ICE_SPELEOTHEM = RegistryUtil.createBlock("ice_speleothem", () -> new SpeleothemBlock(AbstractBlock.Properties.create(Material.PACKED_ICE).slipperiness(0.98F).hardnessAndResistance(0.4F, 0.1F).sound(SoundType.GLASS)), ItemGroup.DECORATIONS);
        OmniBlocks.NETHERRACK_SPELEOTHEM = RegistryUtil.createBlock("netherrack_speleothem", () -> new SpeleothemBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.NETHERRACK).setRequiresTool().hardnessAndResistance(0.3F, 0.1F)), ItemGroup.DECORATIONS);

        OmniBlocks.MALACHITE_BLOCK = RegistryUtil.createBlock("malachite_block", MalachiteBlock::new, ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.BUDDING_MALACHITE = RegistryUtil.createBlock("budding_malachite", BuddingMalachiteBlock::new, ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.MALACHITE_CLUSTER = RegistryUtil.createBlock("malachite_cluster", () -> new MalachiteBudBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(4.5F, 9.5F), 3), ItemGroup.DECORATIONS);
        OmniBlocks.LARGE_MALACHITE_BUD = RegistryUtil.createBlock("large_malachite_bud", () -> new MalachiteBudBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(4.0F, 9.0F), 2), ItemGroup.DECORATIONS);
        OmniBlocks.MEDIUM_MALACHITE_BUD = RegistryUtil.createBlock("medium_malachite_bud", () -> new MalachiteBudBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.5F, 8.5F), 1), ItemGroup.DECORATIONS);
        OmniBlocks.SMALL_MALACHITE_BUD = RegistryUtil.createBlock("small_malachite_bud", () -> new MalachiteBudBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 8.0F), 0), ItemGroup.DECORATIONS);
        OmniBlocks.CARVED_MALACHITE = RegistryUtil.createBlock("carved_malachite", () -> new HorizontalFacingBlock(AbstractBlock.Properties.from(OmniBlocks.MALACHITE_BLOCK.get())), ItemGroup.BUILDING_BLOCKS);

        OmniBlocks.ARCTISS = RegistryUtil.createBlock("arctiss", ArctissBlock::new, ItemGroup.DECORATIONS);
        OmniBlocks.ARCTISS_BLOCK = RegistryUtil.createBlock("arctiss_block", () -> new Block(AbstractBlock.Properties.from(Blocks.STONE)), ItemGroup.BUILDING_BLOCKS);

        OmniBlocks.LYCHGATE_BLOCK = RegistryUtil.createBlock("lychgate_block", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(5.0F, 15.0F).setRequiresTool().sound(SoundType.ANCIENT_DEBRIS)), ItemGroup.BUILDING_BLOCKS);
    }

    @Override
    protected void registerItems() {
        OmniItems.MALACHITE_SHARD = RegistryUtil.createItem("malachite_shard", () -> new Item(new Item.Properties().group(ItemGroup.MATERIALS)));
        //OmniItems.CAVE_SPIDER_SPAWN_EGG = RegistryUtil.createOverrideItem("cave_spider_spawn_egg", () -> new OmniSpawnEggItem(() -> OmniEntities.CAVE_SPIDER.get(), 803406, 11013646, new Item.Properties().group(ItemGroup.MATERIALS)));
        OmniItems.CAVE_MUSHROOM_BOAT = RegistryUtil.createItem("cave_mushroom_boat", () -> new OmniBoatItem(new Item.Properties().group(ItemGroup.TRANSPORTATION).maxStackSize(1), "cave_mushroom"));
        OmniItems.CAVE_MUSHROOM_SIGN = RegistryUtil.createItem("cave_mushroom_sign", () -> new OmniSignItem(new Item.Properties().group(ItemGroup.DECORATIONS).maxStackSize(16), OmniBlocks.CAVE_MUSHROOM_SIGN.get(), OmniBlocks.CAVE_MUSHROOM_WALL_SIGN.get()));
        OmniItems.LYCHMETAL_INGOT = RegistryUtil.createItem("lychmetal_ingot", () -> new Item(new Item.Properties().group(ItemGroup.MATERIALS)));
    }

    @Override
    protected void registerEntities() {
        OmniEntities.CAVE_SPIDER = RegistryUtil.createEntity("cave_spider", () -> OmniEntities.createLivingEntity(SizedCaveSpiderEntity::new, EntityClassification.MONSTER, "cave_spider",0.7F, 0.5F));
        OmniEntities.SPELEOTHEM = RegistryUtil.createEntity("speleothem", OmniEntities::createSpeleothemEntity);
    }

    @Override
    protected void registerFeatures() {
        OmniFeatures.MUSHROOM_CAVE = RegistryUtil.createFeature("mushroom_cave", () -> new MushroomCaveBiomeFeature(CaveBiomeFeatureConfig.CODEC));
        OmniFeatures.ICY_CAVE = RegistryUtil.createFeature("icy_cave", () -> new IcyCaveBiomeFeature(CaveBiomeFeatureConfig.CODEC));
        OmniFeatures.TERRACOTTA_CAVE = RegistryUtil.createFeature("terracotta_cave", () -> new TerracottaCaveBiomeFeature(CaveBiomeFeatureConfig.CODEC));

        OmniFeatures.SPELEOTHEM = RegistryUtil.createFeature("speleothem", () -> new SpeleothemFeature(SpeleothemFeatureConfig.CODEC));

        OmniFeatures.GEODE = RegistryUtil.createFeature("geode", () -> new GeodeFeature(GeodeFeatureConfig.CODEC));
    }

    @Override
    protected void registerConfiguredFeatures() {
        OmniFeatures.Configured.STONE_SPELEOTHEM = RegistryUtil.createConfiguredFeature("stone_speleothem", OmniFeatures.SPELEOTHEM.get().withConfiguration(new SpeleothemFeatureConfig(SpeleothemFeatureConfig.Variant.STONE)).chance(2));
        OmniFeatures.Configured.ICICLE = RegistryUtil.createConfiguredFeature("icicle", OmniFeatures.SPELEOTHEM.get().withConfiguration(new SpeleothemFeatureConfig(SpeleothemFeatureConfig.Variant.ICE)).chance(2));
        OmniFeatures.Configured.NETHERRACK_SPELEOTHEM = RegistryUtil.createConfiguredFeature("netherrack_speleothem", OmniFeatures.SPELEOTHEM.get().withConfiguration(new SpeleothemFeatureConfig(SpeleothemFeatureConfig.Variant.NETHERRACK)).chance(2));
        OmniFeatures.Configured.MALACHITE_GEODE = RegistryUtil.createConfiguredFeature("malachite_geode", OmniFeatures.GEODE.get().withConfiguration(new GeodeFeatureConfig(0.35D, 0.083D, true, 4, 7, 3, 5, 1, 3, -16, 16, 0.05D)).withPlacement((DecoratedPlacement.RANGE.configure(new TopSolidRangeConfig(6, 0, 47)).chance(CoreModule.Configuration.COMMON.MALACHITE_GEODE_SPAWN_CHANCE.get()))));
        OmniFeatures.Configured.MUSHROOM_CAVE = RegistryUtil.createConfiguredFeature("mushroom_cave", OmniFeatures.MUSHROOM_CAVE.get().withConfiguration(new CaveBiomeFeatureConfig(Blocks.MYCELIUM.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState(), 64, 0.075F, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, false)).withPlacement(new ConfiguredPlacement<>(Placement.CARVING_MASK, new CaveEdgeConfig(GenerationStage.Carving.AIR, 0.4F)).chance(CoreModule.Configuration.COMMON.MUSHROOM_CAVE_CHANCE.get())));
        OmniFeatures.Configured.ICY_CAVE = RegistryUtil.createConfiguredFeature("icy_cave", OmniFeatures.ICY_CAVE.get().withConfiguration(new CaveBiomeFeatureConfig(OmniBlocks.ARCTISS_BLOCK.get().getDefaultState(), Blocks.PACKED_ICE.getDefaultState(), Blocks.PACKED_ICE.getDefaultState(), Blocks.STONE.getDefaultState(), 48, 0.1F, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, true)).withPlacement(new ConfiguredPlacement<>(Placement.CARVING_MASK, new CaveEdgeConfig(GenerationStage.Carving.AIR, 0.3F)).chance(CoreModule.Configuration.COMMON.ICY_CAVE_CHANCE.get())));
        OmniFeatures.Configured.TERRACOTTA_CAVE = RegistryUtil.createConfiguredFeature("terracotta_cave", OmniFeatures.TERRACOTTA_CAVE.get().withConfiguration(new CaveBiomeFeatureConfig(Blocks.RED_SAND.getDefaultState(), Blocks.RED_SANDSTONE.getDefaultState(), Blocks.TERRACOTTA.getDefaultState(), Blocks.RED_SANDSTONE.getDefaultState(), 56, 0.075F, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, true)).withPlacement(new ConfiguredPlacement<>(Placement.CARVING_MASK, new CaveEdgeConfig(GenerationStage.Carving.AIR, 0.4F)).chance(CoreModule.Configuration.COMMON.TERRACOTTA_CAVE_CHANCE.get())));
    }

    @Override
    protected void registerSounds() {
        OmniSoundEvents.CRYSTAL_PLACE = RegistryUtil.createSoundEvent("block.crystal.place");
        OmniSoundEvents.CRYSTAL_BREAK = RegistryUtil.createSoundEvent("block.crystal.break");
        OmniSoundEvents.CRYSTAL_STEP = RegistryUtil.createSoundEvent("block.crystal.step");
        OmniSoundEvents.CRYSTAL_SHIMMER = RegistryUtil.createSoundEvent("block.crystal.shimmer");
        OmniSoundEvents.CRYSTAL_BUD_PLACE = RegistryUtil.createSoundEvent("block.crystal_bud.place");
        OmniSoundEvents.CRYSTAL_BUD_BREAK = RegistryUtil.createSoundEvent("block.crystal_bud.break");

        OmniSoundEvents.MUSHROOM_BOUNCE = RegistryUtil.createSoundEvent("block.mushroom.bounce");
    }

    @Override
    protected void registerStats() {
        // does not work
        OmniStats.ARCTISS_DISTURB = RegistryUtil.createStatType("arctiss_disturb", IStatFormatter.DEFAULT);
        OmniStats.CAVE_MUSHROOM_BOUNCE = RegistryUtil.createStatType("cave_mushroom_bounce", IStatFormatter.DEFAULT);
    }

    protected void onBiomeLoading(BiomeLoadingEvent event) {
        Biome.Category category = event.getCategory();
        BiomeGenerationSettingsBuilder gen = event.getGeneration();
        ResourceLocation name = event.getName();

        if (category != Biome.Category.NETHER && category != Biome.Category.THEEND && category != Biome.Category.NONE) {
            BiomeFeatures.addStoneSpeleothems(gen);
            BiomeFeatures.addMalachiteGeodes(gen);
        } else {
            for (String s : StringUtils.split(CoreModule.Configuration.COMMON.MALACHITE_BIOME_BLACKLIST.get(), ",")) {
                if (s.equals(name.toString())) BiomeFeatures.addMalachiteGeodes(gen);
            }
        }

        if (category == Biome.Category.ICY) {
            BiomeFeatures.addIcicles(gen);
            BiomeFeatures.addIcyCaves(gen);
        }
        if (category == Biome.Category.NETHER) {
            BiomeFeatures.addNetherrackSpeleothems(gen);
        }
        if (category == Biome.Category.MUSHROOM) {
            BiomeFeatures.addMushroomCaves(gen);
        }
    }
    
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity living = event.getEntityLiving();
        BlockPos pos = new BlockPos(living.getPositionVec());
        World world = living.getEntityWorld();

        if (world.getBlockState(pos).getBlock() == OmniBlocks.GREEN_CAVE_MUSHROOM.get() && !living.isSuppressingBounce()) {
            if (world.getBlockState(pos.down()).getBlock() instanceof SlimeBlock) living.addVelocity(0.0D, 0.9D * CoreModule.Configuration.COMMON.GREEN_CAVE_MUSHROOM_BOUNCE_MODIFIER.get(), 0.0D);
            else living.addVelocity(0.0D, 0.6D, 0.0D);
        }
    }
}