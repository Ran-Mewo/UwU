package io.github.ran.uwu.client.mixins.minecraft.language;

import com.google.common.collect.ImmutableMap;
import io.github.ran.uwu.client.UwUMod;
import io.github.ran.uwu.client.Uwuifier;
import io.github.ran.uwu.client.config.UwUConfig;
import net.minecraft.client.resources.language.ClientLanguage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.HashMap;
import java.util.Map;

@Mixin(ClientLanguage.class)
public class ClientLanguageMixin {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private static Map<String, String> translations(Map<String, String> translations) {
        UwUConfig.init();
        if (UwUConfig.uwuifyMinecraft) {
            uwuifiedTranslations = new HashMap<>();
            translations.forEach((key, value) -> uwuifiedTranslations.put(key, Uwuifier.uwu(value)));
            if (!uwuifiedTranslations.isEmpty()) {
                uwuifiedTranslations = ImmutableMap.copyOf(uwuifiedTranslations);
                if (!uwuifiedTranslations.isEmpty()) translations = uwuifiedTranslations;
            }
        }
        return translations;
    }

    @Unique
    private static Map<String, String> uwuifiedTranslations = new HashMap<>();
}
