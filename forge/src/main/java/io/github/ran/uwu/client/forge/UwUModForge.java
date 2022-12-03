package io.github.ran.uwu.client.forge;

import io.github.ran.uwu.client.UwUMod;
import io.github.ran.uwu.client.config.UwUConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
#if PRE_MC_1_18_2
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.client.ConfigGuiHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
#elif PRE_MC_1_19_2
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.client.ClientRegistry;
#else
import net.minecraftforge.client.ConfigScreenHandler;
#endif

import java.util.function.BiFunction;

@Mod("uwu")
public class UwUModForge {
    public UwUModForge() {
        UwUMod.init();
        #if PRE_MC_1_18_2
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> (mc, screen) -> AutoConfig.getConfigScreen(UwUConfig.class, screen).get());
        #elif PRE_MC_1_19_2
        ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class, () -> new ConfigGuiHandler.ConfigGuiFactory((BiFunction<Minecraft, Screen, Screen>) (minecraft, screen) -> AutoConfig.getConfigScreen(UwUConfig.class, screen).get()));
        #else
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((BiFunction<Minecraft, Screen, Screen>) (minecraft, screen) -> AutoConfig.getConfigScreen(UwUConfig.class, screen).get()));
        #endif
    }
}
