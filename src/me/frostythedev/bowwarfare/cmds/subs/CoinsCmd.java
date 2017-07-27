package me.frostythedev.bowwarfare.cmds.subs;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.players.BWPlayer;
import me.frostythedev.bowwarfare.utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinsCmd extends BWSubCmd {

    public CoinsCmd(BWPlugin plugin) {
        super(plugin, "coins", "bw.user.coins");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if(args.length >= 2){
                BWPlayer bwPlayer = getPlugin().getPlayerManager().getPlayer(Bukkit.getPlayer(args[1]));
                if (bwPlayer == null) {
                    Colors.message(sender, "&cCould not find coins for a player with the name '" + args[1] + "'");
                } else {
                    Colors.message(sender, "&e" + args[1] + " has &6" + bwPlayer.getCoins() + " &ecoins.");
                }
            }else{
                Colors.message(sender, "&cNot enough args.");
            }
        }
    }
}
