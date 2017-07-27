package me.frostythedev.bowwarfare.listeners.game;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.Config;
import me.frostythedev.bowwarfare.arena.enums.ArenaState;
import me.frostythedev.bowwarfare.players.BWPlayer;
import me.frostythedev.bowwarfare.utils.Colors;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public class DeathListener implements Listener {

    private BWPlugin plugin;

    public DeathListener(BWPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        plugin.isInGame(event.getEntity()).ifPresent(arena -> {

            if(arena.getArenaState().equals(ArenaState.IN_GAME)){
                event.setDeathMessage(null);
                event.getDrops().clear();
                event.setDroppedExp(0);
                event.getEntity().getInventory().clear();

                String message = "&b" + event.getEntity().getName() + " has been &owrecked &bby " + event.getEntity().getKiller().getName();
                arena.broadcast(message);

                BWPlayer killer = plugin.getPlayerManager().getPlayer(event.getEntity().getKiller());
                BWPlayer player = plugin.getPlayerManager().getPlayer(event.getEntity());

                player.setKills(0);

                if(player.getPerk1() != null){
                    player.getPerk1().reset(player);
                }
                if(player.getPerk2() != null){
                    player.getPerk2().reset(player);
                }
                if(player.getPerk3() != null){
                    player.getPerk3().reset(player);
                }

                killer.setKills(killer.getKills() + 1);
                arena.addScore(killer.getUuid(), 1);

                player.setDeaths(player.getDeaths() + 1);

                String perks = killer.activatePerks();
                if (!perks.equals("")) {
                    Colors.message(killer, "&e&oYour " + perks + " perk has been activated.");
                }
            }
        });
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        plugin.isInGame(event.getPlayer()).ifPresent(arena -> {
           if(arena.getArenaState().equals(ArenaState.IN_GAME)){
               event.setRespawnLocation(arena.getRandomSpawn(Config.GAME_SAFE_SPAWN_ENABLED));
               event.getPlayer().getInventory().setItem(0, new ItemStack(Material.BOW));
           }
        });
    }
}
