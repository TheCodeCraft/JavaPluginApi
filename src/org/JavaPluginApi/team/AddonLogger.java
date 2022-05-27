package org.JavaPluginApi.team;

public class AddonLogger {

    private volatile String name = "";

    public AddonLogger(final String name) {this.name = name;}

    public String logInfo(String i) {
        String returned = "[" + name + "] [Info] " + i;
        System.out.println(returned);
        return returned;
    }

    public String logError(String i) {
        String returned = "[" + name + "] [Error] " + i;
        System.err.println(returned);
        return returned;
    }
}
