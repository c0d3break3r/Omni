package pugz.omni.core.util;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public interface IBaseBlock {
    @OnlyIn(Dist.CLIENT)
    default RenderType getRenderType() {
        return RenderType.getSolid();
    }

    default int getFireEncouragement() {
        return 0;
    }

    default int getFireFlammability() {
        return 0;
    }

    default float getCompostChance() {
        return 0.0F;
    }

    @Nullable
    default Block getStrippedBlock() {
        return null;
    }
}