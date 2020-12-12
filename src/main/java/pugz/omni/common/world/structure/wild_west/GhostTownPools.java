package pugz.omni.common.world.structure.wild_west;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.BarrelTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPatternRegistry;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.template.*;
import pugz.omni.core.Omni;

import javax.annotation.Nonnull;
import java.util.Random;

public class GhostTownPools {
    public static final JigsawPattern field_244090_a = JigsawPatternRegistry.func_244094_a(new JigsawPattern(new ResourceLocation("ghost_town/centers"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(JigsawPiece.func_242851_a("ghost_town/centers/crossroads_01", ProcessorLists.field_244108_h), 50)), JigsawPattern.PlacementBehaviour.RIGID));

    public static final StructureProcessorList ROADS_PROCESSOR = WorldGenRegistries.register(WorldGenRegistries.STRUCTURE_PROCESSOR_LIST, new ResourceLocation(Omni.MOD_ID, "roads_ghost_town"), new StructureProcessorList(ImmutableList.of(
            new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new BlockMatchRuleTest(Blocks.GRASS_PATH), new BlockMatchRuleTest(Blocks.WATER), Blocks.OAK_PLANKS.getDefaultState()))),
            new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.GRASS_PATH, 0.3F), AlwaysTrueRuleTest.INSTANCE, Blocks.RED_SAND.getDefaultState()))),
            new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new BlockMatchRuleTest(Blocks.RED_SAND), new BlockMatchRuleTest(Blocks.WATER), Blocks.WATER.getDefaultState()))),
            new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new BlockMatchRuleTest(Blocks.DIRT), new BlockMatchRuleTest(Blocks.WATER), Blocks.WATER.getDefaultState())))
    )));

    public static void init() {
    }

    static {
        JigsawPatternRegistry.func_244094_a(new JigsawPattern(new ResourceLocation(Omni.MOD_ID, "ghost_town/streets"), new ResourceLocation("ghost_town/terminators"), ImmutableList.of(
                Pair.of(JigsawPiece.func_242851_a("ghost_town/streets/street_01", ROADS_PROCESSOR), 2),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/streets/street_02", ROADS_PROCESSOR), 2),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/streets/street_03", ROADS_PROCESSOR), 2),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/streets/street_04", ROADS_PROCESSOR), 4),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/streets/street_05", ROADS_PROCESSOR), 4),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/streets/street_06", ROADS_PROCESSOR), 7),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/streets/street_07", ROADS_PROCESSOR), 7)),
                JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING));
        JigsawPatternRegistry.func_244094_a(new JigsawPattern(new ResourceLocation("", "ghost_town/houses"), new ResourceLocation("ghost_town/terminators"), ImmutableList.of(
                Pair.of(JigsawPiece.func_242851_a("ghost_town/houses/house_large_01", ProcessorLists.field_244101_a), 2),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/houses/house_large_02", ProcessorLists.field_244101_a), 2),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/houses/house_large_03", ProcessorLists.field_244101_a), 2),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/houses/house_large_04", ProcessorLists.field_244101_a), 2),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/houses/house_large_05", ProcessorLists.field_244101_a), 2),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/houses/house_medium_01", ProcessorLists.field_244101_a), 1),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/houses/house_medium_02", ProcessorLists.field_244101_a), 2),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/houses/house_medium_03", ProcessorLists.field_244101_a), 3),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/houses/house_medium_04", ProcessorLists.field_244101_a), 2),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/houses/house_medium_05", ProcessorLists.field_244101_a), 2),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/houses/house_medium_06", ProcessorLists.field_244101_a), 2),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/houses/house_small_01", ProcessorLists.field_244101_a), 2),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/houses/house_small_02", ProcessorLists.field_244101_a), 2),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/houses/house_small_03", ProcessorLists.field_244101_a), 2),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/houses/house_small_04", ProcessorLists.field_244101_a), 2),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/houses/house_small_05", ProcessorLists.field_244101_a), 2),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/houses/house_small_06", ProcessorLists.field_244101_a), 2),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/houses/house_small_07", ProcessorLists.field_244101_a), 2),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/houses/house_small_08", ProcessorLists.field_244101_a), 2),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/houses/house_small_09", ProcessorLists.field_244101_a), 1),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/houses/house_small_10", ProcessorLists.field_244101_a), 5),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/houses/house_small_11", ProcessorLists.field_244101_a), 1),
                Pair.of(JigsawPiece.func_242851_a("ghost_town/houses/house_small_12", ProcessorLists.field_244101_a), 2)),
                JigsawPattern.PlacementBehaviour.RIGID));
        JigsawPatternRegistry.func_244094_a(new JigsawPattern(new ResourceLocation("ghost_town/decor"), new ResourceLocation("empty"), ImmutableList.of(
                Pair.of(JigsawPiece.func_242849_a("ghost_town/decor/barrel_decoration"), 2),
                Pair.of(JigsawPiece.func_242849_a("ghost_town/decor/bell_decoration_1"), 2),
                Pair.of(JigsawPiece.func_242849_a("ghost_town/decor/cactus_decoration"), 2),
                Pair.of(JigsawPiece.func_242849_a("ghost_town/decor/hay_decoration"), 2),
                Pair.of(JigsawPiece.func_242849_a("ghost_town/decor/hay_decoration_2"), 2),
                Pair.of(JigsawPiece.func_242849_a("ghost_town/decor/hay_well_decoration"), 2),
                Pair.of(JigsawPiece.func_242849_a("ghost_town/decor/lamp_decoration"), 2),
                Pair.of(JigsawPiece.func_242849_a("ghost_town/decor/lamp_decoration_2"), 2),
                Pair.of(JigsawPiece.func_242849_a("ghost_town/decor/tree_decoration_1"), 2),
                Pair.of(JigsawPiece.func_242849_a("ghost_town/decor/tree_decoration_2"), 2),
                Pair.of(JigsawPiece.func_242849_a("ghost_town/decor/trough_decoration"), 2),
                Pair.of(JigsawPiece.func_242849_a("ghost_town/decor/water_tower_1"), 2),
                Pair.of(JigsawPiece.func_242849_a("ghost_town/decor/water_tower_2"), 2),
                Pair.of(JigsawPiece.func_242849_a("ghost_town/decor/well_decoration"), 2)),
                JigsawPattern.PlacementBehaviour.RIGID));
    }

    public static class GhostTownLootProcessor extends StructureProcessor {
        public static final GhostTownLootProcessor INSTANCE = new GhostTownLootProcessor();
        public static final Codec<GhostTownLootProcessor> CODEC = Codec.unit(() -> INSTANCE);

        @Override
        public Template.BlockInfo process(IWorldReader world, BlockPos pos, BlockPos blockPos, Template.BlockInfo info, Template.BlockInfo info2, PlacementSettings data, Template template) {
            BlockState blockState = info2.state;
            if (blockState.getBlock() == Blocks.BARREL) {
                TileEntity te = world.getTileEntity(blockPos);
                if (te instanceof BarrelTileEntity) {
                    Random random = new Random();
                    BarrelTileEntity barrel = (BarrelTileEntity) te;
                    barrel.setLootTable(new ResourceLocation(Omni.MOD_ID, "ghost_town/loot_" + random.nextInt(3)), random.nextLong());
                }
            }

            return info2;
        }

        @Nonnull
        @Override
        protected IStructureProcessorType<?> getType() {
            return GhostTownStructure.GHOST_TOWN_PROCESSOR;
        }
    }
}