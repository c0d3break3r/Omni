package pugz.omni.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import pugz.omni.core.util.IBaseBlock;

import javax.annotation.Nullable;

public class OmniLogBlock extends RotatedPillarBlock implements IBaseBlock {
    private final Block stripped;

    public OmniLogBlock(Block stripped) {
        super(AbstractBlock.Properties.from(Blocks.OAK_LOG));
        this.stripped = stripped;
    }

    @Override
    public int getFireFlammability() {
        return 5;
    }

    @Override
    public int getFireEncouragement() {
        return 5;
    }

    @Nullable
    @Override
    public Block getStrippedBlock() {
        return stripped;
    }
}