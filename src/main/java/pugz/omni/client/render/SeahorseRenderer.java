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
        super(manager, new SeahorseModel<>(), 0.4F);
    }

    @Override
    public void render(SeahorseEntity pug, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (pug.isChild()) {
            this.shadowSize *= 0.5F;
        }
        super.render(pug, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(SeahorseEntity seahorseEntity) {
        switch (seahorseEntity.getVariantType()) {
            case FIRE:
                return new ResourceLocation(Omni.MOD_ID, "textures/entity/seahorse/seahorse_fire.png");
            case BRAIN:
                return new ResourceLocation(Omni.MOD_ID, "textures/entity/seahorse/seahorse_brain.png");
            case BUBBLE:
                return new ResourceLocation(Omni.MOD_ID, "textures/entity/seahorse/seahorse_bubble.png");
            case TUBE:
                return new ResourceLocation(Omni.MOD_ID, "textures/entity/seahorse/seahorse_tube.png");
            default:
                return new ResourceLocation(Omni.MOD_ID, "textures/entity/seahorse/seahorse_horn.png");
        }
    }

    @Override
    protected void preRenderCallback(SeahorseEntity pugEntity, MatrixStack matrixStack, float partialTickTime) {
        matrixStack.scale(1.0F, 1.0F, 1.0F);
        if (pugEntity.isChild()) {
            matrixStack.scale(0.5F, 0.5F, 0.5F);
        }
    }
}