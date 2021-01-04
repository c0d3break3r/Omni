package pugz.omni.core.module;

import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.passive.horse.ZombieHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import pugz.omni.common.block.miscellaneous.VexedGlassBlock;
import pugz.omni.common.item.forestry.EnchantedGoldenCarrotItem;
import pugz.omni.core.Omni;
import pugz.omni.core.registry.OmniBlocks;
import pugz.omni.core.registry.OmniItems;
import pugz.omni.core.util.RegistryUtil;

import java.util.List;

public class MiscellaneousModule extends AbstractModule {
    public static final MiscellaneousModule instance = new MiscellaneousModule();

    public MiscellaneousModule() {
        super("Miscellaneous");
    }

    @Override
    protected void sendInitMessage() {
        System.out.println("Miscellaneous, random stuff initialized!");
    }

    @Override
    protected void onInitialize() {
        MinecraftForge.EVENT_BUS.addListener(this::onLootTableLoad);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityStruckByLightning);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityInteractSpecific);
        MinecraftForge.EVENT_BUS.addListener(this::onLivingUpdate);
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
        OmniBlocks.VEXED_GLASS = RegistryUtil.createBlock("vexed_glass", VexedGlassBlock::new, ItemGroup.REDSTONE);
    }

    @Override
    protected void registerItems() {
        OmniItems.ENCHANTED_GOLDEN_CARROT = RegistryUtil.createItem("enchanted_golden_carrot", EnchantedGoldenCarrotItem::new);
    }

    public void onLootTableLoad(LootTableLoadEvent event) {
        LootTable table = event.getTable();
        ResourceLocation name = event.getName();

        if (name.toString().equals(LootTables.CHESTS_ABANDONED_MINESHAFT.toString()) || name.toString().equals(LootTables.CHESTS_SIMPLE_DUNGEON.toString()) || name.toString().equals(LootTables.BASTION_TREASURE.toString()) || name.toString().equals(LootTables.CHESTS_DESERT_PYRAMID.toString()) || name.toString().equals(LootTables.RUINED_PORTAL.toString()) || name.toString().equals(LootTables.CHESTS_WOODLAND_MANSION.toString())) {
            float chance = CoreModule.Configuration.COMMON.ENCHANTED_GOLDEN_CARROT_SPAWN_CHANCE.get().floatValue();
            if (name.toString().equals(LootTables.BASTION_TREASURE.toString())) chance *= 3.0D;
            LootPool pool = new LootPool.Builder().name("omni_inject").addEntry(TableLootEntry.builder(new ResourceLocation(Omni.MOD_ID, "injects/enchanted_golden_carrot"))).acceptCondition(RandomChance.builder(chance)).build();
            table.addPool(pool);
        }
    }

    public void onEntityStruckByLightning(EntityStruckByLightningEvent event) {
        if (CoreModule.Configuration.COMMON.ZOMBIE_HORSE_TRANSMUTATION.get()) {
            LightningBoltEntity lightning = event.getLightning();
            Entity entity = event.getEntity();
            World world = lightning.getEntityWorld();

            if (entity instanceof HorseEntity) {
                List<Entity> list = world.getEntitiesInAABBexcluding(lightning, new AxisAlignedBB(lightning.getPosX() - 3.0D, lightning.getPosY() - 3.0D, lightning.getPosZ() - 3.0D, lightning.getPosX() + 3.0D, lightning.getPosY() + 6.0D + 3.0D, lightning.getPosZ() + 3.0D), Entity::isAlive);
                for (Entity target : list) {
                    if (target instanceof ZombieEntity) {
                        lightning.setEffectOnly(true);
                        transmutateZombieHorse(world, (ZombieEntity) target, (HorseEntity) entity);
                        break;
                    }
                }
            }
        }
    }

    private static void transmutateZombieHorse(World world, ZombieEntity zombie, HorseEntity horse) {
        if (horse.hasCustomName() || horse.isTame()) return;
        if (!world.isRemote) {
            zombie.remove();
            horse.remove();

            Entity entity = EntityType.ZOMBIE_HORSE.spawn((ServerWorld) world, null, null, horse.getPosition(), SpawnReason.CONVERSION, false, false);
            if (entity instanceof ZombieHorseEntity) {
                ZombieHorseEntity zombieHorse = (ZombieHorseEntity)entity;
                if (horse.isChild()) zombieHorse.setChild(true);
                if (!horse.horseChest.func_233543_f_().isEmpty()) zombieHorse.horseChest = horse.horseChest;
                zombieHorse.setLocationAndAngles(entity.getPosX(), entity.getPosY(), entity.getPosZ(), entity.rotationYaw, 0.0F);
                zombieHorse.onInitialSpawn((ServerWorld) world, new DifficultyInstance(world.getDifficulty(), world.getGameTime(), world.getChunkAt(entity.getPosition()).getInhabitedTime(), world.getMoonFactor()), SpawnReason.CONVERSION, (ILivingEntityData) null, (CompoundNBT) null);
            }
        }
    }

    public void onEntityInteractSpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        Entity entity = event.getTarget();
        PlayerEntity player = event.getPlayer();

        if (entity instanceof ZombieHorseEntity && !event.getWorld().isRemote) {
            ZombieHorseEntity zombieHorse = (ZombieHorseEntity)entity;

            player.rotationYaw = zombieHorse.rotationYaw;
            player.rotationPitch = zombieHorse.rotationPitch;
            player.startRiding(zombieHorse);
        }
    }

    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity living = event.getEntityLiving();

        if (living instanceof VexEntity) {
            BlockPos pos = living.getPosition();
            World world = living.getEntityWorld();
            if (world.getBlockState(pos).getBlock() == Blocks.GLASS) {
                world.setBlockState(pos, OmniBlocks.VEXED_GLASS.get().getDefaultState(), 3);
            }
        }
    }
}