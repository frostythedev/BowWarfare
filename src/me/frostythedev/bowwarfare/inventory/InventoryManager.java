package me.frostythedev.bowwarfare.inventory;

import com.google.common.collect.Maps;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

public class InventoryManager {

    private Map<UUID, ItemStack[]> armor;
    private Map<UUID, ItemStack[]> inventories;

    public InventoryManager() {
        this.inventories = Maps.newHashMap();
        this.armor = Maps.newHashMap();
    }

    public boolean isStored(Player player){
        return inventories.containsKey(player.getUniqueId()) || armor.containsKey(player.getUniqueId());
    }

    public boolean saveInventory(Player player){
        if(isStored(player)){
            return false;
        }

        armor.put(player.getUniqueId(), player.getInventory().getArmorContents());
        inventories.put(player.getUniqueId(), player.getInventory().getContents());
        return true;
    }

    public boolean restoreInventory(Player player){
        if(isStored(player)){
            if(armor.containsKey(player.getUniqueId())){
                player.getInventory().setArmorContents(armor.get(player.getUniqueId()));
                armor.remove(player.getUniqueId());
            }
            if(inventories.containsKey(player.getUniqueId())){
                player.getInventory().setContents(inventories.get(player.getUniqueId()));
                inventories.remove(player.getUniqueId());
            }
            return true;
        }
        return false;
    }

    public Map<UUID, ItemStack[]> getInventories() {
        return inventories;
    }
}
