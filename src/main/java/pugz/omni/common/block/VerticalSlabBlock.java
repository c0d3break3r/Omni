package pugz.omni.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import pugz.omni.core.base.IBaseBlock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class VerticalSlabBlock extends Block implements IWaterLoggable, IBaseBlock {
    public static final EnumProperty<VerticalSlabType> TYPE = EnumProperty.create("type", VerticalSlabType.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public VerticalSlabBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(TYPE, VerticalSlabType.NORTH).with(WATERLOGGED, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isTransparent(BlockState state) {
        return state.get(TYPE) != VerticalSlabType.DOUBLE;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.get(TYPE).shape;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos blockpos = context.getPos();
        BlockState blockstate = context.getWorld().getBlockState(blockpos);
        if (blockstate.getBlock() == this)
            return blockstate.with(TYPE, VerticalSlabType.DOUBLE).with(WATERLOGGED, false);
        return this.getDefaultState().with(WATERLOGGED, context.getWorld().getFluidState(blockpos).getFluid() == Fluids.WATER).with(TYPE, VerticalSlabType.fromDirection(this.getDirectionForPlacement(context)));
    }

    private Direction getDirectionForPlacement(BlockItemUseContext context) {
        Direction face = context.getFace();
        if (face.getAxis() != Direction.Axis.Y) return face;
        Vector3d difference = context.getHitVec().subtract(Vector3d.copy(context.getPos())).subtract(0.5, 0, 0.5);
        return Direction.fromAngle(-Math.toDegrees(Math.atan2(difference.getX(), difference.getZ()))).getOpposite();
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isReplaceable(BlockState state, @Nonnull BlockItemUseContext context) {
        VerticalSlabType type = state.get(TYPE);
        return type != VerticalSlabType.DOUBLE && context.getItem().getItem() == this.asItem() && context.replacingClickedOnBlock() && (context.getFace() == type.direction && this.getDirectionForPlacement(context) == type.direction);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : Fluids.EMPTY.getDefaultState();
    }

    @Override
    public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        return state.get(TYPE) != VerticalSlabType.DOUBLE && IWaterLoggable.super.receiveFluid(worldIn, pos, state, fluidStateIn);
    }

    @Override
    public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return state.get(TYPE) != VerticalSlabType.DOUBLE && IWaterLoggable.super.canContainFluid(worldIn, pos, state, fluidIn);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (state.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return state;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return type == PathType.WATER && worldIn.getFluidState(pos).isTagged(FluidTags.WATER);
    }

    public enum VerticalSlabType implements IStringSerializable {
        NORTH(Direction.NORTH),
        SOUTH(Direction.SOUTH),
        WEST(Direction.WEST),
        EAST(Direction.EAST),
        DOUBLE(null);

        private final String name;
        public final Direction direction;
        public final VoxelShape shape;

        VerticalSlabType(@Nullable Direction direction) {
            this.direction = direction;
            this.name = direction == null ? "double" : direction.getString();
            if (direction == null) {
                this.shape = VoxelShapes.fullCube();
            } else {
                boolean isNegativeAxis = direction.getAxisDirection() == Direction.AxisDirection.NEGATIVE;
                double min = isNegativeAxis ? 8 : 0;
                double max = isNegativeAxis ? 16 : 8;
                this.shape = direction.getAxis() == Direction.Axis.X ? Block.makeCuboidShape(min, 0, 0, max, 16, 16) : Block.makeCuboidShape(0, 0, min, 16, 16, max);
            }
        }

        public static VerticalSlabType fromDirection(Direction direction) {
            for (VerticalSlabType type : VerticalSlabType.values()) {
                if (type.direction != null && direction == type.direction) {
                    return type;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Nonnull
        @Override
        public String getString() {
            return this.name;
        }
    }
}