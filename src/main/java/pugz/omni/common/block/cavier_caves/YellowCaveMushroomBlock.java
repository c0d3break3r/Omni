package pugz.omni.common.block.cavier_caves;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pugz.omni.core.base.IBaseBlock;

public class YellowCaveMushroomBlock extends CaveMushroomBlock implements IBaseBlock {
    public YellowCaveMushroomBlock() {
        super(MaterialColor.YELLOW);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof ProjectileEntity) {
            entityIn.entityCollisionReduction = Float.MAX_VALUE;
        }
    }
}