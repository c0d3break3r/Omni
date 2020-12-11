package pugz.omni.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import pugz.omni.common.entity.wild_west.TumbleweedEntity;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class TumbleweedRenderer extends EntityRenderer<TumbleweedEntity> {
    public TumbleweedRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowSize = 0.4F;
    }

    @Override
    public void render(TumbleweedEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStack, bufferIn, packedLightIn);
        matrixStack.push();
        matrixStack.translate(0.0D, 0.5D, 0.0D);
        matrixStack.rotate(slerp(entityIn.prevQuaternion, entityIn.quaternion, partialTicks));

        matrixStack.rotate(Vector3f.YP.rotationDegrees(-90.0F));
        matrixStack.translate(-0.5D, -0.5D, 0.5D);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(90.0F));

        Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(Blocks.DEAD_BUSH.getDefaultState(), matrixStack, bufferIn, packedLightIn,  OverlayTexture.NO_OVERLAY);

        matrixStack.pop();
    }

    public static Quaternion slerp(Quaternion v0, Quaternion v1, float t) {
        float dot = v0.getX() * v1.getX() + v0.getY() * v1.getY() + v0.getZ() * v1.getZ() + v0.getW() * v1.getW();
        if (dot < 0.0f) {
            v1 = new Quaternion(-v1.getX(), -v1.getY(), -v1.getZ(), -v1.getW());
            dot = -dot;
        }

        if (dot > 0.9995F) {
            float x = MathHelper.lerp(t, v0.getX(), v1.getX());
            float y = MathHelper.lerp(t, v0.getY(), v1.getY());
            float z = MathHelper.lerp(t, v0.getZ(), v1.getZ());
            float w = MathHelper.lerp(t, v0.getW(), v1.getW());
            return new Quaternion(x,y,z,w);
        }

        float angle01 = (float)Math.acos(dot);
        float angle0t = angle01*t;
        float sin0t = MathHelper.sin(angle0t);
        float sin01 = MathHelper.sin(angle01);
        float sin1t = MathHelper.sin(angle01 - angle0t);

        float s1 = sin0t / sin01;
        float s0 = sin1t / sin01;

        return new Quaternion(s0 * v0.getX() + s1 * v1.getX(), s0 * v0.getY() + s1 * v1.getY(), s0 * v0.getZ() + s1 * v1.getZ(), s0 * v0.getW() + s1 * v1.getW());
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public ResourceLocation getEntityTexture(TumbleweedEntity entity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }
}
