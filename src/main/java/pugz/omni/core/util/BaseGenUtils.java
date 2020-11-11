package pugz.omni.core.util;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BaseGenUtils {
    public static boolean isBlockWithinRange(World world, BlockPos pos, int rangeX, int rangeY, int rangeZ, Block... blocks) {
        for (double x = pos.getX() - rangeX; x <= pos.getX() + rangeX; x++) {
            for (double y = pos.getY() - rangeY; y <= pos.getY() + rangeY; y++) {
                for (double z = pos.getZ() - rangeZ; z <= pos.getZ() + rangeZ; z++) {

                    for (Block block : blocks) {
                        if (world.getBlockState(new BlockPos(x, y, z)).getBlock() == block) return true;
                    }
                }
            }
        } return false;
    }

    public static boolean isBlockWithinRange(World world, BlockPos pos, int range, Block... blocks) {
        for (double x = pos.getX() - range; x <= pos.getX() + range; x++) {
            for (double y = pos.getY() - range; y <= pos.getY() + range; y++) {
                for (double z = pos.getZ() - range; z <= pos.getZ() + range; z++) {

                    for (Block block : blocks) {
                        if (world.getBlockState(new BlockPos(x, y, z)).getBlock() == block) return true;
                    }
                }
            }
        } return false;
    }
}