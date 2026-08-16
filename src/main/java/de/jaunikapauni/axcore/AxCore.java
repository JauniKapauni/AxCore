package de.jaunikapauni.axcore;

import de.jaunikapauni.axcore.command.*;
import de.jaunikapauni.axcore.listener.DrillListener;
import de.jaunikapauni.axcore.manager.HologramManager;
import de.jaunikapauni.axeconomy.AxEconomy;
import de.jaunikapauni.axeconomy.api.EconomyAPI;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class AxCore extends JavaPlugin {

    EconomyAPI economyAPI;
    public EconomyAPI getEconomyAPI(){
        return economyAPI;
    }

    File hologramFile;
    FileConfiguration hologramConfig;
    HologramManager hologramManager;
    public FileConfiguration getHologramConfig(){
        return hologramConfig;
    }
    public HologramManager getHologramManager(){
        return hologramManager;
    }
    public void saveHologramConfig(){
        try{
            hologramConfig.save(hologramFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("feed").setExecutor(new FeedCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("gm").setExecutor(new GameModeCommand());
        getCommand("gm").setTabCompleter(new GameModeTabCompleter());
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("motd").setExecutor(new MOTDCommand());
        getCommand("day").setExecutor(new DayCommand());
        getCommand("night").setExecutor(new NightCommand());
        getCommand("weather").setExecutor(new WeatherCommand());
        getCommand("weather").setTabCompleter(new WeatherTabCompleter());
        getCommand("flyspeed").setExecutor(new FlySpeedCommand());
        AxEconomy axEconomy = (AxEconomy) Bukkit.getPluginManager().getPlugin("AxEconomy");
        if(axEconomy != null){
            economyAPI = axEconomy.getEconomyAPI();
        }
        getCommand("buydrill").setExecutor(new BuyDrillCommand(this));
        getServer().getPluginManager().registerEvents(new DrillListener(this), this);
        getLogger().info("");
        getLogger().info("----------------------------------------");
        getLogger().info("Name: " + getName());
        getLogger().info("Version: " + getDescription().getVersion());
        getLogger().info(String.join("Authors: " + ", ", getDescription().getAuthors()));
        getLogger().info("----------------------------------------");
        getLogger().info("");
        saveDefaultConfig();
        hologramFile = new File(getDataFolder(), "holograms.yml");
        if(!hologramFile.exists()){
            try{
                hologramFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        hologramConfig = YamlConfiguration.loadConfiguration(hologramFile);
        hologramManager = new HologramManager(this);
        hologramManager.load();
        getCommand("hologram").setExecutor(new HologramCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public boolean isDrill(ItemStack itemStack){
        if(itemStack.getItemMeta() == null){
            return false;
        }
        NamespacedKey namespacedKey = new NamespacedKey(this, "drill");
        return itemStack.getItemMeta().getPersistentDataContainer().has(namespacedKey, PersistentDataType.BOOLEAN);
    }
}
