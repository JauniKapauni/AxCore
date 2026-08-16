package de.jaunikapauni.axcore.manager;

import de.jaunikapauni.axcore.AxCore;
import net.kyori.adventure.text.Component;
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
        TextDisplay hologram = (TextDisplay) location.getWorld().spawnEntity(location, EntityType.TEXT_DISPLAY);
        hologram.text(Component.text(name));
        hologram.setBillboard(Display.Billboard.CENTER);
        holograms.put(name, hologram);
        reference.getHologramConfig().set("holograms." + name + ".world", location.getWorld().getName());
        reference.getHologramConfig().set("holograms." + name + ".uuid", hologram.getUniqueId().toString());
        reference.getHologramConfig().set("holograms." + name + ".x", location.getX());
        reference.getHologramConfig().set("holograms." + name + ".y", location.getY());
        reference.getHologramConfig().set("holograms." + name + ".z", location.getZ());
        reference.saveHologramConfig();
    }

    public void load(){
        if(reference.getHologramConfig().getConfigurationSection("holograms") == null){
            return;
        }
        for(String name : reference.getHologramConfig().getConfigurationSection("holograms").getKeys(false)){
            String world = reference.getHologramConfig().getString("holograms." + name + ".world");
            double x = reference.getHologramConfig().getDouble("holograms." + name + ".x");
            double y = reference.getHologramConfig().getDouble("holograms." + name + ".y");
            double z = reference.getHologramConfig().getDouble("holograms." + name + ".z");
            Location location = new Location(reference.getServer().getWorld(world), x, y, z);
            TextDisplay hologram = (TextDisplay) location.getWorld().spawnEntity(location, EntityType.TEXT_DISPLAY);
            hologram.text(Component.text(name));
            hologram.setBillboard(TextDisplay.Billboard.CENTER);
            holograms.put(name, hologram);
        }
    }

    public void delete(String name){
        TextDisplay hologram = holograms.remove(name);
        if(hologram == null){
            String worldName = reference.getHologramConfig().getString("holograms." + name + ".world");
            String uuidString = reference.getHologramConfig().getString("holograms." + name + ".uuid");
            World world = reference.getServer().getWorld(worldName);
            if(world != null && uuidString != null){
                Entity entity = world.getEntity(UUID.fromString(uuidString));
                if(entity instanceof TextDisplay){
                    hologram = (TextDisplay) entity;
                }
            }
        }
        if(hologram != null){
            hologram.remove();
        }
        reference.getHologramConfig().set("holograms." + name, null);
        reference.saveHologramConfig();
    }
}
