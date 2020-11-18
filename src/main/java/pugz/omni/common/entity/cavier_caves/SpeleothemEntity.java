package pugz.omni.common.entity.cavier_caves;

import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;
import pugz.omni.common.block.cavier_caves.SpeleothemBlock;
import pugz.omni.core.registry.OmniEntities;

import javax.annotation.Nonnull;
import java.util.List;

public class SpeleothemEntity extends Entity implements IEntityAdditionalSpawnData {
    private BlockState fallTile;
    public int fallTime;
    protected static final DataParameter<BlockPos> ORIGIN = EntityDataManager.createKey(FallingBlockEntity.class, DataSerializers.BLOCK_POS);

    public SpeleothemEntity(EntityType<SpeleothemEntity> entity, World worldIn) {
        super(entity, worldIn);
    }

    public SpeleothemEntity(World worldIn, double x, double y, double z, BlockState state) {
        super(OmniEntities.SPELEOTHEM.get(), worldIn);
        this.preventEntitySpawning = true;
        this.setPosition(x, y, z);
        this.setMotion(Vector3d.ZERO);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        this.fallTile = state;
    }

    public boolean canBeAttackedWithItem() {
        return false;
    }

    public void setOrigin(BlockPos origin) {
        this.dataManager.set(ORIGIN, origin);
    }

    @OnlyIn(Dist.CLIENT)
    public BlockPos getOrigin() {
        return this.dataManager.get(ORIGIN);
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    protected void registerData() {
        this.dataManager.register(ORIGIN, BlockPos.ZERO);
    }

    @SuppressWarnings("deprecation")
    public boolean canBeCollidedWith() {
        return !this.removed;
    }

    protected void writeAdditional(CompoundNBT compound) {
        compound.put("BlockState", NBTUtil.writeBlockState(this.fallTile));
        compound.putInt("Time", this.fallTime);
    }

    @SuppressWarnings("deprecation")
    protected void readAdditional(CompoundNBT compound) {
        this.fallTile = NBTUtil.readBlockState(compound.getCompound("BlockState"));
        this.fallTime = compound.getInt("Time");
        if (this.fallTile.isAir()) {
            this.fallTile = Blocks.SAND.getDefaultState();
        }
    }

    @SuppressWarnings("deprecation")
    public void tick() {
        if (this.fallTile.isAir()) {
            this.remove();
        } else {
            Block block = this.fallTile.getBlock();
            if (this.fallTime++ == 0) {
                BlockPos blockpos = this.getPosition();
                if (this.world.getBlockState(blockpos).isIn(block)) {
                    this.world.removeBlock(blockpos, false);
                } else if (!this.world.isRemote) {
                    this.remove();
                    return;
                }
            }

            if (!this.hasNoGravity()) {
                this.setMotion(this.getMotion().add(0.0D, -0.04D, 0.0D));
            }
            this.move(MoverType.SELF, this.getMotion());
            if (!this.world.isRemote) {
                BlockPos blockpos1 = this.getPosition();
                boolean flag = this.fallTile.getBlock() instanceof ConcretePowderBlock;
                boolean flag1 = flag && this.world.getFluidState(blockpos1).isTagged(FluidTags.WATER);
                double d0 = this.getMotion().lengthSquared();
                if (flag && d0 > 1.0D) {
                    BlockRayTraceResult blockraytraceresult = this.world.rayTraceBlocks(new RayTraceContext(new Vector3d(this.prevPosX, this.prevPosY, this.prevPosZ), this.getPositionVec(), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.SOURCE_ONLY, this));
                    if (blockraytraceresult.getType() != RayTraceResult.Type.MISS && this.world.getFluidState(blockraytraceresult.getPos()).isTagged(FluidTags.WATER)) {
                        blockpos1 = blockraytraceresult.getPos();
                        flag1 = true;
                    }
                }
                if (!this.onGround && !flag1) {
                    if (!this.world.isRemote && (this.fallTime > 100 && (blockpos1.getY() < 1 || blockpos1.getY() > 256) || this.fallTime > 600)) {
                        this.remove();
                    }
                } else {
                    BlockState blockstate = this.world.getBlockState(blockpos1);
                    this.setMotion(this.getMotion().mul(0.7D, -0.5D, 0.7D));
                    if (!blockstate.isIn(Blocks.MOVING_PISTON)) {
                        this.remove();
                        boolean flag2 = blockstate.isReplaceable(new DirectionalPlaceContext(this.world, blockpos1, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
                        boolean flag3 = FallingBlock.canFallThrough(this.world.getBlockState(blockpos1.down())) && (!flag || !flag1);
                        boolean flag4 = this.fallTile.isValidPosition(this.world, blockpos1) && !flag3;
                        if (flag2 && flag4) {
                            if (this.fallTile.hasProperty(BlockStateProperties.WATERLOGGED) && this.world.getFluidState(blockpos1).getFluid() == Fluids.WATER) {
                                this.fallTile = this.fallTile.with(BlockStateProperties.WATERLOGGED, Boolean.TRUE);
                            }
                            if (this.world.setBlockState(blockpos1, this.fallTile, 3)) {
                                if (block instanceof FallingBlock) {
                                    ((SpeleothemBlock) block).onEndFalling(this.world, blockpos1, this.fallTile, blockstate, this);
                                }
                            }
                        }
                    }
                }
            }
            this.setMotion(this.getMotion().scale(0.98D));
        }
    }

    public boolean onLivingFall(float distance, float damageMultiplier) {
        int i = MathHelper.ceil(distance - 1.0F);
        if (i > 0) {
            List<Entity> list = Lists.newArrayList(this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox()));

            for(Entity entity : list) {
                entity.attackEntityFrom(DamageSource.FALLING_BLOCK, (float)Math.min(MathHelper.floor((float)i * 4.0F), 40));
            }
        }
        return false;
    }

    @Nonnull
    public BlockState getBlockState() {
        return fallTile;
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeInt(Block.getStateId(this.getBlockState()));
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        this.fallTile = Block.getStateById(additionalData.readInt());
    }
}