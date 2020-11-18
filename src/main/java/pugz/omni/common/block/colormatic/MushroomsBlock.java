package pugz.omni.common.block.colormatic;

import pugz.omni.common.block.AbstractStackableBlock;
import net.minecraft.block.*;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.Supplier;

public class MushroomsBlock extends AbstractStackableBlock implements IGrowable {
    private static final VoxelShape MULTI_MUSHROOM_SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 6.0D, 12.0D);
    public static final IntegerProperty MUSHROOMS = IntegerProperty.create("mushrooms", 1, 4);
    private final Block base;
    private final Supplier<ConfiguredFeature<?, ?>> mushroomsFeature;

    public MushroomsBlock(Properties properties, Block base, Supplier<ConfiguredFeature<?, ?>> mushroomsFeature) {
        super(properties);
        this.base = base;
        this.mushroomsFeature = mushroomsFeature;
    }

    @Override
    public VoxelShape getStackedShape() {
        return MULTI_MUSHROOM_SHAPE;
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
        return MUSHROOMS;
    }

    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.isOpaqueCube(worldIn, pos);
    }

    public boolean grow(ServerWorld world, BlockPos pos, BlockState state, Random rand) {
        if (!world.isRemote) {
            world.removeBlock(pos, false);
            if (mushroomsFeature.get().generate(world, world.getChunkProvider().getChunkGenerator(), rand, pos)) {
                return true;
            } else {
                world.setBlockState(pos, state, 3);
                return false;
            }
        }
        return false;
    }

    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return (double)rand.nextFloat() < 0.4D * state.get(MUSHROOMS);
    }

    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        this.grow(worldIn, pos, state, rand);
    }
}