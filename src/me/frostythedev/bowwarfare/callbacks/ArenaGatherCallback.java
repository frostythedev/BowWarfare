package me.frostythedev.bowwarfare.callbacks;

import me.frostythedev.bowwarfare.arena.Arena;
import me.frostythedev.bowwarfare.storage.mysql.Callback;
import me.frostythedev.bowwarfare.utils.json.JsonManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public class ArenaGatherCallback implements Callback<List<Arena>> {

    private List<Arena> arenas;

    @Override
    public void read(ResultSet rs) throws SQLException {
        List<Arena> arenas = new ArrayList<>();
        while (rs.next()) {
            Arena arena = (Arena) JsonManager.getInstance().deserialize(rs.getString("data"), Arena.class);
            if (arena != null) {
                arenas.add(arena);
            }
        }
        this.arenas = arenas;
    }

    @Override
    public void digestSync() {

    }

    @Override
    public void digestAsync() {

    }

    @Override
    public List<Arena> result() {
        return arenas;
    }
}
