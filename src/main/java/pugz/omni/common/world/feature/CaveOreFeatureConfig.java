package pugz.omni.common.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class CaveOreFeatureConfig implements IFeatureConfig {
    public static final Codec<CaveOreFeatureConfig> CODEC = RecordCodecBuilder.create((p_236568_0_) -> {
        return p_236568_0_.group(RuleTest.field_237127_c_.fieldOf("target").forGetter((config) -> {
            return config.target;
        }), BlockState.CODEC.fieldOf("state").forGetter((config) -> {
            return config.state;
        }), Codec.intRange(0, 64).fieldOf("size").forGetter((config) -> {
            return config.size;
        }), CaveFace.codec.fieldOf("variant").forGetter((config) -> {
            return config.face;
        })).apply(p_236568_0_, CaveOreFeatureConfig::new);
    });
    public final RuleTest target;
    public final int size;
    public final BlockState state;
    public final CaveFace face;

    public CaveOreFeatureConfig(RuleTest ruleTest, BlockState state, int size, CaveFace face) {
        this.size = size;
        this.state = state;
        this.target = ruleTest;
        this.face = face;
    }

    public static final class FillerBlockType {
        public static final RuleTest BASE_STONE_OVERWORLD = new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD);
        public static final RuleTest NETHERRACK = new BlockMatchRuleTest(Blocks.NETHERRACK);
        public static final RuleTest BASE_STONE_NETHER = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
    }

    public enum CaveFace implements IStringSerializable{
        FLOOR("floor"),
        CEILING("ceiling"),
        WALLS("walls"),
        FLOOR_CEILING("floor_ceiling"),
        ALL("all");

        public static final Codec<CaveFace> codec = IStringSerializable.createEnumCodec(CaveFace::values, CaveFace::byName);
        private final String name;
        private static final Map<String, CaveFace> VALUES_MAP = Arrays.stream(values()).collect(Collectors.toMap(CaveFace::getString, (p_236573_0_) -> {
            return p_236573_0_;
        }));
        CaveFace(String name) {
            this.name = name;
        }

        @Nonnull
        public String getString() {
            return this.name;
        }

        public static CaveFace byName(String nameIn) {
            return VALUES_MAP.get(nameIn);
        }
    }
}
