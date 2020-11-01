package com.pugz.omni.core.util;

import com.google.common.collect.Sets;
import com.pugz.omni.core.Omni;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class RegistryUtil {
    public static <B extends Block> RegistryObject<B> createBlock(String name, Supplier<? extends B> supplier, @Nullable ItemGroup group) {
        RegistryObject<B> block = Omni.Registries.BLOCKS.register(name, supplier);
        if (group != null) Omni.Registries.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(group)));
        return block;
    }

    public static <B extends Block> RegistryObject<B> createOverrideBlock(String name, Supplier<? extends B> supplier, @Nullable ItemGroup group) {
        RegistryObject<B> block = Omni.OverrideRegistries.BLOCKS.register(name, supplier);
        if (group != null) Omni.OverrideRegistries.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(group)));
        return block;
    }

    public static <I extends Item> RegistryObject<I> createItem(String name, Supplier<? extends I> supplier) {
        return Omni.Registries.ITEMS.register(name, supplier);
    }

    public static <I extends Item> RegistryObject<I> createOverrideItem(String name, Supplier<? extends I> supplier, @Nullable ItemGroup group) {
        RegistryObject<I> item = Omni.OverrideRegistries.ITEMS.register(name, supplier);
        return item;
    }

    public static <T extends TileEntity> RegistryObject<TileEntityType<T>> createTileEntity(String name, Supplier<? extends T> supplier, Supplier<Block[]> blocks) {
        return Omni.Registries.TILE_ENTITIES.register(name, () -> new TileEntityType<>(supplier, Sets.newHashSet(blocks.get()), null));
    }

    public static <E extends Entity> RegistryObject<EntityType<E>> createEntity(String name, Supplier<EntityType<E>> supplier) {
        return Omni.Registries.ENTITIES.register(name, supplier);
    }

    public static RegistryKey<Biome> createBiome(String name, Biome biome, BiomeManager.BiomeType type, int weight, BiomeDictionary.Type... types) {
        RegistryKey<Biome> key = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, new ResourceLocation(Omni.MOD_ID, name));
        BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(key, weight));
        BiomeDictionary.addTypes(key, types);
        Omni.Registries.BIOMES.register(name, () -> biome);
        return key;
    }

    public static <F extends Feature<?>> RegistryObject<F> createFeature(String name, Supplier<? extends F> supplier) {
        return Omni.Registries.FEATURES.register(name, supplier);
    }
}