package org.JavaPluginApi.team;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

public class JavaPluginApi {

    private static PluginLoader loader;
    private String outName;

    public static JavaPluginApi getApi(Consumer<PluginLoader> consumer, String outName) {
        consumer.accept(loader = new PluginLoader());
        JavaPluginApi api = new JavaPluginApi(loader);
        api.outName = outName;
        return api;
    }

    public PluginLoader getLoader() {
        return loader;
    }

    private JavaPluginApi(PluginLoader loader) {
        JavaPluginApi.loader = loader;
    }

    /**
     * @param pluginFolder folder in plugins located
     * @return files of pluginFolder
     */
    public void loadAll(@NotNull File pluginFolder) { // You can delete @NotNull
        for (File fileIndex : Objects.requireNonNull(pluginFolder.listFiles())) {
            if (fileIndex.getName().endsWith(".jar")) { //Requires a Jar File
                loader.load(fileIndex);
                System.out.println(outName + " " + "Loaded: " + fileIndex.getName());
            }
        }
    }

    /**
     * @param pluginFolder folder in plugins located
     */
    public void loadAllThread(@NotNull File pluginFolder) { // You can delete @NotNull
        Thread loadThread = new Thread("JPAPI plugin loading Thread") {
            @Override
            public void run() {
                for (File fileIndex : Objects.requireNonNull(pluginFolder.listFiles())) {
                    if (fileIndex.getName().endsWith(".jar")) { //Requires a Jar File
                        loader.load(fileIndex);
                        System.out.println(outName + " " + "Loaded: " + fileIndex.getName());
                    }
                }
            }
        };
        loadThread.start();
    }
    /**
     * @param pluginFolder folder in plugins located
     * @return files of pluginFolder
     */
    public void unloadAll(@NotNull File pluginFolder) { // You can delete @NotNull
        for (File fileIndex : Objects.requireNonNull(pluginFolder.listFiles())) {
            loader.unload(fileIndex);
            System.out.println(outName + " " + "Unloaded: " + fileIndex.getName());
        }
    }

    /**
     * @param pluginFolder folder in plugins located
     */
    public void reloadAll(@NotNull File pluginFolder) { // You can delete @NotNull
        Thread loadThread = new Thread("JPAPI plugin reloading Thread") {
            @Override
            public void run() {
                for (File fileIndex : Objects.requireNonNull(pluginFolder.listFiles())) {
                    if (fileIndex.getName().endsWith(".jar")) { //Requires a Jar File
                        loader.reload(fileIndex);
                        System.out.println(outName + " " + "Reloaded: " + fileIndex.getName());
                    }
                }
            }
        };
        loadThread.start();
    }

}
