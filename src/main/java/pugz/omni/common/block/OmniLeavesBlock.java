package pugz.omni.common.block;

import net.minecraft.block.*;
import net.minecraft.client.renderer.RenderType;
import pugz.omni.core.util.IBaseBlock;

public class OmniLeavesBlock extends LeavesBlock implements IBaseBlock {
    public OmniLeavesBlock() {
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