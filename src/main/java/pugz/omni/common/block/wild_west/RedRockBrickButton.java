package pugz.omni.common.block.wild_west;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pugz.omni.core.util.IBaseBlock;

import javax.annotation.Nonnull;

public class RedRockBrickButton extends AbstractButtonBlock implements IBaseBlock {
    public RedRockBrickButton() {
        super(false, AbstractBlock.Properties.from(Blocks.STONE_BUTTON));
    }

    @Override
    @Nonnull
    protected SoundEvent getSoundEvent(boolean isOn) {
        return isOn ? SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON : SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF;
    }

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (!worldIn.isRemote && !state.get(POWERED)) {
            this.powerBlock(state, worldIn, pos);
        }
    }
}