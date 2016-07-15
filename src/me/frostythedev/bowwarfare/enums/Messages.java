package me.frostythedev.bowwarfare.enums;

import me.frostythedev.bowwarfare.Files;
import me.frostythedev.bowwarfare.utils.Colors;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public enum Messages {
    ARENA_ALREADY_EXISTS("arena_already_exists", "&cAn arena with that name already exists."),
    ;

    private String path;
    private String value;

    Messages(String path, String value) {
        this.path = path;
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void load(JavaPlugin plugin) {
        save(plugin);
        Files files = new Files("lang.yml", plugin.getDataFolder());
        files.load();
        for (Messages messages : values()) {
            if (files.getString(path) != null) {
                messages.setValue(files.getString(path));
            }
        }
    }

    public void save(JavaPlugin plugin) {
        Files files = new Files("lang.yml", plugin.getDataFolder());
        files.load();
        for (Messages messages : values()) {
            if (files.getString(path) == null) {
                files.set(messages.path, messages.value);
            }
        }
        files.save();
    }

    public void send(CommandSender sender) {
        Colors.message(sender, value);
    }
}
