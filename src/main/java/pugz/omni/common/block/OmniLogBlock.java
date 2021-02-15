package pugz.omni.common.block;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import pugz.omni.core.util.IBaseBlock;

import javax.annotation.Nonnull;

public class OmniLogBlock extends RotatedPillarBlock implements IBaseBlock {
    private final RotatedPillarBlock stripped;

    public OmniLogBlock(RotatedPillarBlock stripped) {
        super(AbstractBlock.Properties.from(Blocks.OAK_LOG));
        this.stripped = stripped;
    }

    @Override
    public int getFireFlammability() {
        return 5;
    }

    @Override
    public int getFireEncouragement() {
        return 5;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack held = player.getHeldItem(handIn);

        if (held.getItem() instanceof AxeItem) {
            worldIn.setBlockState(pos, stripped.getDefaultState().with(RotatedPillarBlock.AXIS, state.get(RotatedPillarBlock.AXIS)), 2);

            held.damageItem(1, player, (p) -> {
                p.sendBreakAnimation(handIn);
            });

            return ActionResultType.func_233537_a_(worldIn.isRemote);
        }

        return ActionResultType.PASS;
    }
}