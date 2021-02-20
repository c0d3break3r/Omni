package pugz.omni.common.block;

import net.minecraft.block.*;
import pugz.omni.core.util.IBaseBlock;
import pugz.omni.core.util.IOmniSign;

public class OmniWallSignBlock extends WallSignBlock implements IBaseBlock, IOmniSign {
    private final String wood;

    public OmniWallSignBlock(Properties properties, String wood) {
        super(properties, WoodType.OAK);
        this.wood = wood;
    }

    @Override
    public String getWood() {
        return wood;
    }
}