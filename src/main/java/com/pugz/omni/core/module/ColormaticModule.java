package com.pugz.omni.core.module;

import com.google.common.collect.ImmutableSet;
import com.pugz.omni.common.block.colormatic.GlazedTerracottaPillarBlock;
import com.pugz.omni.common.block.colormatic.LayerConcreteBlock;
import com.pugz.omni.common.block.colormatic.LayerConcretePowderBlock;
import com.pugz.omni.common.block.colormatic.QuiltedCarpetBlock;
import com.pugz.omni.common.entity.colormatic.FallingConcretePowderEntity;
import com.pugz.omni.common.world.OmniBiomeMaker;
import com.pugz.omni.core.Omni;
import com.pugz.omni.core.registry.OmniBiomes;
import com.pugz.omni.core.registry.OmniBlocks;
import com.pugz.omni.core.registry.OmniEntities;
import com.pugz.omni.core.util.BiomeFeatures;
import com.pugz.omni.core.util.RegistryUtil;
import com.pugz.omni.core.util.TradeUtils;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.RegistryObject;

public class ColormaticModule extends AbstractModule {
    public static final ColormaticModule instance = new ColormaticModule();

    public ColormaticModule() {
        super("Colormatic");
    }

    public void onInitialize() {
        MinecraftForge.EVENT_BUS.addListener(this::onWandererTrades);
        MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoading);
    }

    @Override
    protected void registerBlocks() {
        for (DyeColor color : DyeColor.values()) {
            RegistryObject<Block> OVERRIDE_CONCRETE = RegistryUtil.createOverrideBlock(color.name().toLowerCase() + "_concrete", () -> new Block(AbstractBlock.Properties.from(Blocks.BLACK_CONCRETE)), null);
            RegistryObject<Block> OVERRIDE_CONCRETE_POWDER = RegistryUtil.createOverrideBlock(color.name().toLowerCase() + "_concrete_powder", () -> new ConcretePowderBlock(OVERRIDE_CONCRETE.get(), AbstractBlock.Properties.from(Blocks.BLACK_CONCRETE_POWDER)), null);

            RegistryObject<Block> QUILTED_CARPET = RegistryUtil.createBlock(color.name().toLowerCase() + "_quilted_carpet", QuiltedCarpetBlock::new, ItemGroup.DECORATIONS);
            RegistryObject<Block> QUILTED_WOOL = RegistryUtil.createBlock(color.name().toLowerCase() + "_quilted_wool", () -> new Block(AbstractBlock.Properties.create(Material.WOOL, color).hardnessAndResistance(0.8F).sound(SoundType.CLOTH)), ItemGroup.BUILDING_BLOCKS);
            RegistryObject<Block> GLAZED_TERRACOTTA_PILLAR = RegistryUtil.createBlock(color.name().toLowerCase() + "_glazed_terracotta_pillar", GlazedTerracottaPillarBlock::new, ItemGroup.DECORATIONS);
            RegistryObject<Block> CONCRETE = RegistryUtil.createBlock(color.name().toLowerCase() + "_concrete", () -> new LayerConcreteBlock(color), ItemGroup.BUILDING_BLOCKS);
            RegistryObject<Block> CONCRETE_POWDER = RegistryUtil.createBlock(color.name().toLowerCase() + "_concrete_powder", () -> new LayerConcretePowderBlock(CONCRETE.get(), color), ItemGroup.BUILDING_BLOCKS);
            //RegistryObject<Block> DYE_SACK = RegistryUtil.createBlock(color.name().toLowerCase() + "_dye_sack", () -> new Block(AbstractBlock.Properties.from(Blocks.BLACK_CONCRETE)), ItemGroup.BUILDING_BLOCKS);
        }

        OmniBlocks.TRADERS_QUILTED_CARPET = RegistryUtil.createBlock("traders_quilted_carpet", QuiltedCarpetBlock::new, ItemGroup.DECORATIONS);
        OmniBlocks.TRADERS_QUILTED_WOOL = RegistryUtil.createBlock("traders_quilted_wool", () -> new Block(AbstractBlock.Properties.create(Material.WOOL, DyeColor.BLUE).hardnessAndResistance(0.8F).sound(SoundType.CLOTH)), ItemGroup.BUILDING_BLOCKS);

        OmniBlocks.RED_HYDRANGEA = RegistryUtil.createBlock("red_hydrangea", () -> new FlowerBlock(Effects.HASTE, 9, AbstractBlock.Properties.from(Blocks.DANDELION)), ItemGroup.DECORATIONS);
        OmniBlocks.BLUE_HYDRANGEA = RegistryUtil.createBlock("yellow_hydrangea", () -> new FlowerBlock(Effects.HASTE, 9, AbstractBlock.Properties.from(Blocks.DANDELION)), ItemGroup.DECORATIONS);
        OmniBlocks.YELLOW_HYDRANGEA = RegistryUtil.createBlock("blue_hydrangea", () -> new FlowerBlock(Effects.HASTE, 9, AbstractBlock.Properties.from(Blocks.DANDELION)), ItemGroup.DECORATIONS);
        OmniBlocks.PURPLE_HYDRANGEA = RegistryUtil.createBlock("purple_hydrangea", () -> new FlowerBlock(Effects.HASTE, 9, AbstractBlock.Properties.from(Blocks.DANDELION)), ItemGroup.DECORATIONS);

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
        OmniEntities.FALLING_CONCRETE_POWDER = Omni.Registries.ENTITIES.register("falling_concrete_powder", () -> OmniEntities.createFallingBlockEntity(FallingConcretePowderEntity::new));

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

                new TradeUtils.ItemsForEmeraldsTrade(new ItemStack(OmniBlocks.TRADERS_QUILTED_WOOL.get()), 1, 8, 8, 2)
        ));
    }

    public void onBiomeLoading(BiomeLoadingEvent event) {
        Biome.Category category = event.getCategory();
        BiomeGenerationSettingsBuilder gen = event.getGeneration();
        ResourceLocation name = event.getName();

        if (category == Biome.Category.JUNGLE) {
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.RED_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 4);
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.BLUE_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 4);
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.PINK_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 4);
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.BLACK_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 1);
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.WHITE_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 1);
        }

        if (name.equals(new ResourceLocation("omni", "flower_field"))) {
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.RED_HYDRANGEA.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK), 16);
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.YELLOW_HYDRANGEA.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK), 16);
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.BLUE_HYDRANGEA.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK), 16);
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.PURPLE_HYDRANGEA.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK), 16);
        }

        if (name.equals(new ResourceLocation("minecraft", "flower_forest"))) {
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.RED_HYDRANGEA.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK), 4);
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.YELLOW_HYDRANGEA.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK), 4);
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.BLUE_HYDRANGEA.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK), 4);
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.PURPLE_HYDRANGEA.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK), 4);
        }
    }
}