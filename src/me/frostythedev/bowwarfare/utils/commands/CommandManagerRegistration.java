package me.frostythedev.bowwarfare.utils.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Programmed by Tevin on 7/9/2016.
 */
public class CommandManagerRegistration extends CommandRegistration {
    protected CommandsManager<?> commands;

    public CommandManagerRegistration(Plugin plugin, CommandsManager<?> commands) {
        super(plugin);
        this.commands = commands;
    }

    public CommandManagerRegistration(Plugin plugin, CommandExecutor executor, CommandsManager<?> commands) {
        super(plugin, executor);
        this.commands = commands;
    }

    public CommandManagerRegistration(Plugin plugin, CommandExecutor executor, @Nullable TabCompleter completer, CommandsManager<?> commands) {
        super(plugin, executor, completer);
        this.commands = commands;
    }

    public boolean register(Class<?> clazz) {
        return registerAll(commands.registerAndReturn(clazz));
    }

    public boolean registerAll(List<Command> registered) {
        List<CommandInfo> toRegister = new ArrayList<CommandInfo>();
        for (Command command : registered) {
            String[] permissions = null;
            Method cmdMethod = commands.getMethods().get(null).get(command.aliases()[0]);
            if (cmdMethod != null) {
                if (cmdMethod.isAnnotationPresent(CommandPermissions.class)) {
                    permissions = cmdMethod.getAnnotation(CommandPermissions.class).value();
                }
            }

            toRegister.add(new CommandInfo(command.usage(), command.desc(), command.aliases(), commands, permissions));
        }

        return register(toRegister);
    }
}
