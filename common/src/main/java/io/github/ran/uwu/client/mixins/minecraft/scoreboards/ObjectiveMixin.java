package io.github.ran.uwu.client.mixins.minecraft.scoreboards;

import io.github.ran.uwu.client.Uwuifier;
import io.github.ran.uwu.client.config.UwUConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.scores.Objective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
#if PRE_MC_1_19_2
import net.minecraft.network.chat.TextComponent;
#endif

@Mixin(Objective.class)
public class ObjectiveMixin {
    @Shadow
    private Component displayName;

    @Inject(method = "getDisplayName", at = @At("HEAD"), cancellable = true)
    private void getDisplayName(CallbackInfoReturnable<Component> cir) {
        cir.setReturnValue(UwUConfig.uwuifyMinecraft ? uwufiedText(this.displayName) : this.displayName);
    }

    @Unique
    private Component uwufiedText(Component text) {
        #if PRE_MC_1_19_2
        return new TextComponent(Uwuifier.uwuWithoutCuteFace(text.getString())).setStyle(text.getStyle());
        #else
        return Component.literal(Uwuifier.uwuWithoutCuteFace(text.getString())).setStyle(text.getStyle());
        #endif
    }
}
