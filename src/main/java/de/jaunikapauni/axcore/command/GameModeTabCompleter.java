package de.jaunikapauni.axcore.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GameModeTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        List<String> gameModes = new ArrayList<>();
        gameModes.add("adventure");
        gameModes.add("survival");
        gameModes.add("creative");
        gameModes.add("spectator");
        if(args.length == 1){
            return gameModes;
        }
        return gameModes;
    }
}
