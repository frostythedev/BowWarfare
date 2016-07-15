package me.frostythedev.bowwarfare.players.storage;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.Files;
import me.frostythedev.bowwarfare.enums.StorageType;
import me.frostythedev.bowwarfare.players.BWPlayer;
import me.frostythedev.bowwarfare.storage.core.DataStorage;
import me.frostythedev.bowwarfare.utils.json.JsonManager;

import java.io.File;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public class PlayerYMLStorage extends DataStorage<BWPlayer>{

    private BWPlugin plugin;
    private File dataFolder;

    public PlayerYMLStorage(BWPlugin plugin) {
        super(StorageType.YML);
        this.plugin = plugin;
    }

    @Override
    public void initialize() {
        this.dataFolder = new File(plugin.getDataFolder() + File.separator + plugin.getConfig().getString("storage.yml.player-folder-name"));
        if (!this.dataFolder.exists()) {
            this.dataFolder.mkdir();
        }
    }

    @Override
    public BWPlayer load(String name) {
        Files files = new Files(name + ".yml", dataFolder);
        if (!files.exists()) {
            return null;
        } else {
            files.load();
            BWPlayer player = (BWPlayer) JsonManager.getInstance().deserialize(files.getString("data"), BWPlayer.class);
            if (player != null) {
                return player;
            }
        }

        return null;
    }

    @Override
    public void save(BWPlayer player) {
        Files files = new Files(player.getUuid().toString() + ".yml", dataFolder);
        files.load();
        files.set("data", JsonManager.getInstance().serialize(player));
        files.save();
    }
}
