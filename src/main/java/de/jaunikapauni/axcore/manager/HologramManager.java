package de.jaunikapauni.axcore.manager;

import de.jaunikapauni.axcore.AxCore;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HologramManager {

    AxCore reference;
    Map<String, TextDisplay> holograms = new HashMap<>();
    public HologramManager(AxCore reference){
        this.reference = reference;
    }

    public void create(String name, Location location){
        int opacity = 128;

        TextDisplay hologram = (TextDisplay) location.getWorld().spawnEntity(location, EntityType.TEXT_DISPLAY);
        hologram.text(Component.text(name));
        hologram.setBillboard(Display.Billboard.FIXED);
        hologram.setBackgroundColor(Color.fromARGB(opacity, 0, 0, 0));

        Location backLocation = location.clone();
        backLocation.setYaw(location.getYaw() + 180);

        TextDisplay back = (TextDisplay) location.getWorld().spawnEntity(backLocation, EntityType.TEXT_DISPLAY);
        back.text(Component.text(name));
        back.setBillboard(Display.Billboard.FIXED);
        back.setBackgroundColor(Color.fromARGB(opacity, 0, 0, 0));

        holograms.put(name, hologram);
        reference.getHologramConfig().set("holograms." + name + ".world", location.getWorld().getName());
        reference.getHologramConfig().set("holograms." + name + ".x", location.getX());
        reference.getHologramConfig().set("holograms." + name + ".y", location.getY());
        reference.getHologramConfig().set("holograms." + name + ".z", location.getZ());
        reference.getHologramConfig().set("holograms." + name + ".yaw", location.getYaw());
        reference.getHologramConfig().set("holograms." + name + ".pitch", location.getPitch());
        reference.getHologramConfig().set("holograms." + name + ".background-opacity", opacity);
        reference.saveHologramConfig();
    }

    public void load(){
        if(reference.getHologramConfig().getConfigurationSection("holograms") == null){
            return;
        }
        for(String name : reference.getHologramConfig().getConfigurationSection("holograms").getKeys(false)){
            String worldName = reference.getHologramConfig().getString("holograms." + name + ".world");
            String uuidString = reference.getHologramConfig().getString("holograms." + name + ".uuid");
            double x = reference.getHologramConfig().getDouble("holograms." + name + ".x");
            double y = reference.getHologramConfig().getDouble("holograms." + name + ".y");
            double z = reference.getHologramConfig().getDouble("holograms." + name + ".z");
            float yaw = (float) reference.getHologramConfig().getDouble("holograms." + name + ".yaw");
            float pitch = (float) reference.getHologramConfig().getDouble("holograms." + name + ".pitch");
            int opacity = reference.getHologramConfig().getInt("holograms." + name + ".background-opacity", 128);
            World world = reference.getServer().getWorld(worldName);
            if(world == null){
                continue;
            }
            Location location = new Location(world, x, y, z, yaw, pitch);
            world.getChunkAt(location);
            TextDisplay hologram = null;
            if(uuidString != null){
                Entity existing = world.getEntity(UUID.fromString(uuidString));
                if(existing instanceof TextDisplay){
                    TextDisplay textDisplay = (TextDisplay) existing;
                    hologram = textDisplay;
                }
            }
            if(hologram == null){
                hologram = (TextDisplay) world.spawnEntity(location, EntityType.TEXT_DISPLAY);
                hologram.text(Component.text(name));
                hologram.setBillboard(TextDisplay.Billboard.FIXED);
                hologram.setBackgroundColor(Color.fromARGB(opacity, 0 ,0 ,0));
                Location backLocation = location.clone();
                backLocation.setYaw(yaw + 180);
                TextDisplay back = (TextDisplay) world.spawnEntity(backLocation, EntityType.TEXT_DISPLAY);
                back.text(Component.text(name));
                back.setBillboard(Display.Billboard.FIXED);
                back.setBackgroundColor(Color.fromARGB(opacity, 0, 0, 0));
            }
            holograms.put(name, hologram);
        }
    }

    public void delete(String name){
        String worldName = reference.getHologramConfig().getString("holograms." + name + ".world");
        String uuidString = reference.getHologramConfig().getString("holograms." + name + ".uuid");
        double x = reference.getHologramConfig().getDouble("holograms." + name + ".x");
        double y = reference.getHologramConfig().getDouble("holograms." + name + ".y");
        double z = reference.getHologramConfig().getDouble("holograms." + name + ".z");
        World world = reference.getServer().getWorld(worldName);
        if(world != null){
            Location location = new Location(world, x, y, z);
            for(TextDisplay hologram : location.getNearbyEntitiesByType(TextDisplay.class, 1)){
                hologram.remove();
            }
        }
        holograms.remove(name);
        reference.getHologramConfig().set("holograms." + name, null);
        reference.saveHologramConfig();
    }

    public void reload(){
        if(reference.getHologramConfig().getConfigurationSection("holograms") != null){
            for(String name : reference.getHologramConfig().getConfigurationSection("holograms").getKeys(false)){
                String worldName = reference.getHologramConfig().getString("holograms." + name + ".world");
                double x = reference.getHologramConfig().getDouble("holograms." + name + ".x");
                double y = reference.getHologramConfig().getDouble("holograms." + name + ".y");
                double z = reference.getHologramConfig().getDouble("holograms." + name + ".z");
                World world = reference.getServer().getWorld(worldName);
                if(world != null){
                    Location location = new Location(world, x, y, z);
                    for(TextDisplay hologram : location.getNearbyEntitiesByType(TextDisplay.class, 1)){
                        hologram.remove();
                    }
                }
            }
        }
        holograms.clear();
        reference.reloadHologramConfig();
        load();
    }
}
