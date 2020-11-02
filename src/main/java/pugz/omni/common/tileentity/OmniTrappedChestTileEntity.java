package com.pugz.omni.common.tileentity;

import com.pugz.omni.core.registry.OmniTileEntities;

public class OmniTrappedChestTileEntity extends OmniChestTileEntity {
    public OmniTrappedChestTileEntity() {
        super(OmniTileEntities.TRAPPED_CHEST.get());
    }

    protected void onOpenOrClose() {
        super.onOpenOrClose();
        this.world.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockState().getBlock());
    }
}