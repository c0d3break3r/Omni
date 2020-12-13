package pugz.omni.common.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class SizedBlockBlobConfig implements IFeatureConfig {
    public static final Codec<SizedBlockBlobConfig> CODEC = RecordCodecBuilder.create((p_236568_0_) -> {
        return p_236568_0_.group(BlockState.CODEC.fieldOf("state").forGetter((config) -> {
            return config.state;
        }), Codec.intRange(0, 64).fieldOf("radius").forGetter((config) -> {
            return config.radius;
        })).apply(p_236568_0_, SizedBlockBlobConfig::new);
    });
    public final BlockState state;
    public final int radius;

    public SizedBlockBlobConfig(BlockState state, int radius) {
        this.state = state;
        this.radius = radius;
    }
}