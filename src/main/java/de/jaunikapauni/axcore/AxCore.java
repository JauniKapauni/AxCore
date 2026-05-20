package de.jaunikapauni.axcore;

import de.jaunikapauni.axcore.command.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class AxCore extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("feed").setExecutor(new FeedCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("gm").setExecutor(new GameModeCommand());
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("motd").setExecutor(new MOTDCommand());
        getCommand("day").setExecutor(new DayCommand());
        getCommand("night").setExecutor(new NightCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
