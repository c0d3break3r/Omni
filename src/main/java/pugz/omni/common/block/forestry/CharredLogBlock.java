package pugz.omni.common.block.forestry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import pugz.omni.core.base.IBaseBlock;

public class CharredLogBlock extends RotatedPillarBlock implements IBaseBlock {
    public CharredLogBlock() {
        super(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(1.5F, 0.5F).sound(SoundType.CLOTH));
    }

    @SuppressWarnings("deprecation")
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return !worldIn.getBlockState(pos.down()).isAir();
    }
}