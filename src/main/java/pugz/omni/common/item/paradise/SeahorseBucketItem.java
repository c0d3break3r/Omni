package pugz.omni.common.item.paradise;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.fish.TropicalFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import pugz.omni.common.entity.paradise.SeahorseEntity;
import pugz.omni.core.registry.OmniEntities;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class SeahorseBucketItem extends BucketItem {
    private final Supplier<EntityType<SeahorseEntity>> seahorse = OmniEntities.SEAHORSE;

    public SeahorseBucketItem() {
        super(() -> Fluids.WATER, new Item.Properties().group(ItemGroup.MISC));
    }

    public void onLiquidPlaced(World world, ItemStack stack, BlockPos pos) {
        if (world instanceof ServerWorld) this.placeEntity((ServerWorld) world, stack, pos);
    }

    protected void playEmptySound(PlayerEntity player, IWorld worldIn, BlockPos pos) {
        worldIn.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY_FISH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
    }

    protected void placeEntity(ServerWorld world, ItemStack stack, BlockPos pos) {
        Entity entity = this.seahorse.get().spawn(world, stack, null, pos, SpawnReason.BUCKET, true, false);

        if (entity instanceof SeahorseEntity) {
            SeahorseEntity seahorse = (SeahorseEntity)entity;
            CompoundNBT nbt = stack.getTag();

            int coralType = world.getRandom().nextInt(SeahorseEntity.CoralType.values().length);
            int size = world.getRandom().nextInt(8);
            if (nbt != null) {
                if (nbt.contains("SeahorseVariantTag", 3)) coralType = nbt.getInt("SeahorseVariantTag");
                if (nbt.contains("SeahorseSizeTag", 4)) size = nbt.getInt("SeahorseSizeTag");
            }

            seahorse.setVariantType(SeahorseEntity.CoralType.getTypeByIndex(coralType));
            seahorse.setSeahorseSize(size);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        CompoundNBT compoundnbt = stack.getTag();
        if (compoundnbt != null && compoundnbt.contains("SeahorseVariantTag", 3)) {
            int i = compoundnbt.getInt("SeahorseVariantTag");
            TextFormatting[] atextformatting = new TextFormatting[]{TextFormatting.ITALIC, TextFormatting.GRAY};
            String s = "coral_type.omni." + SeahorseEntity.getCoralTypeName(i);
            IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent(s);
            iformattabletextcomponent.mergeStyle(atextformatting);
            tooltip.add(iformattabletextcomponent);
        }
    }
}