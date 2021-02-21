package pugz.omni.common.tileentity;

import pugz.omni.core.registry.OmniTileEntities;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;

public class OmniChestTileEntity extends ChestTileEntity {
    public OmniChestTileEntity() {
        super(OmniTileEntities.CHEST.get());
    }

    public OmniChestTileEntity(TileEntityType<?> tileEntity) {
        super(tileEntity);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(this.pos.getX() - 1, this.pos.getY(), this.pos.getZ() - 1, this.pos.getX() + 2, this.pos.getY() + 2, this.pos.getZ() + 2);
    }
}