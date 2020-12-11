package pugz.omni.core.mixin;

import net.minecraft.block.FireBlock;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FireBlock.class)
public class FireBlockMixin {
    //@Redirect(method = "tick",
    //                at = @At(value = "INVOKE",
    //                target = "Lnet/minecraft/world/server/ServerWorld;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"))
    //private boolean removeBlock(BlockPos pos, boolean isMoving) {
    //    return false;
    //}
}