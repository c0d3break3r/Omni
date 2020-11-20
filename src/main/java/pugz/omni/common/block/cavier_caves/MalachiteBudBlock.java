package pugz.omni.common.block.cavier_caves;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import pugz.omni.core.registry.OmniBlocks;
import pugz.omni.core.registry.OmniSoundEvents;

import javax.annotation.Nonnull;

public class MalachiteBudBlock extends Block implements IWaterLoggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    protected static final VoxelShape[] UP_SHAPES = new VoxelShape[] {Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 3.0D, 11.0D), Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 4.0D, 12.0D), Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 5.0D, 12.0D), Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 7.0D, 12.0D)};
    protected static final VoxelShape[] DOWN_SHAPES = new VoxelShape[] {Block.makeCuboidShape(5.0D, 13.0D, 5.0D, 11.0D, 16.0D, 11.0D), Block.makeCuboidShape(4.0D, 12.0D, 4.0D, 12.0D, 16.0D, 12.0D), Block.makeCuboidShape(4.0D, 11.0D, 4.0D, 12.0D, 16.0D, 12.0D), Block.makeCuboidShape(4.0D, 9.0D, 4.0D, 12.0D, 16.0D, 12.0D)};
    protected static final VoxelShape[] NORTH_SHAPES = new VoxelShape[] {Block.makeCuboidShape(5.0D, 5.0D, 13.0D, 11.0D, 11.0D, 16.0D), Block.makeCuboidShape(4.0D, 4.0D, 12.0D, 12.0D, 12.0D, 16.0D), Block.makeCuboidShape(4.0D, 4.0D, 11.0D, 12.0D, 12.0D, 16.0D), Block.makeCuboidShape(4.0D, 4.0D, 9.0D, 12.0D, 12.0D, 16.0D)};
    protected static final VoxelShape[] SOUTH_SHAPES = new VoxelShape[] {Block.makeCuboidShape(5.0D, 5.0D, 0.0D, 11.0D, 11.0D, 3.0D), Block.makeCuboidShape(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 4.0D), Block.makeCuboidShape(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 5.0D), Block.makeCuboidShape(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 7.0D)};
    protected static final VoxelShape[] WEST_SHAPES = new VoxelShape[] {Block.makeCuboidShape(13.0D, 5.0D, 5.0D, 16.0D, 11.0D, 11.0D), Block.makeCuboidShape(12.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D), Block.makeCuboidShape(11.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D), Block.makeCuboidShape(9.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D)};
    protected static final VoxelShape[] EAST_SHAPES = new VoxelShape[] {Block.makeCuboidShape(0.0D, 5.0D, 5.0D, 3.0D, 11.0D, 11.0D), Block.makeCuboidShape(0.0D, 4.0D, 4.0D, 4.0D, 12.0D, 12.0D), Block.makeCuboidShape(0.0D, 4.0D, 4.0D, 5.0D, 12.0D, 12.0D), Block.makeCuboidShape(0.0D, 4.0D, 4.0D, 7.0D, 12.0D, 12.0D)};
    private final int age;

    public MalachiteBudBlock(AbstractBlock.Properties properties, int age) {
        super(properties.setLightLevel((state) -> {
            return 12 + age;
        }).sound(SoundType.SNOW));
        this.age = age;
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.UP).with(WATERLOGGED, false));
    }

    public boolean ticksRandomly(BlockState state) {
        return state.getBlock() != OmniBlocks.MALACHITE_CLUSTER.get();
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isRemote) {
            worldIn.playSound(null, pos, OmniSoundEvents.CRYSTAL_BREAK.get(), SoundCategory.BLOCKS, 1.0F, 0.5F + worldIn.rand.nextFloat() * 1.2F);
            worldIn.playSound(null, pos, OmniSoundEvents.CRYSTAL_BREAK.get(), SoundCategory.BLOCKS, 1.0F, 0.5F + worldIn.rand.nextFloat() * 1.2F);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onProjectileCollision(World world, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile) {
        if (!world.isRemote) {
            world.playSound(null, hit.getPos(), OmniSoundEvents.CRYSTAL_BREAK.get(), SoundCategory.BLOCKS, 1.0F, 0.5F + world.rand.nextFloat() * 1.2F);
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (!worldIn.isRemote) {
            worldIn.playSound(null, pos, OmniSoundEvents.CRYSTAL_PLACE.get(), SoundCategory.BLOCKS, 1.0F, 0.5F + worldIn.rand.nextFloat() * 1.2F);
        }
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.with(FACING, mirrorIn.mirror(state.get(FACING)));
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch(state.get(FACING)) {
            case UP:
                return UP_SHAPES[age];
            case DOWN:
                return DOWN_SHAPES[age];
            case EAST:
                return EAST_SHAPES[age];
            case WEST:
                return WEST_SHAPES[age];
            case NORTH:
                return NORTH_SHAPES[age];
            default:
                return SOUTH_SHAPES[age];
        }
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction direction = context.getFace();
        BlockState blockstate = context.getWorld().getBlockState(context.getPos().offset(direction.getOpposite()));
        FluidState fluidState = context.getWorld().getFluidState(context.getPos().offset(direction.getOpposite()));
        return blockstate.isIn(this) && blockstate.get(FACING) == direction ? this.getDefaultState().with(FACING, direction.getOpposite()).with(WATERLOGGED, fluidState.isTagged(FluidTags.WATER)) : this.getDefaultState().with(FACING, direction).with(WATERLOGGED, fluidState.isTagged(FluidTags.WATER));
    }

    @SuppressWarnings("deprecation")
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return isSideSolidForDirection(worldIn, pos, state.get(FACING).getOpposite());
    }

    public static boolean isSideSolidForDirection(IWorldReader reader, BlockPos pos, Direction direction) {
        BlockPos blockpos = pos.offset(direction);
        return reader.getBlockState(blockpos).isSolidSide(reader, blockpos, direction.getOpposite());
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return !this.isValidPosition(stateIn, worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @SuppressWarnings("deprecation")
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return type == PathType.WATER && worldIn.getFluidState(pos).isTagged(FluidTags.WATER);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }
}