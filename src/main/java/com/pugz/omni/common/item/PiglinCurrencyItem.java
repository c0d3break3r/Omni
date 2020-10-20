package com.pugz.omni.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PiglinCurrencyItem extends Item {

    public PiglinCurrencyItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isPiglinCurrency(ItemStack stack) {
        return true;
    }
}
