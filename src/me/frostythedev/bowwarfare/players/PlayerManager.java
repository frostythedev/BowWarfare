package me.frostythedev.bowwarfare.players;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.utils.Colors;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public class PlayerManager {

    private BWPlugin plugin;
    private Map<UUID, BWPlayer> loadedPlayers;

    public PlayerManager(BWPlugin plugin) {
        this.plugin = plugin;
        this.loadedPlayers = new HashMap<>();
    }

    public BWPlayer loadPlayer(Player player) {
        if (getPlayer(player) != null) {
            return getPlayer(player);
        } else {
            if (plugin.getPlayerDataStorage().load(player.getUniqueId().toString()) == null) {
                BWPlayer bwPlayer = new BWPlayer(player.getUniqueId(), player.getName());
                plugin.getPlayerDataStorage().save(bwPlayer);
                loadedPlayers.put(player.getUniqueId(), bwPlayer);
                return bwPlayer;
            }else{
                BWPlayer bwPlayer = plugin.getPlayerDataStorage().load(player.getUniqueId().toString());
                loadedPlayers.put(player.getUniqueId(), bwPlayer);
                return bwPlayer;
            }
        }
    }

    public void savePlayer(BWPlayer player) {
        plugin.getPlayerDataStorage().save(player);
    }

    public void savePlayer(Player player) {
        if (getPlayer(player) != null) {
            savePlayer(getPlayer(player));
        }
    }

    public BWPlayer getPlayer(Player player) {
        if (loadedPlayers.containsKey(player.getUniqueId())) {
            return loadedPlayers.get(player.getUniqueId());
        }
        return null;
    }

    public void displayStats(CommandSender sender, BWPlayer player) {
        Colors.message(sender, "&a=====[ &2&lPlayer Stats &a]=====");
        Colors.message(sender, "&2Kills: &e" + player.getKills());
        Colors.message(sender, "&2Deaths: &e" + player.getDeaths());
    }

    public Map<UUID, BWPlayer> getLoadedPlayers() {
        return loadedPlayers;
    }
}
