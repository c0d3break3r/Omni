package pugz.omni.core.registry;

import net.minecraft.entity.LivingEntity;
import pugz.omni.common.entity.paradise.SeahorseEntity;
import pugz.omni.common.entity.colormatic.FallingConcretePowderEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;

public class OmniEntities {
    //colormatic
    public static RegistryObject<EntityType<FallingConcretePowderEntity>> FALLING_CONCRETE_POWDER;

    //paradise
    public static RegistryObject<EntityType<SeahorseEntity>> SEAHORSE;

    public static EntityType<FallingConcretePowderEntity> createFallingBlockEntity() {
        return EntityType.Builder.<FallingConcretePowderEntity>create(EntityClassification.MISC)
                .size(0.98F, 0.001F)
                .setTrackingRange(160)
                .setUpdateInterval(20)
                .setShouldReceiveVelocityUpdates(true)
                .setCustomClientFactory((spawnEntity, world) -> new FallingConcretePowderEntity(world))
                .build("falling_concrete_powder");
    }

    public static <E extends LivingEntity> EntityType<E> createLivingEntity(String name, EntityClassification classification, int width, int height) {
        return EntityType.Builder.<E>create(classification)
                .size(width, height)
                .setTrackingRange(64)
                .setShouldReceiveVelocityUpdates(true)
                .setUpdateInterval(3)
                .build(name);
    }
}