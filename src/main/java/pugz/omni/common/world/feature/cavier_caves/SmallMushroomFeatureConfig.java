package pugz.omni.common.world.feature.cavier_caves;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class SmallMushroomFeatureConfig implements IFeatureConfig {
    public static final Codec<SmallMushroomFeatureConfig> CODEC = RecordCodecBuilder.create((p_236568_0_) -> {
        return p_236568_0_.group(BlockState.CODEC.fieldOf("state").forGetter((config) -> {
            return config.state;
        })).apply(p_236568_0_, SmallMushroomFeatureConfig::new);
    });
    public final BlockState state;

    public SmallMushroomFeatureConfig(BlockState state) {
        this.state = state;
    }
}