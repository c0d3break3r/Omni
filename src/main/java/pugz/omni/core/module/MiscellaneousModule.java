package pugz.omni.core.module;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.passive.horse.ZombieHorseEntity;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import pugz.omni.common.item.forestry.EnchantedGoldenCarrotItem;
import pugz.omni.core.Omni;
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
    }

    @Override
    protected void onClientInitialize() {
    }

    @Override
    protected void onPostInitialize() {
    }

    @Override
    protected void registerBlocks() {
        //RegistryObject<Block> PALISADE;

        //RegistryObject<Block> REDSTONE_RECEPTOR;

        //RegistryObject<Block> GOLD_WOOL;
        //RegistryObject<Block> GOLD_CARPET;

        //RegistryObject<Block> CHORMICE;
        //RegistryObject<Block> CHORMICE_BRICKS;
        //RegistryObject<Block> CHORMICE_BRICK_STAIRS;
        //RegistryObject<Block> CHORMICE_BRICK_SLAB;
        //RegistryObject<Block> CHORMICE_BRICK_WALL;

        //RegistryObject<Block> PAPER_LANTERNS;

        //RegistryObject<Block> COW_HEAD;
        //RegistryObject<Block> PIG_HEAD;
        //RegistryObject<Block> SHEEP_HEAD;
        //RegistryObject<Block> CHICKEN_HEAD;
        //RegistryObject<Block> DEER_HEAD;
        //RegistryObject<Block> YAK_HEAD;
    }

    @Override
    protected void registerItems() {
        //RegistryObject<Item> FARMER_HAT;

        //RegistryObject<Item> CHORCOMB;
        //RegistryObject<Item> CHORUS_FLUTE;
        //RegistryObject<Item> CHORUS_PEARL;

        //RegistryObject<Item> AURA_POTION;
        //RegistryObject<Item> PHOENIX_FEATHER;
        //RegistryObject<Item> GOLD_STRING;

        OmniItems.ENCHANTED_GOLDEN_CARROT = RegistryUtil.createItem("enchanted_golden_carrot", EnchantedGoldenCarrotItem::new);
    }

    @Override
    protected void registerTileEntities() {
        //RegistryObject<TileEntityType<?>> WOODEN_PISTON;
        //RegistryObject<TileEntityType<?>> BALLISTA;
    }

    @Override
    protected void registerEntities() {
        //RegistryObject<EntityType<?>> FLAMINGO;
        //RegistryObject<EntityType<?>> PHOENIX;
        //RegistryObject<EntityType<?>> CHORUS;
        //RegistryObject<EntityType<?>> GOLDEN_RAM;
    }

    @Override
    protected void registerFeatures() {
        //RegistryObject<Feature<?>> CHORUS_NEST;
    }

    @Override
    protected void registerEnchantments() {
        //RegistryObject<Enchantment> KINETIC_PROTECTION;
        //RegistryObject<Enchantment> MAGIC_PROTECTION;
        //RegistryObject<Enchantment> ARMOR_BREAKING;
        //RegistryObject<Enchantment> ARMOR_PIERCING;
        //RegistryObject<Enchantment> STEADFAST;
        //RegistryObject<Enchantment> VITALITY;
        //RegistryObject<Enchantment> SHEPHERDS_TOUCH;
        //RegistryObject<Enchantment> BUTCHERING;
        //RegistryObject<Enchantment> WISDOM;
        //SLASHING
    }

    @Override
    protected void registerParticles() {
        //RegistryObject<ParticleType<?>> GOLDEN_STRING;
    }

    @Override
    protected void registerSounds() {
    }

    public void onLootTableLoad(LootTableLoadEvent event) {
        LootTable table = event.getTable();
        ResourceLocation name = event.getName();

        if (name.toString().equals(LootTables.CHESTS_ABANDONED_MINESHAFT.toString()) || name.toString().equals(LootTables.CHESTS_SIMPLE_DUNGEON.toString()) || name.toString().equals(LootTables.BASTION_TREASURE.toString()) || name.toString().equals(LootTables.CHESTS_DESERT_PYRAMID.toString()) || name.toString().equals(LootTables.RUINED_PORTAL.toString()) || name.toString().equals(LootTables.CHESTS_WOODLAND_MANSION.toString())) {
            float chance = 0.03F;
            if (name.toString().equals(LootTables.BASTION_TREASURE.toString())) chance *= 3.0F;
            LootPool pool = new LootPool.Builder().addEntry(TableLootEntry.builder(new ResourceLocation(Omni.MOD_ID, "injects/enchanted_golden_carrot"))).acceptCondition(RandomChance.builder(chance)).build();
            table.addPool(pool);
        }
    }

    public void onEntityStruckByLightning(EntityStruckByLightningEvent event) {
        LightningBoltEntity lightning = event.getLightning();
        Entity entity = event.getEntity();
        World world = lightning.getEntityWorld();

        if (entity instanceof ZombieEntity || entity instanceof HorseEntity) {
            List<Entity> list = world.getEntitiesInAABBexcluding(lightning, new AxisAlignedBB(lightning.getPosX() - 3.0D, lightning.getPosY() - 3.0D, lightning.getPosZ() - 3.0D, lightning.getPosX() + 3.0D, lightning.getPosY() + 6.0D + 3.0D, lightning.getPosZ() + 3.0D), Entity::isAlive);
            list.forEach(target -> {
                if (entity instanceof ZombieEntity && target instanceof HorseEntity) {
                    transmutateZombieHorse(world, (ZombieEntity) entity, (HorseEntity) target);
                } else if (entity instanceof HorseEntity && target instanceof ZombieEntity) {
                    transmutateZombieHorse(world, (ZombieEntity) target, (HorseEntity) entity);
                }
            });
        }
    }

    private void transmutateZombieHorse(World world, ZombieEntity zombie, HorseEntity horse) {
        if (horse.hasCustomName() || zombie.hasCustomName() || horse.isTame()) return;
        if (world instanceof ServerWorld) {
            zombie.remove();
            horse.remove();

            Entity entity = EntityType.ZOMBIE_HORSE.spawn((ServerWorld) world, null, null, horse.getPosition(), SpawnReason.CONVERSION, false, false);
            if (entity instanceof ZombieHorseEntity) {
                ZombieHorseEntity zombieHorse = (ZombieHorseEntity)entity;
                zombieHorse.setLocationAndAngles(entity.getPosX(), entity.getPosY(), entity.getPosZ(), entity.rotationYaw, 0.0F);
                zombieHorse.onInitialSpawn((ServerWorld) world, new DifficultyInstance(world.getDifficulty(), world.getGameTime(), world.getChunkAt(entity.getPosition()).getInhabitedTime(), world.getMoonFactor()), SpawnReason.CONVERSION, (ILivingEntityData) null, (CompoundNBT) null);
            }
        }
    }
}