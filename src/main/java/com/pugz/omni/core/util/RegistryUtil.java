package com.pugz.omni.core.util;

import com.pugz.omni.core.Omni;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class RegistryUtil {
    public static <B extends Block> RegistryObject<B> createBlock(String name, Supplier<? extends B> supplier, @Nullable ItemGroup group) {
        RegistryObject<B> block = Omni.Registries.BLOCKS.register(name, supplier);
        Omni.Registries.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(group)));
        return block;
    }

    public static <B extends Block> RegistryObject<B> createOverrideBlock(String name, Supplier<? extends B> supplier, @Nullable ItemGroup group) {
        RegistryObject<B> block = Omni.OverrideRegistries.BLOCKS.register(name, supplier);
        Omni.OverrideRegistries.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(group)));
        return block;
    }

    public static <F extends Feature<?>> RegistryObject<F> createFeature(String name, Supplier<? extends F> supplier) {
        RegistryObject<F> block = Omni.Registries.FEATURES.register(name, supplier);
        return block;
    }
}