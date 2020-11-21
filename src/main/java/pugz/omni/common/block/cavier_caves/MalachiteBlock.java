package pugz.omni.common.block.cavier_caves;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import pugz.omni.core.registry.OmniSoundEvents;

public class MalachiteBlock extends Block {
    public MalachiteBlock() {
        super(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.WARPED_WART).hardnessAndResistance(4.5F, 10.0F).sound(SoundType.SNOW));
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isRemote) {
            worldIn.playSound(null, pos, OmniSoundEvents.CRYSTAL_BREAK.get(), SoundCategory.BLOCKS, 1.0F, 0.5F + worldIn.rand.nextFloat() * 1.2F);
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onProjectileCollision(World world, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile) {
        if (!world.isRemote) {
            world.playSound(null, hit.getPos(), OmniSoundEvents.CRYSTAL_BREAK.get(), SoundCategory.BLOCKS, 1.0F, 0.5F + world.rand.nextFloat() * 1.2F);
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (!worldIn.isRemote) {
            worldIn.playSound(null, pos, OmniSoundEvents.CRYSTAL_PLACE.get(), SoundCategory.BLOCKS, 1.0F, 0.5F + worldIn.rand.nextFloat() * 1.2F);
        }
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        if (worldIn.rand.nextInt(32) == 0 && !worldIn.isRemote && !entityIn.isSneaking()) worldIn.playSound(null, pos, OmniSoundEvents.CRYSTAL_PLACE.get(), SoundCategory.BLOCKS, 1.0F, 0.5F + worldIn.rand.nextFloat() * 1.2F);
    }
}