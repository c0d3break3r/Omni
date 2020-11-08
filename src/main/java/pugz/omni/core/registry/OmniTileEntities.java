package pugz.omni.core.registry;

import pugz.omni.common.tileentity.OmniBeehiveTileEntity;
import pugz.omni.common.tileentity.OmniChestTileEntity;
import pugz.omni.common.tileentity.OmniSignTileEntity;
import pugz.omni.common.tileentity.OmniTrappedChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.registries.ForgeRegistries;

public class OmniTileEntities {
    public static RegistryObject<TileEntityType<OmniBeehiveTileEntity>> BEEHIVE;
    public static RegistryObject<TileEntityType<OmniSignTileEntity>> SIGN;
    public static RegistryObject<TileEntityType<OmniChestTileEntity>> CHEST;
    public static RegistryObject<TileEntityType<OmniTrappedChestTileEntity>> TRAPPED_CHEST;

    public static Block[] collectBlocks(Class<?> blockClass) {
        return ForgeRegistries.BLOCKS.getValues().stream().filter(blockClass::isInstance).toArray(Block[]::new);
    }

    public static void registerTileEntityRenders() {
            }
}