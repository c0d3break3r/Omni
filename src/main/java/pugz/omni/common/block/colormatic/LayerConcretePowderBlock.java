package pugz.omni.common.block.colormatic;

import pugz.omni.common.entity.colormatic.FallingConcretePowderEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class LayerConcretePowderBlock extends FallingBlock implements IWaterLoggable {
    public static final IntegerProperty LAYERS = BlockStateProperties.LAYERS_1_8;
    protected static final VoxelShape[] SHAPES = new VoxelShape[]{VoxelShapes.empty(), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
    private final BlockState solidifiedState;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public LayerConcretePowderBlock(Block solidifiedIn, DyeColor color) {
        super(AbstractBlock.Properties.create(Material.SAND, color).hardnessAndResistance(0.5F).sound(SoundType.SAND));
        this.setDefaultState(this.stateContainer.getBaseState().with(LAYERS, 8).with(WATERLOGGED, false));
        solidifiedState = solidifiedIn.getDefaultState();
    }

    public BlockState getSolidifiedState() {
        return solidifiedState;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public PushReaction getPushReaction(BlockState state) {
        return state.get(LAYERS) == 1 ? PushReaction.DESTROY : PushReaction.NORMAL;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) && state.get(LAYERS) < 7 ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @SuppressWarnings("deprecation")
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        if (type == PathType.LAND) {
            return state.get(LAYERS) < 5;
        }
        return false;
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.get(LAYERS)];
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.get(LAYERS)];
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos) {
        return SHAPES[state.get(LAYERS)];
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public VoxelShape getRayTraceShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.get(LAYERS)];
    }

    @SuppressWarnings("deprecation")
    public boolean isTransparent(BlockState state) {
        return true;
    }

    /**
     * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific face passed in.
     */
    @Nonnull
    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, this.getFallDelay());
        return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : isTouchingLiquid(worldIn, currentPos) ? this.solidifiedState.with(LAYERS, stateIn.get(LAYERS)).with(WATERLOGGED, stateIn.get(WATERLOGGED)) : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack held = player.getHeldItem(handIn);

        if (held.getItem() instanceof ShovelItem && hit.getFace() == Direction.UP) {
            held.damageItem(1, player, e -> e.sendBreakAnimation(handIn));

            if (state.get(LAYERS) > 1) worldIn.setBlockState(pos, state.with(LAYERS, state.get(LAYERS) - 1), 3);
            else worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);

            return ActionResultType.SUCCESS;
        }

        return ActionResultType.FAIL;
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (worldIn.isAirBlock(pos.down()) || canFallThrough(worldIn.getBlockState(pos.down())) && pos.getY() >= 0) {
            FallingConcretePowderEntity falling = new FallingConcretePowderEntity(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, state.get(LAYERS), state);
            falling.shouldDropItem = state.get(LAYERS) == 8;
            worldIn.addEntity(falling);
        }
    }

    @Override
    protected int getFallDelay() {
        return 2;
    }

    public static boolean canFallThrough(BlockState state) {
        if (state.getBlock() instanceof LayerConcretePowderBlock || (state.getBlock() instanceof LayerConcreteBlock)) return false;

        Material material = state.getMaterial();
        return state.isAir() || state.isIn(BlockTags.FIRE) || material.isLiquid() || material.isReplaceable();
    }

    public void onEndFalling(World worldIn, BlockPos pos, BlockState fallingState) {
        if (shouldSolidify(worldIn, pos, fallingState)) worldIn.setBlockState(pos, solidifiedState.with(LAYERS, fallingState.get(LAYERS)).with(WATERLOGGED, fallingState.get(LAYERS) < 7), 3);
    }

    private static boolean shouldSolidify(IBlockReader reader, BlockPos pos, BlockState state) {
        return causesSolidify(state) || isTouchingLiquid(reader, pos);
    }

    private static boolean isTouchingLiquid(IBlockReader world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);

        if (state.getBlock() instanceof LayerConcretePowderBlock) {
            if (state.get(WATERLOGGED)) return true;
        }

        for (Direction d : Direction.values()) {
            if (d != Direction.DOWN) {
                BlockState check = world.getBlockState(pos.offset(d));

                if (check.getBlock() instanceof LayerConcretePowderBlock || check.getBlock() instanceof LayerConcreteBlock) {
                    if (check.get(WATERLOGGED) && check.get(LAYERS) < state.get(LAYERS)) return true;
                }

                else if (causesSolidify(check) && !check.isSolidSide(world, pos, d.getOpposite())) return true;
            }
        }

        return false;
    }

    private static boolean causesSolidify(BlockState state) {
        return state.getFluidState().isTagged(FluidTags.WATER);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(16) == 0) {
            BlockPos blockpos = pos.down();
            if (worldIn.isAirBlock(blockpos) || canFallThrough(worldIn.getBlockState(blockpos))) {
                double d0 = (double)pos.getX() + rand.nextDouble();
                double d1 = (double)pos.getY() - 0.05D;
                double d2 = (double)pos.getZ() + rand.nextDouble();
                worldIn.addParticle(new BlockParticleData(ParticleTypes.FALLING_DUST, stateIn), d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }

    }

    public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        return IWaterLoggable.super.receiveFluid(worldIn, pos, state, fluidStateIn) && state.get(LAYERS) < 8;
    }

    public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return IWaterLoggable.super.canContainFluid(worldIn, pos, state, fluidIn) && state.get(LAYERS) < 8;
    }

    @SuppressWarnings("deprecation")
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return state.get(LAYERS) == 1;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IBlockReader world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState blockstate = world.getBlockState(pos);
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());

        return shouldSolidify(world, pos, blockstate) ? this.solidifiedState.with(LAYERS, 8).with(WATERLOGGED, fluidstate.isTagged(FluidTags.WATER) && fluidstate.getLevel() == 8) : super.getStateForPlacement(context);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public int getDustColor(BlockState state, IBlockReader reader, BlockPos pos) {
        return state.getBlock().getMaterialColor().colorValue;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LAYERS, WATERLOGGED);
    }
}