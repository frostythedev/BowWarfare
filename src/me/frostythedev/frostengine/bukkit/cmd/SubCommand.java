package me.frostythedev.frostengine.bukkit.cmd;

import org.bukkit.command.CommandSender;

public abstract class SubCommand implements Comparable<SubCommand>{

    private Command command;
    private String name;
    private String permission = "";
    private String[] aliases;

    public SubCommand(String name, String permission) {
        this(null, name, permission);
    }

    public SubCommand(Command command, String name, String permission, String... aliases) {
        this.command = command;
        this.name = name;
        this.permission = permission;
        this.aliases = aliases;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public String[] getAliases() {
        return aliases;
    }

    public abstract void run(CommandSender sender, String[] args);

    @Override
    public int compareTo(SubCommand o) {
        return this.getName().compareToIgnoreCase(o.getName());
    }
}
