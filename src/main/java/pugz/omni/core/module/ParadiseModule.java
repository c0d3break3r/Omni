package pugz.omni.core.module;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import pugz.omni.common.block.paradise.LotusFlowerBlock;
import pugz.omni.core.registry.OmniBlocks;
import pugz.omni.core.util.BiomeFeatures;
import pugz.omni.core.util.RegistryUtil;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.Effects;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class ParadiseModule extends AbstractModule {
    public static final ParadiseModule instance = new ParadiseModule();

    public ParadiseModule() {
        super("Paradise");
    }

    @Override
    protected void sendInitMessage() {
        System.out.println("Ah, yes. Vibing in Paradise...");
    }

    @Override
    protected void onInitialize() {
        MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoading);
    }

    @Override
    protected void onClientInitialize() {
        RenderTypeLookup.setRenderLayer(OmniBlocks.YELLOW_LOTUS_FLOWER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(OmniBlocks.ORANGE_LOTUS_FLOWER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(OmniBlocks.RED_LOTUS_FLOWER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(OmniBlocks.PINK_LOTUS_FLOWER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(OmniBlocks.PURPLE_LOTUS_FLOWER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(OmniBlocks.BLUE_LOTUS_FLOWER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(OmniBlocks.BLACK_LOTUS_FLOWER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(OmniBlocks.WHITE_LOTUS_FLOWER.get(), RenderType.getCutout());
    }

    @Override
    protected void onPostInitialize() {
        FireBlock fire = (FireBlock) Blocks.FIRE;

        fire.setFireInfo(OmniBlocks.YELLOW_LOTUS_FLOWER.get(), 60, 100);
        fire.setFireInfo(OmniBlocks.ORANGE_LOTUS_FLOWER.get(), 60, 100);
        fire.setFireInfo(OmniBlocks.RED_LOTUS_FLOWER.get(), 60, 100);
        fire.setFireInfo(OmniBlocks.PINK_LOTUS_FLOWER.get(), 60, 100);
        fire.setFireInfo(OmniBlocks.PURPLE_LOTUS_FLOWER.get(), 60, 100);
        fire.setFireInfo(OmniBlocks.BLUE_LOTUS_FLOWER.get(), 60, 100);
        fire.setFireInfo(OmniBlocks.BLACK_LOTUS_FLOWER.get(), 60, 100);
        fire.setFireInfo(OmniBlocks.WHITE_LOTUS_FLOWER.get(), 60, 100);

        ComposterBlock.CHANCES.put(OmniBlocks.YELLOW_LOTUS_FLOWER.get().asItem(), 0.65F);
        ComposterBlock.CHANCES.put(OmniBlocks.ORANGE_LOTUS_FLOWER.get().asItem(), 0.65F);
        ComposterBlock.CHANCES.put(OmniBlocks.RED_LOTUS_FLOWER.get().asItem(), 0.65F);
        ComposterBlock.CHANCES.put(OmniBlocks.PINK_LOTUS_FLOWER.get().asItem(), 0.65F);
        ComposterBlock.CHANCES.put(OmniBlocks.PURPLE_LOTUS_FLOWER.get().asItem(), 0.65F);
        ComposterBlock.CHANCES.put(OmniBlocks.BLUE_LOTUS_FLOWER.get().asItem(), 0.65F);
        ComposterBlock.CHANCES.put(OmniBlocks.BLACK_LOTUS_FLOWER.get().asItem(), 0.65F);
        ComposterBlock.CHANCES.put(OmniBlocks.WHITE_LOTUS_FLOWER.get().asItem(), 0.65F);
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

        //OmniItems.BAMBOO_SPEAR = RegistryUtil.createItem("bamboo_spear", () -> new SpearItem(new Item.Properties().group(ItemGroup.COMBAT).maxDamage(240)));
        //OmniItems.LIT_BAMBOO_SPEAR = RegistryUtil.createItem("lit_bamboo_spear", () -> new LitSpearItem(new Item.Properties().group(ItemGroup.COMBAT).maxDamage(240)));

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

        //OmniEntities.BAMBOO_SPEAR = RegistryUtil.createEntity("bamboo_spear", () -> OmniEntities.createBambooSpearEntity(BambooSpearEntity::new));
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
        //RegistryObject<Feature<?>> WHITE_SAND_REPLACEMENT;
    }

    @Override
    protected void registerSounds() {
    }

    public void onBiomeLoading(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder gen = event.getGeneration();

        if (event.getCategory() == Biome.Category.JUNGLE) {
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.RED_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 3, 16);
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.ORANGE_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 3, 16);
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.YELLOW_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 3, 16);
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.BLUE_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 3, 16);
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.PINK_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 3, 16);
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.PURPLE_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 3, 16);
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.BLACK_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 1, 16);
            BiomeFeatures.addScatteredBlock(gen, OmniBlocks.WHITE_LOTUS_FLOWER.get().getDefaultState(), ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL), 1, 16);
        }
    }
}