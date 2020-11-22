package pugz.omni.common.block.forestry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import pugz.omni.core.base.IBaseBlock;

public class CarvedLogBlock extends Block implements IBaseBlock {
    public CarvedLogBlock(MaterialColor color) {
        super(AbstractBlock.Properties.create(Material.WOOD, color).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
    }

    @Override
    public int getFireEncouragement() {
        return 5;
    }

    @Override
    public int getFireFlammability() {
        return 5;
    }
}