package pugz.omni.common.block.colormatic;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;
import pugz.omni.core.base.IBaseBlock;

public class QuiltedWoolBlock extends Block implements IBaseBlock {
    public QuiltedWoolBlock(DyeColor color) {
        super(AbstractBlock.Properties.create(Material.WOOL, color).hardnessAndResistance(0.8F).sound(SoundType.CLOTH));
    }

    @Override
    public int getFireEncouragement() {
        return 30;
    }

    @Override
    public int getFireFlammability() {
        return 60;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        items.forEach(stack -> {
            if (stack.getItem() == Items.BLACK_WOOL) items.add(new ItemStack(this));
        });
    }
}