package me.frostythedev.bowwarfare.threads.tasks.arena;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.Config;
import me.frostythedev.bowwarfare.arena.Arena;
import me.frostythedev.bowwarfare.players.BWPlayer;
import me.frostythedev.bowwarfare.scoreboard.ScoreboardAPI;
import me.frostythedev.bowwarfare.scoreboard.ScoreboardLib;
import me.frostythedev.bowwarfare.scoreboard.common.EntryBuilder;
import me.frostythedev.bowwarfare.scoreboard.type.Entry;
import me.frostythedev.bowwarfare.scoreboard.type.Scoreboard;
import me.frostythedev.bowwarfare.scoreboard.type.ScoreboardHandler;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

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

        for (Player ps : arena.getPhysicalPlayers()) {
            Scoreboard scoreboard = ScoreboardLib.createScoreboard(ps)
                    .setHandler(new ScoreboardHandler() {

                        @Override
                        public String getTitle(Player player) {
                            return BWPlugin.PREFIX;
                        }

                        @Override
                        public List<Entry> getEntries(Player player) {
                            EntryBuilder builder = new EntryBuilder();
                            builder.next("&a&lYou:")
                                    .next(player.getName())
                                    .blank()
                                    .next("&a&lPlayers:")
                                    .next(arena.getSize() + "/" + arena.getMaxPlayers())
                                    .blank();

                            BWPlayer bwPlayer = plugin.getPlayerManager().getPlayer(player);
                            if (bwPlayer != null) {
                                builder.next("&a&lKills:")
                                        .next("" + arena.getScore(player.getUniqueId()))
                                        .blank()
                                        .next("&a&lKS:")
                                        .next("" + bwPlayer.getKills())
                                ;
                            } else {
                                builder.next("&cError loading stats.");
                            }

                            return builder.build();
                        }

                    })
                    .setUpdateInterval(10);
            ScoreboardAPI.getInstance().setScoreboard(ps, scoreboard);
        }


        if (!ended) {
            if (arena.getHighestScore() == Config.GAME_SCORED_REQUIRED_TO_WIN) {
                arena.endGame();
                this.ended = true;
                this.cancel();
            }
        }

        if (!ended) {
            int time = Config.GAME_MAX_TIME;
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
