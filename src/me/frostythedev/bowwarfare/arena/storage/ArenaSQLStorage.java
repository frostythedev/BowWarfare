package me.frostythedev.bowwarfare.arena.storage;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.Files;
import me.frostythedev.bowwarfare.arena.Arena;
import me.frostythedev.bowwarfare.callbacks.ArenaCallback;
import me.frostythedev.bowwarfare.enums.StorageType;
import me.frostythedev.bowwarfare.storage.core.DataStorage;
import me.frostythedev.bowwarfare.storage.mysql.Database;
import me.frostythedev.bowwarfare.utils.json.JsonManager;

import java.io.File;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public class ArenaSQLStorage extends DataStorage<Arena> {

    public static final String TABLE_NAME = "bw_arenas";

    public ArenaSQLStorage() {
        super(StorageType.MYSQL);
    }

    @Override
    public void initialize() {
        Database.getInstance().createTable(TABLE_NAME, "id int NOT NULL PRIMARY KEY AUTO_INCREMENT, name TEXT, data TEXT");
    }

    @Override
    public Arena load(String name) {
        ArenaCallback arenaCallback = new ArenaCallback();
        Database.getInstance().syncQuery("SELECT data FROM " + TABLE_NAME + " WHERE name=?",
                new Object[]{name}, arenaCallback);
        return arenaCallback.result();
    }

    @Override
    public void save(Arena arena) {
        if (load(arena.getName()) == null) {
            Database.getInstance().syncUpdate("INSERT INTO " + TABLE_NAME + "(name,data) VALUES (?,?)",
                    new Object[]{arena.getName(), JsonManager.getInstance().serialize(arena)});
        } else {
            Database.getInstance().syncUpdate("UPDATE " + TABLE_NAME + " SET data=? WHERE name=?",
                    new Object[]{JsonManager.getInstance().serialize(arena), arena.getName()});
        }
    }
}
