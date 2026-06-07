package de.jaunikapauni.axcore.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WeatherTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        List<String> weatherModes = new ArrayList<>();
        weatherModes.add("sun");
        weatherModes.add("rain");
        weatherModes.add("thunder");
        if(args.length == 1){
            return weatherModes;
        }
        return weatherModes;
    }
}
