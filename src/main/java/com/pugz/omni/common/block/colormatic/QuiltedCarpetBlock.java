package com.pugz.omni.common.block.colormatic;

import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import java.util.Map;

public class QuiltedCarpetBlock extends Block {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    public static final BooleanProperty NORTH;
    public static final BooleanProperty EAST;
    public static final BooleanProperty SOUTH;
    public static final BooleanProperty WEST;
    protected static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP;

    public QuiltedCarpetBlock() {
        super(AbstractBlock.Properties.from(Blocks.BLUE_CARPET));
        setDefaultState(stateContainer.getBaseState().with(NORTH, false).with(SOUTH, false).with(EAST, false).with(WEST, false));
    }

    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    public BlockState updatePostPlacement(BlockState state, Direction direction, BlockState state2, IWorld world, BlockPos pos, BlockPos pos2) {
        if (!state.isValidPosition(world, pos)) return Blocks.AIR.getDefaultState();
        return direction.getAxis().getPlane() == Direction.Plane.HORIZONTAL ? (BlockState)state.with((Property)FACING_TO_PROPERTY_MAP.get(direction), canConnect(state, state2)) : super.updatePostPlacement(state, direction, state2, world, pos, pos2);
    }

    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        return !world.isAirBlock(pos.down());
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        switch(rotation) {
            case CLOCKWISE_180:
                return (BlockState)((BlockState)((BlockState)((BlockState)state.with(NORTH, state.get(SOUTH))).with(EAST, state.get(WEST))).with(SOUTH, state.get(NORTH))).with(WEST, state.get(EAST));
            case COUNTERCLOCKWISE_90:
                return (BlockState)((BlockState)((BlockState)((BlockState)state.with(NORTH, state.get(EAST))).with(EAST, state.get(SOUTH))).with(SOUTH, state.get(WEST))).with(WEST, state.get(NORTH));
            case CLOCKWISE_90:
                return (BlockState)((BlockState)((BlockState)((BlockState)state.with(NORTH, state.get(WEST))).with(EAST, state.get(NORTH))).with(SOUTH, state.get(EAST))).with(WEST, state.get(SOUTH));
            default:
                return state;
        }
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        switch(mirror) {
            case LEFT_RIGHT:
                return (BlockState)((BlockState)state.with(NORTH, state.get(SOUTH))).with(SOUTH, state.get(NORTH));
            case FRONT_BACK:
                return (BlockState)((BlockState)state.with(EAST, state.get(WEST))).with(WEST, state.get(EAST));
            default:
                return super.mirror(state, mirror);
        }
    }

    public boolean canConnect(BlockState state, BlockState state2) {
        Block block = state.getBlock();
        return !cannotAttach(block) && block instanceof QuiltedCarpetBlock && block == state2.getBlock();
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IBlockReader world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = world.getBlockState(pos);
        BlockPos north = pos.north();
        BlockPos east = pos.east();
        BlockPos south = pos.south();
        BlockPos west = pos.west();
        BlockState lvt_9_1_ = world.getBlockState(north);
        BlockState lvt_10_1_ = world.getBlockState(east);
        BlockState lvt_11_1_ = world.getBlockState(south);
        BlockState lvt_12_1_ = world.getBlockState(west);
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)super.getStateForPlacement(context).with(NORTH, canConnect(state, lvt_9_1_))).with(EAST, canConnect(state, lvt_10_1_))).with(SOUTH, canConnect(state, lvt_11_1_))).with(WEST, canConnect(state, lvt_12_1_)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST);
    }

    static {
        NORTH = SixWayBlock.NORTH;
        EAST = SixWayBlock.EAST;
        SOUTH = SixWayBlock.SOUTH;
        WEST = SixWayBlock.WEST;
        FACING_TO_PROPERTY_MAP = (Map)SixWayBlock.FACING_TO_PROPERTY_MAP.entrySet().stream().filter((directions) -> {
            return ((Direction)directions.getKey()).getAxis().isHorizontal();
        }).collect(Util.toMapCollector());
    }
}