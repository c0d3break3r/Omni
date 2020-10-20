package com.pugz.omni.common.entity.colormatic;

import com.pugz.omni.common.block.colormatic.LayerConcreteBlock;
import com.pugz.omni.common.block.colormatic.LayerConcretePowderBlock;
import com.pugz.omni.core.registry.OmniEntities;
import net.minecraft.block.*;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FallingConcretePowderEntity extends Entity {
    private BlockState fallTile = Blocks.SAND.getDefaultState();
    public int fallTime;
    public boolean shouldDropItem = true;
    private boolean dontSetBlock;
    protected static final DataParameter<BlockPos> ORIGIN = EntityDataManager.createKey(FallingConcretePowderEntity.class, DataSerializers.BLOCK_POS);

    public FallingConcretePowderEntity(EntityType<? extends FallingConcretePowderEntity> type, World world) {
        super(type, world);
    }

    public FallingConcretePowderEntity(World worldIn, double x, double y, double z, BlockState fallingBlockState) {
        this(OmniEntities.FALLING_CONCRETE_POWDER.get(), worldIn);
        this.fallTile = fallingBlockState;
        this.preventEntitySpawning = true;
        this.setPosition(x, y + (double)((1.0F - this.getHeight()) / 2.0F), z);
        this.setMotion(Vector3d.ZERO);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        this.setOrigin(this.getPosition());
    }

    /**
     * Returns true if it's possible to attack this entity with an item.
     */
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

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith() {
        return !this.removed;
    }

    /**
     * Called to update the entity's position/logic.
     */
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
                boolean flag = this.fallTile.getBlock() instanceof LayerConcretePowderBlock;
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
                        if (this.shouldDropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                            this.entityDropItem(block);
                        }

                        this.remove();
                    }
                } else {
                    BlockState blockstate = this.world.getBlockState(blockpos1);
                    this.setMotion(this.getMotion().mul(0.7D, -0.5D, 0.7D));
                    if (!blockstate.isIn(Blocks.MOVING_PISTON)) {
                        this.remove();
                        if (!this.dontSetBlock) {
                            boolean flag2 = blockstate.isReplaceable(new DirectionalPlaceContext(this.world, blockpos1, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
                            boolean flag3 = LayerConcretePowderBlock.canFallThrough(this.world.getBlockState(blockpos1.down())) && (!flag || !flag1);
                            boolean flag4 = this.fallTile.isValidPosition(this.world, blockpos1) && !flag3;
                            if ((flag2 || (blockstate.getBlock() instanceof LayerConcretePowderBlock || blockstate.getBlock() instanceof LayerConcreteBlock)) && flag4) {
                                if (this.fallTile.hasProperty(BlockStateProperties.WATERLOGGED) && this.world.getFluidState(blockpos1).getFluid() == Fluids.WATER) {
                                    this.fallTile = this.fallTile.with(BlockStateProperties.WATERLOGGED, true);
                                }

                                if (blockstate.getBlock() instanceof LayerConcretePowderBlock) {
                                    if (block.getMaterialColor() == blockstate.getBlock().getMaterialColor()) {
                                        if (blockstate.get(LayerConcretePowderBlock.LAYERS) == 8)
                                            world.setBlockState(blockpos1.up(), this.fallTile, 3);

                                        else {
                                            int totalLayers = blockstate.get(LayerConcretePowderBlock.LAYERS) + this.fallTile.get(LayerConcretePowderBlock.LAYERS);

                                            if (totalLayers <= 8)
                                                world.setBlockState(blockpos1, blockstate.with(LayerConcretePowderBlock.LAYERS, totalLayers), 3);
                                            else {
                                                world.setBlockState(blockpos1, this.fallTile.with(LayerConcretePowderBlock.LAYERS, 8), 3);
                                                world.setBlockState(blockpos1.up(), this.fallTile.with(LayerConcretePowderBlock.LAYERS, totalLayers - 8), 3);
                                            }
                                        }
                                    }
                                }

                                else if (blockstate.getBlock() instanceof LayerConcreteBlock) {
                                    if (((LayerConcretePowderBlock)block).getSolidifiedState().getBlock().getMaterialColor() == blockstate.getBlock().getMaterialColor() && blockstate.get(LayerConcreteBlock.WATERLOGGED) && blockstate.get(LayerConcreteBlock.LAYERS) < 7) {
                                        int totalLayers = blockstate.get(LayerConcreteBlock.LAYERS) + this.fallTile.get(LayerConcretePowderBlock.LAYERS);

                                        if (totalLayers <= 8) world.setBlockState(blockpos1, blockstate.with(LayerConcreteBlock.LAYERS, totalLayers).with(LayerConcreteBlock.WATERLOGGED, totalLayers < 8), 3);
                                        else {
                                            world.setBlockState(blockpos1, blockstate.with(LayerConcreteBlock.LAYERS, 8).with(LayerConcreteBlock.WATERLOGGED, false), 3);
                                            world.setBlockState(blockpos1.up(), this.fallTile.with(LayerConcretePowderBlock.LAYERS, totalLayers - 8).with(LayerConcretePowderBlock.WATERLOGGED, false), 3);
                                        }
                                    }
                                }

                                else if (!(blockstate.getBlock() instanceof LayerConcretePowderBlock)) {
                                    if (this.world.setBlockState(blockpos1, this.fallTile, 3)) {
                                        ((LayerConcretePowderBlock) block).onEndFalling(this.world, blockpos1, this.fallTile, blockstate, this);
                                    }
                                }

                                else if (this.shouldDropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                                    this.entityDropItem(block);
                                }
                            } else if (this.shouldDropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                                this.entityDropItem(block);
                            }
                        }
                    }
                }
            }

            this.setMotion(this.getMotion().scale(0.98D));
        }
    }

    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    protected void writeAdditional(CompoundNBT compound) {
        if (this.fallTile.getBlock() instanceof LayerConcretePowderBlock) compound.put("BlockState", NBTUtil.writeBlockState(this.fallTile));
        compound.putInt("Time", this.fallTime);
        compound.putBoolean("DropItem", this.shouldDropItem);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readAdditional(CompoundNBT compound) {
        this.fallTile = NBTUtil.readBlockState(compound.getCompound("BlockState"));
        this.fallTime = compound.getInt("Time");

        if (compound.contains("DropItem", 99)) {
            this.shouldDropItem = compound.getBoolean("DropItem");
        }

        if (this.fallTile.isAir()) {
            this.fallTile = Blocks.SAND.getDefaultState();
        }

    }

    @OnlyIn(Dist.CLIENT)
    public World getWorldObj() {
        return this.world;
    }

    /**
     * Return whether this entity should be rendered as on fire.
     */
    @OnlyIn(Dist.CLIENT)
    public boolean canRenderOnFire() {
        return false;
    }

    public void fillCrashReport(CrashReportCategory category) {
        super.fillCrashReport(category);
        category.addDetail("Immitating BlockState", this.fallTile.toString());
    }

    public BlockState getBlockState() {
        return this.fallTile;
    }

    public boolean ignoreItemEntityData() {
        return true;
    }

    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this, Block.getStateId(this.getBlockState()));
    }
}