package com.pugz.omni.core.registry;

import com.pugz.omni.common.entity.colormatic.FallingConcretePowderEntity;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class OmniEntities {
    //colormatic
    public static RegistryObject<EntityType<FallingConcretePowderEntity>> FALLING_CONCRETE_POWDER;

    public static EntityType<FallingConcretePowderEntity> createFallingBlockEntity(EntityType.IFactory<FallingConcretePowderEntity> factory) {
        return EntityType.Builder.<FallingConcretePowderEntity>create(factory, EntityClassification.MISC)
                .size(0.98F, 0.98F)
                .trackingRange(160)
                .setUpdateInterval(20)
                .setShouldReceiveVelocityUpdates(true)
                .setCustomClientFactory((spawnEntity, world) -> new FallingConcretePowderEntity(FALLING_CONCRETE_POWDER.get(), world))
                .build("falling_concrete_powder");
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(FALLING_CONCRETE_POWDER.get(), FallingBlockRenderer::new);
    }
}