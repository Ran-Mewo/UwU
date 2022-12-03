package io.github.ran.uwu.client.mixins.minecraft.f3menu;

import io.github.ran.uwu.client.Uwuifier;
import io.github.ran.uwu.client.config.UwUConfig;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(DebugScreenOverlay.class)
public class DebugScreenOverlayMixin {
    @Inject(method = "getSystemInformation", at = @At("RETURN"), cancellable = true)
    private void uwuifyRightText(CallbackInfoReturnable<List<String>> cir) {
        if (UwUConfig.uwuifyMinecraft) {
            List<String> uwuifiedMessages = new ArrayList<>();
            cir.getReturnValue().forEach(message -> uwuifiedMessages.add(Uwuifier.uwuWithoutCuteFace(message)));
            cir.setReturnValue(uwuifiedMessages);
        }
    }

    @Inject(method = "getGameInformation", at = @At("RETURN"), cancellable = true)
    private void uwuifyLeftText(CallbackInfoReturnable<List<String>> cir) {
        if (UwUConfig.uwuifyMinecraft) {
            List<String> uwuifiedMessages = new ArrayList<>();
            cir.getReturnValue().forEach(message -> uwuifiedMessages.add(Uwuifier.uwuWithoutCuteFace(message)));
            cir.setReturnValue(uwuifiedMessages);
        }
    }
}
