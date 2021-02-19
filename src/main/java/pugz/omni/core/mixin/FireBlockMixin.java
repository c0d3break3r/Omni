package pugz.omni.core.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FireBlock.class)
public class FireBlockMixin {
    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/server/ServerWorld;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"
            )
    )
    private boolean removeBlock(ServerWorld world, BlockPos pos, boolean isMoving) {
        world.setBlockState(pos, Blocks.COAL_BLOCK.getDefaultState(), 3);
        return false;
    }
}