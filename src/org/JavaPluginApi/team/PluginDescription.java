package org.JavaPluginApi.team;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
/**
 * An object to hold what is in the plugin.json file
 * Could also just use a hashmap
 * @author Eric Golde
 *
 */
public class PluginDescription {

	private final String main;
	private final String name;
	private final String version;
	
}
