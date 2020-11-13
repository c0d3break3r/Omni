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
        worldIn.playSound(player, pos, OmniSoundEvents.CRYSTAL_BREAK.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onProjectileCollision(World world, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile) {
        world.playSound(null, hit.getPos(), OmniSoundEvents.CRYSTAL_BREAK.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (placer instanceof PlayerEntity) worldIn.playSound((PlayerEntity) placer, pos, OmniSoundEvents.CRYSTAL_PLACE.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
        else worldIn.playSound(null, pos, OmniSoundEvents.CRYSTAL_PLACE.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof PlayerEntity) worldIn.playSound((PlayerEntity) entityIn, pos, OmniSoundEvents.CRYSTAL_STEP.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
        else worldIn.playSound(null, pos, OmniSoundEvents.CRYSTAL_STEP.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
    }
}