package me.frostythedev.bowwarfare.utils.commands;

import org.bukkit.command.CommandSender;

public class BukkitCommandsManager extends CommandsManager<CommandSender> {
    @Override
    public boolean hasPermission(CommandSender player, String perm) {
        return player.hasPermission(perm);
    }
}
