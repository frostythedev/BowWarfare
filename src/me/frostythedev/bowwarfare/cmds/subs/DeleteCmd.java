package me.frostythedev.bowwarfare.cmds.subs;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.utils.Colors;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeleteCmd extends BWSubCmd {

    public DeleteCmd(BWPlugin plugin) {
        super(plugin, "delete", "bw.arena.delete");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (args.length >= 2) {
                String name = args[1];
                if (getPlugin().getArenaManager().deleteArena(name)) {
                    Colors.message(sender, "&aYou have deleted the arena with name '" + name + "'");
                } else {
                    Colors.message(sender, "&cAn error occurred while deleting arena with name '" + name + "'");
                }
            }
        }
    }
}
