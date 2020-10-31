package com.pugz.omni.common.entity.colormatic;

import com.pugz.omni.common.block.colormatic.LayerConcreteBlock;
import com.pugz.omni.common.block.colormatic.LayerConcretePowderBlock;
import com.pugz.omni.core.registry.OmniEntities;
import net.minecraft.block.*;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.FallingBlockEntity;
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
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class FallingConcretePowderEntity extends FallingBlockEntity {
    private BlockState fallTile = Blocks.SAND.getDefaultState();
    public int fallTime;
    public boolean shouldDropItem = true;
    private boolean dontSetBlock;

    public FallingConcretePowderEntity(EntityType<? extends FallingConcretePowderEntity> type, World world) {
        super(type, world);
    }

    public FallingConcretePowderEntity(World world, double x, double y, double z, BlockState fallingBlockState) {
        this(OmniEntities.FALLING_CONCRETE_POWDER.get(), world);
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

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }
}