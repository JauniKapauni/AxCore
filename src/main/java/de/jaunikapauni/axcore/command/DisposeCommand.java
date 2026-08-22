package de.jaunikapauni.axcore.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class DisposeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axcore.dispose")){
            p.sendMessage(ChatColor.RED + "You don't have the permission! [axcore.dispose]");
            return true;
        }
        Inventory inventory = Bukkit.createInventory(null, 27, "Trash");
        p.openInventory(inventory);
        return true;
    }
}
