package pugz.omni.common.item;

import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import pugz.omni.core.util.dispenser.SpawnEggDispenseBehavior;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class OmniSpawnEggItem extends SpawnEggItem {
    private final Supplier<EntityType<?>> entityType;

    public OmniSpawnEggItem(Supplier<EntityType<?>> entityType, int primaryColor, int secondaryColor, Properties properties) {
        super(null, primaryColor, secondaryColor, properties);
        this.entityType = entityType;
        DispenserBlock.registerDispenseBehavior(this, new SpawnEggDispenseBehavior());
    }

    @Nonnull
    @Override
    public EntityType<?> getType(CompoundNBT compound) {
        if (compound != null && compound.contains("EntityTag", 10)) {
            CompoundNBT entityTag = compound.getCompound("EntityTag");

            if (entityTag.contains("id", 8)) {
                return EntityType.byKey(entityTag.getString("id")).orElse(this.entityType.get());
            }
        }
        return this.entityType.get();
    }
}