package pugz.omni.common.entity.paradise;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.world.World;
import pugz.omni.core.registry.OmniEntities;

public class SeahorseEntity extends WaterMobEntity {
    public SeahorseEntity(EntityType<? extends SeahorseEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public SeahorseEntity(World world, double posX, double posY, double posZ) {
        this(OmniEntities.SEAHORSE.get(), world);
        this.setPosition(posX, posY, posZ);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 8.0D);
    }
}