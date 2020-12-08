package pugz.omni.core.util;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class CompatBlocks {
    public static final Block AUTUMN_CROCUS = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("autumnity", "autumn_crocus"));
    public static final Block BUTTERCUP = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("buzzier_bees", "buttercup"));
    public static final Block GLOWSHROOM = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("quark", "glowshroom"));
    public static final Block HOT_MONKEY_BRUSH = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("atmospheric", "hot_monkey_brush"));
    public static final Block SCALDING_MONKEY_BRUSH = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("atmospheric", "scalding_monkey_brush"));
    public static final Block WARM_MONKEY_BRUSH = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("atmospheric", "warm_monkey_brush"));
}