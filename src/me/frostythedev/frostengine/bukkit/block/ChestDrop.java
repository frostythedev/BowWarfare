package me.frostythedev.frostengine.bukkit.block;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.frostengine.bukkit.firework.InstantFirework;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ChestDrop {

    private Location location;

    public ChestDrop(BWPlugin plugin, Location location, ItemStack[] contents) {
        this.location = location;

        //"ChestDrop_" + ThreadLocalRandom.current().nextInt(54321),
        plugin.getRunnableManager().runTaskOneTickLater(new Runnable() {
            @Override
                public void run() {

                Location loc = getLocation().add(0,10,0);
                loc.setYaw(180);
                ArmorStand armorStand = getLocation().getWorld().spawn(loc, ArmorStand.class);
                armorStand.setHelmet(new ItemStack(Material.CHEST));
                armorStand.setVisible(false);

                BukkitRunnable runnable = new BukkitRunnable() {
                    @Override
                    public void run() {
                        FireworkEffect effect = FireworkEffect.builder()
                                .withColor(Color.RED)
                                .withTrail()
                                .build();
                        new InstantFirework(effect, armorStand.getLocation().add(0, 1.5, 0));
                        if (armorStand.isOnGround()) {
                            armorStand.remove();
                            Location loc = armorStand.getLocation();
                            loc.getBlock().setType(Material.CHEST);
                            Chest chest = (Chest) loc.getBlock().getState();
                            chest.getBlockInventory().setContents(contents);
                            chest.update(true);
                            this.cancel();
                        }
                    }
                };
                runnable.runTaskTimerAsynchronously(BWPlugin.getInstance(), 5, 5);
            }
        });
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
