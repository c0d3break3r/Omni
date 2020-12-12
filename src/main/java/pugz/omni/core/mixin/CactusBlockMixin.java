package pugz.omni.core.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.material.Material;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.IPlantable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import pugz.omni.core.registry.OmniBlocks;

@Mixin(CactusBlock.class)
public final class CactusBlockMixin implements IPlantable {
    @Redirect(at = @At(value = "INVOKE", ordinal = 3), method = "isValidPosition")
    private boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState blockstate = worldIn.getBlockState(pos.offset(direction));
            Material material = blockstate.getMaterial();
            if (material.isSolid() || worldIn.getFluidState(pos.offset(direction)).isTagged(FluidTags.LAVA)) {
                return blockstate.getBlock() != OmniBlocks.SAGUARO_CACTUS.get();
            }
        }

        BlockState blockstate1 = worldIn.getBlockState(pos.down());
        return (blockstate1.isIn(Blocks.CACTUS) || blockstate1.isIn(Blocks.SAND) || blockstate1.isIn(Blocks.RED_SAND)) && !worldIn.getBlockState(pos.up()).getMaterial().isLiquid();
    }

    @Shadow
    @Override
    public BlockState getPlant(IBlockReader world, BlockPos pos) {
        return null;
    }
}