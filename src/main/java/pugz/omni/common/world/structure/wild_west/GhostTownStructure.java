package pugz.omni.common.world.structure.wild_west;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.structure.JigsawStructure;
import net.minecraft.world.gen.feature.structure.VillageConfig;

import javax.annotation.Nonnull;
import java.util.List;

public class GhostTownStructure extends JigsawStructure {
    public GhostTownStructure(Codec<VillageConfig> p_i232001_1_) {
        super(p_i232001_1_, 0, true, true);
    }

    @Nonnull
    @Override
    public GenerationStage.Decoration getDecorationStage() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    @Nonnull
    @Override
    public List<MobSpawnInfo.Spawners> getSpawnList() {
        return Lists.newArrayList(new MobSpawnInfo.Spawners(EntityType.VEX, 8, 1, 1));
    }
}