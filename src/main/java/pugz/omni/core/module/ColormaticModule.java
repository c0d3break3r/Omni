package pugz.omni.core.module;

import com.google.common.collect.ImmutableSet;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import pugz.omni.client.render.FallingConcretePowderRenderer;
import pugz.omni.common.block.AbstractStackableBlock;
import pugz.omni.common.block.colormatic.*;
import pugz.omni.core.registry.OmniBiomes;
import pugz.omni.core.registry.OmniBlocks;
import pugz.omni.core.registry.OmniEntities;
import pugz.omni.core.util.RegistryUtil;
import pugz.omni.core.util.TradeUtils;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.HugeFungusConfig;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class ColormaticModule extends AbstractModule {
    public static final ColormaticModule instance = new ColormaticModule();
    public static List<Supplier<AbstractStackableBlock>> stackables = new ArrayList<Supplier<AbstractStackableBlock>>();
    public static List<Supplier<Block>> quilteds = new ArrayList<Supplier<Block>>();

    @Override
    protected void sendInitMessage() {
        System.out.println("Discovered the end of the Colormatic Rainbow!");
    }

    public ColormaticModule() {
        super("Colormatic");
    }

    public void onInitialize() {
        MinecraftForge.EVENT_BUS.addListener(this::onWandererTrades);
        MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoading);
        MinecraftForge.EVENT_BUS.addListener(this::onRightClickBlock);
    }

    @Override
    protected void onClientInitialize() {
        for (Supplier<AbstractStackableBlock> block : ColormaticModule.stackables) {
            RenderTypeLookup.setRenderLayer(block.get(), RenderType.getCutout());
        }

        RenderingRegistry.registerEntityRenderingHandler(OmniEntities.FALLING_CONCRETE_POWDER.get(), FallingConcretePowderRenderer::new);
    }

    @Override
    protected void onPostInitialize() {
        FireBlock fire = (FireBlock) Blocks.FIRE;

        fire.setFireInfo(OmniBlocks.TRADERS_QUILTED_CARPET.get(), 60, 20);
        fire.setFireInfo(OmniBlocks.TRADERS_QUILTED_WOOL.get(), 30, 60);

        for (Supplier<AbstractStackableBlock> block : ColormaticModule.stackables) {
            if (block.get() instanceof FlowersBlock) fire.setFireInfo(block.get(), 60, 100);
        }

        for (Supplier<Block> block : ColormaticModule.quilteds) {
            if (StringUtils.contains(block.get().getRegistryName().getPath(), "wool")) {
                fire.setFireInfo(block.get(), 30, 60);
            } else fire.setFireInfo(block.get(), 60, 20);
        }
    }

    @Override
    protected void registerBlocks() {
        for (DyeColor color : DyeColor.values()) {
            final RegistryObject<Block> OVERRIDE_CONCRETE = RegistryUtil.createOverrideBlock(color.name().toLowerCase() + "_concrete", () -> new Block(AbstractBlock.Properties.from(Blocks.BLACK_CONCRETE)), null);
            final RegistryObject<Block> OVERRIDE_CONCRETE_POWDER = RegistryUtil.createOverrideBlock(color.name().toLowerCase() + "_concrete_powder", () -> new ConcretePowderBlock(OVERRIDE_CONCRETE.get(), AbstractBlock.Properties.from(Blocks.BLACK_CONCRETE_POWDER)), null);

            final RegistryObject<Block> QUILTED_CARPET = RegistryUtil.createBlock(color.name().toLowerCase() + "_quilted_carpet", () -> new QuiltedCarpetBlock(color), ItemGroup.DECORATIONS);
            final RegistryObject<Block> QUILTED_WOOL = RegistryUtil.createBlock(color.name().toLowerCase() + "_quilted_wool", () -> new Block(AbstractBlock.Properties.create(Material.WOOL, color).hardnessAndResistance(0.8F).sound(SoundType.CLOTH)), ItemGroup.BUILDING_BLOCKS);
            final RegistryObject<Block> GLAZED_TERRACOTTA_PILLAR = RegistryUtil.createBlock(color.name().toLowerCase() + "_glazed_terracotta_pillar", GlazedTerracottaPillarBlock::new, ItemGroup.DECORATIONS);
            final RegistryObject<Block> CONCRETE = RegistryUtil.createBlock(color.name().toLowerCase() + "_concrete", () -> new LayerConcreteBlock(color), ItemGroup.BUILDING_BLOCKS);
            final RegistryObject<Block> CONCRETE_POWDER = RegistryUtil.createBlock(color.name().toLowerCase() + "_concrete_powder", () -> new LayerConcretePowderBlock(CONCRETE.get(), color), ItemGroup.BUILDING_BLOCKS);
            //final RegistryObject<Block> DYE_SACK = RegistryUtil.createBlock(color.name().toLowerCase() + "_dye_sack", () -> new Block(AbstractBlock.Properties.from(Blocks.BLACK_CONCRETE)), ItemGroup.BUILDING_BLOCKS);

            quilteds.addAll(ImmutableSet.of(QUILTED_CARPET, QUILTED_WOOL));
        }

        for (Block block : ForgeRegistries.BLOCKS.getValues()) {
            if (block instanceof FlowerBlock) {
                String name = block.getRegistryName().getPath() + "s";
                if (StringUtils.endsWith(name, "ss")) name = StringUtils.removeEnd(name, "ss") + "ses";
                final RegistryObject<AbstractStackableBlock> FLOWERS = RegistryUtil.createBlock(name, () -> new FlowersBlock(AbstractBlock.Properties.from(block), block), null);
                stackables.add(FLOWERS);
            } else if (block instanceof MushroomBlock) {
                String name = block.getRegistryName().getPath() + "s";
                ConfiguredFeature<?, ?> configuredFeature = StringUtils.contains(name, "red") ? Features.HUGE_RED_MUSHROOM : Features.HUGE_BROWN_MUSHROOM;
                final RegistryObject<AbstractStackableBlock> MUSHROOMS = RegistryUtil.createBlock(name, () -> new MushroomsBlock(AbstractBlock.Properties.from(block), block, () -> configuredFeature), null);
                stackables.add(MUSHROOMS);
            } else if (block instanceof FungusBlock) {
                String name = StringUtils.replace(block.getRegistryName().getPath(), "us", "i");
                ConfiguredFeature<HugeFungusConfig, ?> configuredFeature = StringUtils.contains(name, "crimson") ? Features.CRIMSON_FUNGI_PLANTED : Features.WARPED_FUNGI_PLANTED;
                final RegistryObject<AbstractStackableBlock> FUNGI = RegistryUtil.createBlock(name, () -> new FungiBlock(AbstractBlock.Properties.from(block), block, () -> configuredFeature), null);
                stackables.add(FUNGI);
            }
        }

        OmniBlocks.TRADERS_QUILTED_CARPET = RegistryUtil.createBlock("traders_quilted_carpet", () -> new QuiltedCarpetBlock(DyeColor.BLUE), ItemGroup.DECORATIONS);
        OmniBlocks.TRADERS_QUILTED_WOOL = RegistryUtil.createBlock("traders_quilted_wool", () -> new Block(AbstractBlock.Properties.create(Material.WOOL, DyeColor.BLUE).hardnessAndResistance(0.8F).sound(SoundType.CLOTH)), ItemGroup.BUILDING_BLOCKS);

        //OmniBlocks.EUCALYPTUS_LOG = RegistryUtil.createBlock(null, null, null);
        //OmniBlocks.EUCALYPTUS_PLANKS = RegistryUtil.createBlock(null, null, null);

        //OmniBlocks.FLOWER_STEM = RegistryUtil.createBlock(null, null, null);
        //OmniBlocks.DANDELION_PETAL_BLOCK = RegistryUtil.createBlock(null, null, null);
        //OmniBlocks.DANDELION_FLUFF_BLOCK = RegistryUtil.createBlock(null, null, null);
        //OmniBlocks.TULIP_PETAL_BLOCK = RegistryUtil.createBlock(null, null, null);
        //OmniBlocks.ROSE_PETAL_BLOCK = RegistryUtil.createBlock(null, null, null);
        //OmniBlocks.FLOWER_PLANKS = RegistryUtil.createBlock(null, null, null);
    }

    @Override
    protected void registerItems() {
        for (DyeColor color : DyeColor.values()) {
            final RegistryObject<Item> OVERRIDE_CONCRETE = RegistryUtil.createOverrideItem(color.name().toLowerCase() + "_concrete", () -> new BlockItem(Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", color.name().toLowerCase() + "_concrete"))), new Item.Properties()), null);
            final RegistryObject<Item> OVERRIDE_CONCRETE_POWDER = RegistryUtil.createOverrideItem(color.name().toLowerCase() + "_concrete_powder", () -> new BlockItem(Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", color.name().toLowerCase() + "_concrete"))), new Item.Properties()), null);
        }

        //RegistryObject<Item> DYES; ?

        //RegistryObject<Item> DANDELION_FLUFF;
    }

    @Override
    protected void registerEntities() {
        OmniEntities.FALLING_CONCRETE_POWDER = RegistryUtil.createEntity("falling_concrete_powder", OmniEntities::createFallingBlockEntity);
        //RegistryObject<EntityType<?>> AEROMA;
        //RegistryObject<EntityType<?>> KOALA;
    }

    @Override
    protected void registerBiomes() {
        OmniBiomes.FLOWER_FIELD = RegistryUtil.createBiome("flower_field", OmniBiomes.createFlowerFieldBiome(), BiomeManager.BiomeType.WARM, CoreModule.Configuration.CLIENT.FLOWER_FIELD_SPAWN_WEIGHT.get(), BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.RARE, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.LUSH);
        //RegistryObject<Biome> BLOOMING_FLOWER_FIELD;
        //RegistryObject<Biome> BLOOMING_FLOWER_FOREST;
        //RegistryObject<Biome> EUCALYPTUS_FOREST;
    }

    @Override
    protected void registerFeatures() {
        //RegistryObject<Feature<?>> GIANT_DANDELION;
        //RegistryObject<Feature<?>> GIANT_TULIP;
        //RegistryObject<Feature<?>> GIANT_ROSE;
        //RegistryObject<Feature<?>> EUCALYPTUS_TREE;
        //RegistryObject<Feature<?>> RAINBOW_EUCALYPTUS_TREE;
    }

    @Override
    protected void registerEffects() {
        //RegistryObject<Effect> ATTRACTION;
    }

    @Override
    protected void registerParticles() {
        //RegistryObject<ParticleType<?>> AEROMA_PARTICLE;
        //RegistryObject<ParticleType<?>> DYE_PARTICLE;
    }

    @Override
    protected void registerSounds() {
    }

    @Override
    protected void registerStats() {
    }

    public void onWandererTrades(WandererTradesEvent event) {
        event.getGenericTrades().addAll(ImmutableSet.of(
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.TRADERS_QUILTED_WOOL.get()), CoreModule.Configuration.CLIENT.TRADERS_WOOL_TRADE_PRICE.get(), 8, 8, 2)
        ));
    }

    public void onBiomeLoading(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder gen = event.getGeneration();
        ResourceLocation name = event.getName();

        assert name != null;
        if (name.equals(new ResourceLocation("omni", "flower_field"))) {
            gen.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> Features.FOREST_FLOWER_VEGETATION_COMMON);
            gen.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> Features.FLOWER_FOREST);
        }
    }

    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        ItemStack stack = event.getItemStack();
        BlockPos pos = event.getPos();
        Block block = world.getBlockState(pos).getBlock();
        PlayerEntity player = event.getPlayer();

        if (block instanceof FlowerBlock || block instanceof MushroomBlock || block instanceof FungusBlock) {
            for (Supplier<AbstractStackableBlock> b : stackables) {
                AbstractStackableBlock stackable = b.get();
                if (stack.getItem() == block.asItem() && stackable.getBase() == block && !player.isSneaking()) {
                    if (!player.isCreative()) {
                        stack.shrink(1);
                    }

                    world.setBlockState(pos, stackable.getBlock().getDefaultState(), 3);
                    event.setCancellationResult(ActionResultType.func_233537_a_(world.isRemote));
                    event.setCanceled(true);
                }
            }
        }
    }
}