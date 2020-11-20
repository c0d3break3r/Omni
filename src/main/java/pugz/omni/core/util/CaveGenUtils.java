package pugz.omni.core.util;

import pugz.omni.common.world.feature.cavier_caves.SpeleothemFeatureConfig;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;

import javax.annotation.Nullable;

public class CaveGenUtils {
    /**
     * Gets a floor position at the x and y of the given position
     */
    public static BlockPos.Mutable getCaveFloorPosition(ISeedReader world, BlockPos pos, @Nullable SpeleothemFeatureConfig.Variant variant) {
        BlockPos.Mutable pos$mutable = pos.toMutable();

        for (int y = 0; y <= 128; ++y) {
            pos$mutable.setY(y);

            Block block = world.getBlockState(pos$mutable).getBlock();
            Block up = world.getBlockState(pos$mutable.up()).getBlock();
            Block down = world.getBlockState(pos$mutable.down()).getBlock();

            if (variant != null) {
                if ((block == Blocks.CAVE_AIR || block == Blocks.WATER)
                        && up != Blocks.AIR
                        && isValidCavePos(down, variant)
                        && !world.canBlockSeeSky(pos$mutable)) break;
            } else if ((block == Blocks.CAVE_AIR || block == Blocks.WATER)
                    && up != Blocks.AIR
                    && !world.canBlockSeeSky(pos$mutable)) break;
        }

        return pos$mutable;
    }

    /**
     * Gets the height of the cave at the position we are generating
     */
    public static int getCaveHeight(ISeedReader world, BlockPos pos) {
        BlockPos.Mutable pos$mutable = pos.toMutable();
        int height = 0;

        for (int y = pos.getY(); y <= 128; ++y) {
            pos$mutable.setPos(pos.getX(), y, pos.getZ());
            ++height;

            Block block = world.getBlockState(pos$mutable).getBlock();
            BlockState up = world.getBlockState(pos$mutable.up());

            if ((up.getBlock() != Blocks.CAVE_AIR
                    && block == Blocks.CAVE_AIR) || (((block != Blocks.WATER || block != Blocks.LAVA)
                    && up.getBlock() != Blocks.CAVE_AIR)
                    && (block == Blocks.WATER || block == Blocks.LAVA))) break;
        }

        if (height <= 2 || height > 63) return 0;

        return height;
    }

    public static boolean isValidCavePos(Block block, SpeleothemFeatureConfig.Variant variant) {
        for (Block b : variant.getSpawnableBlocks()) {
            if (b == block) return true;
        }
        return false;
    }
}