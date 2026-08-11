package de.jaunikapauni.axcore.command;

import de.jaunikapauni.axcore.AxCore;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class BuyDrillCommand implements CommandExecutor {

    AxCore reference;
    public BuyDrillCommand(AxCore reference){
        this.reference = reference;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can use this command!");
            return true;
        }
        Player p = (Player) sender;
        ItemStack drill = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = drill.getItemMeta();
        meta.displayName(Component.text(ChatColor.AQUA + "DRILL"));
        NamespacedKey namespacedKey = new NamespacedKey(reference, "drill");
        NamespacedKey durabilityKey = new NamespacedKey(reference, "drill_uses");
        meta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.BOOLEAN, true);
        meta.getPersistentDataContainer().set(durabilityKey, PersistentDataType.INTEGER, 500);
        drill.setItemMeta(meta);
        if(reference.getEconomyAPI().has(p.getUniqueId(), 1000)){
            reference.getEconomyAPI().withdraw(p.getUniqueId(), 1000);
        } else {
            p.sendMessage(ChatColor.RED + "You don't have enough money!");
            return true;
        }
        p.getInventory().addItem(drill);
        p.sendMessage(ChatColor.GREEN + "You successfully bought the drill!");
        return true;
    }
}
