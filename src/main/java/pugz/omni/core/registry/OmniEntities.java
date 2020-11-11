package pugz.omni.core.registry;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import pugz.omni.common.entity.paradise.SeahorseEntity;
import pugz.omni.common.entity.paradise.SeahorseEntity1;
import pugz.omni.common.entity.colormatic.FallingConcretePowderEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import pugz.omni.core.Omni;

public class OmniEntities {
    //colormatic
    public static RegistryObject<EntityType<FallingConcretePowderEntity>> FALLING_CONCRETE_POWDER;

    //paradise
    public static RegistryObject<EntityType<SeahorseEntity>> SEAHORSE;

    public static EntityType<FallingConcretePowderEntity> createFallingBlockEntity() {
        return EntityType.Builder.<FallingConcretePowderEntity>create(FallingConcretePowderEntity::new, EntityClassification.MISC)
                .size(0.98F, 0.001F)
                .setTrackingRange(160)
                .setUpdateInterval(20)
                .setShouldReceiveVelocityUpdates(true)
                .setCustomClientFactory((spawnEntity, world) -> new FallingConcretePowderEntity(FALLING_CONCRETE_POWDER.get(), world))
                .build("falling_concrete_powder");
    }

    public static  <E extends LivingEntity> EntityType<E> createLivingEntity(EntityType.IFactory<E> factory, EntityClassification entityClassification, String name, float width, float height) {
        return EntityType.Builder.create(factory, entityClassification)
                .size(width, height)
                .setTrackingRange(64)
                .setShouldReceiveVelocityUpdates(true)
                .setUpdateInterval(3)
                .build(new ResourceLocation(Omni.MOD_ID, name).toString());
    }
}