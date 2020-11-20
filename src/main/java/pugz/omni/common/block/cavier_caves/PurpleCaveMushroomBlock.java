package pugz.omni.common.block.cavier_caves;

import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PurpleCaveMushroomBlock extends CaveMushroomBlock {
    public PurpleCaveMushroomBlock() {
        super(MaterialColor.PURPLE);
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entity, float fallDistance) {
        entity.onLivingFall(fallDistance, 0.0F);

        if (entity instanceof LivingEntity && fallDistance > 5) {
            AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(worldIn, pos.getX(), pos.getY() + 0.5F, pos.getZ());
            areaeffectcloudentity.setRadius(2.0F);
            areaeffectcloudentity.setRadiusOnUse(-0.25F);
            areaeffectcloudentity.setWaitTime(0);
            areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / 75.0F);
            areaeffectcloudentity.setPotion(Potions.POISON);
            areaeffectcloudentity.setColor(7221919);
            worldIn.addEntity(areaeffectcloudentity);
        }
    }
}