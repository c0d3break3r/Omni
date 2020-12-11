package pugz.omni.common.block.wild_west;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import pugz.omni.common.block.HorizontalFacingBlock;
import pugz.omni.core.registry.OmniFeatures;
import pugz.omni.core.util.IBaseBlock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SaguaroCactusBlock extends Block implements IGrowable, IBaseBlock {
    public static final BooleanProperty HORIZONTAL = BooleanProperty.create("horizontal");
    public static final DirectionProperty HORIZONTAL_DIRECTION = HorizontalFacingBlock.HORIZONTAL_FACING;
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;

    public SaguaroCactusBlock() {
        super(AbstractBlock.Properties.from(Blocks.CACTUS));
        this.setDefaultState(this.stateContainer.getBaseState().getBlockState().with(HORIZONTAL, false).with(HORIZONTAL_DIRECTION, Direction.NORTH).with(NORTH, false).with(SOUTH, false).with(EAST, false).with(WEST, false));
    }

    @Override
    public RenderType getRenderType() {
        return RenderType.getCutout();
    }

    @Override
    public float getCompostChance() {
        return 0.5F;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = this.getDefaultState();
        if(context.getFace().getHorizontalIndex() >= 0) state = state.with(HORIZONTAL, true).with(HORIZONTAL_DIRECTION, context.getFace().getOpposite()).with(FACING_PROPERTIES.get(context.getFace().getOpposite()), true);
        return state;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if(!isValidPosition(state, world, currentPos)) {
            world.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
            return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
        }

        if(facing.getAxis().isVertical()) return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);

        if(facingState.getBlock() == this) {
            if(facingState.get(HORIZONTAL) && facingState.get(FACING_PROPERTIES.get(facing.getOpposite()))) return state.with(FACING_PROPERTIES.get(facing), true);
        } else {
            return state.with(FACING_PROPERTIES.get(facing), false);
        }
        return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (!state.isValidPosition(worldIn, pos)) {
            worldIn.removeBlock(pos, true);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        if(state.get(HORIZONTAL)) {
            Direction offset = state.get(HORIZONTAL_DIRECTION);
            if(!world.getBlockState(pos.offset(offset)).isIn(this)) return false;
        } else {
            BlockState checkState = world.getBlockState(pos.down());
            return (checkState.isIn(this) || checkState.isIn(Blocks.SAND) || checkState.isIn(Blocks.RED_SAND)) && !world.getBlockState(pos.up()).getMaterial().isLiquid();
        }

        return super.isValidPosition(state, world, pos);
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.isIn(Blocks.SAND) || state.isIn(Blocks.RED_SAND);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return shapes.get(state);
    }

    private final HashMap<BlockState, VoxelShape> shapes = Util.make(Maps.newHashMap(), m -> getStateContainer().getValidStates().forEach(state -> m.put(state, getShapeForState(state))));

    private VoxelShape getShapeForState(BlockState state) {
        double size = 4;
        VoxelShape base;

        if(state.get(HORIZONTAL)) base = VoxelShapes.create(size, size * 2, size, 16 - size, 15.98, 16 - size);
        else base = VoxelShapes.create(size, 0, size, 16 - size, 15.98, 16 - size);

        List<VoxelShape> connections = Lists.newArrayList();
        for(Direction dir : Direction.values())
        {
            if(dir.getHorizontalIndex() < 0)
                continue;

            if(state.get(FACING_PROPERTIES.get(dir)))
            {
                double x= dir == Direction.WEST ? 0 : dir == Direction.EAST ? 16D : size;
                double z= dir == Direction.NORTH ? 0 : dir == Direction.SOUTH ? 16D : size;

                VoxelShape sh = VoxelShapes.create(x, 8, z, 16 - size, 16, 16 - size);
                connections.add(sh);
            }
        }

        return VoxelShapes.or(base, connections.toArray(new VoxelShape[]{}));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(EAST, WEST, NORTH, SOUTH, HORIZONTAL, HORIZONTAL_DIRECTION);
    }

    public static final Map<Direction, BooleanProperty> FACING_PROPERTIES = Util.make(Maps.newEnumMap(Direction.class), (enumMap) -> {
        enumMap.put(Direction.NORTH, NORTH);
        enumMap.put(Direction.EAST, EAST);
        enumMap.put(Direction.SOUTH, SOUTH);
        enumMap.put(Direction.WEST, WEST);
    });

    public boolean isFertilized(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
        return state.equals(getDefaultState()) && canGrow(world, pos, state, isClient) && world.getBlockState(pos.up()).isAir();
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, BlockState state) {
        return (double) world.rand.nextFloat() < 0.45D;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        OmniFeatures.Configured.SAGUARO_CACTUS.generate(world, world.getChunkProvider().getChunkGenerator(), random, pos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(isFertilized(world, pos, state, world.isRemote) && random.nextInt(10) == 0) grow(world, random, pos, state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entity) {
        entity.attackEntityFrom(DamageSource.CACTUS, 1.0F);
    }

    @Nullable
    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {
        return PathNodeType.DAMAGE_CACTUS;
    }
}