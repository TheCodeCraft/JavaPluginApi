# JavaPluginApi
Java Plugin Api based on eric golde´s GenericJarLoader: https://github.com/egold555/MCP-Snippets/tree/master/JarLoader

in this doc we use IntelliJ
librarys we used:
https://github.com/google/gson/tree/gson-parent-2.4
https://projectlombok.org/downloads/lombok-1.18.20.jar

# Documentation

- first we need a new object of JavaPluginApi:
```Java
JavaPluginApi api = JavaPluginApi.getApi(pluginLoader -> new PluginLoader(), "JavaPluginApiTeam");
```

- to load/unload/reload one plugin we use:
```Java
api.getLoader().load(new File("<Addon File>"));
```

- to load/unload/reload All plugins in a folder we use:
```Java
api.loadAll(new File("<Addon Folder>"));
```

- to load a plugin from a URL we use:
```Java
api.getLoader().loadFromURL("<URL>", new File("<File>"));
```

- to unload/reload a plugin from a URL we have to unload the Jar:
```Java
api.getLoader().unload(new File("<Addon File>"));
```

# Creating a new Plugin

- First we need the jar file from our application for which we used this api.

- Then we create a new project in which we add a library, namely the jar we just created from our application.

- Now we need to create a class that implements the interface "Plugin".

- Now we need 2 methods called "onload" and "onunload".

- into these methods we can now code our plugin.

- then we can export our plugin, in IntelliJ we need an artifact that puts the libraries directly into the jar.
- In IntelliJ this is called "extract to the target Jar".

- We now have to build this artifact.

- we can optional delete the other folders, but not the code we just written!

- Now we have to delete the Mainfest folder in the Addon Jar and add a file called "plugin.json":
```Json
{
  "main": "org.example.Addon",
  "name": "Example Plugin",
  "version": "1.0.0"
}
```
- "main" refers to the package in which the main file is located.
Now we can put our plugin in the folder of the plugins from our application.

that's it.
