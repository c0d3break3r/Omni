package pugz.omni.common.block.cavier_caves;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import pugz.omni.core.base.IBaseBlock;
import pugz.omni.core.module.CoreModule;
import pugz.omni.core.registry.OmniBlocks;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class ArctissBlock extends Block implements IWaterLoggable, IBaseBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 6.0D, 12.0D);

    public ArctissBlock() {
        super(AbstractBlock.Properties.create(Material.TALL_PLANTS).hardnessAndResistance(0.0F).sound(SoundType.GLASS));
        setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, false));
    }

    @Override
    public RenderType getRenderType() {
        return RenderType.getCutout();
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Vector3d offset = state.getOffset(worldIn, pos);
        return SHAPE.withOffset(offset.x, offset.y, offset.z);
    }

    @Nonnull
    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    protected boolean isValidGround(BlockState state) {
        return state.isIn(Tags.Blocks.STONE) || state.getBlock() == OmniBlocks.ARCTISS_BLOCK.get();
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @SuppressWarnings("deprecation")
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.down();
        return this.isValidGround(worldIn.getBlockState(blockpos));
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onProjectileCollision(World worldIn, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile) {
        BlockPos pos = hit.getPos();
        if (worldIn.isRemote) {
            Random random = worldIn.getRandom();
            for (int i = 0; i < 20; ++i) {
                worldIn.addOptionalParticle(new BlockParticleData(ParticleTypes.FALLING_DUST, Blocks.SNOW_BLOCK.getDefaultState()), true, (double) pos.getX() + 0.75D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1), (double) pos.getY() + random.nextDouble() + random.nextDouble() * (double) (random.nextBoolean() ? 1 : -1), (double) pos.getZ() + 0.75D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1), 0.0D, 0.045D, 0.0D);
                worldIn.addParticle(new BlockParticleData(ParticleTypes.FALLING_DUST, Blocks.SNOW_BLOCK.getDefaultState()), (double)pos.getX() + 0.5D + random.nextDouble() / 2.0D * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble() * (double) (random.nextBoolean() ? 1 : -1), (double)pos.getZ() + 0.5D + random.nextDouble() / 2.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.03D, 0.0D);
            }
        }

        List<Entity> list = worldIn.getEntitiesInAABBexcluding(null, new AxisAlignedBB(pos.getX() - 0.5D, pos.getY() - 0.5D, pos.getZ() - 0.5D, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D), Entity::isAlive);
        for (Entity target : list) {
            if (target instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) target;
                living.addPotionEffect(new EffectInstance(Effects.SLOWNESS, CoreModule.Configuration.COMMON.ARCTISS_FREEZE_DURATION.get(), 10));
            }
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return false;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }
}