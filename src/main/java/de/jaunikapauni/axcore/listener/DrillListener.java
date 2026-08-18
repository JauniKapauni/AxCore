package de.jaunikapauni.axcore.listener;

import de.jaunikapauni.axcore.AxCore;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class DrillListener implements Listener {

    AxCore reference;
    public DrillListener(AxCore reference){
        this.reference = reference;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        ItemStack itemStack = e.getPlayer().getInventory().getItemInMainHand();
        if(!reference.isDrill(itemStack)){
            return;
        }
        if(reference.getConfig().getStringList("disabled-worlds").contains(e.getBlock().getWorld().getName())){
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "You can't use that tool in this world!");
            return;
        }
        Block center = e.getBlock();
        BlockFace face = getDirection(e.getPlayer());
        for(int i = -2; i <= 2; i++){
            for(int j = -2; j <= 2; j++){
                Block block = getOffsetBlock(center, face, i, j);
                if(block.getType() == Material.AIR || block.getType() == Material.BEDROCK){
                    continue;
                }
                block.breakNaturally(itemStack);
                damageDrill(itemStack);
            }
        }
    }

    private void damageDrill(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        NamespacedKey key = new NamespacedKey(reference, "drill_uses");
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        int uses = persistentDataContainer.getOrDefault(key, PersistentDataType.INTEGER, 0);
        uses--;
        if(uses <= 0){
            itemStack.setAmount(0);
            return;
        }
        persistentDataContainer.set(key, PersistentDataType.INTEGER, uses);
        itemMeta.lore(List.of(Component.text(ChatColor.GRAY + "Uses: " + uses + " / 500")));
        itemStack.setItemMeta(itemMeta);
    }

    private BlockFace getDirection(Player p){
        float yaw = p.getLocation().getYaw();
        float pitch = p.getLocation().getPitch();
        if(pitch > 45){
            return BlockFace.DOWN;
        }
        if(pitch < -45){
            return BlockFace.UP;
        }
        if(yaw < 0){
            yaw += 360;
        }
        if(yaw >= 315 || yaw < 45){
            return BlockFace.SOUTH;
        }
        if(yaw < 135){
            return BlockFace.WEST;
        }
        if(yaw < 225){
            return BlockFace.NORTH;
        }
        return BlockFace.EAST;
    }

    private Block getOffsetBlock(Block center, BlockFace face, int i, int j) {
        return switch (face){
            case NORTH, SOUTH -> center.getWorld().getBlockAt(center.getX() + i, center.getY() + j, center.getZ());
            case EAST, WEST -> center.getWorld().getBlockAt(center.getX(), center.getY() + j, center.getZ() + i);
            case UP, DOWN -> center.getWorld().getBlockAt(center.getX() + i, center.getY(), center.getZ() + j);
            default -> center;
        };
    }
}
