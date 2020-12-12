package pugz.omni.common.block.wild_west;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DeadBushBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pugz.omni.common.entity.wild_west.TumbleweedEntity;
import pugz.omni.core.util.IBaseBlock;

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
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!world.isRemote) {
            TumbleweedEntity entity = new TumbleweedEntity(world, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D);
            world.addEntity(entity);
            world.removeBlock(pos, false);
        }
    }
}