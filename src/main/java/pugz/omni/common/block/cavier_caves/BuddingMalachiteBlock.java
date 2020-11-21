package pugz.omni.common.block.cavier_caves;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import pugz.omni.core.module.CoreModule;
import pugz.omni.core.registry.OmniBlocks;
import pugz.omni.core.registry.OmniSoundEvents;

import javax.annotation.Nonnull;
import java.util.Random;

public class BuddingMalachiteBlock extends Block {
    public BuddingMalachiteBlock() {
        super(Properties.from(OmniBlocks.MALACHITE_BLOCK.get()).noDrops());
    }

    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);

        if (!worldIn.isRemote) {
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

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        if (worldIn.rand.nextInt(16) == 0 && !worldIn.isRemote) worldIn.playSound(null, pos, OmniSoundEvents.CRYSTAL_PLACE.get(), SoundCategory.BLOCKS, 1.0F, 0.5F + worldIn.rand.nextFloat() * 1.2F);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!worldIn.isAreaLoaded(pos, 1)) return;

        if (!worldIn.isRemote) {
            Direction direction = Direction.byIndex(random.nextInt(Direction.values().length));
            BlockPos place = pos.offset(direction);
            BlockState placeState = worldIn.getBlockState(place);

            if (ForgeHooks.onCropsGrowPre(worldIn, place, placeState, random.nextInt(CoreModule.Configuration.CLIENT.BUDDING_MALACHITE_GROWTH_CHANCE.get()) == 1)) {
                if (worldIn.isAirBlock(place) || worldIn.getBlockState(place).getMaterial().isLiquid()) {
                    worldIn.setBlockState(place, OmniBlocks.SMALL_MALACHITE_BUD.get().getDefaultState().with(MalachiteBudBlock.FACING, direction).with(MalachiteBudBlock.WATERLOGGED, worldIn.getFluidState(place).isTagged(FluidTags.WATER)), 3);
                } else {
                    switch (placeState.getBlock().getRegistryName().getPath()) {
                        case "small_malachite_bud":
                            worldIn.setBlockState(place, OmniBlocks.MEDIUM_MALACHITE_BUD.get().getDefaultState().with(MalachiteBudBlock.FACING, direction).with(MalachiteBudBlock.WATERLOGGED, worldIn.getFluidState(place).isTagged(FluidTags.WATER)), 3);
                            break;
                        case "medium_malachite_bud":
                            worldIn.setBlockState(place, OmniBlocks.LARGE_MALACHITE_BUD.get().getDefaultState().with(MalachiteBudBlock.FACING, direction).with(MalachiteBudBlock.WATERLOGGED, worldIn.getFluidState(place).isTagged(FluidTags.WATER)), 3);
                            break;
                        case "large_malachite_bud":
                            worldIn.setBlockState(place, OmniBlocks.MALACHITE_CLUSTER.get().getDefaultState().with(MalachiteBudBlock.FACING, direction).with(MalachiteBudBlock.WATERLOGGED, worldIn.getFluidState(place).isTagged(FluidTags.WATER)), 3);
                            break;
                        default:
                            return;
                    }
                }
                ForgeHooks.onCropsGrowPost(worldIn, pos, state);
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static boolean isStateAir(BlockState state) {
        return state.isAir() || state.isIn(Blocks.AIR) || state.getMaterial().isLiquid();
    }
}