package de.jaunikapauni.axcore.manager;

import de.jaunikapauni.axcore.AxCore;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Transformation;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HologramManager {

    AxCore reference;
    Map<String, TextDisplay> holograms = new HashMap<>();
    public HologramManager(AxCore reference){
        this.reference = reference;
    }

    public void create(String text, Location location){
        String uuid = UUID.randomUUID().toString();
        int opacity = 128;
        float scale = 1.0f;

        String coloredText = ChatColor.translateAlternateColorCodes('&', text);

        TextDisplay hologram = (TextDisplay) location.getWorld().spawnEntity(location, EntityType.TEXT_DISPLAY);
        hologram.text(Component.text(coloredText));
        hologram.setBillboard(Display.Billboard.FIXED);
        hologram.setBackgroundColor(Color.fromARGB(opacity, 0, 0, 0));
        Transformation transformation = hologram.getTransformation();
        hologram.setTransformation(new Transformation(transformation.getTranslation(), transformation.getLeftRotation(), new Vector3f(scale, scale, scale), transformation.getRightRotation()));

        Location backLocation = location.clone();
        backLocation.setYaw(location.getYaw() + 180);

        TextDisplay back = (TextDisplay) location.getWorld().spawnEntity(backLocation, EntityType.TEXT_DISPLAY);
        back.text(Component.text(coloredText));
        back.setBillboard(Display.Billboard.FIXED);
        back.setBackgroundColor(Color.fromARGB(opacity, 0, 0, 0));
        transformation = back.getTransformation();
        back.setTransformation(new Transformation(transformation.getTranslation(), transformation.getLeftRotation(), new Vector3f(scale, scale, scale), transformation.getRightRotation()));

        holograms.put(uuid, hologram);
        reference.getHologramConfig().set("holograms." + uuid + ".text", text);
        reference.getHologramConfig().set("holograms." + uuid + ".world", location.getWorld().getName());
        reference.getHologramConfig().set("holograms." + uuid + ".uuid", hologram.getUniqueId().toString());
        reference.getHologramConfig().set("holograms." + uuid + ".x", location.getX());
        reference.getHologramConfig().set("holograms." + uuid + ".y", location.getY());
        reference.getHologramConfig().set("holograms." + uuid + ".z", location.getZ());
        reference.getHologramConfig().set("holograms." + uuid + ".yaw", location.getYaw());
        reference.getHologramConfig().set("holograms." + uuid + ".pitch", location.getPitch());
        reference.getHologramConfig().set("holograms." + uuid + ".background-opacity", opacity);
        reference.getHologramConfig().set("holograms." + uuid + ".scale", scale);
        reference.saveHologramConfig();
    }

    public void load(){
        if(reference.getHologramConfig().getConfigurationSection("holograms") == null){
            return;
        }
        for(String uuid : reference.getHologramConfig().getConfigurationSection("holograms").getKeys(false)){
            String text = reference.getHologramConfig().getString("holograms." + uuid + ".text");
            String worldName = reference.getHologramConfig().getString("holograms." + uuid + ".world");
            String entityUuid = reference.getHologramConfig().getString("holograms." + uuid + ".uuid");
            double x = reference.getHologramConfig().getDouble("holograms." + uuid + ".x");
            double y = reference.getHologramConfig().getDouble("holograms." + uuid + ".y");
            double z = reference.getHologramConfig().getDouble("holograms." + uuid + ".z");
            float yaw = (float) reference.getHologramConfig().getDouble("holograms." + uuid + ".yaw");
            float pitch = (float) reference.getHologramConfig().getDouble("holograms." + uuid + ".pitch");
            int opacity = reference.getHologramConfig().getInt("holograms." + uuid + ".background-opacity", 128);
            float scale = (float) reference.getHologramConfig().getDouble("holograms." + uuid + ".scale", 1.0);
            World world = reference.getServer().getWorld(worldName);
            if(world == null){
                continue;
            }
            Location location = new Location(world, x, y, z, yaw, pitch);
            TextDisplay hologram = null;
            if(entityUuid != null){
                Entity existing = world.getEntity(UUID.fromString(entityUuid));
                if(existing instanceof TextDisplay){
                    hologram = (TextDisplay) existing;
                }
            }
            if(hologram == null){
                hologram = (TextDisplay) world.spawnEntity(location, EntityType.TEXT_DISPLAY);
            }
            hologram.text(Component.text(ChatColor.translateAlternateColorCodes('&', text)));
            hologram.setBillboard(TextDisplay.Billboard.FIXED);
            hologram.setBackgroundColor(Color.fromARGB(opacity, 0 ,0 ,0));
            Transformation transformation = hologram.getTransformation();
            hologram.setTransformation(new Transformation(transformation.getTranslation(), transformation.getLeftRotation(), new Vector3f(scale, scale, scale), transformation.getRightRotation()));
            Location backLocation = location.clone();
            backLocation.setYaw(yaw + 180);
            TextDisplay back = (TextDisplay) world.spawnEntity(backLocation, EntityType.TEXT_DISPLAY);
            back.text(Component.text(ChatColor.translateAlternateColorCodes('&', text)));
            back.setBillboard(Display.Billboard.FIXED);
            back.setBackgroundColor(Color.fromARGB(opacity, 0, 0, 0));
            transformation = back.getTransformation();
            back.setTransformation(new Transformation(transformation.getTranslation(), transformation.getLeftRotation(), new Vector3f(scale, scale, scale), transformation.getRightRotation()));

            holograms.put(uuid, hologram);
        }
    }

    public void delete(String uuid){
        String worldName = reference.getHologramConfig().getString("holograms." + uuid + ".world");
        double x = reference.getHologramConfig().getDouble("holograms." + uuid + ".x");
        double y = reference.getHologramConfig().getDouble("holograms." + uuid + ".y");
        double z = reference.getHologramConfig().getDouble("holograms." + uuid + ".z");
        World world = reference.getServer().getWorld(worldName);
        if(world != null){
            Location location = new Location(world, x, y, z);
            for(TextDisplay hologram : location.getNearbyEntitiesByType(TextDisplay.class, 1)){
                hologram.remove();
            }
        }
        holograms.remove(uuid);
        reference.getHologramConfig().set("holograms." + uuid, null);
        reference.saveHologramConfig();
    }

    public void reload(){
        if(reference.getHologramConfig().getConfigurationSection("holograms") != null){
            for(String uuid : reference.getHologramConfig().getConfigurationSection("holograms").getKeys(false)){
                String worldName = reference.getHologramConfig().getString("holograms." + uuid + ".world");
                double x = reference.getHologramConfig().getDouble("holograms." + uuid + ".x");
                double y = reference.getHologramConfig().getDouble("holograms." + uuid + ".y");
                double z = reference.getHologramConfig().getDouble("holograms." + uuid + ".z");
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
