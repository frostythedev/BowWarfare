package me.frostythedev.bowwarfare.cmds.subs;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.players.BWPlayer;
import me.frostythedev.bowwarfare.utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCmd extends BWSubCmd {

    public StatsCmd(BWPlugin plugin) {
        super(plugin, "stats", "bw.user.stats");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            BWPlayer bwPlayer = getPlugin().getPlayerManager().getPlayer(Bukkit.getPlayer(args[1]));
            if (bwPlayer == null) {
                Colors.message(sender, "&cCould not find stats for a player with the name '" + args[1] + "'");
            } else {
                getPlugin().getPlayerManager().displayStats(sender, bwPlayer);
            }
        }
    }
}
