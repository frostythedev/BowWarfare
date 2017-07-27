package me.frostythedev.bowwarfare.arena;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.Config;
import me.frostythedev.bowwarfare.arena.enums.ArenaState;
import me.frostythedev.bowwarfare.arena.enums.Placement;
import me.frostythedev.bowwarfare.scoreboard.ScoreboardAPI;
import me.frostythedev.bowwarfare.utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public class Arena {

    private String name;
    private Location lobbypoint;
    private ArenaState arenaState;
    private int minPlayers, maxPlayers;
    private List<Location> spawnpoints;
    private List<UUID> players;
    private Map<UUID, Integer> scores;
    private Map<Placement, String> placements;

    public Arena(String name, Location lobbypoint, ArenaState arenaState, int minPlayers, int maxPlayers, List<Location> spawnpoints) {
        this.name = name;
        this.lobbypoint = lobbypoint;
        this.arenaState = arenaState;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.spawnpoints = spawnpoints;
        this.players = new ArrayList<>();
        this.scores = new HashMap<>();
        this.placements = new HashMap<>();
    }

    public Set<Player> getPhysicalPlayers() {
        Set<Player> players = new HashSet<>();
        for (UUID uuid : this.players) {
            if (Bukkit.getPlayer(uuid) != null && Bukkit.getPlayer(uuid).isOnline()) {
                players.add(Bukkit.getPlayer(uuid));
            }
        }
        return players;
    }

    public boolean contains(Player player) {
        return players.contains(player.getUniqueId());
    }

    public boolean addPlayer(Player player) {
        return !contains(player) && players.add(player.getUniqueId());
    }

    public boolean removePlayer(Player player) {
        return contains(player) && players.remove(player.getUniqueId());
    }

    public int getSize() {
        return players.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLobbypoint() {
        return lobbypoint;
    }

    public void setLobbypoint(Location lobbypoint) {
        this.lobbypoint = lobbypoint;
    }

    public ArenaState getArenaState() {
        return arenaState;
    }

    public void setArenaState(ArenaState arenaState) {
        this.arenaState = arenaState;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public List<Location> getSpawnpoints() {
        return spawnpoints;
    }

    public void setSpawnpoints(List<Location> spawnpoints) {
        this.spawnpoints = spawnpoints;
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public void addScore(UUID uuid, int amount) {
        if (this.scores.containsKey(uuid)) {
            int total = this.scores.get(uuid) + amount;
            this.scores.replace(uuid, total);
        } else {
            this.scores.put(uuid, amount);
        }
    }

    public int getScore(UUID uuid) {
        if (this.scores.containsKey(uuid)) {
            return this.scores.get(uuid);
        }
        return 0;
    }

    public int getHighestScore() {
        int highest = 0;
        for (int i : scores.values()) {
            if (i > highest) {
                highest = i;
            }
        }
        return highest;
    }

    public TreeMap<UUID, Integer> getOrdered() {
        OrderComparator orderComparator = new OrderComparator(scores);
        TreeMap<UUID, Integer> ordered = new TreeMap<>(orderComparator);
        ordered.putAll(scores);
        return ordered;
    }

    public void endGame() {
        setArenaState(ArenaState.ENDED);

        for(Player ps : getPhysicalPlayers()){
            ScoreboardAPI.getInstance().setScoreboard(ps, null);
        }

        Object[] objects = getOrdered().keySet().toArray();

        if (objects.length >= 1) {
            setPlace(Placement.FIRST, Bukkit.getPlayer(UUID.fromString(String.valueOf(objects[0]))).getName());
        }
        if (objects.length >= 2) {
            setPlace(Placement.SECOND, Bukkit.getPlayer(UUID.fromString(String.valueOf(objects[1]))).getName());
        }
        if (objects.length >= 3) {
            setPlace(Placement.THIRD, Bukkit.getPlayer(UUID.fromString(String.valueOf(objects[2]))).getName());
        }

        broadcast("&e-------------------------------");
        broadcast("&c&lThe Game has Ended!");
        broadcast(" ");
        broadcast("&6&lWinners: ");
        for (Placement placement : Placement.values()) {
            if (placements.containsKey(placement)) {
                broadcast("&e&l" + placement.getPlacement() + "&r&7- &b" + placements.get(placement));
            }
        }
        broadcast("&e-------------------------------");

        for (Player ps : getPhysicalPlayers()) {

            //TODO Fireworks
            Colors.sendMessage(ps, "(&e-------------------------------");
            Colors.sendMessage(ps, "&c&lYour Stats: ");
            Colors.sendMessage(ps, "&cKills: " + getScore(ps.getUniqueId()));
            Colors.sendMessage(ps, "&cPlace: &6&l" + (getPlace(ps.getName()).isPresent() ? getPlace(ps.getName()).get().getPlacement() : "N/A"));
            ps.getInventory().clear();
            ps.getInventory().setArmorContents(null);

            if(Config.GAME_SAVE_INVENTORY_ENABLED){
                BWPlugin.getInstance().getInventoryManager().restoreInventory(ps);
            }

            if(BWPlugin.getInstance().getLobbyLocation() != null){
                ps.teleport(BWPlugin.getInstance().getLobbyLocation());
            }
        }

        reset();
    }

    public void setPlace(Placement place, String winner) {
        this.placements.putIfAbsent(place, winner);
    }

    public Optional<Placement> getPlace(String winner) {
        for (Placement placement : placements.keySet()) {
            if (placements.get(placement).equalsIgnoreCase(winner)) {
                return Optional.of(placement);
            }
        }
        return Optional.empty();
    }

    public void reset() {
        setArenaState(ArenaState.RESTARTING);
        this.scores.clear();
        this.placements.clear();
        getPhysicalPlayers().forEach(this::removePlayer);
        setArenaState(ArenaState.LOBBY);
    }

    public void broadcast(String message) {
        for (Player ps : getPhysicalPlayers()) {
            Colors.sendMessage(ps, message);
        }
    }

    public Location getRandomSpawn(boolean safe) {
        Location found;
        do {
            Location good = null;
            for (Player ps : getPhysicalPlayers()) {
                Location loc = spawnpoints.get(ThreadLocalRandom.current().nextInt(spawnpoints.size()));
                if (safe) {
                    if (loc.distance(ps.getLocation()) >= Config.GAME_SAFE_SPAWN_DISTANCE) {
                        good = loc;
                    }
                    break;
                } else {
                    good = loc;
                }
            }
            found = good;
        } while (found == null);
        return found;
    }


    class OrderComparator implements Comparator<UUID> {

        private Map<UUID, Integer> input;

        public OrderComparator(Map<UUID, Integer> input) {
            this.input = input;
        }

        @Override
        public int compare(UUID a, UUID b) {
            return input.get(a) - input.get(b);
        }
    }
}
