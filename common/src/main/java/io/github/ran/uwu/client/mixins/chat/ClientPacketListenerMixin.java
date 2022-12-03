package io.github.ran.uwu.client.mixins.chat;

import io.github.ran.uwu.client.Uwuifier;
import io.github.ran.uwu.client.config.UwUConfig;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
#if PRE_MC_1_19_2
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundChatPacket;
import net.minecraft.network.chat.ChatType;
#else
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
#endif

import java.util.Optional;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    #if PRE_MC_1_19_2
    @ModifyVariable(method = "handleChat", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private ClientboundChatPacket onGameMessage(ClientboundChatPacket packet) {
        if (UwUConfig.getInstance().uwuifyIncoming) {
            if (packet.getType() == ChatType.CHAT) {
                packet = new ClientboundChatPacket(uwufiedText(packet.getMessage()), packet.getType(), packet.getSender());
            }
        }
        return packet;
    }

    @Unique
    private Component uwufiedText(Component text) {
        return new TextComponent(Uwuifier.uwu(text.getString())).setStyle(text.getStyle());
    }
    #else // Am too lazy... Someone fix this

    @ModifyVariable(method = "handleSystemChat", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private ClientboundSystemChatPacket onGameMessage(ClientboundSystemChatPacket packet) {
        if (UwUConfig.getInstance().uwuifyIncoming) {
            try {
                packet = new ClientboundSystemChatPacket(uwufiedText(packet.content()), packet.overlay());
            } catch (Exception ignored) { }
        }
        return packet;
    }

    @ModifyVariable(method = "handlePlayerChat", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private ClientboundPlayerChatPacket onGameMessage(ClientboundPlayerChatPacket packet) {
        if (UwUConfig.getInstance().uwuifyIncoming) {
            try {
                Optional<Component> optional = packet.message().unsignedContent();
                if (optional.isPresent()) optional = Optional.of(uwufiedText(optional.get()));
                SignedMessageBody uwuifiedSignedMessageBody = new SignedMessageBody(new ChatMessageContent(Uwuifier.signedUwu(packet.message().signedBody().content().plain()), uwufiedText(packet.message().signedBody().content().decorated())), packet.message().signedBody().timeStamp(), packet.message().signedBody().salt(), packet.message().signedBody().lastSeen());
                packet = new ClientboundPlayerChatPacket(new PlayerChatMessage(packet.message().signedHeader(), packet.message().headerSignature(), uwuifiedSignedMessageBody, optional, packet.message().filterMask()), packet.chatType());
            } catch (Exception ignored) { }
        }
        return packet;
    }

    @Unique
    private Component uwufiedText(Component text) {
        return Component.literal(Uwuifier.signedUwu(text.getString())).setStyle(text.getStyle());
    }
    #endif
}
