package pugz.omni.core.mixin;

import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.model.RenderMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pugz.omni.core.util.RegistryUtil;

import java.util.Collections;
import java.util.function.Consumer;

@Mixin(Atlases.class)
public class AtlasesMixin {
    @Inject(
            method = "collectAllMaterials",
            at = @At("RETURN")
    )
    private static void collectModdedSigns(Consumer<RenderMaterial> consumer, CallbackInfo info) {
        for (RenderMaterial material : Collections.unmodifiableCollection(RegistryUtil.sprites)) {
            consumer.accept(material);
        }
    }
}