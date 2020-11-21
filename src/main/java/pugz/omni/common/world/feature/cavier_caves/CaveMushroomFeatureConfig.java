package pugz.omni.common.world.feature.cavier_caves;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class CaveMushroomFeatureConfig implements IFeatureConfig {
    public static final Codec<CaveMushroomFeatureConfig> CODEC = RecordCodecBuilder.create((p_236568_0_) -> {
        return p_236568_0_.group(BlockState.CODEC.fieldOf("state").forGetter((config) -> {
            return config.state;
        }), BlockState.CODEC.fieldOf("smallState").forGetter((config) -> {
            return config.smallState;
        })).apply(p_236568_0_, CaveMushroomFeatureConfig::new);
    });
    public final BlockState state;
    public final BlockState smallState;

    public CaveMushroomFeatureConfig(BlockState state, BlockState smallState) {
        this.state = state;
        this.smallState = smallState;
    }
}