package me.frostythedev.frostengine.bukkit.cmd;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;

import java.util.*;
import java.util.stream.Collectors;

public class Command implements CommandExecutor, Comparator<Command> {

    private String name;
    private String permission;
    private String usage;
    private String[] aliases;
    private boolean playerOnly;
    private Map<String, SubCommand> subCommands;

    private boolean defaultHelp = false;

    public Command(String name) {
        this(name, "");
    }

    public Command(String name, String permission) {
        this(name, permission, "");
    }

    public Command(String name, String permission, String... aliases) {
        this(name, permission, aliases, true);
    }

    public Command(String name, String permission, String[] aliases, boolean playerOnly) {
        this.name = name;
        this.permission = permission;
        this.aliases = aliases;
        this.playerOnly = playerOnly;
        this.subCommands = new HashMap<>();

        this.usage = "&c/" + name + "";

        if (this.isDefaultHelp()) {
            this.addSubCommand(new CommandHelp(this));
        }
    }

    public void addSubCommand(SubCommand command) {
        if (!this.subCommands.containsKey(command.getName())) {
            command.setCommand(this);
            this.subCommands.put(command.getName(), command);
        }
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

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public boolean isPlayerOnly() {
        return playerOnly;
    }

    public void setPlayerOnly(boolean playerOnly) {
        this.playerOnly = playerOnly;
    }

    public boolean isDefaultHelp() {
        return defaultHelp;
    }

    public void setDefaultHelp(boolean defaultHelp) {
        this.defaultHelp = defaultHelp;
    }

    public final Collection<SubCommand> getPermissibleSubCommands(Permissible permissible) {
        return this.subCommands.values().stream().filter(cmd -> permissible.hasPermission(cmd.getPermission())).collect(Collectors.toSet());
    }

    public Optional<SubCommand> getSubCommand(String name) {
        return Optional.ofNullable(this.subCommands.get(name));
    }

    public void run(CommandSender sender, String[] args) {
       if(args.length > 0) {
            String node = args[0];

//            if(node.equalsIgnoreCase("help")){
//                if(isDefaultHelp()){
//                    showHelp(sender);
//                }else{
//                    sender.sendMessage(ChatColor.RED + "There is no help page for command [" + getName() + "]");
//                }
//            }else{
                if (!getSubCommand(node).isPresent()) {
                    sender.sendMessage(ChatColor.RED + "Could not find arg matching that name.");
                    return;
                }

                getSubCommand(node).ifPresent(cmd -> {
                    if (this.permission.equals("") || sender.hasPermission(this.permission)) {
                        cmd.run(sender, args);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You are not allowed to do this!");
                    }
                });
            //}
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        if (this.matchesAlias(command.getName())) {
            if (this.playerOnly && !(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "You must be a player to execute this cmd.");
                return true;
            }

            run(sender, args);
        }
        return false;
    }

    private boolean matchesAlias(String str) {
        if (str == null) return false;
        if (str.equalsIgnoreCase("")) return false;

        if (this.name.equalsIgnoreCase(str)) return true;
        if (this.aliases != null && this.aliases.length > 0) {
            for (String ali : aliases) {
                if (ali.equalsIgnoreCase(str)) return true;
            }
        }

        return false;
    }

    public void sendUsage(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "Invalid arguments.");
        sender.sendMessage(ChatColor.RED + "/" + name + " " + getUsage());
    }

    protected void showHelp(CommandSender sender) {
        String fullName = getName();
        if (sender instanceof Player) {
            Player.Spigot player = ((Player) sender).spigot();
            BaseComponent[] title = new ComponentBuilder(
                    "Command Help: " + fullName + "\n")
                    .color(ChatColor.AQUA).underlined(true).create();
            player.sendMessage(title);
            this.getPermissibleSubCommands(sender).stream().sorted().forEach(
                    command -> player.sendMessage(
                            new ComponentBuilder("> ")
                                    .color(ChatColor.YELLOW)
                                    .bold(true)
                                    .event(new ClickEvent(
                                            ClickEvent.Action.RUN_COMMAND,
                                            fullName + command.getName()))
                                    .append(fullName + " " + command.getName())
                                    .color(ChatColor.AQUA)
                                    .event(new ClickEvent(
                                            ClickEvent.Action.SUGGEST_COMMAND,
                                            fullName + command.getName()))
                                    .create()));
        } else {
            this.getPermissibleSubCommands(sender).stream().sorted().forEach(
                    command -> sender.sendMessage("/" + fullName + " " + command.getName()));
        }
    }

    @Override
    public int compare(Command cmd1, Command cmd2) {
        return cmd1.getName().compareToIgnoreCase(cmd2.getName());
    }
}
