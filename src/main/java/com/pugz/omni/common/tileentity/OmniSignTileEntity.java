package com.pugz.omni.common.tileentity;

import com.pugz.omni.core.registry.OmniTileEntities;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class OmniSignTileEntity extends SignTileEntity {
    @Override
    public TileEntityType<?> getType() {
        return OmniTileEntities.SIGN.get();
    }
}