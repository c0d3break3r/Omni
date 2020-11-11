package pugz.omni.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import pugz.omni.common.entity.colormatic.FallingConcretePowderEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;

import javax.annotation.Nonnull;
import java.util.Random;

public class FallingConcretePowderRenderer extends EntityRenderer<FallingConcretePowderEntity> {
    public FallingConcretePowderRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowSize = 0.5F;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void render(FallingConcretePowderEntity entity, float p_225623_2_, float p_225623_3_, MatrixStack matrixstack, IRenderTypeBuffer buffer, int p_225623_6_) {
        if (entity.getLayers() <= 0 && entity.getLayers() > 8) return;
        BlockState blockstate = entity.getBlockState();
        World world = entity.getEntityWorld();

        if (blockstate.getRenderType() == BlockRenderType.MODEL) {
            matrixstack.push();
            BlockPos blockpos = new BlockPos(entity.getPosX(), entity.getBoundingBox().maxY, entity.getPosZ());
            matrixstack.translate(-0.5D, 0.0D, -0.5D);
            BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
            for (RenderType type : RenderType.getBlockRenderTypes()) {
                if (RenderTypeLookup.canRenderInLayer(blockstate, type)) {
                    ForgeHooksClient.setRenderLayer(type);
                    blockrendererdispatcher.getBlockModelRenderer().renderModel(world, blockrendererdispatcher.getModelForState(blockstate), blockstate, blockpos, matrixstack, buffer.getBuffer(type), false, new Random(), blockstate.getPositionRandom(entity.getOrigin()), OverlayTexture.NO_OVERLAY);
                }
            }
            ForgeHooksClient.setRenderLayer(null);
            matrixstack.pop();
            super.render(entity, p_225623_2_, p_225623_3_, matrixstack, buffer, p_225623_6_);
        }
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public ResourceLocation getEntityTexture(@Nonnull FallingConcretePowderEntity entity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }
}
