package me.frostythedev.bowwarfare.cmds.subs;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.utils.Colors;
import org.bukkit.command.CommandSender;

public class DisableCmd extends BWSubCmd {

    public DisableCmd(BWPlugin plugin) {
        super(plugin, "disable", "bw.admin.disable");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        getPlugin().getServer().getPluginManager().disablePlugin(getPlugin());
        Colors.message(sender, "&4Plugin has been disabled.");
    }
}
