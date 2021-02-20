package pugz.omni.common.tileentity;

public class OmniTrappedChestTileEntity extends OmniChestTileEntity {
    public OmniTrappedChestTileEntity() {
        super();
    }

    protected void onOpenOrClose() {
        super.onOpenOrClose();
        this.world.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockState().getBlock());
    }
}