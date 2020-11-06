package pugz.omni.core.module;

import net.minecraft.block.Block;
import pugz.omni.common.block.cavier_caves.MalachiteBudBlock;
import pugz.omni.common.block.cavier_caves.SpeleothemBlock;
import pugz.omni.common.world.feature.cavier_caves.SpeleothemFeature;
import pugz.omni.common.world.feature.cavier_caves.SpeleothemFeatureConfig;
import pugz.omni.core.registry.OmniBlocks;
import pugz.omni.core.registry.OmniFeatures;
import pugz.omni.core.util.BiomeFeatures;
import pugz.omni.core.util.RegistryUtil;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class CavierCavesModule extends AbstractModule {
    public static final CavierCavesModule instance = new CavierCavesModule();

    public CavierCavesModule() {
        super("Cavier Caves");
    }

    @Override
    protected void sendInitMessage() {
        System.out.println("Explored the Depths of the Cavier Caves!");
    }

    @Override
    protected void onInitialize() {
        MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoading);
    }

    @Override
    protected void registerBlocks() {
        //RegistryObject<Block> SALT_ORE;
        //RegistryObject<Block> SALT_CRYSTAL;
        //RegistryObject<Block> SALT_BLOCK;

        //RegistryObject<Block> SULFUR_BLOCK;

        //RegistryObject<Block> EMBERTINE_ORE;
        //RegistryObject<Block> EMBERTINE_BLOCK;
        //RegistryObject<Block> EMBERTINE_BRICKS;

        //RegistryObject<Block> PETRIFIED_PLANKS;

        //RegistryObject<Block> CAVE_PAINTING;

        //RegistryObject<Block> CAVE_MUSHROOM;
        //RegistryObject<Block> RED_SHELF_MUSHROOM;
        //RegistryObject<Block> BROWN_SHELF_MUSHROOM;
        //RegistryObject<Block> CAVE_SHELF_MUSHROOM;
        //RegistryObject<Block> SHELF_GLOWSHROOM;

        //RegistryObject<Block> WEB_BLOCK;
        //RegistryObject<Block> SPIDER_SAC;

        //RegistryObject<Block> WEAK_STONE;

        //RegistryObject<Block> SLIME;

        //RegistryObject<Block> ROPE;

        //RegistryObject<Block> THIN_ICE;
        //RegistryObject<Block> PERMAFROST;

        //RegistryObject<Block> STONE_SIGN;
        //RegistryObject<Block> BLACKSTONE_SIGN;

        //RegistryObject<Block> GOBLIN_TRAP;

        //RegistryObject<Block> FOSSIL_ORE;

        //RegistryObject<Block> STICKY_RAIL;

        OmniBlocks.STONE_SPELEOTHEM = RegistryUtil.createBlock("stone_speleothem", () -> new SpeleothemBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).setRequiresTool().hardnessAndResistance(1.25F, 4.5F)), ItemGroup.DECORATIONS);
        OmniBlocks.ICE_SPELEOTHEM = RegistryUtil.createBlock("ice_speleothem", () -> new SpeleothemBlock(AbstractBlock.Properties.create(Material.PACKED_ICE).slipperiness(0.98F).setRequiresTool().hardnessAndResistance(0.4F)), ItemGroup.DECORATIONS);
        OmniBlocks.NETHERRACK_SPELEOTHEM = RegistryUtil.createBlock("netherrack_speleothem", () -> new SpeleothemBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.NETHERRACK).setRequiresTool().hardnessAndResistance(0.3F)), ItemGroup.DECORATIONS);

        //RegistryObject<Block> SPAWNER_STONE;

        OmniBlocks.MALACHITE_BLOCK = RegistryUtil.createBlock("malachite_block", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(4.5F, 10.0F)), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.BUDDING_MALACHITE = RegistryUtil.createBlock("budding_malachite", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(4.5F, 10.0F)), ItemGroup.BUILDING_BLOCKS);
        OmniBlocks.MALACHITE_CLUSTER = RegistryUtil.createBlock("malachite_cluster", () -> new MalachiteBudBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(4.5F, 9.5F)), ItemGroup.DECORATIONS);
        OmniBlocks.LARGE_MALACHITE_BUD = RegistryUtil.createBlock("large_malachite_bud", () -> new MalachiteBudBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(4.0F, 9.0F)), ItemGroup.DECORATIONS);
        OmniBlocks.MEDIUM_MALACHITE_BUD = RegistryUtil.createBlock("medium_malachite_bud", () -> new MalachiteBudBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.5F, 8.5F)), ItemGroup.DECORATIONS);
        OmniBlocks.SMALL_MALACHITE_BUD = RegistryUtil.createBlock("small_malachite_bud", () -> new MalachiteBudBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 8.0F)), ItemGroup.DECORATIONS);
    }

    @Override
    protected void registerItems() {
        //RegistryObject<Item> EMBERTINE;
        //RegistryObject<Item> SERPENTINE;
        //RegistryObject<Item> AGATE;

        //RegistryObject<Item> SULFUR_DUST;
        //RegistryObject<Item> SALT_PILE;

        //RegistryObject<Item> CRYSTAL_MELON;

        //RegistryObject<Item> SPAWNER_FRAGMENT;
    }

    @Override
    protected void registerTileEntities() {
        //RegistryObject<TileEntityType<?>> ROPE;
    }

    @Override
    protected void registerEntities() {
        //RegistryObject<EntityType<?>> GOBLIN;
        //RegistryObject<EntityType<?>> SPIDERLING;
    }

    @Override
    protected void registerFeatures() {
        //RegistryObject<Feature<?>> CEILING_ORE;
        //RegistryObject<Feature<?>> EXPOSED_ORE;
        //RegistryObject<Feature<?>> FLOORED_ORE;
        //RegistryObject<Feature<?>> CAVE_FLOWER;

        OmniFeatures.SPELEOTHEM = RegistryUtil.createFeature("speleothem", () -> new SpeleothemFeature(SpeleothemFeatureConfig.codec));
        //RegistryObject<Feature<?>> SLIME;
        //RegistryObject<Feature<?>> CAVE_CARVING;
        //RegistryObject<Feature<?>> GEODE;
        //RegistryObject<Feature<?>> THIN_ICE;
        //RegistryObject<Feature<?>> SPIDER_NEST;
        //RegistryObject<Feature<?>> WALL_MUSHROOM;
        //RegistryObject<Feature<?>> SULFUR;

        //RegistryObject<Feature<?>> PETRIFIED_WOOD_REPLACEMENT;
    }

    @Override
    protected void registerStructures() {
        //RegistryObject<Structure<?>> GOBLIN_FORT;
    }

    @Override
    protected void registerEffects() {
        //RegistryObject<Effect> DEAFENED;
    }

    @Override
    protected void registerParticles() {
        //RegistryObject<ParticleType<?>> SULFUR;
    }

    @Override
    protected void registerStats() {
    }

    public void onBiomeLoading(BiomeLoadingEvent event) {
        Biome.Category category = event.getCategory();
        BiomeGenerationSettingsBuilder gen = event.getGeneration();

        if (category != Biome.Category.NETHER && category != Biome.Category.THEEND) {
            BiomeFeatures.addSpeleothems(gen, SpeleothemFeatureConfig.Variant.STONE, 0.004F);
        }
        if (category == Biome.Category.ICY) {
            BiomeFeatures.addSpeleothems(gen, SpeleothemFeatureConfig.Variant.ICE, 0.006F);
        }
        if (category == Biome.Category.NETHER) {
            BiomeFeatures.addSpeleothems(gen, SpeleothemFeatureConfig.Variant.NETHERRACK, 0.008F);
        }
    }
}