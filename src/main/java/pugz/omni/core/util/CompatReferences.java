package pugz.omni.core.util;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class CompatReferences {
    public static RegistryObject<Block> AUTUMN_CROCUS;
    public static RegistryObject<Block> BLUEBELL;
    public static RegistryObject<Block> BUTTERCUP;
    public static RegistryObject<Block> CARTWHEEL;
    public static RegistryObject<Block> DIANTHUS;
    public static RegistryObject<Block> GILIA;
    public static RegistryObject<Block> GLOWSHROOM;
    public static RegistryObject<Block> MAGENTA_HIBISCUS;
    public static RegistryObject<Block> ORANGE_HIBISCUS;
    public static RegistryObject<Block> PINK_CLOVER;
    public static RegistryObject<Block> PINK_HIBISCUS;
    public static RegistryObject<Block> PURPLE_HIBISCUS;
    public static RegistryObject<Block> RED_HIBISCUS;
    public static RegistryObject<Block> RED_LOTUS_FLOWER;
    public static RegistryObject<Block> VIOLET;
    public static RegistryObject<Block> WHITE_CLOVER;
    public static RegistryObject<Block> WHITE_LOTUS_FLOWER;
    public static RegistryObject<Block> YELLOW_HIBISCUS;
    public static RegistryObject<Block> YUCCA_FLOWER;

    public static void initializeReferences() {
        AUTUMN_CROCUS = RegistryObject.of(new ResourceLocation("autumnity", "autumn_crocus"), ForgeRegistries.BLOCKS);
        BUTTERCUP = RegistryObject.of(new ResourceLocation("buzzier_bees", "buttercup"), ForgeRegistries.BLOCKS);
        BLUEBELL = RegistryObject.of(new ResourceLocation("environmental", "bluebell"), ForgeRegistries.BLOCKS);
        CARTWHEEL = RegistryObject.of(new ResourceLocation("environmental", "cartwheel"), ForgeRegistries.BLOCKS);
        DIANTHUS = RegistryObject.of(new ResourceLocation("environmental", "dianthus"), ForgeRegistries.BLOCKS);
        GILIA = RegistryObject.of(new ResourceLocation("atmospheric", "gilia"), ForgeRegistries.BLOCKS);
        GLOWSHROOM = RegistryObject.of(new ResourceLocation("quark", "glowshroom"), ForgeRegistries.BLOCKS);
        MAGENTA_HIBISCUS = RegistryObject.of(new ResourceLocation("environmental", "magenta_hibiscus"), ForgeRegistries.BLOCKS);
        ORANGE_HIBISCUS = RegistryObject.of(new ResourceLocation("environmental", "orange_hibiscus"), ForgeRegistries.BLOCKS);
        PINK_CLOVER = RegistryObject.of(new ResourceLocation("buzzier_bees", "pink_clover"), ForgeRegistries.BLOCKS);
        PINK_HIBISCUS = RegistryObject.of(new ResourceLocation("environmental", "pink_hibiscus"), ForgeRegistries.BLOCKS);
        PURPLE_HIBISCUS = RegistryObject.of(new ResourceLocation("environmental", "purple_hibiscus"), ForgeRegistries.BLOCKS);
        RED_HIBISCUS = RegistryObject.of(new ResourceLocation("environmental", "red_hibiscus"), ForgeRegistries.BLOCKS);
        RED_LOTUS_FLOWER = RegistryObject.of(new ResourceLocation("environmental", "red_lotus_flower"), ForgeRegistries.BLOCKS);
        VIOLET = RegistryObject.of(new ResourceLocation("environmental", "violet"), ForgeRegistries.BLOCKS);
        WHITE_CLOVER = RegistryObject.of(new ResourceLocation("buzzier_bees", "violet"), ForgeRegistries.BLOCKS);
        WHITE_LOTUS_FLOWER = RegistryObject.of(new ResourceLocation("environmental", "violet"), ForgeRegistries.BLOCKS);
        YELLOW_HIBISCUS = RegistryObject.of(new ResourceLocation("environmental", "violet"), ForgeRegistries.BLOCKS);
        YUCCA_FLOWER = RegistryObject.of(new ResourceLocation("atmospheric", "yucca_flower"), ForgeRegistries.BLOCKS);
    }
}