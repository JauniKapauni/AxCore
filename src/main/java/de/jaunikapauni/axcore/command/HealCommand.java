package de.jaunikapauni.axcore.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HealCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return false;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axcore.heal")){
            p.sendMessage("You don't have the permission! [axcore.heal]");
            return true;
        }
        double pHealth = p.getHealth();
        if(pHealth==20.0){
            p.sendMessage(ChatColor.RED + "You are already healed!");
        } else {
            p.setHealth(p.getMaxHealth());
            p.sendMessage(ChatColor.GREEN + "You were healed!");
        }
        return true;
    }
}
