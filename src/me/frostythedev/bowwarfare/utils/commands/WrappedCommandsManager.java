package me.frostythedev.bowwarfare.utils.commands;

public class WrappedCommandsManager extends CommandsManager<WrappedCommandSender> {
    @Override
    public boolean hasPermission(WrappedCommandSender player, String perm) {
        return player.hasPermission(perm);
    }
}
