package pugz.omni.core.util;

import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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

    default boolean isSign() { return false; }
}