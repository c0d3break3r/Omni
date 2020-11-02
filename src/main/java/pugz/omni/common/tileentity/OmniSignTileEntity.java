package pugz.omni.common.tileentity;

import pugz.omni.core.registry.OmniTileEntities;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nonnull;

public class OmniSignTileEntity extends SignTileEntity {
    @Nonnull
    @Override
    public TileEntityType<?> getType() {
        return OmniTileEntities.SIGN.get();
    }
}