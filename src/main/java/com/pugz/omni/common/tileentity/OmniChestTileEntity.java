package com.pugz.omni.common.tileentity;

import com.pugz.omni.core.registry.OmniTileEntities;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;

public class OmniChestTileEntity extends ChestTileEntity {
    protected OmniChestTileEntity(TileEntityType<?> typeIn) {
        super(typeIn);
    }

    public OmniChestTileEntity() {
        super(OmniTileEntities.CHEST.get());
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(pos.getX() - 1, pos.getY(), pos.getZ() - 1, pos.getX() + 2, pos.getY() + 2, pos.getZ() + 2);
    }
}