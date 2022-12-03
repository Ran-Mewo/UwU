package io.github.ran.uwu.client.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionResult;

import java.util.List;

@Config(name = "UwU")
@Config.Gui.Background("minecraft:textures/block/pink_concrete.png")
public class UwUConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public static boolean uwuifyOutgoing = true;

    @ConfigEntry.Gui.Tooltip
    public static boolean uwuifyIncoming = true;

    @ConfigEntry.Gui.Tooltip
    public static boolean uwuifyMinecraft = true;
    @ConfigEntry.Gui.Excluded
    private static boolean uwuifyMinecraft_Internal = true;

    @ConfigEntry.Gui.Tooltip
    public static boolean uwuifySigns = false;

    @ConfigEntry.Gui.Tooltip
    public static boolean uwuifyFontRenderer = false;

    @ConfigEntry.Gui.CollapsibleObject
    public static Exemptions exemptions = new Exemptions();
    public static class Exemptions {
        @ConfigEntry.Gui.Tooltip
        public static boolean uwuifyCertainCommands = true;

        @ConfigEntry.Gui.Tooltip
        public static List<String> uwuifyCommands = List.of("(\\/msg [0-9a-zA-Z_]{1,16})", "/me", "/say");
    }

    public static void init() {
        AutoConfig.register(UwUConfig.class, Toml4jConfigSerializer::new);

        getConfigHolder().registerSaveListener((manager, data) -> {
            getInstance().reloadResources();
            return InteractionResult.SUCCESS;
        });

        getConfigHolder().registerLoadListener((manager, data) -> {
            uwuifyMinecraft_Internal = uwuifyMinecraft;
            return InteractionResult.SUCCESS;
        });
    }

    public static UwUConfig getInstance() {
        return AutoConfig.getConfigHolder(UwUConfig.class).getConfig();
    }

    public static ConfigHolder<?> getConfigHolder() {
        return AutoConfig.getConfigHolder(UwUConfig.class);
    }

    public void reloadResources() {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (uwuifyMinecraft_Internal != uwuifyMinecraft) {
                uwuifyMinecraft_Internal = uwuifyMinecraft;
                getConfigHolder().save();
                Minecraft.getInstance().reloadResourcePacks();
            }
        }).start();
    }
}
