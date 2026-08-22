package de.jaunikapauni.axcore.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        if(!e.getView().getTitle().equals("Trash")){
            return;
        }
        Inventory inventory = e.getInventory();
        inventory.clear();
        if(e.getPlayer() instanceof Player p){
            p.sendMessage(ChatColor.GREEN + "Trash deleted!");
        }
    }
}
