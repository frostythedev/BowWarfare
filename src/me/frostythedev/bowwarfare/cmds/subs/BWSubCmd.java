package me.frostythedev.bowwarfare.cmds.subs;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.frostengine.bukkit.cmd.SubCommand;

public abstract class BWSubCmd extends SubCommand {

    private BWPlugin plugin;

    public BWSubCmd(BWPlugin plugin, String name, String permission) {
        super(name, permission);
        this.plugin = plugin;
    }

    public BWPlugin getPlugin() {
        return plugin;
    }
}
