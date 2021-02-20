package pugz.omni.core.mixin;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pugz.omni.core.Omni;
import pugz.omni.core.util.IOmniSign;

@Mixin(SignTileEntityRenderer.class)
public class SignTileEntityRendererMixin {
    @Inject(
            method = "getMaterial",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void getMaterial(Block block, CallbackInfoReturnable<RenderMaterial> info) {
        if (block instanceof IOmniSign) {
            info.setReturnValue(new RenderMaterial(Atlases.SIGN_ATLAS, new ResourceLocation(Omni.MOD_ID, "entity/sign/" + ((IOmniSign) block).getWood())));
        }
    }
}