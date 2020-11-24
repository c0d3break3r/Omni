package pugz.omni.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import pugz.omni.common.entity.cavier_caves.SizedCaveSpiderEntity;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class SizedCaveSpiderRenderer extends SpiderRenderer<SizedCaveSpiderEntity> {
    private static final ResourceLocation CAVE_SPIDER_TEXTURES = new ResourceLocation("textures/entity/spider/cave_spider.png");

    public SizedCaveSpiderRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowSize *= 0.7F;
    }

    protected void preRenderCallback(SizedCaveSpiderEntity entity, MatrixStack matrixStackIn, float partialTickTime) {
        int i = entity.getSpiderSize();
        float f = 0.6F + 0.225F * (float) (i * i);
        matrixStackIn.scale(f, f, f);
    }

    @Nonnull
    public ResourceLocation getEntityTexture(SizedCaveSpiderEntity entity) {
        return CAVE_SPIDER_TEXTURES;
    }
}