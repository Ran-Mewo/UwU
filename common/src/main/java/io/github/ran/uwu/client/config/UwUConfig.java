package io.github.ran.uwu.client.config;

import io.github.ran.uwu.client.UwUMod;
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
    public boolean uwuifyOutgoing = true;

    @ConfigEntry.Gui.Tooltip
    public boolean uwuifyIncoming = true;

    @ConfigEntry.Gui.Tooltip
    public boolean uwuifyMinecraft = true;
    @ConfigEntry.Gui.Excluded
    private boolean uwuifyMinecraft_Internal = true;

    @ConfigEntry.Gui.Tooltip
    public boolean uwuifySigns = false;

    @ConfigEntry.Gui.Tooltip
    public boolean uwuifyFontRenderer = false;

    @ConfigEntry.Gui.CollapsibleObject
    public Exemptions exemptions = new Exemptions();
    public static class Exemptions {
        @ConfigEntry.Gui.Tooltip
        public boolean uwuifyCertainCommands = true;

        @ConfigEntry.Gui.Tooltip
        public List<String> uwuifyCommands = List.of("(\\/msg [0-9a-zA-Z_]{1,16})", "/me", "/say");
    }

    public static void init() {
        if (UwUMod.isLoaded) return;
        AutoConfig.register(UwUConfig.class, Toml4jConfigSerializer::new);

        getConfigHolder().registerSaveListener((manager, data) -> {
            getInstance().reloadResources();
            return InteractionResult.SUCCESS;
        });

        getConfigHolder().registerLoadListener((manager, data) -> {
            UwUConfig config = getInstance();
            config.uwuifyMinecraft_Internal = config.uwuifyMinecraft;
            return InteractionResult.SUCCESS;
        });
        UwUMod.isLoaded = true;
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
