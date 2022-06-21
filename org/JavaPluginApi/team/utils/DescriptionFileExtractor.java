package org.JavaPluginApi.team.utils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.JavaPluginApi.team.PluginDescription;
import org.JavaPluginApi.team.utils.PluginException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class DescriptionFileExtractor {

    private final File file;
    private PluginDescription pluginDescription = null;

    public DescriptionFileExtractor(File file) {
        this.file = file;
        try {
            ZipFile zipFile = new ZipFile(file);

            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            PluginDescription pluginJson = null;

            while(entries.hasMoreElements() && pluginJson == null){
                ZipEntry entry = entries.nextElement();

                if(!entry.isDirectory() && entry.getName().equals("plugin.json")) {
                    InputStream stream = zipFile.getInputStream(entry);
                    try {
                        pluginJson = new Gson().fromJson(new InputStreamReader(stream), PluginDescription.class);
                    }
                    catch(JsonParseException jsonParseException) {
                        throw new PluginException("Failed to parse JSON:", jsonParseException);
                    }
                }
            }

            if(pluginJson == null) {
                zipFile.close();
                throw new PluginException("Failed to find plugin.json in the root of the jar.");
            }

            zipFile.close();
            this.pluginDescription = pluginJson;
        }
        catch(IOException e) {
            throw new PluginException("Failed to open the jar as a zip:", e);
        }
    }

    public PluginDescription getObject() {
        return pluginDescription;
    }
}
