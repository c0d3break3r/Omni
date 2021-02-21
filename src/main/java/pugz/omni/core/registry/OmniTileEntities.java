package pugz.omni.core.registry;

import pugz.omni.common.tileentity.OmniBeehiveTileEntity;
import pugz.omni.common.tileentity.OmniChestTileEntity;
import pugz.omni.common.tileentity.OmniTrappedChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class OmniTileEntities {
    public static RegistryObject<TileEntityType<OmniBeehiveTileEntity>> BEEHIVE;
    public static RegistryObject<TileEntityType<OmniChestTileEntity>> CHEST;
    public static RegistryObject<TileEntityType<OmniTrappedChestTileEntity>> TRAPPED_CHEST;

    public static Block[] collectBlocks(Class<?> blockClass) {
        return ForgeRegistries.BLOCKS.getValues().stream().filter(blockClass::isInstance).toArray(Block[]::new);
    }
}