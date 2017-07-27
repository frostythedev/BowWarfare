package me.frostythedev.bowwarfare.cmds.subs;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.Config;
import me.frostythedev.bowwarfare.arena.enums.ArenaState;
import me.frostythedev.bowwarfare.utils.Colors;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PerksCmd extends BWSubCmd {

    public PerksCmd(BWPlugin plugin) {
        super(plugin, "perks", "bw.user.perks");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(!Config.PERKS_ENABLED){
                Colors.message(player, "&cPerks are currently disabled.");
            }else{
                if (getPlugin().getArenaManager().getArena(player) != null) {
                    getPlugin().isInGame(player).ifPresent(arena -> {
                        if (!arena.getArenaState().equals(ArenaState.LOBBY)) {
                            Colors.message(sender, "&cYou cannot change perks while in a game please leave and try again.");
                        } else {
                            getPlugin().getPerkManager().openSelectorMenu(player);
                        }
                    });
                } else {
                    getPlugin().getPerkManager().openSelectorMenu(player);
                }
            }
        }
    }
}
