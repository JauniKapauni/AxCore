package de.jaunikapauni.axcore.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FeedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        Player p = (Player) sender;
        if(p.getFoodLevel() == 20){
            p.sendMessage("You are already saturated!");
        } else {
            p.setFoodLevel(20);
            p.sendMessage("You were saturated!");
        }
        return true;
    }
}
