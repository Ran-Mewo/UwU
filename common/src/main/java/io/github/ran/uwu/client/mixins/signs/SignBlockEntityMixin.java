package io.github.ran.uwu.client.mixins.signs;

import io.github.ran.uwu.client.Uwuifier;
import io.github.ran.uwu.client.config.UwUConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
#if PRE_MC_1_19_3
import net.minecraft.network.chat.TextComponent;
#endif

@Mixin(SignBlockEntity.class)
public abstract class SignBlockEntityMixin {
    #if POST_MC_1_16_5
       @Shadow
       protected abstract Component[] getMessages(boolean filtered);

       @Inject(method = "getMessage", at = @At("HEAD"), cancellable = true)
       private void onGetTextOnRow(int row, boolean filtered, CallbackInfoReturnable<Component> cir) {
           cir.setReturnValue(UwUConfig.getInstance().uwuifySigns ? uwufiedText(this.getMessages(filtered)[row]) : this.getMessages(filtered)[row]);
       }
   #else
    @Shadow @Final private Component[] messages;
    @Inject(method = "getMessage", at = @At("HEAD"), cancellable = true)
    private void onGetTextOnRow(int row, CallbackInfoReturnable<Component> cir) {
        cir.setReturnValue(UwUConfig.getInstance().uwuifySigns ? uwufiedText(this.messages[row]) : this.messages[row]);
    }
    #endif

    @Unique
    private Component uwufiedText(Component text) {
        #if PRE_MC_1_19_3
        return new TextComponent(Uwuifier.uwuWithoutCuteFace(text.getString())).setStyle(text.getStyle());
        #else
        return Component.literal(Uwuifier.uwuWithoutCuteFace(text.getString())).setStyle(text.getStyle());
        #endif
    }
}
