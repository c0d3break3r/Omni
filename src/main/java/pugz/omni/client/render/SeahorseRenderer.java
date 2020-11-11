package pugz.omni.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import pugz.omni.client.model.SeahorseModel;
import pugz.omni.common.entity.paradise.SeahorseEntity;
import pugz.omni.core.Omni;

import javax.annotation.Nonnull;

public class SeahorseRenderer extends MobRenderer<SeahorseEntity, EntityModel<SeahorseEntity>> {
    public SeahorseRenderer(EntityRendererManager manager) {
        super(manager, new SeahorseModel<>(), 0.3F);
    }

    @Override
    public void render(SeahorseEntity seahorse, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(seahorse, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(SeahorseEntity seahorse) {
        return new ResourceLocation(Omni.MOD_ID, "textures/entity/seahorse/seahorse_" + seahorse.getVariantType().getName() + ".png");
    }

    protected void preRenderCallback(SeahorseEntity entity, MatrixStack matrixStackIn, float partialTickTime) {
        int i = entity.getSeahorseSize();
        float f = 1.0F + 0.3F * (float)i;
        matrixStackIn.scale(f, f, f);
    }
}