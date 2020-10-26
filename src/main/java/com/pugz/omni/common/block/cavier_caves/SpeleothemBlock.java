package com.pugz.omni.common.block.cavier_caves;

import com.pugz.omni.core.registry.OmniBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class SpeleothemBlock extends FallingBlock implements IWaterLoggable {
    public static final EnumProperty<Size> SIZE = EnumProperty.create("size", Size.class);
    public static final BooleanProperty STATIC = BooleanProperty.create("static");
    public static final EnumProperty<Half> HALF = EnumProperty.create("half", Half.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public SpeleothemBlock(Properties properties) {
        super(properties);
        setDefaultState(stateContainer.getBaseState().with(SIZE, Size.LARGE).with(HALF, Half.LOWER).with(STATIC, false).with(WATERLOGGED, false));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction face = context.getFace();
        Item held = context.getItem().getItem();
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        BlockState state = getDefaultState();

        if (face == Direction.DOWN) {
            if (held == OmniBlocks.ICE_SPELEOTHEM.get().asItem()) state = state.with(SIZE, Size.ICE_LARGE);
            state = state.with(HALF, Half.UPPER);

        } else if (face == Direction.UP) {
            if (held == OmniBlocks.ICE_SPELEOTHEM.get().asItem()) state = state.with(SIZE, Size.ICE_LARGE);
            state = state.with(HALF, Half.LOWER);
        }

        return state.with(STATIC, true).with(WATERLOGGED, fluidstate.isTagged(FluidTags.WATER));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult ray) {
        ItemStack held = player.getHeldItem(handIn);
        Size size = state.get(SIZE);

        if (!worldIn.isRemote && held.getItem() instanceof PickaxeItem) {
            FluidState fluidstate = worldIn.getFluidState(pos);

            if (size == Size.LARGE || size == Size.ICE_LARGE) worldIn.setBlockState(pos, getDefaultState().with(SIZE, Size.MEDIUM).with(STATIC, true).with(WATERLOGGED, fluidstate.isTagged(FluidTags.WATER)));
            else if (size == Size.MEDIUM) worldIn.setBlockState(pos, getDefaultState().with(SIZE, Size.SMALL).with(STATIC, true).with(WATERLOGGED, fluidstate.isTagged(FluidTags.WATER)));
            else if (size == Size.SMALL) worldIn.removeBlock(pos, false);

            if (held.isDamageable()) held.damageItem(1, player, (living) -> {
                living.sendBreakAnimation(handIn);
            });

            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        if (!state.get(STATIC)) {
            if (world.isAirBlock(pos.down()) || canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= 0) {
                FallingBlockEntity fallingblockentity = new FallingBlockEntity(world, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, world.getBlockState(pos));
                this.onStartFalling(fallingblockentity);
                world.addEntity(fallingblockentity);
            }

            if (notConnected(state, world, pos)) super.tick(state, world, pos, rand);
        }

        if (notConnected(state, world, pos)) {
            world.setBlockState(pos, state.with(STATIC, false), 0);
        }
    }

    /**
     * Performs a random tick on a block.
     */
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        for (int y = pos.getY(); y >= 0; --y) {
            BlockPos check = new BlockPos(pos.getX(), y, pos.getZ());
            BlockState block = world.getBlockState(check);

            if (block.getBlock() == Blocks.CAULDRON && rand.nextInt(40) == 0) {
                int level = block.get(CauldronBlock.LEVEL);
                if (level < 3) world.setBlockState(check, block.with(CauldronBlock.LEVEL, level + 1));
            }
        }
    }

    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    public boolean notConnected(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockState down = worldIn.getBlockState(pos.down());
        BlockState up = worldIn.getBlockState(pos.up());

        if (state.get(HALF) == Half.UPPER) return !down.isSolid();
        else if (state.get(HALF) == Half.LOWER) return !up.isSolid();

        return false;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockState down = worldIn.getBlockState(pos.down());
        BlockState up = worldIn.getBlockState(pos.up());
        return down.isSolid() || up.isSolid();
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public boolean allowsMovement(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, PathType type) {
        return type == PathType.WATER && worldIn.getFluidState(pos).isTagged(FluidTags.WATER);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.get(SIZE).shape;
    }

    @Override
    protected int getFallDelay() {
        return 4;
    }

    @Override
    public void onEndFalling(World worldIn, BlockPos pos, BlockState fallingState, BlockState hitState, FallingBlockEntity fallingBlock) {
        worldIn.destroyBlock(pos, false);
    }

    @OnlyIn(Dist.CLIENT)
    public int getDustColor(BlockState state, IBlockReader reader, BlockPos pos) {
        return state.getMaterialColor(reader, pos).colorValue;
    }

    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        BlockState state = worldIn.getBlockState(pos);
        entityIn.onLivingFall(fallDistance, 4.0F + 1 / (state.get(SIZE).width * 0.5F));
    }

    @Override
    public void onProjectileCollision(World worldIn, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile) {
        worldIn.setBlockState(hit.getPos(), state.with(STATIC, false));
        worldIn.getPendingBlockTicks().scheduleTick(hit.getPos(), this, 1);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        if (world.isAirBlock(pos.down()) || world.getBlockState(pos.down()).getBlock() == Blocks.CAULDRON) {
            for(int i = 0; i < rand.nextInt(1) + 1; ++i) {
                this.addDripParticle(world, pos, state);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void addDripParticle(World world, BlockPos pos, BlockState state) {
        if (state.getFluidState().isEmpty() && !(world.rand.nextFloat() < 0.3F)) {
            VoxelShape shape = state.getCollisionShape(world, pos);
            double d0 = shape.getEnd(Direction.Axis.Y);
            if (d0 >= 1.0D && !state.isIn(BlockTags.IMPERMEABLE)) {
                double d1 = shape.getStart(Direction.Axis.Y);
                if (d1 > 0.0D) {
                    this.addDripParticle(world, pos, shape, (double)pos.getY() + d1 - 0.05D);
                } else {
                    BlockPos down = pos.down();
                    BlockState downState = world.getBlockState(down);
                    double d2 = downState.getCollisionShape(world, down).getEnd(Direction.Axis.Y);
                    if ((d2 < 1.0D || !downState.hasOpaqueCollisionShape(world, down)) && downState.getFluidState().isEmpty()) {
                        this.addDripParticle(world, pos, shape, (double)pos.getY() - 0.05D);
                    }
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void addDripParticle(World world, BlockPos pos, VoxelShape shape, double y) {
        world.addParticle(ParticleTypes.DRIPPING_WATER, MathHelper.lerp(world.rand.nextDouble(), (double)pos.getX() + shape.getStart(Direction.Axis.X), (double)pos.getX() + shape.getEnd(Direction.Axis.X)), y, MathHelper.lerp(world.rand.nextDouble(), (double)pos.getZ() + shape.getStart(Direction.Axis.Z), (double)pos.getZ() + shape.getEnd(Direction.Axis.Z)), 0.0D, 0.0D, 0.0D);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SIZE, HALF, STATIC, WATERLOGGED);
    }

    public enum Size implements IStringSerializable {
        SMALL("small", 4),
        MEDIUM("medium", 8),
        ICE_LARGE("ice_large", 12),
        LARGE("large", 14);

        Size(String nameIn, int width) {
            int pad = (16 - width) / 2;
            shape = Block.makeCuboidShape(pad, 0, pad, 16 - pad, 16, 16 - pad);
            name = nameIn;
            this.width = width;
        }

        public VoxelShape shape;
        public String name;
        private final int width;

        @Override
        public String getString() {
            return name;
        }
    }

    public enum Half implements IStringSerializable {
        LOWER("lower"),
        UPPER("upper"),
        FULL("full");

        Half(String nameIn) {
            name = nameIn;
        }

        public String name;

        @Override
        public String getString() {
            return name;
        }
    }
}