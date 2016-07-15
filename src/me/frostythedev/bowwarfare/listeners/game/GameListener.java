package me.frostythedev.bowwarfare.listeners.game;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.arena.Arena;
import me.frostythedev.bowwarfare.players.BWPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.function.Consumer;

/**
 * Programmed by Tevin on 7/14/2016.
 */
public class GameListener implements Listener {

    private BWPlugin plugin;

    public GameListener(BWPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        plugin.isInGame(event.getPlayer()).ifPresent(arena -> {
            event.setCancelled(true);

            BWPlayer bwPlayer = plugin.getPlayerManager().getPlayer(event.getPlayer());
            String format = "&c[" + bwPlayer.getKills() + "&c] &b" + event.getPlayer().getName() + "&7» &f" + event.getMessage();
            arena.broadcast(format);
        });
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        plugin.isInGame(event.getPlayer()).ifPresent(arena -> {
            event.setCancelled(true);
        });
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        plugin.isInGame(event.getPlayer()).ifPresent(arena -> {
            event.setCancelled(true);
        });
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        plugin.isInGame(event.getPlayer()).ifPresent(arena -> {
            event.setCancelled(true);
        });
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        plugin.isInGame(event.getPlayer()).ifPresent(arena -> {
            event.setCancelled(true);
        });
    }


}
