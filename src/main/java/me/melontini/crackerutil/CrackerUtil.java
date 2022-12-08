package me.melontini.crackerutil;

import net.fabricmc.api.ModInitializer;

public class CrackerUtil implements ModInitializer {
    public static final String MODID = "cracker-util";

    @Override
    public void onInitialize() {
        CrackerLog.error("error log test {}");
    }
}
