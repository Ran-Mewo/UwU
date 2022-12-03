package io.github.ran.uwu.client;

import io.github.ran.uwu.client.config.UwUConfig;

public class UwUMod {
    public static boolean isLoaded = false;
    public static String prevUwuifiedMessage = "\r";
    public static String prevMessage = "\r";
    
    public static void init() {
        UwUConfig.init();
    }
}
