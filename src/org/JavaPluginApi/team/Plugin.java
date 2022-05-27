package org.JavaPluginApi.team;

public interface Plugin {

	ThreadLocal<PluginDescription> descriptionFile = new ThreadLocal<PluginDescription>();
	
	abstract void onEnable();
	abstract void onDisable();

	default void setDescriptionFile(PluginDescription descriptionFile) {
		if(this.descriptionFile.get() != null) {
			throw new PluginException("Can't set the description file. Its already set!");
		}
		this.descriptionFile.set(descriptionFile);
	}
	
	default PluginDescription getDescriptionFile() {
		return descriptionFile.get();
	}
	
}
