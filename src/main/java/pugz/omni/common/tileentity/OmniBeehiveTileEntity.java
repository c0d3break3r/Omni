package pugz.omni.common.tileentity;

import pugz.omni.core.registry.OmniTileEntities;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nonnull;

public class OmniBeehiveTileEntity extends BeehiveTileEntity {
    public OmniBeehiveTileEntity() {
        super();
    }

    @Nonnull
    @Override
    public TileEntityType<?> getType() {
        return OmniTileEntities.BEEHIVE.get();
    }
}