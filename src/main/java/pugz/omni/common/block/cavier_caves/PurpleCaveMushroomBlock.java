package pugz.omni.common.block.cavier_caves;

import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import pugz.omni.core.base.IBaseBlock;
import pugz.omni.core.registry.OmniSoundEvents;

public class PurpleCaveMushroomBlock extends CaveMushroomBlock implements IBaseBlock {
    public PurpleCaveMushroomBlock() {
        super(MaterialColor.PURPLE);
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entity, float fallDistance) {
        entity.onLivingFall(fallDistance, 0.0F);

        if (entity instanceof LivingEntity && fallDistance >= 4.0F && !entity.isSuppressingBounce()) {
            worldIn.playSound(null, pos, OmniSoundEvents.MUSHROOM_BOUNCE.get(), SoundCategory.BLOCKS, 0.75F, 0.5F + worldIn.rand.nextFloat() * 1.2F);

            AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(worldIn, pos.getX(), pos.getY() + 0.5F, pos.getZ());
            areaeffectcloudentity.setRadius(MathHelper.clamp(fallDistance * 0.25F, 0.0F, 5.0F));
            areaeffectcloudentity.setRadiusOnUse(-0.25F);
            areaeffectcloudentity.setWaitTime(0);
            areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / MathHelper.clamp(fallDistance * 10.0F, 0.0F, 600.0F));
            areaeffectcloudentity.setPotion(new Potion(new EffectInstance(Effects.POISON, Math.round(fallDistance * 15.0F))));
            areaeffectcloudentity.setColor(7221919);
            worldIn.addEntity(areaeffectcloudentity);
        }
    }
}