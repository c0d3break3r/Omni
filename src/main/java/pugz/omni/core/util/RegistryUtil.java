package pugz.omni.core.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.StatType;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import pugz.omni.common.world.biome.AbstractBiome;
import pugz.omni.core.Omni;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;
import pugz.omni.core.registry.OmniStructures;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class RegistryUtil {
    public static <B extends Block> RegistryObject<B> createBlock(String name, Supplier<? extends B> supplier, @Nullable ItemGroup group) {
        RegistryObject<B> block = Omni.Registries.BLOCKS.register(name, supplier);
        if (group != null) Omni.Registries.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(group)));
        return block;
    }

    public static <I extends Item> RegistryObject<I> createItem(String name, Supplier<? extends I> supplier) {
        return Omni.Registries.ITEMS.register(name, supplier);
    }

    public static <I extends Item> RegistryObject<I> createOverrideItem(String name, Supplier<? extends I> supplier) {
        return Omni.OverrideRegistries.ITEMS.register(name, supplier);
    }

    public static <T extends TileEntity> RegistryObject<TileEntityType<T>> createTileEntity(String name, Supplier<? extends T> supplier, Supplier<Block[]> blocks) {
        return Omni.Registries.TILE_ENTITIES.register(name, () -> new TileEntityType<>(supplier, Sets.newHashSet(blocks.get()), null));
    }

    public static <E extends Entity> RegistryObject<EntityType<E>> createEntity(String name, Supplier<EntityType<E>> supplier) {
        return Omni.Registries.ENTITIES.register(name, supplier);
    }

    public static Biome createBiome(AbstractBiome biome) {
        Omni.Registries.BIOMES.register(biome.getName(), biome::getBiome);
        return biome.getBiome();
    }

    public static <S extends SurfaceBuilder<?>> RegistryObject<S> createSurfaceBuilder(String name, Supplier<? extends S> surfaceBuilder) {
        return Omni.Registries.SURFACE_BUILDERS.register(name, surfaceBuilder);
    }

    public static ConfiguredSurfaceBuilder<SurfaceBuilderConfig> createConfiguredSurfaceBuilder(String name, ConfiguredSurfaceBuilder<SurfaceBuilderConfig> surfaceBuilder) {
        return Registry.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, new ResourceLocation(Omni.MOD_ID, name), surfaceBuilder);
    }

    public static <F extends Feature<?>> RegistryObject<F> createFeature(String name, Supplier<? extends F> supplier) {
        return Omni.Registries.FEATURES.register(name, supplier);
    }

    public static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> createConfiguredFeature(String name, ConfiguredFeature<FC, ?> feature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Omni.MOD_ID, name), feature);
    }

    public static <F extends Structure<?>> RegistryObject<F> createStructure(String name, Supplier<? extends F> supplier) {
        return Omni.Registries.STRUCTURES.register(name, supplier);
    }

    public static <FC extends IFeatureConfig> StructureFeature<FC, ?> createStructureFeature(String name, StructureFeature<FC, ?> feature) {
        FlatGenerationSettings.STRUCTURES.put(OmniStructures.GHOST_TOWN.get(), feature);
        return Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(Omni.MOD_ID, name), feature);
    }

    public static <C extends WorldCarver<?>> RegistryObject<C> createCarver(String name, Supplier<? extends C> supplier) {
        return Omni.Registries.CARVERS.register(name, supplier);
    }

    public static <E extends Enchantment> RegistryObject<E> createEnchantment(String name, Supplier<? extends E> supplier) {
        return Omni.Registries.ENCHANTMENTS.register(name, supplier);
    }

    public static RegistryObject<SoundEvent> createSoundEvent(String name) {
        return Omni.Registries.SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(Omni.MOD_ID, name)));
    }

    public static RegistryObject<BasicParticleType> createParticle(String name) {
        return Omni.Registries.PARTICLES.register(name, () -> new BasicParticleType(false));
    }

    public static RegistryObject<StatType<?>> createStatType(String name, IStatFormatter formatter) {
        //Stats.CUSTOM.get(new ResourceLocation(Omni.MOD_ID, name), formatter);
        return Omni.Registries.STATS.register(name, () -> new StatType<>(Registry.CUSTOM_STAT));
    }
}