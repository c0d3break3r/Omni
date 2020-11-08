package pugz.omni.core.registry;

import pugz.omni.client.render.FallingConcretePowderRenderer;
import pugz.omni.common.entity.colormatic.FallingConcretePowderEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class OmniEntities {
    //colormatic
    public static RegistryObject<EntityType<FallingConcretePowderEntity>> FALLING_CONCRETE_POWDER;

    public static EntityType<FallingConcretePowderEntity> createFallingBlockEntity() {
        return EntityType.Builder.<FallingConcretePowderEntity>create(EntityClassification.MISC)
                .size(0.98F, 0.001F)
                .setTrackingRange(160)
                .setUpdateInterval(20)
                .setShouldReceiveVelocityUpdates(true)
                .setCustomClientFactory((spawnEntity, world) -> new FallingConcretePowderEntity(world))
                .build("falling_concrete_powder");
    }

    public static void registerEntityRenders() {
    }
}