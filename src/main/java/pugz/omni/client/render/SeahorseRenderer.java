package pugz.omni.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import pugz.omni.client.model.SeahorseModel;
import pugz.omni.common.entity.paradise.SeahorseEntity;
import pugz.omni.core.Omni;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
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

    protected void applyRotations(SeahorseEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        if (!entityLiving.isInWater() && !entityLiving.isBeingRidden()) {
            matrixStackIn.translate((double)0.2F, (double)0.1F, 0.0D);
            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(90.0F));
        }
    }
}