package me.frostythedev.bowwarfare.scoreboard;

import me.frostythedev.bowwarfare.scoreboard.type.Scoreboard;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Programmed by Tevin on 7/16/2016.
 */
public class ScoreboardAPI {

    private static ScoreboardAPI instance;
    private Map<UUID, Scoreboard> cachedScoreboards = new HashMap<>();

    public static ScoreboardAPI getInstance() {
        if (instance == null) {
            instance = new ScoreboardAPI();
        }
        return instance;
    }

    public void setScoreboard(Player player, Scoreboard scoreboard){
        if(getScoreboard(player) != null){
            getScoreboard(player).deactivate();
            cachedScoreboards.replace(player.getUniqueId(), scoreboard);
        }else{
            cachedScoreboards.put(player.getUniqueId(), scoreboard);
        }
        if(scoreboard != null){
            scoreboard.activate();
        }
    }

    public Scoreboard getScoreboard(Player player){
        if(cachedScoreboards.containsKey(player.getUniqueId())){
            return cachedScoreboards.get(player.getUniqueId());
        }
        return null;
    }

    public Map<UUID, Scoreboard> getCachedScoreboards() {
        return cachedScoreboards;
    }
}
