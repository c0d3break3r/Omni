package pugz.omni.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.client.renderer.RenderType;
import pugz.omni.core.util.IBaseBlock;

public class OmniSaplingBlock extends SaplingBlock implements IBaseBlock {
    public OmniSaplingBlock(Tree tree) {
        super(tree, AbstractBlock.Properties.from(Blocks.OAK_SAPLING));
    }

    @Override
    public RenderType getRenderType() {
        return RenderType.getCutout();
    }

    @Override
    public float getCompostChance() {
        return 0.3F;
    }
}