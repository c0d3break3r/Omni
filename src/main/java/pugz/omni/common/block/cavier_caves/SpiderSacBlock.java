package pugz.omni.common.block.cavier_caves;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
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
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import pugz.omni.common.entity.cavier_caves.SizedCaveSpiderEntity;
import pugz.omni.core.base.IBaseBlock;
import pugz.omni.core.registry.OmniBlocks;
import pugz.omni.core.registry.OmniEntities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class SpiderSacBlock extends Block implements IWaterLoggable, IBaseBlock {
    public static final IntegerProperty SIZE = IntegerProperty.create("size", 1, 4);
    public static final BooleanProperty WEBBED = BooleanProperty.create("webbed");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SMALL_AABB = Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D);
    private static final VoxelShape MEDIUM_AABB = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 8.0D, 11.0D);
    private static final VoxelShape LARGE_AABB = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 12.0D, 12.0D);
    private static final VoxelShape HUGE_AABB = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public SpiderSacBlock() {
        super(AbstractBlock.Properties.create(Material.ORGANIC).hardnessAndResistance(0.25F).notSolid().sound(SoundType.SLIME));
        setDefaultState(this.stateContainer.getBaseState().with(SIZE, 2).with(WEBBED, false).with(WATERLOGGED, false));
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(SIZE)) {
            case 1:
                return state.get(WEBBED) ? VoxelShapes.or(SMALL_AABB, CobwebCarpetBlock.DOWN_AABB) : SMALL_AABB;
            case 2:
                return state.get(WEBBED) ? VoxelShapes.or(MEDIUM_AABB, CobwebCarpetBlock.DOWN_AABB) : MEDIUM_AABB;
            case 3:
                return state.get(WEBBED) ? VoxelShapes.or(LARGE_AABB, CobwebCarpetBlock.DOWN_AABB) : LARGE_AABB;
            default:
                return state.get(WEBBED) ? VoxelShapes.or(HUGE_AABB, CobwebCarpetBlock.DOWN_AABB) : HUGE_AABB;
        }
    }

    @Override
    public RenderType getRenderType() {
        return RenderType.getCutout();
    }

    @SuppressWarnings("deprecation")
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return hasEnoughSolidSide(worldIn, pos.down(), Direction.UP);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isRemote && worldIn.getRandom().nextBoolean() && !player.isCreative()) {
            SizedCaveSpiderEntity caveSpider = OmniEntities.CAVE_SPIDER.get().create(worldIn);
            caveSpider.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY() + 0.05D, (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
            if (state.get(SIZE) == 4) {
                caveSpider.setSpiderSize(state.get(SIZE));
            }
            else caveSpider.setSpiderSize(state.get(SIZE) - 1);
            worldIn.addEntity(caveSpider);
        }

        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack held = player.getHeldItem(handIn);

        if (held.getItem() == OmniBlocks.COBWEB_CARPET.get().asItem()) {
            worldIn.setBlockState(pos, state.with(WEBBED, true), 3);
            if (!player.isCreative()) held.shrink(1);

            return ActionResultType.func_233537_a_(worldIn.isRemote);
        }

        return ActionResultType.PASS;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (worldIn.isPlayerWithin(pos.getX(), pos.getY(), pos.getZ(), 2.0D)) {
            worldIn.removeBlock(pos, false);
        }
        super.tick(state, worldIn, pos, rand);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
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

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SIZE, WEBBED, WATERLOGGED);
    }
}