package pugz.omni.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.Property;
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
import net.minecraft.world.server.ServerWorld;
import pugz.omni.core.util.IBaseBlock;

import javax.annotation.Nonnull;
import java.util.Random;

public abstract class AbstractStackableBlock extends BushBlock implements IBaseBlock {
    public AbstractStackableBlock(AbstractBlock.Properties properties) {
        super(properties);
        setDefaultState(this.stateContainer.getBaseState().with(getCountProperty(), 2));
    }

    public abstract VoxelShape getStackedShape();

    public abstract Block getBase();

    public abstract Block getBlock();

    public abstract Property<Integer> getCountProperty();

    @Override
    public RenderType getRenderType() {
        return RenderType.getCutout();
    }

    public void removeOne(World world, BlockPos pos, BlockState state) {
        world.playSound(null, pos, SoundEvents.BLOCK_CROP_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + world.rand.nextFloat() * 0.2F);
        if (!world.isRemote) {
            int i = state.get(getCountProperty());
            switch (i) {
                case 1:
                    world.destroyBlock(pos, true, null);
                    break;
                case 2:
                    world.setBlockState(pos, getBase().getDefaultState(), 3);
                    break;
                default:
                    world.setBlockState(pos, state.with(getCountProperty(), i - 1), 3);
                    world.playEvent(2001, pos, Block.getStateId(state));
                    break;
            }
        }
    }

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        removeOne(worldIn, pos, state);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack held = player.getHeldItem(handIn);

        if (held.getItem() == getBase().asItem()) {
            int i = state.get(getCountProperty());
            if (i < 4) {
                if (!player.isCreative()) {
                    held.shrink(1);
                }

                world.setBlockState(pos, state.with(getCountProperty(), i + 1), 3);
                return ActionResultType.func_233537_a_(world.isRemote);
            }
        }
        return ActionResultType.PASS;
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.get(getCountProperty()) == 2 ? getBase().getShape(state, worldIn, pos, context) : getStackedShape();
    }

    @Nonnull
    @Override
    public OffsetType getOffsetType() {
        return getBase().getOffsetType();
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return getBase().isValidPosition(state, worldIn, pos);
    }

    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        getBase().animateTick(stateIn, worldIn, pos, rand);
    }

    @SuppressWarnings("deprecation")
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        getBase().onEntityCollision(state, worldIn, pos, entityIn);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        getBase().randomTick(getBase().getDefaultState(), worldIn, pos, random);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return new ItemStack(getBase());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(getCountProperty());
    }
}