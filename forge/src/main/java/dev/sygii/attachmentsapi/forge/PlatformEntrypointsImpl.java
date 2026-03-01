package dev.sygii.attachmentsapi.forge;

import dev.sygii.attachmentsapi.forge.entrypoint.EntrypointContainer;
import dev.sygii.attachmentsapi.forge.entrypoint.EntrypointHandler;

import java.util.List;

@SuppressWarnings("unused")
public class PlatformEntrypointsImpl {
    public static <T> List<T> getEntrypoints(String key, Class<T> type) {
        return EntrypointHandler.getEntrypointContainers(key, type)
                .stream().map(EntrypointContainer::entrypoint).toList();
    }
}
