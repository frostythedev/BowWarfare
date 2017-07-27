package me.frostythedev.bowwarfare.threads.tasks.perks;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.UUID;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public class PlayerArrowTask extends BukkitRunnable {

    private BWPlugin plugin;
    private HashSet<UUID> storage;

    public PlayerArrowTask(BWPlugin plugin) {
        this.plugin = plugin;
        this.storage = new HashSet<>();
        runTaskTimer(plugin, 300, 300);
    }

    @Override
    public void run() {
        for (UUID uuid : storage) {
            Player player = Bukkit.getPlayer(uuid);
            if (plugin.getArenaManager().getArena(player) != null) {
                int amount = 0;
                if (player.getInventory().getItem(1) != null) {
                    if (player.getInventory().getItem(1).getType().equals(Material.ARROW)) {
                        amount = player.getInventory().getItem(1).getAmount();
                    }
                }
                if (amount < 3) {
                    player.getInventory().setItem(1, new ItemStack(Material.ARROW, amount+1));
                    Colors.message(player, "&e&oYou have salvaged 1 arrow from your Salvage Perk.");
                }
            }
        }
    }

    public void addPlayer(Player player) {
        if (!storage.contains(player.getUniqueId())) {
            storage.add(player.getUniqueId());
        }
    }

    public void removePlayer(Player player) {
        if (storage.contains(player.getUniqueId())) {
            storage.remove(player.getUniqueId());
        }
    }
}
