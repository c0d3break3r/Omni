package pugz.omni.common.block.cavier_caves;

import net.minecraft.block.material.Material;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import pugz.omni.core.module.CoreModule;
import pugz.omni.core.registry.OmniBlocks;
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
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class SpeleothemBlock extends FallingBlock implements IWaterLoggable {
    public static final EnumProperty<Size> SIZE = EnumProperty.create("size", Size.class);
    public static final EnumProperty<Part> PART = EnumProperty.create("part", Part.class);
    public static final BooleanProperty STATIC = BooleanProperty.create("static");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public SpeleothemBlock(Properties properties) {
        super(properties);
        setDefaultState(stateContainer.getBaseState().with(SIZE, Size.LARGE).with(PART, Part.FULL).with(STATIC, true).with(WATERLOGGED, false));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Item held = context.getItem().getItem();
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        BlockState state = getDefaultState();
        Direction face = context.getFace();

        if (face == Direction.UP || face == Direction.DOWN) {
            if (face == Direction.UP) {
                state = state.with(PART, Part.LOWER);
            } else {
                state = state.with(PART, Part.UPPER);
            }

            if (held == OmniBlocks.ICE_SPELEOTHEM.get().asItem()) state = state.with(SIZE, Size.ICE_LARGE);
            else state = state.with(SIZE, Size.LARGE);

            return state.with(STATIC, true).with(WATERLOGGED, fluidstate.isTagged(FluidTags.WATER));
        }

        return null;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult ray) {
        if (!worldIn.isRemote) {
            ItemStack held = player.getHeldItem(handIn);
            Size size = state.get(SIZE);

            if (held.getItem() instanceof PickaxeItem) {
                FluidState fluidstate = worldIn.getFluidState(pos);

                if (size == Size.LARGE || size == Size.ICE_LARGE)
                    worldIn.setBlockState(pos, getDefaultState().with(SIZE, Size.MEDIUM).with(PART, state.get(PART)).with(STATIC, state.get(STATIC)).with(WATERLOGGED, fluidstate.isTagged(FluidTags.WATER)), 1);
                else if (size == Size.MEDIUM)
                    worldIn.setBlockState(pos, getDefaultState().with(SIZE, Size.SMALL).with(PART, state.get(PART)).with(STATIC, state.get(STATIC)).with(WATERLOGGED, fluidstate.isTagged(FluidTags.WATER)), 1);
                else if (size == Size.SMALL) worldIn.removeBlock(pos, false);

                if (held.isDamageable()) held.damageItem(1, player, (living) -> {
                    living.sendBreakAnimation(handIn);
                });

                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        if (!state.get(STATIC)) trySpawnEntity(world, pos);
    }

    private void trySpawnEntity(World world, BlockPos pos) {
        if (CoreModule.Configuration.CLIENT.SPELEOTHEMS_FALL.get() && !world.isRemote) {
            if (world.isAirBlock(pos.down()) || canFallThrough(world.getBlockState(pos.down()))) {
                FallingBlockEntity fallingblockentity = new FallingBlockEntity(world, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, world.getBlockState(pos));
                this.onStartFalling(fallingblockentity);
                fallingblockentity.setHurtEntities(true);
                world.addEntity(fallingblockentity);
                world.getPendingBlockTicks().scheduleTick(pos.up(), this, 1);
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        for (int y = pos.getY(); y >= Math.max(0, pos.getY() - 64); --y) {
            BlockPos check = new BlockPos(pos.getX(), y, pos.getZ());
            BlockState block = world.getBlockState(check);

            if (block.getBlock() == Blocks.CAULDRON && rand.nextInt(40) == 0 && state.getBlock() != OmniBlocks.NETHERRACK_SPELEOTHEM.get()) {
                int level = block.get(CauldronBlock.LEVEL);
                if (level < 3) world.setBlockState(check, block.with(CauldronBlock.LEVEL, level + 1), 3);
            }
        }
    }

    public boolean ticksRandomly(BlockState state) {
        return CoreModule.Configuration.CLIENT.SPELEOTHEMS_FILL_CAULDRONS.get();
    }

    @Nonnull
    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if (CoreModule.Configuration.CLIENT.SPELEOTHEMS_FALL.get()) {
            BlockState down = world.getBlockState(currentPos.down());
            BlockState up = world.getBlockState(currentPos.up());
            Part part = state.get(PART);

            if (!state.isValidPosition(world, currentPos) && !state.get(STATIC)) {
                world.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
            }

            if ((down.getBlock() instanceof SpeleothemBlock || down.isSolid()) && (up.getBlock() instanceof SpeleothemBlock || up.isSolid()))
                return state.with(PART, Part.FULL);

            if (part == Part.FULL) {
                if (!down.isSolid() && up.isSolid()) {
                    if (world.isAirBlock(currentPos.down()) || canFallThrough(world.getBlockState(currentPos.down()))) {
                        world.getPendingBlockTicks().scheduleTick(currentPos.up(), this, 1);
                        return state.with(PART, Part.UPPER).with(STATIC, false);
                    } else return state.with(PART, Part.UPPER).with(STATIC, true);
                } else if (!up.isSolid() && down.isSolid()) {
                    if (world.isAirBlock(currentPos.down()) || canFallThrough(world.getBlockState(currentPos.down()))) {
                        world.getPendingBlockTicks().scheduleTick(currentPos.up(), this, 1);
                        return state.with(PART, Part.UPPER).with(STATIC, false);
                    } else return state.with(PART, Part.LOWER).with(STATIC, true);
                } else if (!down.isSolid() && !up.isSolid()) {
                    world.getPendingBlockTicks().scheduleTick(currentPos.up(), this, 1);
                    return state.with(STATIC, false);
                }
            } else if (part == Part.UPPER) {
                if (!up.isSolid()) {
                    world.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
                    return state.with(STATIC, false);
                }
            } else {
                if (!down.isSolid()) {
                    world.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
                    return state.with(STATIC, false);
                }
            }
        }

        return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
    }

    @Override
    @SuppressWarnings("deprecation")
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
    @SuppressWarnings("deprecation")
    public boolean allowsMovement(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, PathType type) {
        return type == PathType.WATER && worldIn.getFluidState(pos).isTagged(FluidTags.WATER);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Vector3d offset = state.getOffset(worldIn, pos);
        return state.get(SIZE).shape.withOffset(offset.getX(), offset.getY(), offset.getZ());
    }

    @Override
    protected int getFallDelay() {
        return 4;
    }

    @SuppressWarnings("deprecation")
    public static boolean canFallThrough(BlockState state) {
        Material material = state.getMaterial();
        return state.isAir() || state.isIn(BlockTags.FIRE) || material.isLiquid() || material.isReplaceable();
    }

    @Override
    public void onEndFalling(World worldIn, BlockPos pos, BlockState fallingState, BlockState hitState, FallingBlockEntity fallingBlock) {
        if (!worldIn.isRemote) worldIn.destroyBlock(pos, false);
    }

    public int getDustColor(BlockState state, IBlockReader reader, BlockPos pos) {
        return state.getMaterialColor(reader, pos).colorValue;
    }

    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        super.onFallenUpon(worldIn, pos, entityIn, fallDistance * 1.5F);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onProjectileCollision(World world, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile) {
        if (CoreModule.Configuration.CLIENT.SPELEOTHEMS_FALL_BY_PROJECTILES.get() && !world.isRemote) trySpawnEntity(world, hit.getPos());
    }

    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        if (world.isAirBlock(pos.down()) || world.getBlockState(pos.down()).getBlock() == Blocks.CAULDRON && !world.isRemote) {
            for (int i = 0; i < rand.nextInt(1) + 1; ++i) {
                this.addDripParticle(world, pos, state);
            }
        }
    }

    private void addDripParticle(World world, BlockPos pos, BlockState state) {
        if (state.getFluidState().isEmpty() && !(world.rand.nextFloat() < 0.3F)) {
            VoxelShape shape = state.getCollisionShape(world, pos);
            double d0 = shape.getEnd(Direction.Axis.Y);
            if (d0 >= 1.0D && !state.isIn(BlockTags.IMPERMEABLE)) {
                double d1 = shape.getStart(Direction.Axis.Y);
                if (d1 > 0.0D) {
                    this.addDripParticle(world, pos, state, shape, (double)pos.getY() + d1 - 0.05D);
                } else {
                    BlockPos down = pos.down();
                    BlockState downState = world.getBlockState(down);
                    double d2 = downState.getCollisionShape(world, down).getEnd(Direction.Axis.Y);
                    if ((d2 < 1.0D || !downState.hasOpaqueCollisionShape(world, down)) && downState.getFluidState().isEmpty()) {
                        this.addDripParticle(world, pos, state, shape, (double)pos.getY() - 0.05D);
                    }
                }
            }
        }
    }

    private void addDripParticle(World world, BlockPos pos, BlockState state, VoxelShape shape, double y) {
        IParticleData type = state.getBlock() == OmniBlocks.NETHERRACK_SPELEOTHEM.get() ? ParticleTypes.DRIPPING_LAVA : ParticleTypes.DRIPPING_WATER;
        world.addParticle(type, MathHelper.lerp(world.rand.nextDouble(), (double)pos.getX() + shape.getStart(Direction.Axis.X), (double)pos.getX() + shape.getEnd(Direction.Axis.X)), y, MathHelper.lerp(world.rand.nextDouble(), (double)pos.getZ() + shape.getStart(Direction.Axis.Z), (double)pos.getZ() + shape.getEnd(Direction.Axis.Z)), 0.0D, 0.0D, 0.0D);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SIZE, PART, STATIC, WATERLOGGED);
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

        @Nonnull
        @Override
        public String getString() {
            return name;
        }
    }

    public enum Part implements IStringSerializable {
        LOWER("lower"),
        UPPER("upper"),
        FULL("full");

        Part(String nameIn) {
            name = nameIn;
        }

        public String name;

        @Nonnull
        @Override
        public String getString() {
            return name;
        }
    }
}