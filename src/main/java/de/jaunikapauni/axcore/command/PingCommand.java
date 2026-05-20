package de.jaunikapauni.axcore.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        Player p = (Player) sender;
        int playerPing = p.getPing();
        if(playerPing < 50){
            p.sendMessage(ChatColor.GREEN + "" + playerPing);
        } else if(playerPing < 100){
            p.sendMessage(ChatColor.YELLOW + "" + playerPing);
        } else {
            p.sendMessage(ChatColor.RED + "" + playerPing);
        }
        return true;
    }
}
