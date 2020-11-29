package pugz.omni.common.block.forestry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import pugz.omni.core.base.IBaseBlock;

public class GoldenOakLeavesBlock extends Block implements IBaseBlock {
    public GoldenOakLeavesBlock() {
        super(AbstractBlock.Properties.from(Blocks.OAK_LEAVES));
    }

    @Override
    public int getFireEncouragement() {
        return 30;
    }

    @Override
    public int getFireFlammability() {
        return 60;
    }

    @Override
    public RenderType getRenderType() {
        return RenderType.getCutoutMipped();
    }
}