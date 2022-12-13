package io.github.ran.uwu.client.mixins.chat;

import io.github.ran.uwu.client.Uwuifier;
import io.github.ran.uwu.client.config.UwUConfig;
import net.minecraft.client.player.LocalPlayer;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.regex.Pattern;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    #if PRE_MC_1_19_3
    @ModifyVariable(method = "chat", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private String onSendChatMessage(String message) {
        UwUConfig config = UwUConfig.getInstance();
        if (config.exemptions.uwuifyCertainCommands && message.startsWith("/")) {
            String command = message.substring(1);
            String prefix = message.substring(0, 1);
            if (!config.exemptions.uwuifyCommands.isEmpty()) {
                for (String regexCommands : config.exemptions.uwuifyCommands) {
                    try {
                        if (Pattern.compile(regexCommands).matcher(command).find()) {
                            String toBeUwuified;
                            return prefix + command.replaceAll(Pattern.quote(toBeUwuified = command.replaceAll(regexCommands, "")), Uwuifier.uwu(toBeUwuified));
                        }
                    } catch (Exception e) {
                        LogManager.getLogger("Uwuifer").error("Error while trying to compile regex command: " + regexCommands);
                    }
                }
            }
        }
        if (message.startsWith("/")) return message; // ignore commands
        return config.uwuifyOutgoing ? Uwuifier.uwu(message) : message;
    }
    #endif
}
