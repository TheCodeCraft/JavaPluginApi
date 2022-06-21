package org.JavaPluginApi.team;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.JavaPluginApi.team.utils.AddonLogger;
import org.JavaPluginApi.team.utils.DescriptionFileExtractor;
import org.JavaPluginApi.team.utils.PluginException;

public class PluginLoader {

	private final AddonLogger logger = new AddonLogger("JPApi");

	//JSON instance for the plugin.json file
	private static final Gson GSON = new GsonBuilder().serializeNulls().create();

	//Map from FIle to Plugin instance.
	//Really not the best way to do it, but it works for this example
	private final HashMap<File, Plugin> map = new HashMap<File, Plugin>();

	/**
	 * Try to load a plugin
	 * @param file path to jar file
	 */
	public Plugin load(File file) {
		if (!(file.getName().endsWith(".jar"))) throw new PluginException("File have to be a Jar! " + file.getName());
		try {

			//Make sure the plugin isn't loaded
			//if it is, throw an exception
			if(map.containsKey(file)) {
				throw new PluginException(file.getName() + " " + "Plugin already loaded.");
			}

			//Gets the plugin.json file out of the jar file
			PluginDescription pluginDescriptionFile = new DescriptionFileExtractor(file).getObject();

			//get the class loader
			ClassLoader loader = URLClassLoader.newInstance( new URL[] { file.toURI().toURL() }, getClass().getClassLoader() );

			//load the main class specified in the plugin.json file
			Class<?> clazz = Class.forName(pluginDescriptionFile.getMain(), true, loader);

			//Get an instance of the main class that should be run
			Class<? extends Plugin> instanceClass = clazz.asSubclass(Plugin.class);

			//Get the constructor of the instance class
			Constructor<? extends Plugin> instanceClassConstructor = instanceClass.getConstructor();

			//Create a new instance of it
			Plugin plugin = instanceClassConstructor.newInstance();

			//set the description file, so we can read it from the plugin instance.
			plugin.setDescriptionFile(pluginDescriptionFile);

			//add the plugin to the map of enabled plugins
			map.put(file, plugin);

			//call onEnable
			plugin.load();

			//return the instance
			return plugin;
		}
		catch(MalformedURLException e) {
			throw new PluginException("Failed to convert the file path to a URL.", e);
		}
		catch(ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new PluginException("Failed to create a new instance of the plugin.", e);
		}
	}

	/**
	 * Try to call the update method in a plugin
	 * @param file path to jar file
	 */
	public Plugin update(File file) {
		if (!(file.getName().endsWith(".jar"))) throw new PluginException("File have to be a Jar! " + file.getName());
		try {

			if(!map.containsKey(file)) {
				throw new PluginException(file.getName() + " " + "Plugin isn´t loaded!");
			}

			//Gets the plugin.json file out of the jar file
			PluginDescription pluginDescriptionFile = new DescriptionFileExtractor(file).getObject();

			//get the class loader
			ClassLoader loader = URLClassLoader.newInstance( new URL[] { file.toURI().toURL() }, getClass().getClassLoader() );

			//load the main class specified in the plugin.json file
			Class<?> clazz = Class.forName(pluginDescriptionFile.getMain(), true, loader);

			//Get an instance of the main class that should be run
			Class<? extends Plugin> instanceClass = clazz.asSubclass(Plugin.class);

			//Get the constructor of the instance class
			Constructor<? extends Plugin> instanceClassConstructor = instanceClass.getConstructor();

			//Create a new instance of it
			Plugin plugin = instanceClassConstructor.newInstance();

			//set the description file, so we can read it from the plugin instance.
			plugin.setDescriptionFile(pluginDescriptionFile);

			//call onEnable
			plugin.update();

			//return the instance
			return plugin;
		}
		catch(MalformedURLException e) {
			throw new PluginException("Failed to convert the file path to a URL.", e);
		}
		catch(ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new PluginException("Failed to create a new instance of the plugin.", e);
		}
	}


	/**
	 * Try to load a plugin
	 * @param url path to jar file
	 */
	public Plugin loadFromURL(final String url, final File filePath) {
		InputStream in;
		try {
			in = new URL(url).openStream();
			Files.copy(in, Paths.get(filePath.getPath()), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		File file = Paths.get(filePath.getPath()).toFile();
		if (!(file.getName().endsWith(".jar"))) throw new PluginException("File have to be a Jar! " + file.getName());
		try {

			//Make sure the plugin isn't loaded
			//if it is, throw an exception
			if(map.containsKey(file)) {
				throw new PluginException(file.getName() + " " + "Plugin already loaded.");
			}

			//Gets the plugin.json file out of the jar file
			PluginDescription pluginDescriptionFile = new DescriptionFileExtractor(file).getObject();

			//get the class loader
			ClassLoader loader = URLClassLoader.newInstance( new URL[] { file.toURI().toURL() }, getClass().getClassLoader() );

			//load the main class specified in the plugin.json file
			Class<?> clazz = Class.forName(pluginDescriptionFile.getMain(), true, loader);

			//Get an instance of the main class that should be run
			Class<? extends Plugin> instanceClass = clazz.asSubclass(Plugin.class);

			//Get the constructor of the instance class
			Constructor<? extends Plugin> instanceClassConstructor = instanceClass.getConstructor();

			//Create a new instance of it
			Plugin plugin = instanceClassConstructor.newInstance();

			//set the description file, so we can read it from the plugin instance.
			plugin.setDescriptionFile(pluginDescriptionFile);

			//add the plugin to the map of enabled plugins
			map.put(file, plugin);

			//call onEnable
			plugin.load();

			//return the instance
			return plugin;
		}
		catch(MalformedURLException e) {
			throw new PluginException("Failed to convert the file path to a URL.", e);
		}
		catch(ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new PluginException("Failed to create a new instance of the plugin.", e);
		}
	}

	/**
	 * Unload a plugin
	 */
	public Plugin unload(File file) {
		if (!(file.getName().endsWith(".jar"))) throw new PluginException("File have to be a Jar! " + file.getName());

		//Make sure we can't unload the plugin twice
		if(!map.containsKey(file)) {
			throw new PluginException("Can't unload a Plugin that wasn't loaded in the first place.");
		}

		//get the instance
		Plugin plugin = map.get(file);

		//call on disable
		plugin.unload();

		//remove the file from the map, so it can be enabled again
		map.remove(file);

		//return the plugin
		return plugin;
	}
	
	/**
	 * Reload a plugin
	 */
	public void reload(File file) {
		unload(file);
		load(file);
	}
}
