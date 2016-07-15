package me.frostythedev.bowwarfare.players.storage;

import me.frostythedev.bowwarfare.callbacks.ArenaCallback;
import me.frostythedev.bowwarfare.callbacks.PlayerCallback;
import me.frostythedev.bowwarfare.enums.StorageType;
import me.frostythedev.bowwarfare.players.BWPlayer;
import me.frostythedev.bowwarfare.storage.core.DataStorage;
import me.frostythedev.bowwarfare.storage.mysql.Database;
import me.frostythedev.bowwarfare.utils.json.JsonManager;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public class PlayerSQLStorage extends DataStorage<BWPlayer>{

    public static final String TABLE_NAME = "bw_players";

    public PlayerSQLStorage() {
        super(StorageType.MYSQL);
    }

    @Override
    public void initialize() {
        Database.getInstance().createTable(TABLE_NAME, "id int NOT NULL PRIMARY KEY AUTO_INCREMENT, uuid VARCHAR(36), data TEXT");

    }

    @Override
    public BWPlayer load(String name) {
        PlayerCallback playerCallback = new PlayerCallback();
        Database.getInstance().syncQuery("SELECT data FROM " + TABLE_NAME + " WHERE uuid=?",
                new Object[]{name}, playerCallback);
        return playerCallback.result();
    }

    @Override
    public void save(BWPlayer player) {
        if (load(player.getUuid().toString()) == null) {
            Database.getInstance().syncUpdate("INSERT INTO " + TABLE_NAME + "(uuid,data) VALUES (?,?)",
                    new Object[]{player.getUuid().toString(), JsonManager.getInstance().serialize(player)});
        } else {
            Database.getInstance().syncUpdate("UPDATE " + TABLE_NAME + " SET data=? WHERE uuid=?",
                    new Object[]{JsonManager.getInstance().serialize(player), player.getUuid().toString()});
        }
    }
}
