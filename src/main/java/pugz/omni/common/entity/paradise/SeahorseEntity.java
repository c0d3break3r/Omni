package pugz.omni.common.entity.paradise;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;
import pugz.omni.core.Omni;
import pugz.omni.core.module.CoreModule;
import pugz.omni.core.registry.OmniEntities;
import pugz.omni.core.registry.OmniItems;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class SeahorseEntity extends TameableEntity implements IMob {
    private static final DataParameter<Boolean> MOVING = EntityDataManager.createKey(SeahorseEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> SIZE = EntityDataManager.createKey(SeahorseEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> CORAL_TYPE = EntityDataManager.createKey(SeahorseEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Byte> CORAL_TYPE_FLAGS = EntityDataManager.createKey(SeahorseEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Byte> STATUS = EntityDataManager.createKey(SeahorseEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(SeahorseEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    private BlockPos savedCoralPos = null;

    public SeahorseEntity(EntityType<? extends SeahorseEntity> type, World worldIn) {
        super(type, worldIn);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
        this.moveController = new SeahorseEntity.MoveHelperController(this);
    }

    public SeahorseEntity(World world, double posX, double posY, double posZ) {
        this(OmniEntities.SEAHORSE.get(), world);
        this.setPosition(posX, posY, posZ);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 8.0F).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.8F);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(MOVING, false);
        this.dataManager.register(SIZE, 0);
        this.dataManager.register(CORAL_TYPE, 0);
        this.dataManager.register(CORAL_TYPE_FLAGS, (byte)0);
        this.dataManager.register(STATUS, (byte)0);
        this.dataManager.register(OWNER_UNIQUE_ID, Optional.empty());
    }

    @Nonnull
    public ResourceLocation getLootTable() {
        if (this.getVariantType() != CoralType.MYSTERY) {
            return new ResourceLocation(Omni.MOD_ID, "entities/seahorse/" + this.getVariantType().getName());
        } else return new ResourceLocation(Omni.MOD_ID, "entities/seahorse");
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, PlayerEntity.class, 6.0F, 1.0D, 1.1D));
        this.goalSelector.addGoal(2, new FollowParentGoal(this, 0.8D));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomSwimmingGoal(this, 1.0D, 25));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
    }

    @Nonnull
    @Override
    protected PathNavigator createNavigator(World worldIn) {
        return new SwimmerPathNavigator(this, worldIn);
    }

    public static boolean canSeahorseSpawn(EntityType<? extends SeahorseEntity> seahorse, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
        for (String spawnBiomeName : CoreModule.Configuration.COMMON.SEAHORSE_SPAWN_BIOMES.get().replace(" ", "").split(",")) {
            if (worldIn.getBiome(pos).getRegistryName().toString().equals(spawnBiomeName) && worldIn.getBlockState(pos).isIn(Blocks.WATER) && worldIn.getBlockState(pos.up()).isIn(Blocks.WATER)) {
                return true;
            }
        } return false;
    }

    @Nonnull
    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_FISH_SWIM;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_TROPICAL_FISH_FLOP;
    }

    @Override
    public void onEnterBubbleColumn(boolean downwards) {}

    @Override
    public void onEnterBubbleColumnWithAirAbove(boolean downwards) {}

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(OmniItems.SEAHORSE_SPAWN_EGG.get());
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    @Nonnull
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.WATER;
    }

    public boolean isNotColliding(IWorldReader worldIn) {
        return worldIn.checkNoEntityCollision(this);
    }

    public int getTalkInterval() {
        return 120;
    }

    protected int getExperiencePoints(PlayerEntity player) {
        return 1 + this.world.rand.nextInt(2);
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 6;
    }

    protected void updateAir(int p_209207_1_) {
        if (this.isAlive() && !this.isInWaterOrBubbleColumn()) {
            this.setAir(p_209207_1_ - 1);
            if (this.getAir() == -20) {
                this.setAir(0);
                this.attackEntityFrom(DamageSource.DRYOUT, 2.0F);
            }
        } else {
            this.setAir(300);
        }
    }

    public void baseTick() {
        int i = this.getAir();
        super.baseTick();
        this.updateAir(i);
    }

    public boolean isPushedByWater() {
        return false;
    }

    public boolean canBeLeashedTo(PlayerEntity player) {
        return false;
    }

    public void setSeahorseSize(int sizeIn) {
        this.dataManager.set(SIZE, MathHelper.clamp(sizeIn, 0, 64));
    }

    private void updateSeahorseSize() {
        this.recalculateSize();
    }

    public int getSeahorseSize() {
        return this.dataManager.get(SIZE);
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height * 0.35F;
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        if (SIZE.equals(key)) {
            this.updateSeahorseSize();
        }
        super.notifyDataManagerChange(key);
    }

    @Nullable
    public BlockPos getCoralPos() {
        return this.savedCoralPos;
    }

    public SeahorseEntity.CoralType getVariantType() {
        return SeahorseEntity.CoralType.getTypeByIndex(this.dataManager.get(CORAL_TYPE));
    }

    public void setVariantType(SeahorseEntity.CoralType typeIn) {
        this.dataManager.set(CORAL_TYPE, typeIn.getIndex());
    }

    protected boolean getSeahorseWatchableBoolean(int p_110233_1_) {
        return (this.dataManager.get(STATUS) & p_110233_1_) != 0;
    }

    protected void setSeahorseWatchableBoolean(int p_110208_1_, boolean p_110208_2_) {
        byte b0 = this.dataManager.get(STATUS);
        if (p_110208_2_) {
            this.dataManager.set(STATUS, (byte)(b0 | p_110208_1_));
        } else {
            this.dataManager.set(STATUS, (byte)(b0 & ~p_110208_1_));
        }
    }

    public boolean isTame() {
        return this.getSeahorseWatchableBoolean(2);
    }

    @Nullable
    public UUID getOwnerUniqueId() {
        return this.dataManager.get(OWNER_UNIQUE_ID).orElse((UUID)null);
    }

    public void setOwnerUniqueId(@Nullable UUID uniqueId) {
        this.dataManager.set(OWNER_UNIQUE_ID, Optional.ofNullable(uniqueId));
    }

    public void setSeahorseTamed(boolean tamed) {
        this.setSeahorseWatchableBoolean(2, tamed);
    }

    public boolean isMoving() {
        return this.dataManager.get(MOVING);
    }

    public void setMoving(boolean moving) {
        this.dataManager.set(MOVING, moving);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        if (this.hasCoral()) {
            compound.put("CoralPos", NBTUtil.writeBlockPos(this.getCoralPos()));
        }
        compound.putBoolean("Moving", this.isMoving());
        compound.putString("Type", this.getVariantType().getName());
        compound.putInt("Size", this.getSeahorseSize());
        compound.putBoolean("Tame", this.isTame());
        if (this.getOwnerUniqueId() != null) {
            compound.putUniqueId("Owner", this.getOwnerUniqueId());
        }
    }

    public void readAdditional(CompoundNBT compound) {
        this.setVariantType(SeahorseEntity.CoralType.getTypeByName(compound.getString("Type")));
        this.savedCoralPos = null;
        if (compound.contains("CoralPos")) {
            this.savedCoralPos = NBTUtil.readBlockPos(compound.getCompound("CoralPos"));
        }
        this.setMoving(compound.getBoolean("Moving"));
        this.setSeahorseSize(compound.getInt("Size"));
        this.setSeahorseTamed(compound.getBoolean("Tame"));
        UUID uuid;
        if (compound.hasUniqueId("Owner")) {
            uuid = compound.getUniqueId("Owner");
        } else {
            String s = compound.getString("Owner");
            uuid = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), s);
        }

        if (uuid != null) {
            this.setOwnerUniqueId(uuid);
        }
        super.readAdditional(compound);
    }

    public AgeableEntity func_241840_a(ServerWorld world, AgeableEntity ageableEntity) {
        return OmniEntities.SEAHORSE.get().create(world);
    }

    public boolean canBeSteered() {
        return this.getControllingPassenger() instanceof LivingEntity;
    }

    public boolean onLivingFall(float distance, float damageMultiplier) {
        int i = this.calculateFallDamage(distance, damageMultiplier);
        if (i <= 0) {
            return false;
        } else {
            this.attackEntityFrom(DamageSource.FALL, (float)i);
            if (this.isBeingRidden()) {
                for(Entity entity : this.getRecursivePassengers()) {
                    entity.attackEntityFrom(DamageSource.FALL, (float)i);
                }
            }
            this.playFallSound();
            return true;
        }
    }

    protected int calculateFallDamage(float distance, float damageMultiplier) {
        return MathHelper.ceil((distance * 0.5F - 3.0F) * damageMultiplier);
    }

    protected void spawnSeahorseParticles(boolean p_110216_1_) {
        IParticleData iparticledata = p_110216_1_ ? ParticleTypes.HEART : ParticleTypes.SMOKE;
        for(int i = 0; i < 7; ++i) {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.world.addParticle(iparticledata, this.getPosXRandom(1.0D), this.getPosYRandom() + 0.5D, this.getPosZRandom(1.0D), d0, d1, d2);
        }
    }

    public void handleStatusUpdate(byte id) {
        if (id == 7) {
            this.spawnSeahorseParticles(true);
        } else if (id == 6) {
            this.spawnSeahorseParticles(false);
        } else {
            super.handleStatusUpdate(id);
        }
    }

    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        if (passenger instanceof MobEntity) {
            MobEntity mobentity = (MobEntity)passenger;
            this.renderYawOffset = mobentity.renderYawOffset;
        }
        float f3 = MathHelper.sin(this.renderYawOffset * ((float)Math.PI / 180F));
        float f = MathHelper.cos(this.renderYawOffset * ((float)Math.PI / 180F));
        float f1 = 0.7F;
        float f2 = 0.15F;
        passenger.setPosition(this.getPosX() + (double)(f1 * f3), this.getPosY() + this.getMountedYOffset() + passenger.getYOffset() + (double)f2, this.getPosZ() - (double)(f1 * f));
        if (passenger instanceof LivingEntity) {
            ((LivingEntity)passenger).renderYawOffset = this.renderYawOffset;
        }
    }

    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        SeahorseEntity.CoralType seahorseentity$coraltype = SeahorseEntity.CoralType.getTypeByIndex(worldIn.getRandom().nextInt(CoralType.values().length - 1));
        this.setVariantType(seahorseentity$coraltype);
        this.setSeahorseSize(worldIn.getRandom().nextInt(4));
        if (worldIn.getRandom().nextInt(CoreModule.Configuration.COMMON.LARGE_SEAHORSE_SPAWN_CHANCE.get()) == 0) this.setSeahorseSize(this.rand.nextInt(1) + 6);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)this.getModifiedMaxHealth());
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.getModifiedMovementSpeed());
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Nonnull
    public EntitySize getSize(Pose poseIn) {
        int i = this.getSeahorseSize();
        EntitySize entitysize = super.getSize(poseIn);
        float f = 1.0F + 0.3F * (float) i;

        if (!this.isInWater() && !this.isBeingRidden()) {
            EntitySize size = new EntitySize(entitysize.height, entitysize.width, entitysize.fixed);
            return size.scale(f);
        }

        return entitysize.scale(f);
    }

    protected float getModifiedMaxHealth() {
        return 6.0F + (float)this.rand.nextInt(5);
    }

    protected double getModifiedMovementSpeed() {
        return (double)0.4F + this.rand.nextDouble();
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    public boolean hasCoral() {
        return this.savedCoralPos != null;
    }

    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
            this.setHealth(8.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
        }
    }

    public void livingTick() {
        if (!this.isInWater()) {
            if (this.onGround && this.collidedVertically) {
                this.setMotion(this.getMotion().add((double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.05F), (double) 0.4F, (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.05F)));
                this.onGround = false;
                this.isAirBorne = true;
                this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getSoundPitch());
            }
        }
        super.livingTick();
    }

    protected ItemStack getBucket() {
        return new ItemStack(OmniItems.SEAHORSE_BUCKET.get());
    }

    public static String getCoralTypeName(int index) {
        return CoralType.getTypeByIndex(index).getName();
    }

    @Nonnull
    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack held = player.getHeldItem(hand);
        if (!this.isChild()) {
            if ((held.getItem() == Items.KELP) && !this.isTamed()) {
                if (!player.abilities.isCreativeMode && !this.world.isRemote) {
                    held.shrink(1);
                }
                if (this.rand.nextInt(CoreModule.Configuration.COMMON.SEAHORSE_TAME_CHANCE.get()) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                    this.setTamedBy(player);
                    this.navigator.clearPath();
                    this.func_233687_w_(true);
                    this.world.setEntityState(this, (byte) 7);
                } else {
                    this.world.setEntityState(this, (byte) 6);
                }
                return ActionResultType.func_233537_a_(world.isRemote);
            } else if (held.getItem() == Items.WATER_BUCKET && this.isAlive()) {
                this.playSound(SoundEvents.ITEM_BUCKET_FILL_FISH, 1.0F, 1.0F);
                if (!player.abilities.isCreativeMode && !this.world.isRemote) {
                    held.shrink(1);
                }
                ItemStack bucket = this.getBucket();
                if (this.hasCustomName()) bucket.setDisplayName(this.getCustomName());
                CompoundNBT compoundnbt = bucket.getOrCreateTag();
                compoundnbt.putInt("SeahorseVariantTag", this.getVariantType().getIndex());
                compoundnbt.putInt("SeahorseSizeTag", this.getSeahorseSize());

                if (!this.world.isRemote)
                    CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity) player, bucket);

                if (held.isEmpty()) player.setHeldItem(hand, bucket);
                else if (!player.inventory.addItemStackToInventory(bucket)) player.dropItem(bucket, false);

                this.remove();
                return ActionResultType.SUCCESS;
            } else if (this.getSeahorseSize() > 5 && this.isTamed() && this.getOwner() == player && CoreModule.Configuration.COMMON.RIDEABLE_SEAHORSES.get()) {
                this.mountTo(player);
                return ActionResultType.func_233537_a_(this.world.isRemote);
            }
        }
        return ActionResultType.PASS;
    }

    protected void mountTo(PlayerEntity player) {
        if (!this.world.isRemote) {
            player.rotationYaw = this.rotationYaw;
            player.rotationPitch = this.rotationPitch;
            player.startRiding(this);
        }
    }

    @Override
    public double getMountedYOffset() {
        int i = this.getSeahorseSize();
        float f = 0.85F + 0.3F * (float)i;
        return 0.55F * f;
    }

    @Override
    public boolean canRiderInteract() {
        return false;
    }

    public void travel(Vector3d travelVector) {
        if (this.isAlive()) {
            if (this.isBeingRidden() && this.canBeSteered() && this.isInWater()) {
                LivingEntity livingentity = (LivingEntity)this.getControllingPassenger();
                this.rotationYaw = livingentity.rotationYaw;
                if (this.isInWater()) {
                    this.rotationYawHead = livingentity.rotationYawHead;
                }
                this.prevRotationYaw = this.rotationYaw;
                this.prevRotationYawHead = this.rotationYawHead;
                this.setRotation(this.rotationYaw, this.rotationPitch);
                this.setHeadRotation(this.rotationYawHead, (int)this.rotationPitch);
                this.renderYawOffset = this.rotationYaw;

                float f1 = livingentity.moveForward;
                float f = livingentity.moveStrafing * 0.75F;
                if (this.canPassengerSteer()) {
                    this.setAIMoveSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.9F);
                    this.moveRelative(0.1F, new Vector3d((double)f, livingentity.getLookVec().y, (double)f1));
                    this.move(MoverType.PLAYER, this.getMotion());
                    this.setMotion(this.getMotion().scale(0.9D));
                } else if (livingentity instanceof PlayerEntity) {
                    this.moveRelative(0.1F, new Vector3d((double)f, livingentity.getLookVec().y * 0.5F, (double)f1));
                    this.move(MoverType.SELF, this.getMotion());
                    this.setMotion(this.getMotion().scale(0.9D));
                }
                this.func_233629_a_(this, false);
            } else {
                super.travel(travelVector);
            }
        }
    }

    protected boolean isMovementBlocked() {
        return super.isMovementBlocked() && this.isBeingRidden();
    }

    @Override
    public boolean canBreed() {
        return false;
    }

    @Override
    public boolean isChild() {
        return false;
    }

    public void setCustomName(@Nullable ITextComponent name) {
        super.setCustomName(name);
        if (name.getString().toLowerCase().equals(CoralType.MYSTERY.name) && this.getVariantType() != CoralType.MYSTERY) {
            super.setCustomName(name);
            this.setVariantType(CoralType.MYSTERY);
        }
    }

    static class MoveHelperController extends MovementController {
        private final SeahorseEntity seahorse;

        public MoveHelperController(SeahorseEntity seahorse) {
            super(seahorse);
            this.seahorse = seahorse;
        }

        public void tick() {
            if (this.seahorse.isInWater()) {
                if (this.action != MovementController.Action.MOVE_TO || this.seahorse.getNavigator().noPath()) {
                    this.seahorse.setAIMoveSpeed(0.0F);
                    return;
                }
                double d0 = this.posX - this.seahorse.getPosX();
                double d1 = this.posY - this.seahorse.getPosY();
                double d2 = this.posZ - this.seahorse.getPosZ();
                double d3 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 = d1 / d3;
                float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                this.seahorse.rotationYaw = this.limitAngle(this.seahorse.rotationYaw, f, 90.0F);
                this.seahorse.renderYawOffset = this.seahorse.rotationYaw;
                float f1 = (float)(this.speed * this.seahorse.getAttributeValue(Attributes.MOVEMENT_SPEED));
                float f2 = MathHelper.lerp(0.125F, this.seahorse.getAIMoveSpeed(), f1);
                this.seahorse.setAIMoveSpeed(f2);
                this.seahorse.setMotion(this.seahorse.getMotion().add((double)f2 * d0 * 0.005D, (double)f2 * d1 * 0.1D, (double)f2 * d2 * 0.005D));
                this.seahorse.setMoving(true);
            } else {
                if (!this.seahorse.onGround) {
                    this.seahorse.setMotion(this.seahorse.getMotion().add(0.0D, -0.008D, 0.0D));
                    this.seahorse.setMoving(false);
                }
                super.tick();
            }
        }
    }

    public enum CoralType {
        BUBBLE(0, "bubble", Blocks.BUBBLE_CORAL_BLOCK.getDefaultState(), Blocks.BUBBLE_CORAL.getDefaultState(), Blocks.BUBBLE_CORAL_WALL_FAN.getDefaultState()),
        FIRE(1, "fire", Blocks.FIRE_CORAL_BLOCK.getDefaultState(), Blocks.FIRE_CORAL.getDefaultState(), Blocks.FIRE_CORAL_WALL_FAN.getDefaultState()),
        TUBE(2, "tube", Blocks.TUBE_CORAL_BLOCK.getDefaultState(), Blocks.TUBE_CORAL.getDefaultState(), Blocks.TUBE_CORAL_WALL_FAN.getDefaultState()),
        BRAIN(3, "brain", Blocks.BRAIN_CORAL_BLOCK.getDefaultState(), Blocks.BRAIN_CORAL.getDefaultState(), Blocks.BRAIN_CORAL_WALL_FAN.getDefaultState()),
        HORN(4, "horn", Blocks.HORN_CORAL_BLOCK.getDefaultState(), Blocks.HORN_CORAL.getDefaultState(), Blocks.HORN_CORAL_WALL_FAN.getDefaultState()),
        MYSTERY(5, "mystery", null, null, null);

        private static final SeahorseEntity.CoralType[] field_221088_c = Arrays.stream(values()).sorted(Comparator.comparingInt(SeahorseEntity.CoralType::getIndex)).toArray(SeahorseEntity.CoralType[]::new);
        private static final Map<String, SeahorseEntity.CoralType> TYPES_BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(SeahorseEntity.CoralType::getName, (p_221081_0_) -> {
            return p_221081_0_;
        }));
        private final int index;
        private final String name;
        private final BlockState block;
        private final BlockState coral;
        private final BlockState fan;

        CoralType(int index, String name, @Nullable BlockState block, @Nullable BlockState coral, @Nullable BlockState fan) {
            this.index = index;
            this.name = name;
            this.block = block;
            this.coral = coral;
            this.fan = fan;
        }

        public String getName() {
            return this.name;
        }

        public int getIndex() {
            return this.index;
        }

        public BlockState getBlock() { return block; }

        public BlockState getCoral() {
            return coral;
        }

        public BlockState getFan() {
            return fan;
        }

        public static SeahorseEntity.CoralType getTypeByName(String nameIn) {
            return TYPES_BY_NAME.getOrDefault(nameIn, HORN);
        }

        public static SeahorseEntity.CoralType getTypeByIndex(int indexIn) {
            if (indexIn < 0 || indexIn > field_221088_c.length) {
                indexIn = 0;
            }
            return field_221088_c[indexIn];
        }
    }
}