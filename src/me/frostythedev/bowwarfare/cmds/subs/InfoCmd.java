package me.frostythedev.bowwarfare.cmds.subs;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.utils.Colors;
import org.bukkit.command.CommandSender;

public class InfoCmd extends BWSubCmd {

    public InfoCmd(BWPlugin plugin) {
        super(plugin, "info", "");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        Colors.message(sender, "&a=====[ &2&lInformation &a&l]=====");
        Colors.sendMessage(sender, "&dAuthor: &bfrostythedev");
        Colors.sendMessage(sender, "&dOriginal Plugin: &bXxLeetGamerxX");
        Colors.sendMessage(sender, "&dLast Updated: &b13/7/16");
        Colors.sendMessage(sender, "&dIn Partnership: &bVisionCoding LLC {c}");
    }
}
