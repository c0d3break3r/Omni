package pugz.omni.common.block.cavier_caves;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import pugz.omni.core.util.IBaseBlock;
import pugz.omni.core.registry.OmniSoundEvents;

public class BlueCaveMushroomBlock extends CaveMushroomBlock implements IBaseBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty LIGHT = IntegerProperty.create("light", 2, 14);

    public BlueCaveMushroomBlock() {
        super(MaterialColor.BLUE);
        this.setDefaultState(this.stateContainer.getBaseState().with(LIGHT, 8).with(WATERLOGGED, false));
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return state.get(LIGHT);
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entity, float fallDistance) {
        BlockState state = worldIn.getBlockState(pos);
        entity.onLivingFall(fallDistance, 0.0F);

        if (entity instanceof LivingEntity && fallDistance >= 1.0F) {
            if (!worldIn.isRemote && fallDistance > 1.0F) worldIn.playSound(null, pos, OmniSoundEvents.MUSHROOM_BOUNCE.get(), SoundCategory.BLOCKS, 0.75F, 0.5F + worldIn.rand.nextFloat() * 1.2F);

            if (entity.isSuppressingBounce()) {
                worldIn.setBlockState(pos, state.with(LIGHT, MathHelper.clamp(state.get(LIGHT) - 2, 2, 14)), 3);
            } else {
                worldIn.setBlockState(pos, state.with(LIGHT, MathHelper.clamp(state.get(LIGHT) + 2, 2, 14)), 3);
            }
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIGHT, WATERLOGGED);
    }
}