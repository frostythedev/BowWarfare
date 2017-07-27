package me.frostythedev.bowwarfare.threads.tasks.arena;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.arena.Arena;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public class ArenaQueryTask extends BukkitRunnable {

    private BWPlugin plugin;
    private Map<String, ArenaStartTask> arenaTasks;

    public ArenaQueryTask(BWPlugin plugin) {
        this.plugin = plugin;
        this.arenaTasks = new HashMap<>();
        runTaskTimer(plugin, 20, 20);
    }

    @Override
    public void run() {
        for (Arena arena : plugin.getArenaManager().getAvailableArenas()) {
            if (!arenaTasks.containsKey(arena.getName())) {
                arenaTasks.put(arena.getName(),  new ArenaStartTask(plugin, arena, 0, 60, new Integer[]{50, 45, 30, 15, 5, 4, 3, 2, 1}));
            }
        }
    }

    public ArenaStartTask getTask(String name) {
        if (arenaTasks.containsKey(name)) {
            return arenaTasks.get(name);
        }
        return null;
    }

    public ArenaStartTask getTask(Arena arena) {
        return getTask(arena.getName());
    }

    public Map<String, ArenaStartTask> getArenaTasks() {
        return arenaTasks;
    }
}
