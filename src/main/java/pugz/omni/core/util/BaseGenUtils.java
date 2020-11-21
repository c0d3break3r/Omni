package pugz.omni.core.util;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;

public class BaseGenUtils {
    public static boolean isBlockWithinRange(World world, BlockPos pos, int rangeX, int rangeY, int rangeZ, Block... blocks) {
        for (int x = pos.getX() - rangeX; x <= pos.getX() + rangeX; x++) {
            for (int y = pos.getY() - rangeY; y <= pos.getY() + rangeY; y++) {
                for (int z = pos.getZ() - rangeZ; z <= pos.getZ() + rangeZ; z++) {

                    for (Block block : blocks) {
                        if (world.getBlockState(new BlockPos(x, y, z)).getBlock() == block) return true;
                    }
                }
            }
        } return false;
    }

    public static boolean isBlockWithinRange(World world, BlockPos pos, int range, Block... blocks) {
        for (int x = pos.getX() - range; x <= pos.getX() + range; x++) {
            for (int y = pos.getY() - range; y <= pos.getY() + range; y++) {
                for (int z = pos.getZ() - range; z <= pos.getZ() + range; z++) {

                    for (Block block : blocks) {
                        if (world.getBlockState(new BlockPos(x, y, z)).getBlock() == block) return true;
                    }
                }
            }
        } return false;
    }

    public static List<BlockPos> getBlocksWithinRange(World world, BlockPos pos, int range, Block block) {
        List<BlockPos> positions = new LinkedList<BlockPos>();
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        for (int x = pos.getX() - range; x <= pos.getX() + range; x++) {
            for (int y = pos.getY() - range; y <= pos.getY() + range; y++) {
                for (int z = pos.getZ() - range; z <= pos.getZ() + range; z++) {
                    blockpos$mutable.setPos(x, y, z);
                    if (world.getBlockState(blockpos$mutable).getBlock() == block) positions.add(blockpos$mutable);
                }
            }
        } return positions;
    }

    public static boolean isAirPresent(ISeedReader world, BlockPos minimum, BlockPos maximum, float percent) {
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        int volume = (maximum.getX() - minimum.getX()) * (maximum.getZ() - minimum.getZ()) * (maximum.getY() - minimum.getY());

        int air = 0;
        for (int x = minimum.getX(); x <= maximum.getX(); ++x) {
            for (int y = minimum.getY(); y <= maximum.getY(); ++y) {
                for (int z = minimum.getZ(); z <= maximum.getZ(); ++z) {
                    blockpos$mutable.setPos(x, y, z);
                    if (world.isAirBlock(blockpos$mutable)) ++air;
                }
            }
        }

        return (float)air / volume < percent;
    }
}