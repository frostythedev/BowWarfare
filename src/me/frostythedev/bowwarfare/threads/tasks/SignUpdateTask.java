package me.frostythedev.bowwarfare.threads.tasks;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.arena.Arena;
import me.frostythedev.bowwarfare.utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public class SignUpdateTask extends BukkitRunnable {

    private BWPlugin plugin;

    public SignUpdateTask(BWPlugin plugin) {
        this.plugin = plugin;
        runTaskTimer(plugin, 20, 20);
    }

    @Override
    public void run() {
        for (World world : Bukkit.getWorlds()) {
            for (Chunk chunk : world.getLoadedChunks()) {
                for (BlockState block : chunk.getTileEntities()) {
                    if (block.getBlock().getType().equals(Material.SIGN) || block.getBlock().getType().equals(Material.WALL_SIGN)
                            || block.getBlock().getType().equals(Material.SIGN_POST)) {
                        Sign sign = (Sign) block;
                        if (Colors.stripColors(sign.getLine(0)).equalsIgnoreCase("[BW]")) {
                            String arenaName = Colors.stripColors(sign.getLine(1));

                            Arena arena = plugin.getArenaManager().getArena(arenaName);
                            if (arena != null) {
                                sign.setLine(2, Colors.toColors("" + arena.getSize() + "/" + arena.getMaxPlayers()));
                                sign.setLine(3, Colors.toColors(arena.getArenaState().getSignFormat()));
                                sign.update(true);
                            }
                        }
                    }
                }
            }
        }
    }
}
