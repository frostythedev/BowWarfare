package me.frostythedev.bowwarfare.threads.tasks;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.arena.Arena;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public class ArenaEndTask extends BukkitRunnable {

    private BWPlugin plugin;
    private Arena arena;
    private int ticks;
    private boolean ended;

    public ArenaEndTask(BWPlugin plugin, Arena arena) {
        this.plugin = plugin;
        this.arena = arena;
        this.runTaskTimer(plugin, 20, 20);
    }

    @Override
    public void run() {
        if(!ended){
            if (arena.getHighestScore() == 25) {
                arena.endGame();
                this.ended = true;
                this.cancel();
            }
        }

        if (!ended) {
            int time = 26 * 60 * 5;
            if (ticks == time) {
                arena.endGame();
                ended = true;
                this.cancel();
            }
        }
        ticks++;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }
}
