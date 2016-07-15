package me.frostythedev.bowwarfare.listeners.general;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.arena.Arena;
import me.frostythedev.bowwarfare.arena.enums.ArenaState;
import me.frostythedev.bowwarfare.utils.Colors;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public class SignChangeListener implements Listener {

    private BWPlugin plugin;

    public SignChangeListener(BWPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChange(SignChangeEvent event) {
        if (event.getLine(0).equalsIgnoreCase("[BW]")) {
            String arenaName = event.getLine(1);

            Arena arena = plugin.getArenaManager().getArena(arenaName);
            if (arena == null) {
                event.setLine(0, Colors.toColors("&4&l[BW]"));
                event.setLine(1, Colors.toColors("&c&lERROR"));
                event.setLine(2, Colors.toColors("&cInvalid arena"));
                event.setLine(3, Colors.toColors("&cname"));
            } else {
                event.setLine(0, Colors.toColors("&4[&2BW&4]"));
                event.setLine(1, Colors.toColors("&l" + arena.getName()));
                event.setLine(2, Colors.toColors("" + arena.getSize() + "/" + arena.getMaxPlayers()));
                event.setLine(3, Colors.toColors(arena.getArenaState().getSignFormat()));
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Block block = event.getClickedBlock();

            if (block.getType().equals(Material.SIGN) || block.getType().equals(Material.WALL_SIGN)
                    || block.getType().equals(Material.SIGN_POST)) {
                Sign sign = (Sign) block.getState();
                if (Colors.stripColors(sign.getLine(0)).equalsIgnoreCase("[BW]")) {
                    String arenaName = Colors.stripColors(sign.getLine(1));

                    Arena arena = plugin.getArenaManager().getArena(arenaName);
                    if (arena != null) {
                        if (arena.getArenaState().equals(ArenaState.LOBBY)) {
                            if(plugin.getArenaManager().getArena(player) != null){
                                Colors.message(player, "&eYou must leave your current arena before joining another.");
                            }else{
                                if (arena.addPlayer(player)) {
                                    arena.broadcast(BWPlugin.PREFIX + "&b" + player.getName() + " has joined the game!");
                                }
                            }
                        } else {
                            Colors.message(player, "&eThe arena you are trying to join is currently running or has ended.");
                        }
                    }
                }
            }
        }
    }
}
