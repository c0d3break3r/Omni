package pugz.omni.common.block.colormatic;

import net.minecraft.client.renderer.RenderType;
import pugz.omni.common.block.AbstractStackableBlock;
import net.minecraft.block.*;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import pugz.omni.core.util.IBaseBlock;

import javax.annotation.Nonnull;

public class FlowersBlock extends AbstractStackableBlock implements IBaseBlock {
    private static final VoxelShape MULTI_FLOWER_SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D);
    public static final IntegerProperty FLOWERS = IntegerProperty.create("flowers", 1, 4);
    private final Block base;

    public FlowersBlock(AbstractBlock.Properties properties, Block base) {
        super(properties);
        this.base = base;
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
    public VoxelShape getStackedShape() {
        return MULTI_FLOWER_SHAPE;
    }

    @Override
    public Block getBase() {
        return base;
    }

    @Override
    public Block getBlock() {
        return this;
    }

    @Override
    public Property<Integer> getCountProperty() {
        return FLOWERS;
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Vector3d vector3d = state.getOffset(worldIn, pos);
        return state.get(FLOWERS) == 2 ? base.getShape(state, worldIn, pos, context) : MULTI_FLOWER_SHAPE.withOffset(vector3d.x, vector3d.y, vector3d.z);
    }

    @Nonnull
    public AbstractBlock.OffsetType getOffsetType() {
        return base.getOffsetType();
    }
}