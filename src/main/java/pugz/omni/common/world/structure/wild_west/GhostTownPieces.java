package pugz.omni.common.world.structure.wild_west;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPatternRegistry;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.template.ProcessorLists;

public class GhostTownPieces {
    public static final JigsawPattern pieces = JigsawPatternRegistry.func_244094_a(new JigsawPattern(new ResourceLocation("bastion/starts"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(JigsawPiece.func_242861_b("bastion/units/air_base", ProcessorLists.field_244124_x), 1), Pair.of(JigsawPiece.func_242861_b("bastion/hoglin_stable/air_base", ProcessorLists.field_244124_x), 1), Pair.of(JigsawPiece.func_242861_b("bastion/treasure/big_air_full", ProcessorLists.field_244124_x), 1), Pair.of(JigsawPiece.func_242861_b("bastion/bridge/starting_pieces/entrance_base", ProcessorLists.field_244124_x), 1)), JigsawPattern.PlacementBehaviour.RIGID));

    public static JigsawPattern register(JigsawPattern pattern) {
        return WorldGenRegistries.register(WorldGenRegistries.JIGSAW_POOL, pattern.getName(), pattern);
    }

    public static void func_236258_a_() {
        GhostTownPools.init();
    }
}