package io.github.ran.uwu.client.mixins.fontRenderer;

import io.github.ran.uwu.client.Uwuifier;
import io.github.ran.uwu.client.config.UwUConfig;
import net.minecraft.client.gui.Font;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Font.class)
public class FontMixin {
    @ModifyVariable(method = "drawInternal(Ljava/lang/String;FFIZLcom/mojang/math/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;ZIIZ)I", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private String uwuifiedString(String string) {
//        return (UwUConfig.uwuifyFontRenderer && UwUConfig.uwuifyMinecraft) ? Uwuifier.uwuWithoutCuteFace(string) : string;
        return (UwUConfig.uwuifyFontRenderer) ? Uwuifier.uwuWithoutCuteFace(string) : string;
    }
}
