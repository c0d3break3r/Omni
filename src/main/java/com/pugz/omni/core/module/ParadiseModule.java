package com.pugz.omni.core.module;

import com.pugz.omni.common.block.paradise.LotusFlowerBlock;
import com.pugz.omni.core.registry.OmniBlocks;
import com.pugz.omni.core.util.RegistryUtil;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.Effects;

public class ParadiseModule extends AbstractModule {
    public static final ParadiseModule instance = new ParadiseModule();

    public ParadiseModule() {
        super("Paradise");
    }

    @Override
    protected void registerBlocks() {
        //RegistryObject<Block> SAND_LAYER;
        //RegistryObject<Block> RED_SAND_LAYER;
        //RegistryObject<Block> WHITE_SAND_LAYER;
        //RegistryObject<Block> SOUL_SAND_LAYER;
        //RegistryObject<Block> ARID_SAND_LAYER;
        //RegistryObject<Block> RED_ARID_SAND_LAYER;

        //RegistryObject<Block> NAUTILUS_SHELL_BLOCK;

        //RegistryObject<Block> WHITE_SAND;
        //RegistryObject<Block> WHITE_SANDSTONE;
        //RegistryObject<Block> WHITE_SANDSTONE_STAIRS;
        //RegistryObject<Block> WHITE_SANDSTONE_SLAB;
        //RegistryObject<Block> WHITE_SANDSTONE_WALL;

        //RegistryObject<Block> PALM_LOG;
        //RegistryObject<Block> PALM_PLANKS;

        //RegistryObject<Block> COCONUT;
        //RegistryObject<Block> COCONUT_BLOCK;

        OmniBlocks.RED_LOTUS_FLOWER = RegistryUtil.createBlock("red_lotus_flower", () -> new LotusFlowerBlock(Effects.NAUSEA, 9), ItemGroup.DECORATIONS);
        OmniBlocks.YELLOW_LOTUS_FLOWER = RegistryUtil.createBlock("yellow_lotus_flower", () -> new LotusFlowerBlock(Effects.NAUSEA, 9), ItemGroup.DECORATIONS);;
        OmniBlocks.ORANGE_LOTUS_FLOWER = RegistryUtil.createBlock("orange_lotus_flower", () -> new LotusFlowerBlock(Effects.NAUSEA, 9), ItemGroup.DECORATIONS);;
        OmniBlocks.BLUE_LOTUS_FLOWER = RegistryUtil.createBlock("blue_lotus_flower", () -> new LotusFlowerBlock(Effects.NAUSEA, 9), ItemGroup.DECORATIONS);;
        OmniBlocks.PINK_LOTUS_FLOWER = RegistryUtil.createBlock("pink_lotus_flower", () -> new LotusFlowerBlock(Effects.NAUSEA, 9), ItemGroup.DECORATIONS);;
        OmniBlocks.PURPLE_LOTUS_FLOWER = RegistryUtil.createBlock("purple_lotus_flower", () -> new LotusFlowerBlock(Effects.NAUSEA, 9), ItemGroup.DECORATIONS);;
        OmniBlocks.BLACK_LOTUS_FLOWER = RegistryUtil.createBlock("black_lotus_flower", () -> new LotusFlowerBlock(Effects.BLINDNESS, 8), ItemGroup.DECORATIONS);;
        OmniBlocks.WHITE_LOTUS_FLOWER = RegistryUtil.createBlock("white_lotus_flower", () -> new LotusFlowerBlock(Effects.NIGHT_VISION, 8), ItemGroup.DECORATIONS);;

        //RegistryObject<Block> TROPICAL_FERN;
    }

    @Override
    protected void registerItems() {
        //RegistryObject<Item> COCONUT;
        //RegistryObject<Item> COCONUT_MILK;
        //RegistryObject<Item> COCONUT_BOMB;

        //RegistryObject<Item> BAMBOO_SPEAR;

        //RegistryObject<Item> NEPTFIN;

        //RegistryObject<Item> RED_PARROT_FEATHER;
        //RegistryObject<Item> GREEN_PARROT_FEATHER;
        //RegistryObject<Item> BLUE_PARROT_FEATHER;

        //RegistryObject<Item> TIKI_MASK;

        //RegistryObject<Item> KIWI;

        //RegistryObject<Item> SHELLS;
    }

    @Override
    protected void registerEntities() {
        //RegistryObject<EntityType<?>> KELPIE;
        //RegistryObject<EntityType<?>> KIWI;
        //RegistryObject<EntityType<?>> TIKI;
        //RegistryObject<EntityType<?>> SEAHORSE;
        //RegistryObject<EntityType<?>> GOLIATH;
        //RegistryObject<EntityType<?>> HERMIT_CRAB;
        //RegistryObject<EntityType<?>> SEAGULL;
    }

    @Override
    protected void registerBiomes() {
        //RegistryObject<Biome> TROPICAL_PLAINS;
        //RegistryObject<Biome> TROPICAL_ISLANDS;
    }

    @Override
    protected void registerSurfaceBuilders() {
        //RegistryObject<SurfaceBuilder<?>> TROPICAL_ISLAND_SURFACE_BUILDER;
    }

    @Override
    protected void registerFeatures() {
        //RegistryObject<Feature<?>> JUNGLE_FLOWER;
        //RegistryObject<Feature<?>> WHITE_SAND_REPLACEMENT;
    }

    @Override
    protected void registerSounds() {
    }
}