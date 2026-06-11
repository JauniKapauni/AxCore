package de.jaunikapauni.axcore.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlySpeedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return false;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axcore.flyspeed")){
            p.sendMessage("You don't have the permission! [axcore.flyspeed]");
            return true;
        }
        if(args.length == 0){
            p.sendMessage(ChatColor.RED + "Please enter a flyspeed!");
            return false;
        }
        try{
            int speed = Integer.parseInt(args[0]);
            if(speed < 1 || speed > 10){
                p.sendMessage(ChatColor.RED + "Please enter a flyspeed between 1 and 10!");
                return false;
            }
            float flySpeed = speed / 10.0f;
            p.setFlySpeed(flySpeed);
            p.sendMessage(ChatColor.GREEN + "Your flyspeed was changed to: " + flySpeed);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
