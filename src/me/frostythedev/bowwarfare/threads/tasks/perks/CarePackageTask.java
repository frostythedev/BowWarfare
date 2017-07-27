package me.frostythedev.bowwarfare.threads.tasks.perks;

import me.frostythedev.bowwarfare.BWPlugin;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class CarePackageTask extends BukkitRunnable {

    private BWPlugin plugin;
    private Location location;

    public CarePackageTask(BWPlugin plugin, Location location) {
        this.plugin = plugin;
        this.location = location;

    }

    @Override
    public void run() {

    }
}
