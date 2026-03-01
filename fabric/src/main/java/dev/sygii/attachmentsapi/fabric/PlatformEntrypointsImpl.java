package dev.sygii.attachmentsapi.fabric;

import net.fabricmc.loader.api.FabricLoader;

@SuppressWarnings("unused")
public class PlatformEntrypointsImpl {
    public static <T> java.util.List<T> getEntrypoints(String key, Class<T> type) {
        return FabricLoader.getInstance().getEntrypoints(key, type);
    }
}
