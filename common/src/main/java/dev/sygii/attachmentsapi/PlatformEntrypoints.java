package dev.sygii.attachmentsapi;

import dev.architectury.injectables.annotations.ExpectPlatform;

import java.util.List;

public class PlatformEntrypoints {
    @ExpectPlatform
    public static <T> List<T> getEntrypoints(String key, Class<T> type) {
        throw new AssertionError();
    }
}
