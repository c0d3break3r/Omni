package pugz.omni.core.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.material.Material;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pugz.omni.core.registry.OmniBlocks;

@Mixin(CactusBlock.class)
public final class CactusBlockMixin {
    @Inject(
            at = @At(value = "HEAD"),
            method = "isValidPosition",
            cancellable = true
    )
    private void isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState blockstate = worldIn.getBlockState(pos.offset(direction));
            Material material = blockstate.getMaterial();
            if (material.isSolid() || worldIn.getFluidState(pos.offset(direction)).isTagged(FluidTags.LAVA)) {
                info.setReturnValue(blockstate.getBlock() == OmniBlocks.SAGUARO_CACTUS.get());
            }
        }

        BlockState down = worldIn.getBlockState(pos.down());
        info.setReturnValue((down.isIn(Blocks.CACTUS) || down.isIn(Blocks.SAND) || down.isIn(Blocks.RED_SAND)) && !worldIn.getBlockState(pos.up()).getMaterial().isLiquid());
    }
}