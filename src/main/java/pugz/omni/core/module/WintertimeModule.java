package pugz.omni.core.module;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.StrayEntity;
import net.minecraft.nbt.CompoundNBT;
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
        if (CoreModule.Configuration.COMMON.POLAR_BEAR_JOCKEY_CHANCE.get() > 0) MinecraftForge.EVENT_BUS.addListener(this::onEntityJoinWorld);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void onClientInitialize() {
    }

    @Override
    protected void onPostInitialize() {
    }

    @Override
    protected void registerBlocks() {
        //RegistryObject<Block> ANTLER_BLOCK;

        //RegistryObject<Block> ARCTISS_BLOCK;

        //RegistryObject<Block> MOONSTONE_BLOCK;
        //RegistryObject<Block> CREOSTONE;
        //RegistryObject<Block> CREOSTONE_STAIRS;
        //RegistryObject<Block> CREOSTONE_SLAB;
        //RegistryObject<Block> CREOSTONE_WALL;

        //RegistryObject<Block> MISTLETOE;
    }

    @Override
    protected void registerItems() {
        //RegistryObject<Item> BLIZZARD_SHARD;
        //RegistryObject<Item> ANTLER;
        //RegistryObject<Item> MOONSTONES;
    }

    @Override
    protected void registerEntities() {
        //RegistryObject<EntityType<?>> BLIZZARD;
        //RegistryObject<EntityType<?>> REINDEER;
    }

    @Override
    protected void registerStructures() {
        //RegistryObject<Structure<?>> BLIZZARD_MONUMENT;
    }

    @Override
    protected void registerEnchantments() {
        //RegistryObject<Enchantment> ICY_TOUCH;
    }

    @Override
    protected void registerParticles() {
        //RegistryObject<ParticleType<?>> MOON;
        //RegistryObject<ParticleType<?>> ARCTISS;
    }

    @Override
    protected void registerSounds() {
    }

    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        World world = event.getWorld();
        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld)world;
            Entity entity = event.getEntity();
            if (entity.getType() == EntityType.POLAR_BEAR && world.getRandom().nextInt(CoreModule.Configuration.COMMON.POLAR_BEAR_JOCKEY_CHANCE.get()) == 0) {
                Entity entity1 = EntityType.STRAY.spawn(serverWorld, null, null, entity.getPosition(), SpawnReason.JOCKEY, false, false);
                if (entity1 instanceof StrayEntity) {
                    StrayEntity stray = (StrayEntity)entity1;
                    stray.setLocationAndAngles(entity.getPosX(), entity.getPosY(), entity.getPosZ(), entity.rotationYaw, 0.0F);
                    stray.onInitialSpawn(serverWorld, new DifficultyInstance(world.getDifficulty(), world.getGameTime(), world.getChunkAt(entity.getPosition()).getInhabitedTime(), world.getMoonFactor()), SpawnReason.JOCKEY, (ILivingEntityData) null, (CompoundNBT) null);
                    stray.startRiding(entity);
                }
            }
        }
    }
}