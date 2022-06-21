import lombok.SneakyThrows;
import org.JavaPluginApi.team.JavaPluginApi;
import org.JavaPluginApi.team.PluginLoader;

import java.io.File;

public class Example {

    @SneakyThrows
    public static void main(String[] args) {
        JavaPluginApi api = JavaPluginApi.getApi(pluginLoader -> new PluginLoader(), "JavaPluginApiTeam");
        api.loadAll(new File("res")); //Load all first loading plugins
        Thread.sleep(1000);
        api.unloadAll(new File("res"));
    }

}
