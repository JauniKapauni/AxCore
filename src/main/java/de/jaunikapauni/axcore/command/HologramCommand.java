package de.jaunikapauni.axcore.command;

import de.jaunikapauni.axcore.AxCore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HologramCommand implements CommandExecutor {

    AxCore reference;
    public HologramCommand(AxCore reference){
        this.reference = reference;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can use this command!");
            return true;
        }
        Player p = (Player) sender;
        if(args.length == 0){
            return false;
        }
        if(args[0].equalsIgnoreCase("delete")){
            if(args.length < 2){
                return false;
            }
            reference.getHologramManager().delete(args[1]);
            p.sendMessage(ChatColor.GREEN + "Hologram deleted!");
            return true;
        }
        if(args[0].equalsIgnoreCase("reload")){
            reference.getHologramManager().reload();
            p.sendMessage(ChatColor.GREEN + "Holograms reloaded!");
            return true;
        }
        reference.getHologramManager().create(args[0], p.getLocation());
        p.sendMessage(ChatColor.GREEN + "Hologram created!");
        return true;
    }
}
