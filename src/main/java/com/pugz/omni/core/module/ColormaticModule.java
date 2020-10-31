package com.pugz.omni.core.module;

import com.google.common.collect.ImmutableSet;
import com.pugz.omni.common.block.colormatic.*;
import com.pugz.omni.common.entity.colormatic.FallingConcretePowderEntity;
import com.pugz.omni.common.world.OmniBiomeMaker;
import com.pugz.omni.core.registry.OmniBiomes;
import com.pugz.omni.core.registry.OmniBlocks;
import com.pugz.omni.core.registry.OmniEntities;
import com.pugz.omni.core.util.RegistryUtil;
import com.pugz.omni.core.util.TradeUtils;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
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

public class ColormaticModule extends AbstractModule {
    public static final ColormaticModule instance = new ColormaticModule();
    public static List<Supplier<Block>> stackables = new ArrayList<Supplier<Block>>();
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
    protected void registerBlocks() {
        for (DyeColor color : DyeColor.values()) {
            final RegistryObject<Block> OVERRIDE_CONCRETE = RegistryUtil.createOverrideBlock(color.name().toLowerCase() + "_concrete", () -> new Block(AbstractBlock.Properties.from(Blocks.BLACK_CONCRETE)), null);
            final RegistryObject<Block> OVERRIDE_CONCRETE_POWDER = RegistryUtil.createOverrideBlock(color.name().toLowerCase() + "_concrete_powder", () -> new ConcretePowderBlock(OVERRIDE_CONCRETE.get(), AbstractBlock.Properties.from(Blocks.BLACK_CONCRETE_POWDER)), null);

            final RegistryObject<Block> QUILTED_CARPET = RegistryUtil.createBlock(color.name().toLowerCase() + "_quilted_carpet", QuiltedCarpetBlock::new, ItemGroup.DECORATIONS);
            final RegistryObject<Block> QUILTED_WOOL = RegistryUtil.createBlock(color.name().toLowerCase() + "_quilted_wool", () -> new Block(AbstractBlock.Properties.create(Material.WOOL, color).hardnessAndResistance(0.8F).sound(SoundType.CLOTH)), ItemGroup.BUILDING_BLOCKS);
            final RegistryObject<Block> GLAZED_TERRACOTTA_PILLAR = RegistryUtil.createBlock(color.name().toLowerCase() + "_glazed_terracotta_pillar", GlazedTerracottaPillarBlock::new, ItemGroup.DECORATIONS);
            final RegistryObject<Block> CONCRETE = RegistryUtil.createBlock(color.name().toLowerCase() + "_concrete", () -> new LayerConcreteBlock(color), ItemGroup.BUILDING_BLOCKS);
            final RegistryObject<Block> CONCRETE_POWDER = RegistryUtil.createBlock(color.name().toLowerCase() + "_concrete_powder", () -> new LayerConcretePowderBlock(CONCRETE.get(), color), ItemGroup.BUILDING_BLOCKS);
            //final RegistryObject<Block> DYE_SACK = RegistryUtil.createBlock(color.name().toLowerCase() + "_dye_sack", () -> new Block(AbstractBlock.Properties.from(Blocks.BLACK_CONCRETE)), ItemGroup.BUILDING_BLOCKS);

            quilteds.addAll(ImmutableSet.of(QUILTED_CARPET, QUILTED_WOOL));
        }

        for (Block block : ForgeRegistries.BLOCKS.getValues()) {
            if (block instanceof FlowerBlock || block instanceof MushroomBlock || block instanceof FungusBlock) {
                String name;

                if (block instanceof FungusBlock) {
                    name = StringUtils.replace(block.getRegistryName().getPath(), "us", "i");
                }
                else name = block.getRegistryName().getPath() + "s";

                final RegistryObject<Block> FLOWERS = RegistryUtil.createBlock(name, () -> new FlowersBlock(AbstractBlock.Properties.from(block), block), null);
                stackables.add(FLOWERS);
            }
        }

        OmniBlocks.TRADERS_QUILTED_CARPET = RegistryUtil.createBlock("traders_quilted_carpet", QuiltedCarpetBlock::new, ItemGroup.DECORATIONS);
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
        //RegistryObject<Item> DYES; ?

        //RegistryObject<Item> DANDELION_FLUFF;
    }

    @Override
    protected void registerEntities() {
        OmniEntities.FALLING_CONCRETE_POWDER = RegistryUtil.createEntity("falling_concrete_powder", () -> OmniEntities.createFallingBlockEntity(FallingConcretePowderEntity::new));

        //RegistryObject<EntityType<?>> AEROMA;
        //RegistryObject<EntityType<?>> KOALA;
    }

    @Override
    protected void registerBiomes() {
        OmniBiomes.FLOWER_FIELD = RegistryUtil.createBiome("flower_field", OmniBiomeMaker.makeFlowerFieldBiome(), BiomeManager.BiomeType.WARM, 1, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.RARE, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.LUSH);
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
        //RegistryObject<ParticleType<?>> FLOWER_PARTICLE;
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
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.RED_LOTUS_FLOWER.get()), 1, 1, 12, 1),
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.BLUE_LOTUS_FLOWER.get()), 1, 1, 12, 1),
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.PINK_LOTUS_FLOWER.get()), 1, 1, 12, 1),
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.BLACK_LOTUS_FLOWER.get()), 1, 1, 12, 1),
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.WHITE_LOTUS_FLOWER.get()), 1, 1, 12, 1),
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.PURPLE_LOTUS_FLOWER.get()), 1, 1, 12, 1),
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.ORANGE_LOTUS_FLOWER.get()), 1, 1, 12, 1),
                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.YELLOW_LOTUS_FLOWER.get()), 1, 1, 12, 1),

                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.TRADERS_QUILTED_WOOL.get()), 1, 8, 8, 2)
        ));
    }

    public void onBiomeLoading(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder gen = event.getGeneration();
        ResourceLocation name = event.getName();

        if (name.equals(new ResourceLocation("omni", "flower_field"))) {
            gen.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> Features.FOREST_FLOWER_VEGETATION_COMMON);
            gen.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> Features.FLOWER_FOREST);
        }
    }

    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        BlockPos pos = event.getPos();
        World world = event.getWorld();
        Block block = world.getBlockState(pos).getBlock();
        PlayerEntity player = event.getPlayer();

        if ((block instanceof FlowerBlock || block instanceof MushroomBlock || block instanceof FungusBlock) && stack.getItem() == block.asItem() && !player.isSneaking()) {
            for (Supplier<Block> b : stackables) {
                if (((FlowersBlock)b.get()).getBase() == block) {
                    if (!player.isCreative()) {
                        stack.shrink(1);
                    }
                    player.sendBreakAnimation(event.getHand());

                    world.setBlockState(pos, b.get().getDefaultState(), 3);
                }
            }
            event.setCanceled(true);
        }
    }
}