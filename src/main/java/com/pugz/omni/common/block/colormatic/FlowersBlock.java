package com.pugz.omni.common.block.colormatic;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class FlowersBlock extends BushBlock {
    private static final VoxelShape MULTI_FLOWER_SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D);
    private static final VoxelShape MULTI_MUSHROOM_SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);
    public static final IntegerProperty FLOWERS = IntegerProperty.create("flowers", 1, 4);
    private final Block base;

    public FlowersBlock(AbstractBlock.Properties properties, Block base) {
        super(properties);
        this.base = base;
        setDefaultState(this.stateContainer.getBaseState().with(FLOWERS, 2));
    }

    public Block getBase() {
        return base;
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

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        base.onEntityCollision(state, worldIn, pos, entityIn);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Vector3d vector3d = state.getOffset(worldIn, pos);
        return state.get(FLOWERS) == 2 ? base.getShape(state, worldIn, pos, context) : (base instanceof FlowerBlock || base instanceof FungusBlock) ? MULTI_FLOWER_SHAPE.withOffset(vector3d.x, vector3d.y, vector3d.z) : MULTI_MUSHROOM_SHAPE;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        base.randomTick(base.getDefaultState(), worldIn, pos, random);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return new ItemStack(base);
    }

    public AbstractBlock.OffsetType getOffsetType() {
        return base.getOffsetType();
    }

    private void removeOneFlower(World world, BlockPos pos, BlockState state) {
        world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_CROP_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + world.rand.nextFloat() * 0.2F);
        int i = state.get(FLOWERS);
        if (i == 2) {
            world.setBlockState(pos, base.getDefaultState(), 3);
        } else if (i == 1) {
            world.destroyBlock(pos, true, (PlayerEntity)null);
        } else {
            world.setBlockState(pos, state.with(FLOWERS, i - 1), 3);
            world.playEvent(2001, pos, Block.getStateId(state));
        }

        world.addEntity(new ItemEntity(world, pos.getX() + state.getOffset(world, pos).x, pos.getY() + state.getOffset(world, pos).y, pos.getZ() + state.getOffset(world, pos).z, new ItemStack(base)));
    }

    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        this.removeOneFlower(worldIn, pos, state);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack held = player.getHeldItem(handIn);

        if (held.getItem() == getBase().asItem()) {
            int i = state.get(FLOWERS);
            if (i < 4) {
                if (!player.isCreative()) {
                    held.shrink(1);
                }
                player.sendBreakAnimation(handIn);

                worldIn.setBlockState(pos, state.with(FLOWERS, i + 1), 3);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FLOWERS);
    }
}