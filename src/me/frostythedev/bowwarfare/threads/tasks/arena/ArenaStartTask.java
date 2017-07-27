package me.frostythedev.bowwarfare.threads.tasks.arena;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.Config;
import me.frostythedev.bowwarfare.arena.Arena;
import me.frostythedev.bowwarfare.arena.enums.ArenaState;
import me.frostythedev.bowwarfare.utils.Colors;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public class ArenaStartTask extends BukkitRunnable {

    private BWPlugin plugin;
    private Arena arena;
    private int ticks = 0;
    private int time;
    private Integer[] interval;

    public ArenaStartTask(BWPlugin plugin, Arena arena, int ticks, int time, Integer[] interval) {
        this.plugin = plugin;
        this.arena = arena;
        this.ticks = ticks;
        this.time = time;
        this.interval = interval;
        runTaskTimer(plugin, 20,20);
    }

    @Override
    public void run() {

        if (arena.getSize() >= arena.getMinPlayers()) {
            if (this.ticks == 0) {
                arena.broadcast(BWPlugin.PREFIX + "&bEnough players have joined the countdown will now begin.");
            }
            if (this.ticks + 1 <= this.time) {
                this.ticks += 1;
            }
        } else {
            this.ticks = 0;
        }


        if (this.ticks > 0) {
            if (this.ticks == this.time) {
                arena.setArenaState(ArenaState.IN_GAME);
                for (Player ps : arena.getPhysicalPlayers()) {
                    Colors.clearChat(ps);
                    Colors.message(ps, "&bFree For All - Attack all enemies");
                    ps.teleport(arena.getRandomSpawn(true));

                   if(Config.GAME_SAVE_INVENTORY_ENABLED){
                       BWPlugin.getInstance().getInventoryManager().saveInventory(ps);
                   }
                    ps.getInventory().clear();
                    ps.getInventory().setArmorContents(null);

                    ps.getInventory().addItem(new ItemStack(Material.BOW,1));
                    ps.getInventory().addItem(new ItemStack(Material.ARROW,1));
                    plugin.getPlayerArrowTask().addPlayer(ps);
                }
                this.cancel();

                new ArenaEndTask(plugin, arena);
            }
        }

        for (int i : interval) {
            int result = time - ticks;
            if (result == i) {
                arena.broadcast(BWPlugin.PREFIX + "&eThe game will begin in &6" + result + " &eseconds.");
            }
        }
    }
}
