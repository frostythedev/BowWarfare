package me.frostythedev.bowwarfare.listeners.general;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.arena.enums.ArenaState;
import me.frostythedev.bowwarfare.utils.Colors;
import me.frostythedev.bowwarfare.utils.effects.Effects;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Programmed by Tevin on 7/14/2016.
 */
public class ClickListener implements Listener {

    private BWPlugin plugin;

    public ClickListener(BWPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (plugin.isInGame(player).isPresent()) {
                ItemStack hand = player.getItemInHand();

                if (hand != null && hand.hasItemMeta() && hand.getItemMeta().hasDisplayName()) {
                    String name = Colors.stripColors(hand.getItemMeta().getDisplayName());

                    if (plugin.isInGame(player).get().getArenaState().equals(ArenaState.IN_GAME)) {
                        if (name.equalsIgnoreCase("Flashbang")) {
                            player.setItemInHand(null);

                            Item item = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.SLIME_BALL));
                            item.setVelocity(player.getEyeLocation().getDirection().multiply(1.25));

                            new BukkitRunnable() {
                                int ticks = 6;

                                @Override
                                public void run() {
                                    if (ticks == 0) {
                                        Effects.explode(item.getLocation(), 1.0f, false, false);
                                        for (Entity en : item.getNearbyEntities(6, 6, 6)) {
                                            if (en instanceof Player) {
                                                Player p = (Player) en;
                                                int distance = (int) item.getLocation().distance(p.getLocation());
                                                int seconds = (7 - distance) * 20;
                                                p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, seconds, 0));
                                                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, seconds, 0));
                                            }
                                        }
                                        this.cancel();
                                    } else {
                                        Effects.playEffect(item.getLocation(), Effect.PARTICLE_SMOKE);
                                        ticks--;
                                    }
                                }
                            }.runTaskTimer(plugin, 10, 10);

                        } else if (name.equalsIgnoreCase("Grenade")) {

                            player.setItemInHand(null);

                            Item item = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.FLINT));
                            item.setVelocity(player.getEyeLocation().getDirection().multiply(1.25));

                            new BukkitRunnable() {
                                int ticks = 6;

                                @Override
                                public void run() {
                                    if (ticks == 0) {
                                        Effects.explode(item.getLocation(), 2.0f, false, false);
                                        for (Entity en : item.getNearbyEntities(6, 6, 6)) {
                                            if (en instanceof Player) {
                                                Player p = (Player) en;
                                                int distance = (int) item.getLocation().distance(p.getLocation());
                                                double damage = ((7 - distance) * 3.5);
                                                EntityDamageByEntityEvent ede = new EntityDamageByEntityEvent(player, p, EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, damage);
                                                plugin.getServer().getPluginManager().callEvent(ede);
                                            }
                                        }
                                        this.cancel();
                                    } else {
                                        Effects.playEffect(item.getLocation(), Effect.FIREWORKS_SPARK
                                        );
                                        ticks--;
                                    }
                                }
                            }.runTaskTimer(plugin, 10, 10);
                        }
                    }
                }
            }
        }
    }
}
