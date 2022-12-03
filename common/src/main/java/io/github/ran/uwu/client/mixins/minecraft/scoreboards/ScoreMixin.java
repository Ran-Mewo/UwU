package io.github.ran.uwu.client.mixins.minecraft.scoreboards;

import io.github.ran.uwu.client.Uwuifier;
import io.github.ran.uwu.client.config.UwUConfig;
import net.minecraft.world.scores.Score;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Score.class)
public class ScoreMixin {
    @Shadow @Final private String owner;

    @Inject(method = "getOwner", at = @At("HEAD"), cancellable = true)
    private void getDisplayName(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(UwUConfig.uwuifyMinecraft ? Uwuifier.uwuWithoutCuteFace(this.owner) : this.owner);
    }
}
