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
        Player p = (Player) sender;
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
