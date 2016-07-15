package me.frostythedev.bowwarfare.callbacks;

import me.frostythedev.bowwarfare.arena.Arena;
import me.frostythedev.bowwarfare.players.BWPlayer;
import me.frostythedev.bowwarfare.storage.mysql.Callback;
import me.frostythedev.bowwarfare.utils.json.JsonManager;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public class PlayerCallback implements Callback<BWPlayer>{

    private BWPlayer player;

    @Override
    public void read(ResultSet rs) throws SQLException {
        if(rs.next()){
            this.player = (BWPlayer) JsonManager.getInstance().deserialize(rs.getString("data"), BWPlayer.class);
        }
    }

    @Override
    public void digestSync() {

    }

    @Override
    public void digestAsync() {

    }

    @Override
    public BWPlayer result() {
        return player;
    }
}
