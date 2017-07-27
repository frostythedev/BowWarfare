package me.frostythedev.bowwarfare.cmds.subs;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.utils.Colors;
import me.frostythedev.bowwarfare.utils.Locations;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLobbyCmd extends BWSubCmd {

    public SetLobbyCmd(BWPlugin plugin) {
        super(plugin, "setlobby", "bw.admin.setlobby");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            getPlugin().getConfig().set("lobby-location", Locations.toString(player.getLocation()));
            getPlugin().saveConfig();
            Colors.message(player, "&aYou have set the &bglobal &alobby location to your feet.");
        }
    }
}
