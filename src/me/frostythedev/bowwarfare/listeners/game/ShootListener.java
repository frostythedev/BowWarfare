package me.frostythedev.bowwarfare.listeners.game;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.arena.Arena;
import me.frostythedev.bowwarfare.arena.enums.ArenaState;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.function.Consumer;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public class ShootListener implements Listener {

    private BWPlugin plugin;

    public ShootListener(BWPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            plugin.isInGame(player).ifPresent(arena -> {
                if (arena.getArenaState().equals(ArenaState.IN_GAME)) {
                    double damage = event.getForce() * 20;
                    event.getProjectile().setMetadata("DamageData", new FixedMetadataValue(plugin, player.getName() + ";" + damage));
                }
            });
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Arrow) {
            Player player = (Player) event.getEntity();
            Arrow arrow = (Arrow) event.getDamager();

            plugin.isInGame(player).ifPresent(arena -> {
                if (arena.getArenaState().equals(ArenaState.IN_GAME)) {
                    if (arrow.hasMetadata("DamageData")) {
                        String data = arrow.getMetadata("DamageData").get(0).asString();
                        double damage = Double.parseDouble(data.split(";")[1]);
                        event.setDamage(damage);
                        arrow.removeMetadata("DamageData", plugin);
                        arrow.remove();
                    }
                }
            });
        }
    }
}
