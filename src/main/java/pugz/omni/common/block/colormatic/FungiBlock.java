package pugz.omni.common.block.colormatic;

import pugz.omni.common.block.AbstractStackableBlock;
import pugz.omni.common.block.IStackable;
import net.minecraft.block.*;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.HugeFungusConfig;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.function.Supplier;

public class FungiBlock extends AbstractStackableBlock implements IGrowable, IStackable {
    private static final VoxelShape MULTI_FUNGUS_SHAPE = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 9.0D, 13.0D);
    public static final IntegerProperty FUNGI = IntegerProperty.create("fungi", 1, 4);
    private final Block base;
    private final Supplier<ConfiguredFeature<HugeFungusConfig, ?>> fungusFeature;

    public FungiBlock(Properties properties, Block base, Supplier<ConfiguredFeature<HugeFungusConfig, ?>> fungusFeature) {
        super(properties);
        this.base = base;
        this.fungusFeature = fungusFeature;
    }

    @Override
    public VoxelShape getStackedShape() {
        return MULTI_FUNGUS_SHAPE;
    }

    @Override
    public Block getBase() {
        return base;
    }

    @Override
    public Block getBlock() {
        return this;
    }

    @Override
    public Property<Integer> getCountProperty() {
        return FUNGI;
    }

    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.isIn(BlockTags.NYLIUM) || state.isIn(Blocks.MYCELIUM) || state.isIn(Blocks.SOUL_SOIL) || super.isValidGround(state, worldIn, pos);
    }

    @Nonnull
    @SuppressWarnings("deprecated")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.get(FUNGI) == 2 ? base.getShape(state, worldIn, pos, context) : MULTI_FUNGUS_SHAPE;
    }

    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        Block block = ((HugeFungusConfig)(this.fungusFeature.get()).config).field_236303_f_.getBlock();
        Block block1 = worldIn.getBlockState(pos.down()).getBlock();
        return block1 == block;
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return (double)rand.nextFloat() < 0.4D * state.get(FUNGI);
    }

    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        this.fungusFeature.get().generate(worldIn, worldIn.getChunkProvider().getChunkGenerator(), rand, pos);
    }
}