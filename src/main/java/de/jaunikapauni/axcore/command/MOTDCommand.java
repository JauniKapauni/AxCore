package de.jaunikapauni.axcore.command;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MOTDCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return false;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axcore.motd")){
            p.sendMessage("You don't have the permission! [axcore.motd]");
            return true;
        }
        String newMOTD = "";
        for(int i = 0; i < args.length; i++){
            newMOTD += args[i];
            if(i < args.length - 1){
                newMOTD = " ";
            }
        }
        Bukkit.getServer().motd(Component.text(newMOTD));
        p.sendMessage(ChatColor.GREEN + "The motd was changed!");
        return true;
    }
}
