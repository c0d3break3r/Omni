package com.pugz.omni.core.registry;

import com.pugz.omni.common.entity.colormatic.FallingConcretePowderEntity;
import com.pugz.omni.core.Omni;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class OmniEntities {
    //colormatic
    public static RegistryObject<EntityType<FallingConcretePowderEntity>> FALLING_CONCRETE_POWDER;

    public static <T extends FallingConcretePowderEntity> EntityType<T> createFallingBlockEntity(EntityType.IFactory<T> factory) {
        ResourceLocation location = new ResourceLocation(Omni.MOD_ID, "falling_concrete_powder");
        return EntityType.Builder.create(factory, EntityClassification.MISC).size(0.98F, 0.98F).trackingRange(10).func_233608_b_(20).build(location.toString());
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(OmniEntities.FALLING_CONCRETE_POWDER.get(), FallingBlockRenderer::new);
    }
}