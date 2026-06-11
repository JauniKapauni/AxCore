package de.jaunikapauni.axcore.command;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GameModeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return false;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axcore.gamemode")){
            p.sendMessage("You don't have the permission! [axcore.gamemode]");
            return true;
        }
        if(args.length == 0){
            p.sendMessage(ChatColor.RED + "Please enter a gamemode!");
            return false;
        }
        String mode = args[0].toLowerCase();
        switch (mode){
            case "survival", "0" -> p.setGameMode(GameMode.SURVIVAL);
            case "creative", "1" -> p.setGameMode(GameMode.CREATIVE);
            case "adventure", "2" -> p.setGameMode(GameMode.ADVENTURE);
            case "spectator", "3" -> p.setGameMode(GameMode.SPECTATOR);
            default -> {
                p.sendMessage(ChatColor.RED + "Invalid gamemode!");
                return false;
            }
        }
        p.sendMessage(ChatColor.GREEN + "Gamemode changed to " + mode);
        return true;
    }
}
