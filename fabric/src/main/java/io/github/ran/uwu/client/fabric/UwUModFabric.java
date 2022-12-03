package io.github.ran.uwu.client.fabric;

import io.github.ran.uwu.client.UwUMod;
import net.fabricmc.api.ModInitializer;

public class UwUModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        UwUMod.init();
    }
}
