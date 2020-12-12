package pugz.omni.common.block.wild_west;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DeadBushBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import pugz.omni.core.registry.OmniEntities;
import pugz.omni.core.util.IBaseBlock;

import javax.annotation.Nonnull;
import java.util.Random;

public class TumbleweedBlock extends DeadBushBlock implements IBaseBlock {
    public TumbleweedBlock() {
        super(AbstractBlock.Properties.from(Blocks.DEAD_BUSH).lootFrom(Blocks.DEAD_BUSH).tickRandomly());
    }

    @Override
    public int getFireFlammability() {
        return 60;
    }

    @Override
    public int getFireEncouragement() {
        return 100;
    }

    @Override
    public RenderType getRenderType() {
        return RenderType.getCutout();
    }

    @SuppressWarnings("deprecation")
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        worldIn.getPendingBlockTicks().scheduleTick(pos, this, 2);
    }

    @Nonnull
    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 2);
        return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        if (!world.isRemote) {
            Entity entity = OmniEntities.TUMBLEWEED.get().spawn(world, null, null, new BlockPos((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D), SpawnReason.NATURAL, false, false);
            world.addEntity(entity);
            world.removeBlock(pos, false);
        }
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }
}