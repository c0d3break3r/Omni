package pugz.omni.common.entity.wild_west;

import com.sun.javafx.geom.Vec3d;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.network.NetworkHooks;
import pugz.omni.core.Omni;
import pugz.omni.core.registry.OmniBlocks;
import pugz.omni.core.registry.OmniEntities;

import javax.annotation.Nonnull;
import java.util.Random;

public class TumbleweedEntity extends Entity {
    private static final Random random = new Random();
    public Quaternion quaternion = new Quaternion(0,0,0,1);
    public Quaternion prevQuaternion = new Quaternion(0,0,0,1);
    private final float windOffset;
    private float age = 0;
    public static float windX = random.nextBoolean() ? random.nextFloat() / 10.0F : -random.nextFloat() / 10.0F;
    public static float windZ = random.nextBoolean() ? random.nextFloat() / 10.0F : -random.nextFloat() / 10.0F;

    private static float directionX = random.nextBoolean() ? 1.0F : -1.0F;
    private static float directionZ = random.nextBoolean() ? 1.0F : -1.0F;

    public TumbleweedEntity(EntityType<TumbleweedEntity> entity, World worldIn) {
        super(entity, worldIn);
        this.windOffset = 1.2F - (world.getRandom().nextFloat() / 3);
    }

    public TumbleweedEntity(World worldIn, double x, double y, double z) {
        super(OmniEntities.TUMBLEWEED.get(), worldIn);
        this.windOffset = 1.2F - (world.getRandom().nextFloat() / 3.0F);
        this.setPosition(x, y, z);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    @Override
    protected void registerData() {
    }

    @Nonnull
    public ResourceLocation getLootTable() {
        return new ResourceLocation(Omni.MOD_ID, "entities/tumbleweed");
    }

    @Override
    public void tick() {
        if (++age > 1500) remove();
        if (!isInWater()) this.setVelocity(this.getMotion().x, this.getMotion().y - 0.04D, this.getMotion().z);

        super.tick();

        prevQuaternion = new Quaternion(quaternion);
        Vec3d prevVelocity = new Vec3d(this.getMotion().x, this.getMotion().y, this.getMotion().z);

        double pX = this.getPosX();
        double pZ = this.getPosZ();

        this.move(MoverType.SELF, this.getMotion());

        double fX = this.getPosX();
        double fZ = this.getPosZ();
        double disX = fX - pX;
        double disZ = fZ - pZ;

        double vX = 0.0D, vY = 0.0D, vZ = 0.0D;

        if (!world.isRemote) {
            float acceleration = 0.0025F;
            vX = step(this.getMotion().getX(), (windX * windOffset), acceleration);
            vZ = step(this.getMotion().getZ(), (windZ * windOffset), acceleration);
            vY = this.getMotion().getY();

            if (onGround) {
                vY = MathHelper.clamp(Math.abs(prevVelocity.y) * 0.75D, 0.31F, 2.0F);
                //this.playSound(BMEffects.TUMBLEWEED_TUMBLE, 0.25F, 1.0F);
            }

            if(isInWater()) {
                vX *= 0.75F;
                vZ *= 0.75F;
                vY = 0.1F;
            }
        } else {
            float xRot;
            float zRot;
            if (onGround) {
                makeParticles(15);
                xRot = (float) -(disX / 0.25D);
                zRot = (float) (disZ / 0.25D);
            } else {
                xRot = (float) -(disX / 0.6D);
                zRot = (float) (disZ / 0.6D);
            }

            Quaternion rot = new Quaternion(zRot,0.0F, xRot, false);
            rot.multiply(quaternion);
            quaternion = rot;
        }

        if (!world.isRemote) {
            setVelocity(vX, vY, vZ);
            this.markVelocityChanged();
        }
    }

    @Override
    public void remove() {
        makeParticles(25);
        super.remove();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(source != DamageSource.CACTUS) {
            //this.playSound(BMEffects.TUMBLEWEED_BREAK, 0.25F, 1.0F);
            remove();
        }
        return true;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(OmniBlocks.TUMBLEWEED.get());
    }

    private void makeParticles(int count) {
        for(int i = 0; i < count; ++i) {
            world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, Blocks.DEAD_BUSH.getDefaultState()), -0.5D + (this.getPosX() + this.world.rand.nextDouble()), this.getPosY() + this.world.rand.nextDouble(), -0.5D + (this.getPosZ() + this.world.rand.nextDouble()), 0.0D, 0.0D, 0.0D);
        }
    }

    public static void updateWind() {
        if (random.nextInt(100) == 0) {
            directionX *= -1;
            windX += directionX * (random.nextFloat() / 25.0F);
        }

        if (random.nextInt(100) == 0) {
            directionZ *= -1;
            windZ += directionZ * (random.nextFloat() / 25.0F);
        }

        if (random.nextInt(20) == 0) {
            windX += directionX * (random.nextFloat() / 30.0F);
            windZ += directionZ * (random.nextFloat() / 30.0F);

            windX = MathHelper.clamp(windX, -0.7F, 0.7F);
            windZ = MathHelper.clamp(windZ, -0.7F, 0.7F);
        }
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canCollide(Entity entity) {
        return !this.removed;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    private double step(double val, double target, double step) {
        return val < target ? Math.min(val + step, target) : Math.max(val - step, target);
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}