package com.pugz.omni.core.registry;

import com.pugz.omni.common.block.colormatic.FlowersBlock;
import com.pugz.omni.core.module.ColormaticModule;
import net.minecraft.block.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.RegistryObject;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Supplier;

public class OmniBlocks {
    //cavier caves
    public static RegistryObject<Block> STONE_SPELEOTHEM;
    public static RegistryObject<Block> ICE_SPELEOTHEM;
    public static RegistryObject<Block> NETHERRACK_SPELEOTHEM;

    //colormatic
    public static RegistryObject<Block> TRADERS_QUILTED_WOOL;
    public static RegistryObject<Block> TRADERS_QUILTED_CARPET;

    //paradise
    public static RegistryObject<Block> RED_LOTUS_FLOWER;
    public static RegistryObject<Block> YELLOW_LOTUS_FLOWER;
    public static RegistryObject<Block> ORANGE_LOTUS_FLOWER;
    public static RegistryObject<Block> BLUE_LOTUS_FLOWER;
    public static RegistryObject<Block> PINK_LOTUS_FLOWER;
    public static RegistryObject<Block> PURPLE_LOTUS_FLOWER;
    public static RegistryObject<Block> BLACK_LOTUS_FLOWER;
    public static RegistryObject<Block> WHITE_LOTUS_FLOWER;

    //deserted
    public static RegistryObject<Block> RED_ROCK;
    public static RegistryObject<Block> RED_ROCK_STAIRS;
    public static RegistryObject<Block> RED_ROCK_SLAB;
    public static RegistryObject<Block> RED_ROCK_WALL;
    public static RegistryObject<Block> RED_ROCK_BRICKS;
    public static RegistryObject<Block> RED_ROCK_BRICK_STAIRS;
    public static RegistryObject<Block> RED_ROCK_BRICK_SLAB;
    public static RegistryObject<Block> RED_ROCK_BRICK_WALL;
    public static RegistryObject<Block> CHISELED_RED_ROCK_BRICKS;
    public static RegistryObject<Block> RED_ROCK_PILLAR;
    public static RegistryObject<Block> RED_ROCK_BRICK_BUTTON;
    public static RegistryObject<Block> RED_ROCK_BRICK_PRESSURE_PLATE;

    public static void registerFlammables() {
        FireBlock fire = (FireBlock) Blocks.FIRE;

        fire.setFireInfo(YELLOW_LOTUS_FLOWER.get(), 60, 100);
        fire.setFireInfo(ORANGE_LOTUS_FLOWER.get(), 60, 100);
        fire.setFireInfo(RED_LOTUS_FLOWER.get(), 60, 100);
        fire.setFireInfo(PINK_LOTUS_FLOWER.get(), 60, 100);
        fire.setFireInfo(PURPLE_LOTUS_FLOWER.get(), 60, 100);
        fire.setFireInfo(BLUE_LOTUS_FLOWER.get(), 60, 100);
        fire.setFireInfo(BLACK_LOTUS_FLOWER.get(), 60, 100);
        fire.setFireInfo(WHITE_LOTUS_FLOWER.get(), 60, 100);

        for (Supplier<Block> block : ColormaticModule.stackables) {
            if (((FlowersBlock) block.get()).getBase() instanceof FlowerBlock) fire.setFireInfo(block.get(), 60, 100);
        }

        fire.setFireInfo(TRADERS_QUILTED_CARPET.get(), 60, 20);
        fire.setFireInfo(TRADERS_QUILTED_WOOL.get(), 30, 60);

        for (Supplier<Block> block : ColormaticModule.quilteds) {
            if (StringUtils.contains(block.get().getRegistryName().getPath(), "wool")) {
                fire.setFireInfo(block.get(), 30, 60);
            } else fire.setFireInfo(block.get(), 60, 20);
        }
    }

    public static void registerCompostables() {
        ComposterBlock.CHANCES.put(YELLOW_LOTUS_FLOWER.get().asItem(), 0.65F);
        ComposterBlock.CHANCES.put(ORANGE_LOTUS_FLOWER.get().asItem(), 0.65F);
        ComposterBlock.CHANCES.put(RED_LOTUS_FLOWER.get().asItem(), 0.65F);
        ComposterBlock.CHANCES.put(PINK_LOTUS_FLOWER.get().asItem(), 0.65F);
        ComposterBlock.CHANCES.put(PURPLE_LOTUS_FLOWER.get().asItem(), 0.65F);
        ComposterBlock.CHANCES.put(BLUE_LOTUS_FLOWER.get().asItem(), 0.65F);
        ComposterBlock.CHANCES.put(BLACK_LOTUS_FLOWER.get().asItem(), 0.65F);
        ComposterBlock.CHANCES.put(WHITE_LOTUS_FLOWER.get().asItem(), 0.65F);
    }

    public static void registerBlockRenders() {
        RenderTypeLookup.setRenderLayer(YELLOW_LOTUS_FLOWER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ORANGE_LOTUS_FLOWER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(RED_LOTUS_FLOWER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(PINK_LOTUS_FLOWER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(PURPLE_LOTUS_FLOWER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BLUE_LOTUS_FLOWER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BLACK_LOTUS_FLOWER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(WHITE_LOTUS_FLOWER.get(), RenderType.getCutout());

        for (Supplier<Block> block : ColormaticModule.stackables) {
            RenderTypeLookup.setRenderLayer(block.get(), RenderType.getCutout());
        }
    }
}