package me.frostythedev.bowwarfare.arena.adaptor;

import com.google.gson.*;
import me.frostythedev.bowwarfare.arena.Arena;
import me.frostythedev.bowwarfare.arena.enums.ArenaState;
import me.frostythedev.bowwarfare.utils.Locations;
import me.frostythedev.bowwarfare.utils.json.JsonAdaptor;
import org.bukkit.Location;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public class ArenaAdaptor implements JsonAdaptor<Arena> {

    @Override
    public Arena deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        Location lobbypoint = Locations.fromString(jsonObject.get("lobbypoint").getAsString());
        int minPlayers = jsonObject.get("min").getAsInt();
        int maxPlayers = jsonObject.get("max").getAsInt();
        String spawnString = jsonObject.get("spawns").getAsString();
        List<Location> spawns = new ArrayList<>();
        if (!spawnString.equals("")) {
            String[] parts = spawnString.split("#");
            for (String str : parts) {
                Location loc = Locations.fromString(str);
                if (loc != null) {
                    spawns.add(loc);
                }
            }
        }

        return new Arena(name, lobbypoint, ArenaState.LOBBY, minPlayers, maxPlayers, spawns);
    }

    @Override
    public JsonElement serialize(Arena arena, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", arena.getName());
        jsonObject.addProperty("lobbypoint", Locations.toString(arena.getLobbypoint()));
        jsonObject.addProperty("min", arena.getMinPlayers());
        jsonObject.addProperty("max", arena.getMaxPlayers());

        String spawns = "";
        if (!arena.getSpawnpoints().isEmpty()) {
            for (Location loc : arena.getSpawnpoints()) {
                if (spawns.equals("")) {
                    spawns += Locations.toString(loc);
                } else {
                    spawns += "#" + Locations.toString(loc);
                }
            }
        }
        jsonObject.addProperty("spawns", spawns);

        return jsonObject;
    }
}
