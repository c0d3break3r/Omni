package com.pugz.omni.core.module;

import com.pugz.omni.common.tileentity.OmniBeehiveTileEntity;
import com.pugz.omni.common.tileentity.OmniChestTileEntity;
import com.pugz.omni.common.tileentity.OmniSignTileEntity;
import com.pugz.omni.common.tileentity.OmniTrappedChestTileEntity;
import com.pugz.omni.core.registry.OmniTileEntities;
import com.pugz.omni.core.util.RegistryUtil;

public class CoreModule extends AbstractModule {
    public CoreModule() {
        super("Core");
    }

    @Override
    protected void onInitialize() {

    }

    @Override
    protected void registerTileEntities() {
        OmniTileEntities.BEEHIVE = RegistryUtil.createTileEntity("beehive", OmniBeehiveTileEntity::new, () -> OmniTileEntities.collectBlocks(OmniBeehiveTileEntity.class));
        OmniTileEntities.SIGN = RegistryUtil.createTileEntity("sign", OmniSignTileEntity::new, () -> OmniTileEntities.collectBlocks(OmniSignTileEntity.class));
        OmniTileEntities.CHEST = RegistryUtil.createTileEntity("chest", OmniChestTileEntity::new, () -> OmniTileEntities.collectBlocks(OmniChestTileEntity.class));
        OmniTileEntities.TRAPPED_CHEST = RegistryUtil.createTileEntity("trapped_chest", OmniTrappedChestTileEntity::new, () -> OmniTileEntities.collectBlocks(OmniTrappedChestTileEntity.class));
    }
}