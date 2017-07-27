package me.frostythedev.bowwarfare.threads.tasks.perks;

import com.google.common.collect.Sets;
import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.utils.Colors;
import me.frostythedev.bowwarfare.utils.ItemStackBuilder;
import me.frostythedev.bowwarfare.utils.Locations;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Programmed by Tevin on 7/14/2016.
 */
public class UAVUpdateTask extends BukkitRunnable {

    private BWPlugin plugin;
    private Map<UUID, Integer> storage;

    private Map<UUID, Integer> jammer;

    public UAVUpdateTask(BWPlugin plugin) {
        this.plugin = plugin;
        this.storage = new HashMap<>();
        this.jammer = new HashMap<>();
        runTaskTimer(plugin, 20, 20);
    }

    @Override
    public void run() {

//        for(Player ps : Bukkit.getOnlinePlayers()){
//            if(ps.getInventory().getItem(8) != null && ps.getInventory().getItem(8).getType().equals(Material.COMPASS)){
//                for(Entity en : ps.getNearbyEntities(256,256,256)){
//
//                }
//            }
//        }

        if(!jammer.isEmpty()){
            for (UUID uuid : jammer.keySet()) {
                Player player = Bukkit.getPlayer(uuid);
                if (plugin.getArenaManager().getArena(player) != null) {
                    int time = storage.get(uuid);
                    if (player.getInventory().getItem(8) != null && player.getInventory().getItem(8).getType().equals(Material.REDSTONE_TORCH_ON)) {
                        if (time - 1 == 0) {
                            player.getInventory().setItem(8, null);
                            Colors.message(player, "&e&oYour UAV Jammer perk has expired.");
                            removePlayerJammer(player);
                        } else {
                            time -= 1;
                            player.getInventory().setItem(8, new ItemStackBuilder(new ItemStack(Material.REDSTONE_TORCH_ON))
                                    .setDisplayName(Colors.toColors("&c&lUAV JAMMER"))
                                    .setLore(Colors.toColors("&eTime Left: &7" + time))
                                    .getItemStack());
                            player.updateInventory();
                        }
                    }
                }else{
                    removePlayerJammer(player);
                }
            }
        }


        if(!storage.isEmpty()){
            for (UUID uuid : storage.keySet()) {
                Player player = Bukkit.getPlayer(uuid);
                if (plugin.getArenaManager().getArena(player) != null) {
                    int time = storage.get(uuid);

                    if (player.getInventory().getItem(6) != null && player.getInventory().getItem(6).getType().equals(Material.COMPASS)) {
                        if (time - 1 == 0) {
                            player.getInventory().setItem(6, null);
                            Colors.message(player, "&e&oYour UAV perk has expired.");
                            removePlayer(player);
                        } else {

                            if(Locations.getNearestPlayer(player, 256, jammer.keySet()) != null){
                                player.setCompassTarget(Locations.getNearestPlayer(player, 256).getLocation());
                            }

                            time -= 1;
                            player.getInventory().setItem(8, new ItemStackBuilder(new ItemStack(Material.COMPASS))
                                    .setDisplayName(Colors.toColors("&a&lUAV TRACKER"))
                                    .setLore(Colors.toColors("&eTime Left: &7" + time))
                                    .getItemStack());
                            player.updateInventory();
                        }
                    }
                }else{
                    removePlayer(player);
                }
            }
        }
    }

    public void addPlayer(Player player, int time) {
        if (!storage.containsKey(player.getUniqueId())) {
            storage.put(player.getUniqueId(), time);
        }
    }

    public void removePlayer(Player player) {
        if (storage.containsKey(player.getUniqueId())) {
            storage.remove(player.getUniqueId());
        }
    }

    public void addPlayerJammer(Player player, int time) {
        if (!jammer.containsKey(player.getUniqueId())) {
            jammer.put(player.getUniqueId(), time);
        }
    }

    public void removePlayerJammer(Player player) {
        if (jammer.containsKey(player.getUniqueId())) {
            jammer.remove(player.getUniqueId());
        }
    }
}
