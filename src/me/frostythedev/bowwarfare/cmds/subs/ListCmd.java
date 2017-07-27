package me.frostythedev.bowwarfare.cmds.subs;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.utils.Colors;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListCmd extends BWSubCmd {

    public ListCmd(BWPlugin plugin) {
        super(plugin, "list", "bw.admin.list");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (getPlugin().getArenaManager().getArenaNames().isEmpty()) {
                Colors.message(sender, "&cNo arenas have been loaded.");
            } else {
                Colors.message(sender, "&a=====[ &2&lLoaded Arenas &a&l]=====");
                getPlugin().getArenaManager().getArenaNames().forEach(str -> Colors.sendMessage(sender, "&e" + str));
            }
        }
    }
}
