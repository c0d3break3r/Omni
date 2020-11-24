package pugz.omni.core.module;

import net.minecraft.block.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import pugz.omni.client.render.SizedCaveSpiderRenderer;
import pugz.omni.client.render.SpeleothemRenderer;
import pugz.omni.common.block.HorizontalFacingBlock;
import pugz.omni.common.block.VerticalSlabBlock;
import pugz.omni.common.block.cavier_caves.*;
import pugz.omni.common.entity.cavier_caves.SizedCaveSpiderEntity;
import pugz.omni.common.item.OmniSpawnEggItem;
import pugz.omni.common.world.feature.ExposedOreFeatureConfig;
import pugz.omni.common.world.feature.cavier_caves.*;
import pugz.omni.core.registry.*;
import pugz.omni.core.util.BaseGenUtils;
import pugz.omni.core.util.BiomeFeatures;
import pugz.omni.core.util.RegistryUtil;
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
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerBreakSpeed);
        MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoading);
        MinecraftForge.EVENT_BUS.addListener(this::onLivingJump);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void onClientInitialize() {
        RenderingRegistry.registerEntityRenderingHandler(OmniEntities.CAVE_SPIDER.get(), SizedCaveSpiderRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(OmniEntities.SPELEOTHEM.get(), SpeleothemRenderer::new);}

    @Override
    protected void onPostInitialize() {
        GlobalEntityTypeAttributes.put(OmniEntities.CAVE_SPIDER.get(), SizedCaveSpiderEntity.registerAttributes().create());
    }

    @Override
    protected void registerBlocks() {
        //RegistryObject<Block> SULFUR_BLOCK;

        //RegistryObject<Block> PETRIFIED_PLANKS;

        //RegistryObject<Block> CAVE_PAINTING;

        OmniBlocks.YELLOW_CAVE_MUSHROOM = RegistryUtil.createBlock("yellow_cave_mushroom", () -> new CaveMushroomBlock(MaterialColor.YELLOW), ItemGroup.DECORATIONS);
        OmniBlocks.GREEN_CAVE_MUSHROOM = RegistryUtil.createBlock("green_cave_mushroom", () -> new CaveMushroomBlock(MaterialColor.GREEN), ItemGroup.DECORATIONS);
        OmniBlocks.BLUE_CAVE_MUSHROOM = RegistryUtil.createBlock("blue_cave_mushroom", BlueCaveMushroomBlock::new, ItemGroup.DECORATIONS);
        OmniBlocks.PURPLE_CAVE_MUSHROOM = RegistryUtil.createBlock("purple_cave_mushroom", PurpleCaveMushroomBlock::new, ItemGroup.DECORATIONS);
        OmniBlocks.YELLOW_CAVE_MUSHROOM_BLOCK = RegistryUtil.createBlock("yellow_cave_mushroom_block", () -> new Block(AbstractBlock.Properties.from(Blocks.RED_MUSHROOM_BLOCK)), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.GREEN_CAVE_MUSHROOM_BLOCK = RegistryUtil.createBlock("green_cave_mushroom_block", () -> new Block(AbstractBlock.Properties.from(Blocks.RED_MUSHROOM_BLOCK)), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.BLUE_CAVE_MUSHROOM_BLOCK = RegistryUtil.createBlock("blue_cave_mushroom_block", () -> new Block(AbstractBlock.Properties.from(Blocks.RED_MUSHROOM_BLOCK)), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.PURPLE_CAVE_MUSHROOM_BLOCK = RegistryUtil.createBlock("purple_cave_mushroom_block", () -> new Block(AbstractBlock.Properties.from(Blocks.RED_MUSHROOM_BLOCK)), ItemGroup.BUILDING_BLOCKS);

        //if (ModList.get().isLoaded("enhanced_mushrooms")) {
            OmniBlocks.CAVE_MUSHROOM_STEM = RegistryUtil.createBlock("cave_mushroom_stem", () -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.OAK_LOG)), ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.CAVE_MUSHROOM_PLANKS = RegistryUtil.createBlock("cave_mushroom_planks", () -> new Block(AbstractBlock.Properties.from(Blocks.OAK_PLANKS)), ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.CAVE_MUSHROOM_STAIRS = RegistryUtil.createBlock("cave_mushroom_stairs", () -> new StairsBlock(OmniBlocks.CAVE_MUSHROOM_PLANKS.get()::getDefaultState, AbstractBlock.Properties.from(OmniBlocks.CAVE_MUSHROOM_PLANKS.get())), ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.CAVE_MUSHROOM_SLAB = RegistryUtil.createBlock("cave_mushroom_slab", () -> new SlabBlock(AbstractBlock.Properties.from(OmniBlocks.CAVE_MUSHROOM_PLANKS.get())), ItemGroup.BUILDING_BLOCKS);
            OmniBlocks.CAVE_MUSHROOM_FENCE = RegistryUtil.createBlock("cave_mushroom_fence", () -> new FenceBlock(AbstractBlock.Properties.from(OmniBlocks.CAVE_MUSHROOM_PLANKS.get())), ItemGroup.DECORATIONS);
            OmniBlocks.CAVE_MUSHROOM_FENCE_GATE = RegistryUtil.createBlock("cave_mushroom_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.from(OmniBlocks.CAVE_MUSHROOM_PLANKS.get())), ItemGroup.REDSTONE);
            OmniBlocks.CAVE_MUSHROOM_BUTTON = RegistryUtil.createBlock("cave_mushroom_button", () -> new WoodButtonBlock(AbstractBlock.Properties.from(Blocks.OAK_BUTTON)), ItemGroup.REDSTONE);
            OmniBlocks.CAVE_MUSHROOM_PRESSURE_PLATE = RegistryUtil.createBlock("cave_mushroom_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.from(Blocks.OAK_PRESSURE_PLATE)), ItemGroup.REDSTONE);

            //if (ModList.get().isLoaded("quark")) {
                OmniBlocks.CAVE_MUSHROOM_VERTICAL_SLAB = RegistryUtil.createBlock("cave_mushroom_vertical_slab", () -> new VerticalSlabBlock(AbstractBlock.Properties.from(OmniBlocks.CAVE_MUSHROOM_PLANKS.get())), ItemGroup.BUILDING_BLOCKS);
            //}
        //}

        OmniBlocks.COBWEB_CARPET = RegistryUtil.createBlock("cobweb_carpet", CobwebCarpetBlock::new, ItemGroup.DECORATIONS);
        OmniBlocks.CAVE_SPIDER_SAC = RegistryUtil.createBlock("cave_spider_sac", SpiderSacBlock::new, ItemGroup.DECORATIONS);

        //RegistryObject<Block> WEAK_STONE;

        //RegistryObject<Block> SLIME;

        //RegistryObject<Block> THIN_ICE;
        //RegistryObject<Block> PERMAFROST;

        //RegistryObject<Block> STONE_SIGN;
        //RegistryObject<Block> BLACKSTONE_SIGN;

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
    }

    @Override
    protected void registerItems() {
        //RegistryObject<Item> SULFUR_DUST;

        //RegistryObject<Item> CRYSTAL_MELON;

        OmniItems.MALACHITE_SHARD = RegistryUtil.createItem("malachite_shard", () -> new Item(new Item.Properties().group(ItemGroup.MATERIALS)));
        OmniItems.CAVE_SPIDER_SPAWN_EGG = RegistryUtil.createOverrideItem("cave_spider_spawn_egg", () -> new OmniSpawnEggItem(() -> OmniEntities.CAVE_SPIDER.get(), 803406, 11013646, new Item.Properties().group(ItemGroup.MATERIALS)));
    }

    @Override
    protected void registerEntities() {
        OmniEntities.CAVE_SPIDER = RegistryUtil.createEntity("cave_spider", () -> OmniEntities.createLivingEntity(SizedCaveSpiderEntity::new, EntityClassification.MONSTER, "cave_spider",0.7F, 0.5F));
        OmniEntities.SPELEOTHEM = RegistryUtil.createEntity("speleothem", OmniEntities::createSpeleothemEntity);
    }

    @Override
    protected void registerFeatures() {
        OmniFeatures.MUSHROOM_CAVE = RegistryUtil.createFeature("mushroom_cave", () -> new MushroomCaveBiomeFeature(CaveBiomeFeatureConfig.CODEC));

        OmniFeatures.SPELEOTHEM = RegistryUtil.createFeature("speleothem", () -> new SpeleothemFeature(SpeleothemFeatureConfig.CODEC));
        //RegistryObject<Feature<?>> SLIME;
        //RegistryObject<Feature<?>> CAVE_CARVING;
        //RegistryObject<Feature<?>> THIN_ICE;
        //RegistryObject<Feature<?>> SPIDER_NEST;
        //RegistryObject<Feature<?>> WALL_MUSHROOM;
        //RegistryObject<Feature<?>> SULFUR;

        //RegistryObject<Feature<?>> PETRIFIED_WOOD_REPLACEMENT;

        OmniFeatures.GEODE = RegistryUtil.createFeature("geode", () -> new GeodeFeature(GeodeFeatureConfig.CODEC));
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
    }

    protected void onBiomeLoading(BiomeLoadingEvent event) {
        Biome.Category category = event.getCategory();
        BiomeGenerationSettingsBuilder gen = event.getGeneration();

        if (category != Biome.Category.NETHER && category != Biome.Category.THEEND) {
            BiomeFeatures.addSpeleothems(gen, SpeleothemFeatureConfig.Variant.STONE, CoreModule.Configuration.COMMON.SPELEOTHEMS_SPAWN_PROBABILITY.get().floatValue(), 3);
            BiomeFeatures.addMalachiteGeodes(gen);
        }
        if (category == Biome.Category.ICY) {
            BiomeFeatures.addSpeleothems(gen, SpeleothemFeatureConfig.Variant.ICE, CoreModule.Configuration.COMMON.SPELEOTHEMS_SPAWN_PROBABILITY.get().floatValue() * 1.5F, 3);
        }
        if (category == Biome.Category.NETHER) {
            BiomeFeatures.addSpeleothems(gen, SpeleothemFeatureConfig.Variant.NETHERRACK, CoreModule.Configuration.COMMON.SPELEOTHEMS_SPAWN_PROBABILITY.get().floatValue() * 2.0F, 3);
        }
        if (category == Biome.Category.MUSHROOM) {
            BiomeFeatures.addMushroomCave(gen, Blocks.MYCELIUM.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState(),160, 0.1F, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, 0.5F, 15);
        }
    }

    public void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event) {
        BlockPos pos = event.getPos();
        PlayerEntity player = event.getPlayer();
        World world = player.getEntityWorld();

        if (BaseGenUtils.isBlockWithinRange(world, pos, 6, OmniBlocks.YELLOW_CAVE_MUSHROOM.get())) {
            event.setNewSpeed(event.getOriginalSpeed() * 1.15F);
        }
    }
    
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity living = event.getEntityLiving();
        BlockPos pos = new BlockPos(living.getPositionVec());
        World world = living.getEntityWorld();

        if (world.getBlockState(pos).getBlock() == OmniBlocks.GREEN_CAVE_MUSHROOM.get() && !living.isSuppressingBounce()) {
            if (world.getBlockState(pos.down()).getBlock() instanceof SlimeBlock) living.addVelocity(0.0D, 0.9D, 0.0D);
            else living.addVelocity(0.0D, 0.6D, 0.0D);
        }
    }
}