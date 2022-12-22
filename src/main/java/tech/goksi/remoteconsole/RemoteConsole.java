package tech.goksi.remoteconsole;

import org.bukkit.plugin.java.JavaPlugin;

public final class RemoteConsole extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {

    }

    public static RemoteConsole getInstance() {
        return getPlugin(RemoteConsole.class);
    }
}
