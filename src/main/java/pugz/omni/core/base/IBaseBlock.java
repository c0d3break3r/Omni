package pugz.omni.core.base;

import net.minecraft.client.renderer.RenderType;

public interface IBaseBlock {
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
}