package de.jaunikapauni.axcore.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return false;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axcore.fly")){
            p.sendMessage("You don't have the permission! [axcore.fly]");
            return true;
        }
        if(p.getAllowFlight()){
            p.setAllowFlight(false);
            p.setFlying(false);
            p.sendMessage(ChatColor.RED + "Fly deactivated!");
        } else {
            p.setAllowFlight(true);
            p.setFlying(true);
            p.sendMessage(ChatColor.GREEN + "Fly activated!");
        }
        return true;
    }
}
