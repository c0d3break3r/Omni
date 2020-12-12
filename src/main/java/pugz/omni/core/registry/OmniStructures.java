package pugz.omni.core.registry;

import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraftforge.fml.RegistryObject;

public class OmniStructures {
    //wild west
    public static RegistryObject<Structure<VillageConfig>> GHOST_TOWN;

    public static class Features {
        //wild west
        public static StructureFeature<VillageConfig, ? extends Structure<VillageConfig>> GHOST_TOWN;
    }
}