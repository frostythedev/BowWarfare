package me.frostythedev.bowwarfare.arena.storage;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.Files;
import me.frostythedev.bowwarfare.arena.Arena;
import me.frostythedev.bowwarfare.enums.StorageType;
import me.frostythedev.bowwarfare.storage.core.DataStorage;
import me.frostythedev.bowwarfare.utils.json.JsonManager;

import java.io.File;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public class ArenaYMLStorage extends DataStorage<Arena> {

    private BWPlugin plugin;
    private File dataFolder;

    public ArenaYMLStorage(BWPlugin plugin) {
        super(StorageType.YML);
        this.plugin = plugin;
    }

    @Override
    public void initialize() {
        this.dataFolder = new File(plugin.getDataFolder() + File.separator + plugin.getConfig().getString("storage.yml.arena-folder-name"));
        if (!this.dataFolder.exists()) {
            this.dataFolder.mkdir();
        }
    }

    @Override
    public Arena load(String name) {
        Files files = new Files(name + ".yml", dataFolder);
        if (!files.exists()) {
            return null;
        } else {
            files.load();
            Arena arena = (Arena) JsonManager.getInstance().deserialize(files.getString("data"), Arena.class);
            if (arena != null) {
                return arena;
            }
        }

        return null;
    }

    @Override
    public void save(Arena arena) {
        Files files = new Files(arena.getName() + ".yml", dataFolder);
        files.load();
        files.set("data", JsonManager.getInstance().serialize(arena));
        files.save();
    }
}
