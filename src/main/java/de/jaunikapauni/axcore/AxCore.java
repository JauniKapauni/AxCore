package de.jaunikapauni.axcore;

import de.jaunikapauni.axcore.command.FeedCommand;
import de.jaunikapauni.axcore.command.FlyCommand;
import de.jaunikapauni.axcore.command.GameModeCommand;
import de.jaunikapauni.axcore.command.HealCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class AxCore extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("feed").setExecutor(new FeedCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("gm").setExecutor(new GameModeCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
