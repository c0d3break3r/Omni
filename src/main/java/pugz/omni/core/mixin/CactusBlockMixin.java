package pugz.omni.core.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.CactusBlock;
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
    @Inject(at = @At(value = "HEAD"), method = "isValidPosition")
    private void isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            info.setReturnValue(worldIn.getBlockState(pos.offset(direction)).getBlock() != OmniBlocks.SAGUARO_CACTUS.get());
        }
    }
}