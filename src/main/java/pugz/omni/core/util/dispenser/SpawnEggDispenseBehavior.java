package pugz.omni.core.util.dispenser;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Direction;

import javax.annotation.Nonnull;

public class SpawnEggDispenseBehavior extends DefaultDispenseItemBehavior {
    @Nonnull
    @Override
    public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
        Direction direction = source.getBlockState().get(DispenserBlock.FACING);
        EntityType<?> type = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
        type.spawn(source.getWorld(), stack, (PlayerEntity) null, source.getBlockPos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
        stack.shrink(1);
        return stack;
    }
}