package pugz.omni.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import pugz.omni.common.tileentity.OmniSignTileEntity;

import javax.annotation.Nonnull;

public class OmniAbstractSignBlock extends AbstractSignBlock {
    public OmniAbstractSignBlock(AbstractBlock.Properties properties) {
        super(properties, null);
    }

    @Nonnull
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemstack = player.getHeldItem(handIn);
        boolean flag = itemstack.getItem() instanceof DyeItem && player.abilities.allowEdit;
        if (worldIn.isRemote) {
            return flag ? ActionResultType.SUCCESS : ActionResultType.CONSUME;
        } else {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof OmniSignTileEntity) {
                OmniSignTileEntity signtileentity = (OmniSignTileEntity) tileentity;
                if (flag) {
                    boolean flag1 = signtileentity.setTextColor(((DyeItem)itemstack.getItem()).getDyeColor());
                    if (flag1 && !player.isCreative()) {
                        itemstack.shrink(1);
                    }
                }

                return signtileentity.executeCommand(player) ? ActionResultType.SUCCESS : ActionResultType.PASS;
            } else {
                return ActionResultType.PASS;
            }
        }
    }

    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new OmniSignTileEntity();
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}