package pugz.omni.common.world.feature.cavier_caves;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;

public class CaveBiomeFeatureConfig implements IFeatureConfig {
    public static final Codec<CaveBiomeFeatureConfig> CODEC = RecordCodecBuilder.create((p_236568_0_) -> {
        return p_236568_0_.group(BlockState.CODEC.fieldOf("floorState").forGetter((config) -> {
            return config.floorState;
        }), BlockState.CODEC.fieldOf("ceilingState").forGetter((config) -> {
            return config.ceilingState;
        }), BlockState.CODEC.fieldOf("wallState").forGetter((config) -> {
            return config.wallState;
        }), BlockState.CODEC.fieldOf("fillerState").forGetter((config) -> {
            return config.wallState;
        }), Codec.intRange(0, 64).fieldOf("size").forGetter((config) -> {
            return config.size;
        }), Codec.floatRange(0.0F, 64.0F).fieldOf("featureChance").forGetter((config) -> {
            return config.featureChance;
        }), RuleTest.field_237127_c_.fieldOf("target").forGetter((config) -> {
            return config.target;
        }), Codec.BOOL.fieldOf("ridgedFloor").orElse(false).forGetter((config) -> {
            return config.ridged;
        })).apply(p_236568_0_, CaveBiomeFeatureConfig::new);
    });

    public final BlockState floorState;
    public final BlockState ceilingState;
    public final BlockState wallState;
    public final BlockState fillerState;
    public final int size;
    public final float featureChance;
    public final RuleTest target;
    public final boolean ridged;

    public CaveBiomeFeatureConfig(BlockState floorState, BlockState ceilingState, BlockState wallState, BlockState fillerState, int size, float featureChance, RuleTest target, boolean ridged) {
        this.floorState = floorState;
        this.ceilingState = ceilingState;
        this.wallState = wallState;
        this.fillerState = fillerState;
        this.size = size;
        this.featureChance = featureChance;
        this.target = target;
        this.ridged = ridged;
    }
}