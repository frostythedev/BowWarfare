package me.frostythedev.bowwarfare.arena;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.arena.enums.ArenaState;
import me.frostythedev.bowwarfare.arena.storage.ArenaSQLStorage;
import me.frostythedev.bowwarfare.callbacks.ArenaGatherCallback;
import me.frostythedev.bowwarfare.enums.StorageType;
import me.frostythedev.bowwarfare.storage.mysql.Database;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.Main;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public class ArenaManager {

    private BWPlugin plugin;
    private Map<String, Arena> loadedArenas;

    public ArenaManager(BWPlugin plugin) {
        this.plugin = plugin;
        this.loadedArenas = new HashMap<>();
    }

    public Set<String> getArenaNames(){
        return loadedArenas.keySet();
    }

    public Set<Arena> getAvailableArenas(){
        Set<Arena> arenas = new HashSet<>();
        for(Arena arena : loadedArenas.values()){
            if(arena.getArenaState().equals(ArenaState.LOBBY)){
                arenas.add(arena);
            }
        }
        return arenas;
    }

    public Arena createArena(String name, Location lobbypoint, int min, int max, List<Location> spawnpoints) {
        if (getArena(name) != null) {
            return getArena(name);
        } else {
            Arena arena = new Arena(name, lobbypoint, ArenaState.LOBBY, min, max, spawnpoints);
            saveArena(arena);
            loadedArenas.put(name, arena);
            return arena;
        }
    }

    public void loadAllArenas() {
        this.loadedArenas.clear();
        if (plugin.getArenaDataStorage().getStorageType().equals(StorageType.MYSQL)) {
            ArenaGatherCallback arenaGatherCallback = new ArenaGatherCallback();
            Database.getInstance().syncQuery("SELECT * FROM " + ArenaSQLStorage.TABLE_NAME, null, arenaGatherCallback);
            if (!arenaGatherCallback.result().isEmpty()) {
                arenaGatherCallback.result().forEach(a -> loadedArenas.put(a.getName(), a));
            }
        } else {
            File[] files = new File(plugin.getDataFolder() + File.separator + "arenas").listFiles();
            if (files != null) {
                if (files.length > 0) {
                    for (File file : files) {
                        Arena arena = plugin.getArenaDataStorage().load(file.getName().replace(".yml", ""));
                        if (arena != null) {
                            loadedArenas.put(arena.getName(), arena);
                        }
                    }
                }
            }
        }
    }

    public void saveArena(Arena arena) {
        plugin.getArenaDataStorage().save(arena);
    }

    public boolean deleteArena(String name) {
        if (getArena(name) != null) {
            if (plugin.getArenaDataStorage().getStorageType().equals(StorageType.MYSQL)) {
                Database.getInstance().syncUpdate("DELETE FROM " + ArenaSQLStorage.TABLE_NAME + " WHERE name=?",
                        new Object[]{name});
                loadedArenas.remove(name);
                return true;
            } else {
                File file = new File(plugin.getDataFolder() + File.separator + "arenas", name + ".yml");
                if (file.delete()) {
                    loadedArenas.remove(name);
                    return true;
                }
            }
        }
        return false;
    }

    public AddResult addSpawn(Arena arena, Location location) {
        if (arena.getSpawnpoints().contains(location)) {
            return AddResult.ALREADY_ADDED;
        } else {
            arena.getSpawnpoints().add(location);
            if (arena.getSpawnpoints().size() >= arena.getMaxPlayers()) {
                return AddResult.EXCEEDED;
            } else {
                return AddResult.INSUFFICIENT;
            }
        }
    }

    public Arena getArena(String name) {
        if (loadedArenas.containsKey(name)) {
            return loadedArenas.get(name);
        }
        return null;
    }

    public Arena getArena(Player player){
        for(Arena arena : loadedArenas.values()){
            if(arena.contains(player)){
                return arena;
            }
        }
        return null;
    }

    public Map<String, Arena> getLoadedArenas() {
        return loadedArenas;
    }

    public enum AddResult {
        INSUFFICIENT,
        EXCEEDED,
        ALREADY_ADDED
    }
}
