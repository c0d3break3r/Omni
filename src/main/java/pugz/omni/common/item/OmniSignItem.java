package pugz.omni.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pugz.omni.common.tileentity.OmniSignTileEntity;

import javax.annotation.Nullable;

public class OmniSignItem extends WallOrFloorItem {
    public OmniSignItem(Item.Properties propertiesIn, Block floorBlockIn, Block wallBlockIn) {
        super(floorBlockIn, wallBlockIn, propertiesIn);
    }

    protected boolean onBlockPlaced(BlockPos pos, World worldIn, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        boolean flag = super.onBlockPlaced(pos, worldIn, player, stack, state);
        if (!worldIn.isRemote && !flag && player != null) {
            player.openSignEditor((OmniSignTileEntity)worldIn.getTileEntity(pos));
        }

        return flag;
    }
}