package pugz.omni.common.block.cavier_caves;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import pugz.omni.core.base.IBaseBlock;
import pugz.omni.core.registry.OmniSoundEvents;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CaveMushroomBlock extends Block implements IWaterLoggable, IBaseBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape CAP = Block.makeCuboidShape(0.0D, 6.0D, 0.0D, 16.0D, 13.0D, 16.0D);
    protected static final VoxelShape STEM = Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D);
    private static final VoxelShape SHAPE = VoxelShapes.or(CAP, STEM);

    public CaveMushroomBlock(MaterialColor color) {
        super(AbstractBlock.Properties.create(Material.WOOD, color).hardnessAndResistance(0.2F).sound(SoundType.WOOD));
        this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, false));
    }

    @SuppressWarnings("deprecation")
    public boolean isTransparent(BlockState state) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return hasEnoughSolidSide(worldIn, pos.down(), Direction.UP);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(WATERLOGGED, context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER);
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

    public void onFallenUpon(World worldIn, BlockPos pos, Entity entity, float fallDistance) {
        entity.onLivingFall(fallDistance, 0.0F);

        if (!worldIn.isRemote && entity instanceof LivingEntity && fallDistance > 1.0F) {
            worldIn.playSound(null, pos, OmniSoundEvents.MUSHROOM_BOUNCE.get(), SoundCategory.BLOCKS, 0.75F, 0.5F + worldIn.rand.nextFloat() * 1.2F);
        }
    }

    @Override
    public void onLanded(IBlockReader world, Entity entity) {
        Vector3d vector3d = entity.getMotion();
        if (vector3d.y < 0.0D) {
            entity.setMotion(vector3d.x, -vector3d.y * 0.25D, vector3d.z);
        }
    }

    @SuppressWarnings("deprecation")
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        switch(type) {
            case WATER:
                return worldIn.getFluidState(pos).isTagged(FluidTags.WATER);
            case AIR:
            default:
                return false;
        }
    }
}