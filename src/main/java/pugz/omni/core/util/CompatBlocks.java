package pugz.omni.core.util;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class CompatBlocks {
    public static Block AUTUMN_CROCUS;
    public static Block BLUEBELL;
    public static Block BUTTERCUP;
    public static Block CARTWHEEL;
    public static Block DIANTHUS;
    public static Block GILIA;
    public static Block GLOWSHROOM;
    public static Block MAGENTA_HIBISCUS;
    public static Block ORANGE_HIBISCUS;
    public static Block PINK_CLOVER;
    public static Block PINK_HIBISCUS;
    public static Block PURPLE_HIBISCUS;
    public static Block RED_HIBISCUS;
    public static Block RED_LOTUS_FLOWER;
    public static Block VIOLET;
    public static Block WHITE_CLOVER;
    public static Block WHITE_LOTUS_FLOWER;
    public static Block YELLOW_HIBISCUS;

    public static void initializeBlocks() {
        AUTUMN_CROCUS = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("autumnity", "autumn_crocus"));
        BUTTERCUP = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("buzzier_bees", "buttercup"));
        BLUEBELL = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("environmental", "bluebell"));
        CARTWHEEL = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("environmental", "cartwheel"));
        DIANTHUS = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("environmental", "dianthus"));
        GILIA = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("atmospheric", "gilia"));
        GLOWSHROOM = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("quark", "glowshroom"));
        MAGENTA_HIBISCUS = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("environmental", "magenta_hibiscus"));
        ORANGE_HIBISCUS = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("environmental", "orange_hibiscus"));
        PINK_CLOVER = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("buzzier_bees", "pink_clover"));
        PINK_HIBISCUS = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("environmental", "pink_hibiscus"));
        PURPLE_HIBISCUS = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("environmental", "purple_hibiscus"));
        RED_HIBISCUS = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("environmental", "red_hibiscus"));
        RED_LOTUS_FLOWER = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("environmental", "red_lotus_flower"));
        VIOLET = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("environmental", "violet"));
        WHITE_CLOVER = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("buzzier_bees", "white_clover"));
        WHITE_LOTUS_FLOWER = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("environmental", "white_lotus_flower"));
        YELLOW_HIBISCUS = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("environmental", "yellow_hibiscus"));
    }
}