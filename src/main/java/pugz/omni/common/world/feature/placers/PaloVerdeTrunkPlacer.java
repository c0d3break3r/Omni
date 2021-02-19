package pugz.omni.common.world.feature.placers;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;
import pugz.omni.core.registry.OmniFeatures;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PaloVerdeTrunkPlacer extends AbstractTrunkPlacer {
    public static final Codec<PaloVerdeTrunkPlacer> CODEC = RecordCodecBuilder.create((p_236883_0_) -> {
        return func_236915_a_(p_236883_0_).apply(p_236883_0_, PaloVerdeTrunkPlacer::new);
    });

    public PaloVerdeTrunkPlacer(int baseHeight, int p_i232053_2_, int p_i232053_3_) {
        super(baseHeight, p_i232053_2_, p_i232053_3_);
    }

    @Nonnull
    protected TrunkPlacerType<?> func_230381_a_() {
        return OmniFeatures.Placers.PALO_VERDE_TRUNK_PLACER;
    }

    @Nonnull
    public List<FoliagePlacer.Foliage> func_230382_a_(IWorldGenerationReader world, Random random, int p_230382_3_, BlockPos pos, Set<BlockPos> p_230382_5_, MutableBoundingBox boundingBox, BaseTreeFeatureConfig config) {
        func_236909_a_(world, pos.down());
        List<FoliagePlacer.Foliage> list = Lists.newArrayList();
        Direction direction = Direction.Plane.HORIZONTAL.random(random);
        int i = p_230382_3_ - random.nextInt(3) - 1;
        int j = 3 - random.nextInt(3);
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        int k = pos.getX();
        int l = pos.getZ();
        int i1 = 0;

        for(int j1 = 0; j1 < p_230382_3_; ++j1) {
            int k1 = pos.getY() + j1;
            if (j1 >= i && j > 0) {
                k += direction.getXOffset();
                l += direction.getZOffset();
                --j;
            }

            if (func_236911_a_(world, random, blockpos$mutable.setPos(k, k1, l), p_230382_5_, boundingBox, config)) {
                i1 = k1 + 1;
            }
        }

        list.add(new FoliagePlacer.Foliage(new BlockPos(k, i1, l), 1, false));
        k = pos.getX();
        l = pos.getZ();
        Direction direction1 = Direction.Plane.HORIZONTAL.random(random);
        if (direction1 != direction) {
            int k2 = i - random.nextInt(2);
            int l1 = 1 + random.nextInt(3);
            i1 = 0;

            for(int i2 = k2; i2 < p_230382_3_ && l1 > 0; --l1) {
                if (i2 >= 1) {
                    int j2 = pos.getY() + i2;
                    k += direction1.getXOffset();
                    l += direction1.getZOffset();
                    if (func_236911_a_(world, random, blockpos$mutable.setPos(k, j2, l), p_230382_5_, boundingBox, config)) {
                        i1 = j2 + 1;
                    }
                }

                ++i2;
            }

            if (i1 > 1) {
                list.add(new FoliagePlacer.Foliage(new BlockPos(k, i1, l), 0, false));
            }
        }

        return list;
    }
}
