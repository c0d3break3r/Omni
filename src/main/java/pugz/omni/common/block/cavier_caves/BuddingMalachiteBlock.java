package pugz.omni.common.block.cavier_caves;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import pugz.omni.core.registry.OmniBlocks;

import java.util.Random;

public class BuddingMalachiteBlock extends Block {
    public BuddingMalachiteBlock() {
        super(Properties.from(OmniBlocks.MALACHITE_BLOCK.get()).noDrops());
    }

    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!worldIn.isAreaLoaded(pos, 1)) return;

        Direction direction = Direction.byIndex(random.nextInt(Direction.values().length));
        BlockPos place = pos.offset(direction);
        BlockState placeState = worldIn.getBlockState(place);

        if (ForgeHooks.onCropsGrowPre(worldIn, place, placeState, random.nextInt(5) == 1)) {
            if (worldIn.isAirBlock(place)) {
                worldIn.setBlockState(place, OmniBlocks.SMALL_MALACHITE_BUD.get().getDefaultState().with(MalachiteBudBlock.FACING, direction).with(MalachiteBudBlock.WATERLOGGED, worldIn.getFluidState(place).isTagged(FluidTags.WATER)), 3);
            } else {
                switch (placeState.getBlock().getRegistryName().getPath()) {
                    case "small_malachite_bud":
                        worldIn.setBlockState(place, OmniBlocks.MEDIUM_MALACHITE_BUD.get().getDefaultState().with(MalachiteBudBlock.FACING, direction).with(MalachiteBudBlock.WATERLOGGED, worldIn.getFluidState(place).isTagged(FluidTags.WATER)), 3);
                        break;
                    case "medium_malachite_bud":
                        worldIn.setBlockState(place, OmniBlocks.LARGE_MALACHITE_BUD.get().getDefaultState().with(MalachiteBudBlock.FACING, direction).with(MalachiteBudBlock.WATERLOGGED,worldIn.getFluidState(place).isTagged(FluidTags.WATER)), 3);
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