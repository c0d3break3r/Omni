package pugz.omni.common.tileentity;

import pugz.omni.core.registry.OmniTileEntities;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;

public class OmniChestTileEntity extends ChestTileEntity {
    private static String WOOD_TYPE = "";

    protected OmniChestTileEntity(TileEntityType<?> typeIn) {
        super(typeIn);
    }

    public OmniChestTileEntity() {
        super(OmniTileEntities.CHEST.get());
    }

    public OmniChestTileEntity(String wood) {
        super(OmniTileEntities.CHEST.get());
        WOOD_TYPE = wood;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(this.pos.getX() - 1, this.pos.getY(), this.pos.getZ() - 1, this.pos.getX() + 2, this.pos.getY() + 2, this.pos.getZ() + 2);
    }

    public String getWoodType() {
        return WOOD_TYPE;
    }
}