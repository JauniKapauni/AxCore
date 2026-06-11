package de.jaunikapauni.axcore.command;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WeatherCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return false;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axcore.weather")){
            p.sendMessage("You don't have the permission! [axcore.weather]");
            return true;
        }
        World w = p.getWorld();

        if (args.length == 0) {
            return false;
        }

        String weather = args[0].toLowerCase();
        switch (weather){
            case "sun":
                w.setStorm(false);
                w.setThundering(false);
                w.setWeatherDuration(12000);
                p.sendMessage("You have set the weather to sunny!");
                break;
            case "rain":
                w.setStorm(true);
                w.setThundering(false);
                w.setWeatherDuration(12000);
                p.sendMessage("You have set the weather to rainy!");
                break;
            case "thunder":
                w.setStorm(true);
                w.setThundering(true);
                w.setWeatherDuration(12000);
                p.sendMessage("You have set the weather to thunder");
                break;
            default:
                return false;
        }
        return true;
    }
}
