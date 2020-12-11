package pugz.omni.core.module;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.StrayEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class WintertimeModule extends AbstractModule {
    public static final WintertimeModule instance = new WintertimeModule();

    public WintertimeModule() {
        super("Wintertime");
    }

    @Override
    protected void sendInitMessage() {
        System.out.println("Drinking hot chocolate by the fire during Wintertime!");
    }

    @Override
    protected void onInitialize() {
        MinecraftForge.EVENT_BUS.addListener(this::onEntityJoinWorld);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void onClientInitialize() {
    }

    @Override
    protected void onPostInitialize() {
    }

    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        World world = event.getWorld();
        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) world;
            Entity entity = event.getEntity();
            if (CoreModule.Configuration.COMMON.POLAR_BEAR_JOCKEY_CHANCE.get() > 0) {
                if (entity.getType() == EntityType.POLAR_BEAR && world.getRandom().nextInt(MathHelper.clamp(CoreModule.Configuration.COMMON.POLAR_BEAR_JOCKEY_CHANCE.get(), 1, 1000)) == 0) {
                    Entity entity1 = EntityType.STRAY.spawn(serverWorld, null, null, entity.getPosition(), SpawnReason.JOCKEY, false, false);
                    if (entity1 instanceof StrayEntity) {
                        StrayEntity stray = (StrayEntity) entity1;
                        stray.setLocationAndAngles(entity.getPosX(), entity.getPosY(), entity.getPosZ(), entity.rotationYaw, 0.0F);
                        stray.onInitialSpawn(serverWorld, new DifficultyInstance(world.getDifficulty(), world.getGameTime(), world.getChunkAt(entity.getPosition()).getInhabitedTime(), world.getMoonFactor()), SpawnReason.JOCKEY, (ILivingEntityData) null, (CompoundNBT) null);
                        stray.startRiding(entity);

                        serverWorld.addEntity(stray);
                    }
                }
            }
        }
    }
}