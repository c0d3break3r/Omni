package pugz.omni.common.entity.paradise;

import net.minecraft.block.AbstractCoralPlantBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CoralFanBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
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
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;
import pugz.omni.core.registry.OmniEntities;
import pugz.omni.core.registry.OmniItems;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SeahorseEntity extends TameableEntity implements IMob {
    private static final DataParameter<Integer> SIZE = EntityDataManager.createKey(SeahorseEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> CORAL_TYPE = EntityDataManager.createKey(SeahorseEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Byte> CORAL_TYPE_FLAGS = EntityDataManager.createKey(SeahorseEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Byte> STATUS = EntityDataManager.createKey(SeahorseEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(SeahorseEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    private int remainingCooldownBeforeLocatingNewCoral = 0;
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
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 8.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.8D);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SIZE, 0);
        this.dataManager.register(CORAL_TYPE, 0);
        this.dataManager.register(CORAL_TYPE_FLAGS, (byte)0);
        this.dataManager.register(STATUS, (byte)0);
        this.dataManager.register(OWNER_UNIQUE_ID, Optional.empty());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(2, new FollowParentGoal(this, 0.8D));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PlayerEntity.class, 6.0F, 1.0D, 1.1D));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 25));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new GrowCoralGoal());
        this.goalSelector.addGoal(6, new FindCoralGoal());
    }

    @Nonnull
    @Override
    protected PathNavigator createNavigator(World worldIn) {
        return new SwimmerPathNavigator(this, worldIn);
    }

    private boolean isCoral(BlockPos pos) {
        return this.world.isBlockPresent(pos) && this.world.getBlockState(pos).getBlock().isIn(BlockTags.CORAL_BLOCKS);
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

    protected void updateAir(int p_209207_1_) {
        if (this.isAlive() && !this.isInWaterOrBubbleColumn()) {
            this.setAir(p_209207_1_ - 1);
            if (this.getAir() == -20) {
                this.setAir(0);
                this.attackEntityFrom(DamageSource.DROWN, 2.0F);
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

    private void setVariantType(SeahorseEntity.CoralType typeIn) {
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

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        if (this.hasCoral()) {
            compound.put("CoralPos", NBTUtil.writeBlockPos(this.getCoralPos()));
        }
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
        SeahorseEntity seahorseEntity = OmniEntities.SEAHORSE.get().create(world);
        return seahorseEntity;
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
        SeahorseEntity.CoralType seahorseentity$coraltype = SeahorseEntity.CoralType.getTypeByIndex(worldIn.getRandom().nextInt(CoralType.values().length));
        this.setVariantType(seahorseentity$coraltype);
        this.setSeahorseSize(worldIn.getRandom().nextInt(4));
        if (worldIn.getRandom().nextInt(25) == 0) this.setSeahorseSize(6);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)this.getModifiedMaxHealth());
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.getModifiedMovementSpeed());
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Nonnull
    public EntitySize getSize(Pose poseIn) {
        int i = this.getSeahorseSize();
        EntitySize entitysize = super.getSize(poseIn);
        float f = 1.0F + 0.3F * (float)i;
        return i > 3 ? entitysize.scale(f) : entitysize;
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

    private boolean isTooFar(BlockPos pos) {
        return this.isWithinDistance(pos, 32);
    }

    private boolean isWithinDistance(BlockPos pos, int distance) {
        return !pos.withinDistance(this.getPosition(), (double) distance);
    }

    private void startMovingTo(BlockPos pos) {
        Vector3d vector3d = Vector3d.copyCenteredHorizontally(pos);
        int i = 0;
        BlockPos blockpos = this.getPosition();
        int j = (int)vector3d.y - blockpos.getY();
        if (j > 2) {
            i = 4;
        } else if (j < -2) {
            i = -4;
        }

        int k = 6;
        int l = 8;
        int i1 = blockpos.manhattanDistance(pos);
        if (i1 < 15) {
            k = i1 / 2;
            l = i1 / 2;
        }

        Vector3d vector3d1 = RandomPositionGenerator.func_226344_b_(this, k, l, i, vector3d, (double)((float)Math.PI / 10F));
        if (vector3d1 != null) {
            this.navigator.setRangeMultiplier(0.5F);
            this.navigator.tryMoveToXYZ(vector3d1.x, vector3d1.y, vector3d1.z, 1.0D);
        }
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

    @Nonnull
    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack held = player.getHeldItem(hand);
        Item item = held.getItem();
        if (!this.isChild()) {
            if ((item == Items.KELP || item == Items.SEAGRASS) && !this.isTamed()) {
                if (!player.abilities.isCreativeMode) {
                    held.shrink(1);
                }
                if (this.rand.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                    this.setTamedBy(player);
                    this.navigator.clearPath();
                    this.setAttackTarget((LivingEntity)null);
                    this.func_233687_w_(true);
                    this.world.setEntityState(this, (byte)7);
                } else {
                    this.world.setEntityState(this, (byte)6);
                }
                return ActionResultType.SUCCESS;
            } else if (this.getSeahorseSize() > 5 && this.isTamed() && this.getOwner() == player) {
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
        return 1.3D;
    }

    @Override
    public boolean canRiderInteract() {
        return false;
    }

    public void travel(Vector3d travelVector) {
        if (this.isAlive()) {
            if (this.isBeingRidden() && this.canBeSteered()) {
                LivingEntity livingentity = (LivingEntity)this.getControllingPassenger();
                this.rotationYaw = livingentity.rotationYaw;
                this.rotationYawHead = livingentity.rotationYawHead;
                this.prevRotationYaw = this.rotationYaw;
                this.prevRotationYawHead = this.rotationYawHead;
                this.setRotation(this.rotationYaw, this.rotationPitch);
                this.setHeadRotation(this.rotationYawHead, (int)this.rotationPitch);
                this.renderYawOffset = this.rotationYaw;
                if (this.canPassengerSteer()) {
                    float f = -MathHelper.sin(livingentity.rotationPitch * ((float)Math.PI / 135F)) * 2.5F;
                    if (livingentity instanceof PlayerEntity) {
                        if (((PlayerEntity)livingentity).jumpMovementFactor > 0.02F) f *= 2.5F;
                    }
                    this.setAIMoveSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    super.travel(new Vector3d((double)livingentity.moveStrafing, f, (double)livingentity.moveForward));
                } else if (livingentity instanceof PlayerEntity) {
                    this.setMotion(Vector3d.ZERO);
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
            } else {
                if (!this.seahorse.onGround) {
                    this.seahorse.setMotion(this.seahorse.getMotion().add(0.0D, -0.008D, 0.0D));
                }
                super.tick();
            }
        }
    }

    public class FindCoralGoal extends Goal {
        private int ticks = SeahorseEntity.this.world.rand.nextInt(10);

        FindCoralGoal() {
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canStart() {
            return SeahorseEntity.this.savedCoralPos != null && SeahorseEntity.this.isCoral(SeahorseEntity.this.savedCoralPos) && SeahorseEntity.this.isWithinDistance(SeahorseEntity.this.savedCoralPos, 2);
        }

        @Override
        public boolean shouldExecute() {
            return this.canStart();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return this.canStart();
        }

        public void startExecuting() {
            this.ticks = 0;
            super.startExecuting();
        }

        public void resetTask() {
            this.ticks = 0;
            SeahorseEntity.this.navigator.clearPath();
            SeahorseEntity.this.navigator.resetRangeMultiplier();
        }

        public void tick() {
            if (SeahorseEntity.this.savedCoralPos != null) {
                ++this.ticks;
                if (this.ticks > 600) {
                    SeahorseEntity.this.savedCoralPos = null;
                } else if (!SeahorseEntity.this.navigator.hasPath()) {
                    if (SeahorseEntity.this.isTooFar(SeahorseEntity.this.savedCoralPos)) {
                        SeahorseEntity.this.savedCoralPos = null;
                    } else {
                        SeahorseEntity.this.startMovingTo(SeahorseEntity.this.savedCoralPos);
                    }
                }
            }
        }
    }

    class GrowCoralGoal extends Goal {
        private final Predicate<BlockState> coralPredicate = (state) -> {
            return state.isIn(BlockTags.CORAL_BLOCKS);
        };
        private int growTicks = 0;
        private int lastPollinationTick = 0;
        private boolean running;
        private Vector3d nextTarget;
        private int ticks = 0;

        GrowCoralGoal() {
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean shouldExecute() {
            if (SeahorseEntity.this.remainingCooldownBeforeLocatingNewCoral > 0) {
                return false;
            } else if (SeahorseEntity.this.world.isRaining()) {
                return false;
            } else if (SeahorseEntity.this.rand.nextFloat() < 0.7F) {
                return false;
            } else {
                Optional<BlockPos> optional = this.getCoral();
                if (optional.isPresent()) {
                    SeahorseEntity.this.savedCoralPos = optional.get();
                    SeahorseEntity.this.navigator.tryMoveToXYZ((double) SeahorseEntity.this.savedCoralPos.getX() + 0.5D, (double) SeahorseEntity.this.savedCoralPos.getY() + 0.5D, (double) SeahorseEntity.this.savedCoralPos.getZ() + 0.5D, (double)1.2F);
                    return true;
                } else {
                    return false;
                }
            }
        }

        public boolean shouldContinueExecuting() {
            if (!this.running) {
                return false;
            } else if (!SeahorseEntity.this.hasCoral()) {
                return false;
            } else if (SeahorseEntity.this.world.isRaining()) {
                return false;
            } else if (this.completedGrowth()) {
                return SeahorseEntity.this.rand.nextFloat() < 0.2F;
            } else if (SeahorseEntity.this.ticksExisted % 20 == 0 && !SeahorseEntity.this.isCoral(SeahorseEntity.this.savedCoralPos)) {
                SeahorseEntity.this.savedCoralPos = null;
                return false;
            } else {
                return true;
            }
        }

        private boolean completedGrowth() {
            return this.growTicks > 400;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.growTicks = 0;
            this.ticks = 0;
            this.lastPollinationTick = 0;
            this.running = true;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            this.running = false;
            SeahorseEntity.this.navigator.clearPath();
            SeahorseEntity.this.remainingCooldownBeforeLocatingNewCoral = 200;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            ++this.ticks;
            if (this.ticks > 600) {
                SeahorseEntity.this.savedCoralPos = null;
            } else {
                Vector3d vector3d = Vector3d.copyCenteredHorizontally(SeahorseEntity.this.savedCoralPos).add(0.0D, (double)0.6F, 0.0D);
                if (vector3d.distanceTo(SeahorseEntity.this.getPositionVec()) > 1.0D) {
                    this.nextTarget = vector3d;
                    this.moveToNextTarget();
                } else {
                    if (this.nextTarget == null) {
                        this.nextTarget = vector3d;
                    }

                    boolean flag = SeahorseEntity.this.getPositionVec().distanceTo(this.nextTarget) <= 0.1D;
                    boolean flag1 = true;
                    if (!flag && this.ticks > 600) {
                        SeahorseEntity.this.savedCoralPos = null;
                    } else {
                        if (flag) {
                            boolean flag2 = SeahorseEntity.this.rand.nextInt(25) == 0;
                            if (flag2) {
                                BlockPos coralPos = new BlockPos(this.nextTarget.x, this.nextTarget.y, this.nextTarget.z);
                                Direction direction = Direction.byIndex(SeahorseEntity.this.rand.nextInt(Direction.values().length));
                                BlockPos place = coralPos.offset(direction);

                                if ((SeahorseEntity.this.world.isAirBlock(place) || SeahorseEntity.this.world.getBlockState(place).getMaterial().isLiquid()) && direction != Direction.DOWN) {
                                    if (direction == Direction.UP) {
                                        SeahorseEntity.this.world.setBlockState(place, getVariantType().getCoral().with(AbstractCoralPlantBlock.WATERLOGGED, SeahorseEntity.this.world.getFluidState(place).isTagged(FluidTags.WATER)), 3);
                                    } else {
                                        SeahorseEntity.this.world.setBlockState(place, getVariantType().getFan().with(CoralFanBlock.WATERLOGGED, SeahorseEntity.this.world.getFluidState(place).isTagged(FluidTags.WATER)), 3);
                                    }
                                }

                                this.nextTarget = new Vector3d(vector3d.getX() + (double)this.getRandomOffset(), vector3d.getY(), vector3d.getZ() + (double)this.getRandomOffset());
                                SeahorseEntity.this.navigator.clearPath();
                            } else {
                                flag1 = false;
                            }

                            SeahorseEntity.this.getLookController().setLookPosition(vector3d.getX(), vector3d.getY(), vector3d.getZ());
                        }

                        if (flag1) {
                            this.moveToNextTarget();
                        }

                        ++this.growTicks;
                        if (SeahorseEntity.this.rand.nextFloat() < 0.05F && this.growTicks > this.lastPollinationTick + 60) {
                            this.lastPollinationTick = this.growTicks;
                            SeahorseEntity.this.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, 1.0F, 1.0F);
                        }

                    }
                }
            }
        }

        private void moveToNextTarget() {
            SeahorseEntity.this.getMoveHelper().setMoveTo(this.nextTarget.getX(), this.nextTarget.getY(), this.nextTarget.getZ(), (double)0.35F);
        }

        private float getRandomOffset() {
            return (SeahorseEntity.this.rand.nextFloat() * 2.0F - 1.0F) * 0.33333334F;
        }

        private Optional<BlockPos> getCoral() {
            return this.findCoral(this.coralPredicate, 5.0D);
        }

        private Optional<BlockPos> findCoral(Predicate<BlockState> p_226500_1_, double distance) {
            BlockPos blockpos = SeahorseEntity.this.getPosition();
            BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

            for(int i = 0; (double)i <= distance; i = i > 0 ? -i : 1 - i) {
                for(int j = 0; (double)j < distance; ++j) {
                    for(int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
                        for(int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
                            blockpos$mutable.setAndOffset(blockpos, k, i - 1, l);
                            if (blockpos.withinDistance(blockpos$mutable, distance) && p_226500_1_.test(SeahorseEntity.this.world.getBlockState(blockpos$mutable))) {
                                return Optional.of(blockpos$mutable);
                            }
                        }
                    }
                }
            }
            return Optional.empty();
        }
    }

    public enum CoralType {
        BUBBLE(0, "bubble", Blocks.BUBBLE_CORAL.getDefaultState(), Blocks.BUBBLE_CORAL_FAN.getDefaultState()),
        FIRE(1, "fire", Blocks.FIRE_CORAL.getDefaultState(), Blocks.FIRE_CORAL_FAN.getDefaultState()),
        TUBE(2, "tube", Blocks.TUBE_CORAL.getDefaultState(), Blocks.TUBE_CORAL_FAN.getDefaultState()),
        BRAIN(3, "brain", Blocks.BRAIN_CORAL.getDefaultState(), Blocks.BRAIN_CORAL_FAN.getDefaultState()),
        HORN(4, "horn", Blocks.HORN_CORAL.getDefaultState(), Blocks.HORN_CORAL_FAN.getDefaultState());

        private static final SeahorseEntity.CoralType[] field_221088_c = Arrays.stream(values()).sorted(Comparator.comparingInt(SeahorseEntity.CoralType::getIndex)).toArray(SeahorseEntity.CoralType[]::new);
        private static final Map<String, SeahorseEntity.CoralType> TYPES_BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(SeahorseEntity.CoralType::getName, (p_221081_0_) -> {
            return p_221081_0_;
        }));
        private final int index;
        private final String name;
        private final BlockState coral;
        private final BlockState fan;

        CoralType(int index, String name, BlockState coral, BlockState fan) {
            this.index = index;
            this.name = name;
            this.coral = coral;
            this.fan = fan;
        }

        public String getName() {
            return this.name;
        }

        public int getIndex() {
            return this.index;
        }

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