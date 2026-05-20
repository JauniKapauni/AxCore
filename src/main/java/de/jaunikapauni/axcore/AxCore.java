package de.jaunikapauni.axcore;

import de.jaunikapauni.axcore.command.FeedCommand;
import de.jaunikapauni.axcore.command.HealCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class AxCore extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("feed").setExecutor(new FeedCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
