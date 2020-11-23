package pugz.omni.common.entity.cavier_caves;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.CaveSpiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import pugz.omni.core.registry.OmniItems;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class SizedCaveSpiderEntity extends CaveSpiderEntity {
    private static final DataParameter<Integer> SIZE = EntityDataManager.createKey(SizedCaveSpiderEntity.class, DataSerializers.VARINT);

    public SizedCaveSpiderEntity(EntityType<? extends SizedCaveSpiderEntity> type, World worldIn) {
        super(type, worldIn);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SIZE, 0);
    }

    @Nonnull
    public ResourceLocation getLootTable() {
        return new ResourceLocation("entities/spider/cave_spider");
    }

    public void setSpiderSize(int sizeIn) {
        this.dataManager.set(SIZE, MathHelper.clamp(sizeIn, 0, 32));
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(12.0D * 0.5D * sizeIn);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1.0D * 0.5D * sizeIn);
    }

    private void updateSpiderSize() {
        this.recalculateSize();
    }

    public int getSpiderSize() {
        return this.dataManager.get(SIZE);
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height * 0.5F;
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        if (SIZE.equals(key)) {
            this.updateSpiderSize();
        }
        super.notifyDataManagerChange(key);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Size", this.getSpiderSize());
    }

    public void readAdditional(CompoundNBT compound) {
        this.setSpiderSize(compound.getInt("Size"));
        super.readAdditional(compound);
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        if (super.attackEntityAsMob(entityIn)) {
            if (entityIn instanceof LivingEntity) {
                int i = this.getSpiderSize();
                if (this.world.getDifficulty() == Difficulty.NORMAL) {
                    i = 7;
                } else if (this.world.getDifficulty() == Difficulty.HARD) {
                    i = 15;
                }

                if (i > 0) {
                    ((LivingEntity)entityIn).addPotionEffect(new EffectInstance(Effects.POISON, i * 20, 0));
                }
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(OmniItems.CAVE_SPIDER_SPAWN_EGG.get());
    }

    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setSpiderSize(worldIn.getRandom().nextInt(4));
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Nonnull
    public EntitySize getSize(Pose poseIn) {
        int i = this.getSpiderSize();
        EntitySize entitysize = super.getSize(poseIn);
        float f = 1.0F + 0.3F * (float) i;
        return entitysize.scale(f * 2.0F, f * 1.6F);
    }
}