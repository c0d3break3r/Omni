package pugz.omni.core.module;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import pugz.omni.core.registry.OmniBlocks;
import pugz.omni.core.util.RegistryUtil;

public class FieryNetherModule extends AbstractModule {
    public static final FieryNetherModule instance = new FieryNetherModule();

    public FieryNetherModule() {
        super("Fiery Nether");
    }

    @Override
    protected void sendInitMessage() {
        System.out.println("Erupted out of the Fiery Nether");
    }

    @Override
    protected void onInitialize() {
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void onClientInitialize() {
    }

    @Override
    protected void onPostInitialize(final FMLCommonSetupEvent event) {
    }

    @Override
    protected void registerBlocks() {
        OmniBlocks.COBBLED_BASALT = RegistryUtil.createBlock("cobbled_basalt", () -> new Block(AbstractBlock.Properties.from(Blocks.BASALT)), ItemGroup.BUILDING_BLOCKS);
    }
}