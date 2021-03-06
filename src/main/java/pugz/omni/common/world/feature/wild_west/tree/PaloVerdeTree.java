package pugz.omni.common.world.feature.wild_west.tree;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import pugz.omni.core.registry.OmniFeatures;

import javax.annotation.Nullable;
import java.util.Random;

public class PaloVerdeTree extends Tree {
    @Nullable
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive) {
        return OmniFeatures.Configured.PALO_VERDE_TREE;
    }
}