package com.pugz.omni.core.registry;

import com.pugz.omni.common.entity.colormatic.FallingConcretePowderEntity;
import com.pugz.omni.core.Omni;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class OmniEntities {
    public static RegistryObject<EntityType<FallingConcretePowderEntity>> FALLING_CONCRETE_POWDER;

    public static <T extends FallingConcretePowderEntity> EntityType<T> createFallingBlockEntity(EntityType.IFactory<T> factory) {
        ResourceLocation location = new ResourceLocation(Omni.MOD_ID, "falling_concrete_powder");
        return EntityType.Builder.create(factory, EntityClassification.MISC).trackingRange(10).func_233608_b_(20).size(0.98F, 0.98F).build(location.toString());
    }
}