package pugz.omni.core.module;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ForgeConfigSpec;
import pugz.omni.core.registry.OmniBlocks;
import pugz.omni.core.util.RegistryUtil;

public class FieryNetherModule extends AbstractModule {
    public static final FieryNetherModule instance = new FieryNetherModule();
    public static boolean cobbledBasalt = true;

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
    protected void onClientInitialize() {
    }

    @Override
    protected void onPostInitialize() {
    }

    @Override
    protected void registerBlocks() {
        //RegistryObject<Block> MOLTEN_MAGMA;
        //RegistryObject<Block> PUMICE;

        //RegistryObject<Block> FOOLS_NETHERITE;

        //RegistryObject<Block> MORTAR;

        //RegistryObject<Block> BASALT_VENT;

        //RegistryObject<Block> NETHERITE_GONG;

        //RegistryObject<Block> MAGMISS;

        if (cobbledBasalt) OmniBlocks.COBBLED_BASALT = RegistryUtil.createBlock("cobbled_basalt", () -> new Block(AbstractBlock.Properties.from(Blocks.BASALT)), ItemGroup.BUILDING_BLOCKS);
    }

    @Override
    protected void registerItems() {
        //RegistryObject<Item> ERUPTION_SHARD;
    }

    @Override
    protected void registerFeatures() {
        //RegistryObject<Feature<?>> MORTAR;
        //RegistryObject<Feature<?>> BASALT_VENT;
        //RegistryObject<Feature<?>> MAGMISS;
        //RegistryObject<Feature<?>> CHUNK;
    }

    @Override
    protected void registerEffects() {
        //RegistryObject<Effect> BLAZEFIRE;
    }

    @Override
    protected void registerParticles() {
        //RegistryObject<ParticleType<?>> MOLTEN_MAGMA;
        //RegistryObject<ParticleType<?>> MAGMISS;
    }

    @Override
    protected void registerSounds() {
    }
}