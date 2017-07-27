package me.frostythedev.bowwarfare.cmds.subs;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.arena.Arena;
import me.frostythedev.bowwarfare.enums.Messages;
import me.frostythedev.bowwarfare.utils.Colors;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CreateCmd extends BWSubCmd {

    public CreateCmd(BWPlugin plugin) {
        super(plugin, "create", "bw.arena.create");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length >= 4) {
                String name = args[1];
                int min = 4;
                int max = 24;
                try {
                    min = Integer.valueOf(args[2]);
                    max = Integer.valueOf(args[3]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                if (getPlugin().getArenaManager().getArena(name) != null) {
                    Messages.ARENA_ALREADY_EXISTS.send(sender);
                } else {
                    Arena arena = getPlugin().getArenaManager().createArena(name, player.getLocation(), min, max, new ArrayList<>());
                    Colors.message(sender, "&aYou have created an arena with name '" + arena.getName() + "'");
                }
            }
        }
    }
}
