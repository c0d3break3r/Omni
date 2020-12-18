package pugz.omni.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import pugz.omni.common.block.OmniStandingSignBlock;
import pugz.omni.common.block.OmniWallSignBlock;
import pugz.omni.common.tileentity.OmniSignTileEntity;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class OmniSignTileEntityRenderer<S extends OmniSignTileEntity> extends TileEntityRenderer<S> {
    private final OmniSignTileEntityRenderer.SignModel model = new OmniSignTileEntityRenderer.SignModel();

    public OmniSignTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    public void render(S sign, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLightIn, int combinedOverlayIn) {
        BlockState blockstate = sign.getBlockState();
        matrixStack.push();
        if(blockstate.getBlock() instanceof OmniStandingSignBlock) {
            matrixStack.translate(0.5D, 0.5D, 0.5D);
            float f1 = -((float)(blockstate.get(OmniStandingSignBlock.ROTATION) * 360) / 16.0F);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(f1));
            this.model.signStick.showModel = true;
        } else {
            matrixStack.translate(0.5D, 0.5D, 0.5D);
            float f4 = -blockstate.get(OmniWallSignBlock.FACING).getHorizontalAngle();
            matrixStack.rotate(Vector3f.YP.rotationDegrees(f4));
            matrixStack.translate(0.0D, -0.3125D, -0.4375D);
            this.model.signStick.showModel = false;
        }
        float scale = 0.6666667F;
        matrixStack.push();
        matrixStack.scale(scale, -scale, -scale);
        IVertexBuilder ivertexbuilder = buffer.getBuffer(RenderType.getEntityCutoutNoCull(blockstate.getBlock().getRegistryName()));
        this.model.signBoard.render(matrixStack, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        this.model.signStick.render(matrixStack, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        matrixStack.pop();

        FontRenderer fontrenderer = this.renderDispatcher.getFontRenderer();
        float textScale = 0.010416667F;
        matrixStack.translate(0.0F, 0.33333334F, 0.046666667F);
        matrixStack.scale(textScale, -textScale, textScale);
        int i = sign.getTextColor().getTextColor();
        double colorMultiplier = 0.4D;
        int j = (int) ((double) NativeImage.getRed(i) * colorMultiplier);
        int k = (int) ((double) NativeImage.getGreen(i) * colorMultiplier);
        int l = (int) ((double) NativeImage.getBlue(i) * colorMultiplier);
        int i1 = NativeImage.getCombined(0, l, k, j);

        for (int line = 0; line < 4; line++) {
            IReorderingProcessor processor = sign.func_242686_a(line, (p_239309_1_) -> {
                List<IReorderingProcessor> list = fontrenderer.trimStringToWidth(p_239309_1_, 90);
                return list.isEmpty() ? IReorderingProcessor.field_242232_a : list.get(0);
            });
            if (processor != null) {
                float f3 = (float)(-fontrenderer.func_243245_a(processor) / 2);
                fontrenderer.func_238416_a_(processor, f3, (float) (line * 10 - 20), i1, false, matrixStack.getLast().getMatrix(), buffer, false, 0, combinedLightIn);
            }
        }
        matrixStack.pop();
    }

    @OnlyIn(Dist.CLIENT)
    public static final class SignModel extends Model {
        public final ModelRenderer signBoard = new ModelRenderer(64, 32, 0, 0);
        public final ModelRenderer signStick;

        public SignModel() {
            super(RenderType::getEntityCutoutNoCull);
            this.signBoard.addBox(-12.0F, -14.0F, -1.0F, 24.0F, 12.0F, 2.0F, 0.0F);
            this.signStick = new ModelRenderer(64, 32, 0, 14);
            this.signStick.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 14.0F, 2.0F, 0.0F);
        }

        public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
            this.signBoard.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.signStick.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
    }
}