package me.frostythedev.bowwarfare.cmds.subs;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.arena.Arena;
import me.frostythedev.bowwarfare.utils.Colors;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveSpawnCmd extends BWSubCmd {

    public RemoveSpawnCmd(BWPlugin plugin) {
        super(plugin, "removespawn", "bw.arena.removespawn");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length >= 2) {
                String name = args[1];

                Arena arena = getPlugin().getArenaManager().getArena(name);
                if (arena == null) {
                    Colors.message(sender, "&cCould not find an arena with that specified name '" + name + "'");
                } else {
                    if (!getPlugin().getArenaManager().removeSpawn(arena, player.getLocation())) {
                        Colors.message(sender, "&cThis location has not been registered as a spawnpoint.");
                    } else {
                        Colors.message(sender, "&eSpawnpoint removed! Size: &6" + arena.getSpawnpoints().size());
                    }
                }
            }
        }
    }
}
