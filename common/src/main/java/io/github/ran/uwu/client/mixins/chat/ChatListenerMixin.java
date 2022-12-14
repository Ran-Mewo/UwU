package io.github.ran.uwu.client.mixins.chat;

#if POST_CURRENT_MC_1_19_3
import io.github.ran.uwu.client.Uwuifier;
import io.github.ran.uwu.client.config.UwUConfig;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.chat.SignedMessageBody;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.minecraft.client.multiplayer.chat.ChatListener;
#endif
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

#if POST_CURRENT_MC_1_19_3
@Mixin(ChatListener.class)
#else
@Mixin(Minecraft.class)
#endif
public class ChatListenerMixin {
    #if POST_CURRENT_MC_1_19_3
    @ModifyVariable(method = "handlePlayerChatMessage", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private PlayerChatMessage handlePlayerChatMessage(PlayerChatMessage playerChatMessage) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && playerChatMessage.sender().equals(player.getUUID())) return playerChatMessage;
        if (UwUConfig.getInstance().uwuifyIncoming) {
            return new PlayerChatMessage(playerChatMessage.link(), playerChatMessage.signature(),
                    new SignedMessageBody(Uwuifier.signedUwu(playerChatMessage.signedBody().content()), playerChatMessage.signedBody().timeStamp(), playerChatMessage.signedBody().salt(), playerChatMessage.signedBody().lastSeen()),
                    uwufiedText(playerChatMessage.unsignedContent()), playerChatMessage.filterMask());
        }
        return playerChatMessage;
    }

    @Unique
    private Component uwufiedText(Component text) {
        if (text == null) return null;
        return Component.literal(Uwuifier.signedUwu(text.getString())).setStyle(text.getStyle());
    }
    #endif
}
