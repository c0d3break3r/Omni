package pugz.omni.common.item;

import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import pugz.omni.core.util.dispenser.SpawnEggDispenseBehavior;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class OmniSpawnEggItem extends SpawnEggItem {
    private final Supplier<EntityType<?>> entityType;
    private final int primaryColor;
    private final int secondaryColor;

    public OmniSpawnEggItem(Supplier<EntityType<?>> entityType, int primaryColor, int secondaryColor, Properties properties) {
        super(null, primaryColor, secondaryColor, properties);
        this.entityType = entityType;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        DispenserBlock.registerDispenseBehavior(this, new SpawnEggDispenseBehavior());
    }

    @OnlyIn(Dist.CLIENT)
    public int getColor(int tintIndex) {
        return tintIndex == 0 ? this.primaryColor : this.secondaryColor;
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