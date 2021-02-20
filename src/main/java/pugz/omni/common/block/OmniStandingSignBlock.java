package pugz.omni.common.block;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import pugz.omni.core.util.IBaseBlock;
import pugz.omni.core.util.IOmniSign;

import javax.annotation.Nonnull;

public class OmniStandingSignBlock extends StandingSignBlock implements IBaseBlock, IOmniSign {
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_0_15;
    private final String wood;

    public OmniStandingSignBlock(AbstractBlock.Properties properties, String wood) {
        super(properties, WoodType.OAK);
        this.setDefaultState(this.stateContainer.getBaseState().with(ROTATION, 0).with(WATERLOGGED, false));
        this.wood = wood;
    }

    @Override
    public boolean isSign() {
        return true;
    }

    @Override
    public String getWood() {
        return wood;
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.down()).getMaterial().isSolid();
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        return this.getDefaultState().with(ROTATION, MathHelper.floor((double) ((180.0F + context.getPlacementYaw()) * 16.0F / 360.0F) + 0.5D) & 15).with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
    }

    @Nonnull
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.DOWN && !this.isValidPosition(stateIn, worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(ROTATION, rot.rotate(state.get(ROTATION), 16));
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.with(ROTATION, mirrorIn.mirrorRotation(state.get(ROTATION), 16));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ROTATION, WATERLOGGED);
    }
}