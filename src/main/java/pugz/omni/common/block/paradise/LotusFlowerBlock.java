package pugz.omni.common.block.paradise;

import net.minecraft.block.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.potion.Effect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import pugz.omni.core.base.IBaseBlock;

import javax.annotation.Nonnull;

public class LotusFlowerBlock extends FlowerBlock implements IBaseBlock {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 4.0D, 15.0D);

    public LotusFlowerBlock(Effect effect, int duration) {
        super(effect, duration, AbstractBlock.Properties.from(Blocks.DANDELION));
    }

    @Override
    public RenderType getRenderType() {
        return RenderType.getCutout();
    }

    @Override
    public int getFireEncouragement() {
        return 60;
    }

    @Override
    public int getFireFlammability() {
        return 100;
    }

    @Override
    public float getCompostChance() {
        return 0.65F;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    public AbstractBlock.OffsetType getOffsetType() {
        return OffsetType.NONE;
    }
}