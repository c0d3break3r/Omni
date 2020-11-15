package pugz.omni.common.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import pugz.omni.common.entity.paradise.SeahorseEntity;
import pugz.omni.core.registry.OmniEntities;

import java.util.function.Supplier;

public class SeahorseBucketItem extends BucketItem {
    private final Supplier<EntityType<SeahorseEntity>> seahorse = OmniEntities.SEAHORSE;

    public SeahorseBucketItem() {
        super(() -> Fluids.WATER, new Item.Properties().group(ItemGroup.MISC));
    }

    public void onLiquidPlaced(World world, ItemStack stack, BlockPos pos) {
        if (!world.isRemote) {
            this.placeEntity((ServerWorld) world, stack, pos);
        }
    }

    protected void playEmptySound(PlayerEntity player, IWorld worldIn, BlockPos pos) {
        worldIn.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY_FISH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
    }

    protected void placeEntity(ServerWorld world, ItemStack stack, BlockPos pos) {
        this.seahorse.get().spawn(world, stack, null, pos, SpawnReason.BUCKET, true, false);
    }
}