package io.github.ran.uwu.client.mixins.chat;

import io.github.ran.uwu.client.Uwuifier;
import io.github.ran.uwu.client.config.UwUConfig;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.*;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
#if PRE_MC_1_19_3
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundChatPacket;
import net.minecraft.network.chat.ChatType;
#else
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;

import java.util.regex.Pattern;
#endif

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    #if PRE_MC_1_19_3
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

    // Receiving chat messages

    @ModifyVariable(method = "handleSystemChat", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private ClientboundSystemChatPacket onGameMessage(ClientboundSystemChatPacket packet) {
        if (UwUConfig.getInstance().uwuifyIncoming) {
            try {
                packet = new ClientboundSystemChatPacket(uwufiedText(packet.content()), packet.overlay());
            } catch (Exception ignored) { }
        }
        return packet;
    }

    // Handling player chat messages in 1.19.3 is done in the ChatListenerMixin class

    // Sending Chat Messages

    @ModifyVariable(method = "sendChat", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private String onSendChatMessage(String message) {
        return UwUConfig.getInstance().uwuifyOutgoing ? Uwuifier.uwu(message) : message;
    }

    @ModifyVariable(method = "sendCommand", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private String onSendCommandMessage(String message) {
        UwUConfig config = UwUConfig.getInstance();
        if (config.exemptions.uwuifyCertainCommands) {
            if (!config.exemptions.uwuifyCommands.isEmpty()) {
                for (String regexCommands : config.exemptions.uwuifyCommands) {
                    try {
                        if (Pattern.compile(regexCommands).matcher(message).find()) {
                            String toBeUwuified;
                            return message.replaceAll(Pattern.quote(toBeUwuified = message.replaceAll(regexCommands, "")), Uwuifier.uwu(toBeUwuified));
                        }
                    } catch (Exception e) {
                        LogManager.getLogger("Uwuifer").error("Error while trying to compile regex command: " + regexCommands);
                    }
                }
            }
        }
        return message;
    }

    @ModifyVariable(method = "sendUnsignedCommand", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private String onSendUnsignedCommandMessage(String message) {
        UwUConfig config = UwUConfig.getInstance();
        if (config.exemptions.uwuifyCertainCommands) {
            if (!config.exemptions.uwuifyCommands.isEmpty()) {
                for (String regexCommands : config.exemptions.uwuifyCommands) {
                    try {
                        if (Pattern.compile(regexCommands).matcher(message).find()) {
                            String toBeUwuified;
                            return message.replaceAll(Pattern.quote(toBeUwuified = message.replaceAll(regexCommands, "")), Uwuifier.uwu(toBeUwuified));
                        }
                    } catch (Exception e) {
                        LogManager.getLogger("Uwuifer").error("Error while trying to compile regex command: " + regexCommands);
                    }
                }
            }
        }
        return message;
    }

    @Unique
    private Component uwufiedText(Component text) {
        return Component.literal(Uwuifier.signedUwu(text.getString())).setStyle(text.getStyle());
    }
    #endif
}
