package me.frostythedev.bowwarfare.cmds.subs;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.scoreboard.ScoreboardAPI;
import me.frostythedev.bowwarfare.utils.Colors;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCmd extends BWSubCmd {

    public LeaveCmd(BWPlugin plugin) {
        super(plugin, "leave", "bw.user.leave");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (getPlugin().getArenaManager().getArena(player) != null) {
                getPlugin().getArenaManager().getArena(player).removePlayer(player);
                Colors.message(sender, "&bYou have left the arena you were previously in.");
                ScoreboardAPI.getInstance().setScoreboard(player, null);

                //TODO Restore inventories
                player.getInventory().clear();
                player.getInventory().setArmorContents(null);

                if (getPlugin().getLobbyLocation() != null) {
                    player.teleport(getPlugin().getLobbyLocation());
                }
            } else {
                Colors.message(sender, "&cYou are currently not in an arena.");
            }
        }
    }
}
