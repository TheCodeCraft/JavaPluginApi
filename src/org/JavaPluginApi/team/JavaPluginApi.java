package org.JavaPluginApi.team;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

public class JavaPluginApi {

    private static JavaPluginApi api;
    private static PluginLoader loader;
    private static String outName;

    public static JavaPluginApi getApi(Consumer<PluginLoader> consumer, String outName) {
        consumer.accept(loader = new PluginLoader());
        JavaPluginApi.outName = outName;
        return api == null ? new JavaPluginApi(loader) : api;
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
    public File[] loadAll(@NotNull File pluginFolder) { // You can delete @NotNull
        for (File fileIndex : Objects.requireNonNull(pluginFolder.listFiles())) {
            if (fileIndex.getName().endsWith(".jar")) { //Requires a Jar File
                loader.load(fileIndex);
                System.out.println(outName + " " + "Loaded: " + fileIndex.getName());
            }
        }
        System.out.println(outName + " " + "---Credits---");
        Arrays.stream(Credits.values()).map(credits -> outName + " " + credits.name() + " | " + credits.DO).forEach(System.out::println);
        System.out.println(outName + " " + "---Credits---");
        return pluginFolder.listFiles();
    }
    /**
     * @param pluginFolder folder in plugins located
     * @return files of pluginFolder
     */
    public File[] unloadAll(@NotNull File pluginFolder) { // You can delete @NotNull
        for (File fileIndex : Objects.requireNonNull(pluginFolder.listFiles())) {
            loader.unload(fileIndex);
            System.out.println(outName + " " + "Unloaded: " + fileIndex.getName());
        }
        System.out.println(outName + " " + "---Credits---");
        Arrays.stream(Credits.values()).map(credits -> outName + " " + credits.name() + " | " + credits.DO).forEach(System.out::println);
        System.out.println(outName + " " + "---Credits---");
        return pluginFolder.listFiles();
    }

    /**
     * @param pluginFolder folder in plugins located
     * @return files of pluginFolder
     */
    public File[] reloadAll(@NotNull File pluginFolder) { // You can delete @NotNull
        for (File fileIndex : Objects.requireNonNull(pluginFolder.listFiles())) {
            loader.reload(fileIndex);
            System.out.println(outName + " " + "Reloaded: " + fileIndex.getName());
        }
        return pluginFolder.listFiles();
    }

}
