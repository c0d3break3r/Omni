package com.pugz.omni.common.block.colormatic;

import com.pugz.omni.common.block.IStackable;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.HugeFungusConfig;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

public class FungiBlock extends BushBlock implements IGrowable, IStackable {
    private static final VoxelShape MULTI_FUNGUS_SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 9.0D, 12.0D);
    public static final IntegerProperty FUNGI = IntegerProperty.create("fungi", 1, 4);
    private final Block base;
    private final Supplier<ConfiguredFeature<HugeFungusConfig, ?>> fungusFeature;

    public FungiBlock(Properties properties, Block base, Supplier<ConfiguredFeature<HugeFungusConfig, ?>> fungusFeature) {
        super(properties);
        this.base = base;
        this.fungusFeature = fungusFeature;
        setDefaultState(this.stateContainer.getBaseState().with(FUNGI, 2));
    }

    @Override
    public Block getBase() {
        return base;
    }

    @Override
    public Block getBlock() {
        return this;
    }

    @Override
    @SuppressWarnings("deprecated")
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return base.isValidPosition(state, worldIn, pos);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        base.animateTick(stateIn, worldIn, pos, rand);
    }

    @SuppressWarnings("deprecated")
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        base.onEntityCollision(state, worldIn, pos, entityIn);
    }

    @Nonnull
    @SuppressWarnings("deprecated")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.get(FUNGI) == 2 ? base.getShape(state, worldIn, pos, context) : MULTI_FUNGUS_SHAPE;
    }

    @Override
    @SuppressWarnings("deprecated")
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        base.randomTick(base.getDefaultState(), worldIn, pos, random);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return new ItemStack(base);
    }

    @Nonnull
    public OffsetType getOffsetType() {
        return base.getOffsetType();
    }

    @Override
    public void removeOne(World world, BlockPos pos, BlockState state) {
        world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_CROP_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + world.rand.nextFloat() * 0.2F);
        int i = state.get(FUNGI);
        switch (i) {
            case 1:
                world.destroyBlock(pos, true, (PlayerEntity)null);
                break;
            case 2:
                world.setBlockState(pos, base.getDefaultState(), 3);
                break;
            default:
                world.setBlockState(pos, state.with(FUNGI, i - 1), 3);
                world.playEvent(2001, pos, Block.getStateId(state));
                break;
        }
    }

    public void harvestBlock(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(world, player, pos, state, te, stack);
        this.removeOne(world, pos, state);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecated")
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack held = player.getHeldItem(handIn);

        if (held.getItem() == getBase().asItem()) {
            int i = state.get(FUNGI);
            if (i < 4) {
                player.sendBreakAnimation(handIn);
                if (!player.isCreative()) {
                    held.shrink(1);
                }

                world.setBlockState(pos, state.with(FUNGI, i + 1), 3);
                return ActionResultType.func_233537_a_(world.isRemote);
            }
        }
        return ActionResultType.FAIL;
    }

    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        Block block = ((HugeFungusConfig)(this.fungusFeature.get()).config).field_236303_f_.getBlock();
        Block block1 = worldIn.getBlockState(pos.down()).getBlock();
        return block1 == block;
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return (double)rand.nextFloat() < 0.4D * state.get(FUNGI);
    }

    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        this.fungusFeature.get().generate(worldIn, worldIn.getChunkProvider().getChunkGenerator(), rand, pos);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FUNGI);
    }
}