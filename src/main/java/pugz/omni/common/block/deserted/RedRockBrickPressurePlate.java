package pugz.omni.common.block.deserted;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class RedRockBrickPressurePlate extends AbstractPressurePlateBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public RedRockBrickPressurePlate() {
        super(Properties.from(Blocks.STONE_PRESSURE_PLATE));
        setDefaultState(this.stateContainer.getBaseState().with(POWERED, false));
    }

    protected int getPoweredDuration() {
        return 20;
    }

    protected int getRedstoneStrength(BlockState state) {
        return state.get(POWERED) ? 15 : 0;
    }

    @Nonnull
    protected BlockState setRedstoneStrength(BlockState state, int strength) {
        return state.with(POWERED, strength > 0);
    }

    protected void playClickOnSound(IWorld worldIn, BlockPos pos) {
        worldIn.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
    }

    protected void playClickOffSound(IWorld worldIn, BlockPos pos) {
        worldIn.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.5F);
    }

    protected int computeRedstoneStrength(World worldIn, BlockPos pos) {
        AxisAlignedBB axisalignedbb = PRESSURE_AABB.offset(pos);
        List<? extends Entity> list;

        list = worldIn.getEntitiesWithinAABBExcludingEntity((Entity)null, axisalignedbb);

        if (!list.isEmpty()) {
            for(Entity entity : list) {
                if (!entity.doesEntityNotTriggerPressurePlate()) {
                    return 15;
                }
            }
        }

        return 0;
    }

    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        if (!world.isRemote) {
            world.setBlockState(pos, state.with(POWERED, false), 3);
            this.updateNeighbors(world, pos);
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (!worldIn.isRemote) {
            int i = this.getRedstoneStrength(state);
            if (i == 0) {
                this.updateState(worldIn, pos, state, i);
            }

            if (!state.get(POWERED)) this.checkPressed(state, worldIn, pos);
        }
    }

    private void checkPressed(BlockState state, World world, BlockPos pos) {
        if (!world.isRemote) {
            List<? extends Entity> list = world.getEntitiesWithinAABB(AbstractArrowEntity.class, state.getShape(world, pos).getBoundingBox().offset(pos));
            boolean flag = !list.isEmpty();
            boolean flag1 = state.get(POWERED);
            if (flag != flag1) {
                world.setBlockState(pos, state.with(POWERED, flag), 3);
                this.updateNeighbors(world, pos);
            }

            if (flag) {
                world.getPendingBlockTicks().scheduleTick(new BlockPos(pos), this, this.getPoweredDuration());
            }
        }
    }

    public void powerBlock(BlockState state, World world, BlockPos pos) {
        if (!world.isRemote) {
            world.setBlockState(pos, state.with(POWERED, true), 3);
            this.updateNeighbors(world, pos);
            world.getPendingBlockTicks().scheduleTick(pos, this, this.getPoweredDuration());
        }
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (state.get(POWERED)) {
            return ActionResultType.CONSUME;
        } else {
            this.powerBlock(state, worldIn, pos);
            return ActionResultType.func_233537_a_(worldIn.isRemote);
        }
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }
}