package pugz.omni.common.block.cavier_caves;

import net.minecraft.block.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import pugz.omni.core.base.IBaseBlock;
import pugz.omni.core.registry.OmniBlocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CobwebCarpetBlock extends DirectionalBlock implements IWaterLoggable, IBaseBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape UP_AABB = Block.makeCuboidShape(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    public static final VoxelShape DOWN_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    private static final VoxelShape EAST_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
    private static final VoxelShape WEST_AABB = Block.makeCuboidShape(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape SOUTH_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
    private static final VoxelShape NORTH_AABB = Block.makeCuboidShape(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);

    public CobwebCarpetBlock() {
        super(AbstractBlock.Properties.from(Blocks.COBWEB).hardnessAndResistance(1.0F));
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.DOWN).with(WATERLOGGED, false));
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
        return 20;
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape voxelshape = VoxelShapes.empty();
        if (state.get(FACING) == Direction.UP) {
            voxelshape = UP_AABB;
        }

        if (state.get(FACING) == Direction.DOWN) {
            voxelshape = DOWN_AABB;
        }

        if (state.get(FACING) == Direction.NORTH) {
            voxelshape = VoxelShapes.or(voxelshape, SOUTH_AABB);
        }

        if (state.get(FACING) == Direction.SOUTH) {
            voxelshape = VoxelShapes.or(voxelshape, NORTH_AABB);
        }

        if (state.get(FACING) == Direction.EAST) {
            voxelshape = VoxelShapes.or(voxelshape, WEST_AABB);
        }

        if (state.get(FACING) == Direction.WEST) {
            voxelshape = VoxelShapes.or(voxelshape, EAST_AABB);
        }

        return voxelshape;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack held = player.getHeldItem(handIn);

        if (held.getItem() == OmniBlocks.CAVE_SPIDER_SAC.get().asItem()) {
            worldIn.setBlockState(pos, OmniBlocks.CAVE_SPIDER_SAC.get().getDefaultState().with(SpiderSacBlock.WEBBED, true), 3);
            if (!player.isCreative()) held.shrink(1);

            return ActionResultType.func_233537_a_(worldIn.isRemote);
        }

        return ActionResultType.PASS;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos up = context.getPos().up();
        World world = context.getWorld();

        if (world.getBlockState(up).getBlock() == OmniBlocks.CAVE_SPIDER_SAC.get() && context.getFace() == Direction.DOWN) {
            return world.getBlockState(up).with(SpiderSacBlock.WEBBED, true);
        }

        return getDefaultState().with(FACING, context.getFace().getOpposite()).with(WATERLOGGED, context.getWorld().getFluidState(context.getPos()).isTagged(FluidTags.WATER));
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        return IWaterLoggable.super.receiveFluid(worldIn, pos, state, fluidStateIn);
    }

    public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return IWaterLoggable.super.canContainFluid(worldIn, pos, state, fluidIn);
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @SuppressWarnings("deprecation")
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof SpiderEntity) return;
        entityIn.setMotionMultiplier(state, new Vector3d(0.8D, (double)0.8F, 0.8D));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }
}