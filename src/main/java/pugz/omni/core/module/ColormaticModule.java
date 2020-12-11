package pugz.omni.core.module;

import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import pugz.omni.client.render.FallingConcretePowderRenderer;
import pugz.omni.common.block.AbstractStackableBlock;
import pugz.omni.common.block.colormatic.*;
import pugz.omni.common.entity.colormatic.FallingConcretePowderEntity;
import pugz.omni.core.registry.OmniBiomes;
import pugz.omni.core.registry.OmniBlocks;
import pugz.omni.core.registry.OmniEntities;
import pugz.omni.core.util.CompatReferences;
import pugz.omni.core.util.RegistryUtil;
import pugz.omni.core.util.TradeUtils;
import net.minecraft.block.*;
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
import java.util.function.Supplier;

import static pugz.omni.common.block.colormatic.LayerConcretePowderBlock.LAYERS;

public class ColormaticModule extends AbstractModule {
    public static final ColormaticModule instance = new ColormaticModule();
    public static List<Supplier<Block>> CONCRETES = new ArrayList<Supplier<Block>>();
    public static List<Supplier<Block>> CONCRETE_POWDERS = new ArrayList<Supplier<Block>>();
    public static List<Supplier<AbstractStackableBlock>> STACKABLES = new ArrayList<Supplier<AbstractStackableBlock>>();
    public static List<Supplier<Block>> QUILTEDS = new ArrayList<Supplier<Block>>();

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
        MinecraftForge.EVENT_BUS.addListener(this::onEntityJoinWorld);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void onClientInitialize() {
        RenderingRegistry.registerEntityRenderingHandler(OmniEntities.FALLING_CONCRETE_POWDER.get(), FallingConcretePowderRenderer::new);
    }

    @Override
    protected void onPostInitialize() {
    }

    @Override
    protected void registerBlocks() {
        for (DyeColor color : DyeColor.values()) {
            final RegistryObject<Block> CONCRETE = RegistryUtil.createBlock(color.name().toLowerCase() + "_concrete", () -> new LayerConcreteBlock(color), null);
            CONCRETES.add(CONCRETE);
            final RegistryObject<Block> CONCRETE_POWDER = RegistryUtil.createBlock(color.name().toLowerCase() + "_concrete_powder", () -> new LayerConcretePowderBlock(CONCRETE.get(), color), null);
            CONCRETE_POWDERS.add(CONCRETE_POWDER);

            final RegistryObject<Block> QUILTED_CARPET = RegistryUtil.createBlock(color.name().toLowerCase() + "_quilted_carpet", () -> new QuiltedCarpetBlock(color), ItemGroup.DECORATIONS);
            final RegistryObject<Block> QUILTED_WOOL = RegistryUtil.createBlock(color.name().toLowerCase() + "_quilted_wool", () -> new QuiltedWoolBlock(color), ItemGroup.BUILDING_BLOCKS);
            QUILTEDS.addAll(ImmutableSet.of(QUILTED_CARPET, QUILTED_WOOL));
        }

        for (Block block : ForgeRegistries.BLOCKS.getValues()) {
            if (block.getRegistryName().getNamespace().equals("minecraft")) {
                if (block instanceof FlowerBlock) {
                    String name = block.getRegistryName().getPath() + "s";
                    if (StringUtils.endsWith(name, "ss")) name = StringUtils.removeEnd(name, "ss") + "ses";
                    final RegistryObject<AbstractStackableBlock> FLOWERS = RegistryUtil.createBlock(name, () -> new FlowersBlock(AbstractBlock.Properties.from(block), block), null);
                    STACKABLES.add(FLOWERS);
                } else if (block instanceof MushroomBlock) {
                    String name = block.getRegistryName().getPath() + "s";
                    ConfiguredFeature<?, ?> configuredFeature = StringUtils.contains(name, "red") ? Features.HUGE_RED_MUSHROOM : Features.HUGE_BROWN_MUSHROOM;
                    final RegistryObject<AbstractStackableBlock> MUSHROOMS = RegistryUtil.createBlock(name, () -> new MushroomsBlock(AbstractBlock.Properties.from(block), block, () -> configuredFeature), null);
                    STACKABLES.add(MUSHROOMS);
                } else if (block instanceof FungusBlock) {
                    String name = StringUtils.replace(block.getRegistryName().getPath(), "us", "i");
                    ConfiguredFeature<HugeFungusConfig, ?> configuredFeature = StringUtils.contains(name, "crimson") ? Features.CRIMSON_FUNGI_PLANTED : Features.WARPED_FUNGI_PLANTED;
                    final RegistryObject<AbstractStackableBlock> FUNGI = RegistryUtil.createBlock(name, () -> new FungiBlock(AbstractBlock.Properties.from(block), block, () -> configuredFeature), null);
                    STACKABLES.add(FUNGI);
                }
            }
        }

        if (ModList.get().isLoaded("quarkno")) {
            OmniBlocks.GLOWSHROOMS = RegistryUtil.createBlock("glowshrooms", () -> new FlowersBlock(AbstractBlock.Properties.from(CompatReferences.GLOWSHROOM.get()), CompatReferences.GLOWSHROOM.get()), null);
            STACKABLES.add(OmniBlocks.GLOWSHROOMS);
        }

        if (ModList.get().isLoaded("buzzier_beesno")) {
            OmniBlocks.BUTTERCUPS = RegistryUtil.createBlock("buttercups", () -> new FlowersBlock(AbstractBlock.Properties.from(CompatReferences.BUTTERCUP.get()), CompatReferences.BUTTERCUP.get()), null);
            OmniBlocks.PINK_CLOVERS = RegistryUtil.createBlock("pink_clovers", () -> new FlowersBlock(AbstractBlock.Properties.from(CompatReferences.PINK_CLOVER.get()), CompatReferences.PINK_CLOVER.get()), null);
            OmniBlocks.WHITE_CLOVERS = RegistryUtil.createBlock("white_clovers", () -> new FlowersBlock(AbstractBlock.Properties.from(CompatReferences.WHITE_CLOVER.get()), CompatReferences.WHITE_CLOVER.get()), null);
            STACKABLES.add(OmniBlocks.BUTTERCUPS);
            STACKABLES.add(OmniBlocks.PINK_CLOVERS);
            STACKABLES.add(OmniBlocks.WHITE_CLOVERS);
        }

        if (ModList.get().isLoaded("environmentalno")) {
            OmniBlocks.BLUEBELLS = RegistryUtil.createBlock("bluebells", () -> new FlowersBlock(AbstractBlock.Properties.from(CompatReferences.BLUEBELL.get()), CompatReferences.BLUEBELL.get()), null);
            OmniBlocks.CARTWHEELS = RegistryUtil.createBlock("cartwheels", () -> new FlowersBlock(AbstractBlock.Properties.from(CompatReferences.CARTWHEEL.get()), CompatReferences.CARTWHEEL.get()), null);
            OmniBlocks.DIANTHUSES = RegistryUtil.createBlock("dianthuses", () -> new FlowersBlock(AbstractBlock.Properties.from(CompatReferences.DIANTHUS.get()), CompatReferences.DIANTHUS.get()), null);
            OmniBlocks.MAGENTA_HIBISCUSES = RegistryUtil.createBlock("magenta_hibiscuses", () -> new FlowersBlock(AbstractBlock.Properties.from(CompatReferences.MAGENTA_HIBISCUS.get()), CompatReferences.MAGENTA_HIBISCUS.get()), null);
            OmniBlocks.ORANGE_HIBISCUSES = RegistryUtil.createBlock("orange_hibiscuses", () -> new FlowersBlock(AbstractBlock.Properties.from(CompatReferences.ORANGE_HIBISCUS.get()), CompatReferences.ORANGE_HIBISCUS.get()), null);
            OmniBlocks.PINK_HIBISCUSES = RegistryUtil.createBlock("pink_hibiscuses", () -> new FlowersBlock(AbstractBlock.Properties.from(CompatReferences.PINK_HIBISCUS.get()), CompatReferences.PINK_HIBISCUS.get()), null);
            OmniBlocks.PURPLE_HIBISCUSES = RegistryUtil.createBlock("purple_hibiscuses", () -> new FlowersBlock(AbstractBlock.Properties.from(CompatReferences.PURPLE_HIBISCUS.get()), CompatReferences.PURPLE_HIBISCUS.get()), null);
            OmniBlocks.RED_HIBISCUSES = RegistryUtil.createBlock("red_hibiscuses", () -> new FlowersBlock(AbstractBlock.Properties.from(CompatReferences.RED_HIBISCUS.get()), CompatReferences.RED_HIBISCUS.get()), null);
            OmniBlocks.RED_LOTUS_FLOWERS = RegistryUtil.createBlock("red_lotus_flowers", () -> new FlowersBlock(AbstractBlock.Properties.from(CompatReferences.RED_LOTUS_FLOWER.get()), CompatReferences.RED_LOTUS_FLOWER.get()), null);
            OmniBlocks.VIOLETS = RegistryUtil.createBlock("violets", () -> new FlowersBlock(AbstractBlock.Properties.from(CompatReferences.VIOLET.get()), CompatReferences.VIOLET.get()), null);
            OmniBlocks.WHITE_LOTUS_FLOWERS = RegistryUtil.createBlock("white_lotus_flowers", () -> new FlowersBlock(AbstractBlock.Properties.from(CompatReferences.WHITE_LOTUS_FLOWER.get()), CompatReferences.WHITE_LOTUS_FLOWER.get()), null);
            OmniBlocks.YELLOW_HIBISCUSES = RegistryUtil.createBlock("yellow_hibiscuses", () -> new FlowersBlock(AbstractBlock.Properties.from(CompatReferences.YELLOW_HIBISCUS.get()), CompatReferences.YELLOW_HIBISCUS.get()), null);
            STACKABLES.add(OmniBlocks.BLUEBELLS);
            STACKABLES.add(OmniBlocks.CARTWHEELS);
            STACKABLES.add(OmniBlocks.DIANTHUSES);
            STACKABLES.add(OmniBlocks.MAGENTA_HIBISCUSES);
            STACKABLES.add(OmniBlocks.ORANGE_HIBISCUSES);
            STACKABLES.add(OmniBlocks.PINK_HIBISCUSES);
            STACKABLES.add(OmniBlocks.PURPLE_HIBISCUSES);
            STACKABLES.add(OmniBlocks.RED_HIBISCUSES);
            STACKABLES.add(OmniBlocks.RED_LOTUS_FLOWERS);
            STACKABLES.add(OmniBlocks.VIOLETS);
            STACKABLES.add(OmniBlocks.WHITE_LOTUS_FLOWERS);
            STACKABLES.add(OmniBlocks.YELLOW_HIBISCUSES);
        }

        if (ModList.get().isLoaded("atmosphericno")) {
            OmniBlocks.GILIAS = RegistryUtil.createBlock("gilias", () -> new FlowersBlock(AbstractBlock.Properties.from(CompatReferences.GILIA.get()), CompatReferences.GILIA.get()), null);
            STACKABLES.add(OmniBlocks.GILIAS);
        }

        if (ModList.get().isLoaded("autumnityno")) {
            OmniBlocks.AUTUMN_CROCUSES = RegistryUtil.createBlock("autumn_crocuses", () -> new FlowersBlock(AbstractBlock.Properties.from(CompatReferences.AUTUMN_CROCUS.get()), CompatReferences.AUTUMN_CROCUS.get()), null);
            STACKABLES.add(OmniBlocks.AUTUMN_CROCUSES);
        }

        OmniBlocks.TRADERS_QUILTED_CARPET = RegistryUtil.createBlock("traders_quilted_carpet", () -> new QuiltedCarpetBlock(DyeColor.BLUE), ItemGroup.DECORATIONS);
        OmniBlocks.TRADERS_QUILTED_WOOL = RegistryUtil.createBlock("traders_quilted_wool", () -> new QuiltedWoolBlock(DyeColor.BLUE), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.ENCHANTERS_QUILTED_CARPET = RegistryUtil.createBlock("enchanters_quilted_carpet", () -> new QuiltedCarpetBlock(DyeColor.RED), ItemGroup.DECORATIONS);
        OmniBlocks.ENCHANTERS_QUILTED_WOOL = RegistryUtil.createBlock("enchanters_quilted_wool", () -> new QuiltedWoolBlock(DyeColor.RED), ItemGroup.BUILDING_BLOCKS);
    }

    @Override
    protected void registerEntities() {
        OmniEntities.FALLING_CONCRETE_POWDER = RegistryUtil.createEntity("falling_concrete_powder", OmniEntities::createFallingBlockEntity);
    }

    @Override
    protected void registerBiomes() {
        OmniBiomes.FLOWER_FIELD = RegistryUtil.createBiome("flower_field", OmniBiomes.createFlowerFieldBiome(), BiomeManager.BiomeType.WARM, CoreModule.Configuration.COMMON.FLOWER_FIELD_SPAWN_WEIGHT.get(), BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.RARE, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.LUSH);
    }

    public void onWandererTrades(WandererTradesEvent event) {
        event.getGenericTrades().addAll(ImmutableSet.of(
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.TRADERS_QUILTED_WOOL.get()), CoreModule.Configuration.COMMON.TRADERS_WOOL_TRADE_PRICE.get(), 8, 8, 2)
        ));
    }

    protected void onBiomeLoading(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder gen = event.getGeneration();
        ResourceLocation name = event.getName();

        if (name.equals(new ResourceLocation("omni", "flower_field"))) {
            gen.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> Features.FOREST_FLOWER_VEGETATION_COMMON);
            gen.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> Features.FLOWER_FOREST);
        }
    }

    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        ItemStack stack = event.getItemStack();
        BlockPos pos = event.getPos();
        BlockState state = world.getBlockState(pos);
        PlayerEntity player = event.getPlayer();
        Hand hand = event.getHand();
        Direction direction = event.getFace();

        if (CoreModule.Configuration.COMMON.STACK_FLOWERS.get()) {
            if (state.getBlock() instanceof FlowerBlock || state.getBlock() instanceof MushroomBlock || state.getBlock() instanceof FungusBlock) {
                for (Supplier<AbstractStackableBlock> b : STACKABLES) {
                    AbstractStackableBlock stackable = b.get();
                    if (stack.getItem() == state.getBlock().asItem() && stackable.getBase() == state.getBlock() && !player.isSneaking()) {
                        if (!player.isCreative() && !world.isRemote) {
                            stack.shrink(1);
                        }

                        world.setBlockState(pos, stackable.getBlock().getDefaultState(), 3);
                        event.setCancellationResult(ActionResultType.func_233537_a_(world.isRemote));
                        event.setCanceled(true);
                    }
                }
            }
        }

        if (state.getBlock() instanceof ConcretePowderBlock && CoreModule.Configuration.COMMON.LAYER_CONCRETE.get()) {
            if (stack.getItem() instanceof ShovelItem && direction == Direction.UP) {
                for (Supplier<Block> supplier : CONCRETE_POWDERS) {
                    Block block = supplier.get();
                    if (block.getRegistryName().getPath().equals(state.getBlock().getRegistryName().getPath())) {
                        if (!player.isCreative() && !world.isRemote) stack.damageItem(1, player, e -> e.sendBreakAnimation(hand));
                        world.setBlockState(pos, block.getDefaultState().with(LAYERS, 7).with(LayerConcretePowderBlock.WATERLOGGED, world.getFluidState(pos).isTagged(FluidTags.WATER)), 3);
                        event.setCancellationResult(ActionResultType.func_233537_a_(world.isRemote));
                    }
                }
            }
        }
    }

    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        World world = event.getWorld();

        if (entity instanceof FallingBlockEntity) {
            FallingBlockEntity fallingBlock = (FallingBlockEntity) entity;

            if (fallingBlock.getBlockState().getBlock() instanceof ConcretePowderBlock) {
                for (Supplier<Block> supplier : CONCRETE_POWDERS) {
                    Block block = supplier.get();
                    if (block.getRegistryName().getPath().equals(fallingBlock.getBlockState().getBlock().getRegistryName().getPath())) {
                        event.setCanceled(true);
                        world.removeBlock(fallingBlock.getPosition(), false);
                        FallingConcretePowderEntity fallingConcretePowder = new FallingConcretePowderEntity(world, (double) fallingBlock.getPosition().getX() + 0.5D, (double) fallingBlock.getPosition().getY(), (double) fallingBlock.getPosition().getZ() + 0.5D, 8, block.getDefaultState());
                        world.addEntity(fallingConcretePowder);
                    }
                }
            }
        }
    }
}