package com.pugz.omni.core.registry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;

public class OmniBlocks {
    //cavier caves
    public static RegistryObject<Block> STONE_SPELEOTHEM;
    public static RegistryObject<Block> ICE_SPELEOTHEM;
    public static RegistryObject<Block> NETHERRACK_SPELEOTHEM;
    public static RegistryObject<Block> END_STONE_SPELEOTHEM;

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
    public static RegistryObject<Block> RED_ROCK_BRICK_BUTTON;
    public static RegistryObject<Block> RED_ROCK_BRICK_PRESSURE_PLATE;

    @OnlyIn(Dist.CLIENT)
    public static void registerRenders() {
        RenderTypeLookup.setRenderLayer(RED_LOTUS_FLOWER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BLUE_LOTUS_FLOWER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(PINK_LOTUS_FLOWER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BLACK_LOTUS_FLOWER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(WHITE_LOTUS_FLOWER.get(), RenderType.getCutout());
    }
}