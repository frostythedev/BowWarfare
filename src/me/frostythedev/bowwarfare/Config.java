package me.frostythedev.bowwarfare;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    private static FileConfiguration config = BWPlugin.getInstance().getConfig();

    public static boolean SHOULD_DEBUG = config.getBoolean("debug", false);
            ;
    public static String STORAGE_MODE = config.getString("storage.mode", "yml");

    public static String ARENA_YML_FOLDER = config.getString("storage.yml.arena-folder-name", "arenas");
    public static String PLAYER_YML_FOLDER = config.getString("storage.yml.player-folder-name", "players");

    public static String MYSQL_HOSTNAME = config.getString("storage.sql.hostname");
    public static String MYSQL_DATABASE = config.getString("storage.sql.database");
    public static String MYSQL_PORT = config.getString("storage.sql.port");
    public static String MYSQL_USERNAME = config.getString("storage.sql.username");
    public static String MYSQL_PASSWORD = config.getString("storage.sql.password");

    public static boolean BACKUP_ENABLED = config.getBoolean("storage.backup.enable", false);
    public static String BACKUP_MODE = config.getString("storage.backup.mode", "yml");

    public static boolean PERKS_ENABLED = config.getBoolean("perks.enabled", true);
    public static boolean ITEM_SHOP_ENABLED = config.getBoolean("item-shop.enabled", true);

    public static boolean SCOUT_PERK_ENABLED = config.getBoolean("scout-perk.enabled", true);
    public static int SCOUT_PERK_COST = config.getInt("scout-perk.cost", 250);

    public static boolean UAV_PERK_ENABLED = config.getBoolean("uav-perk.enabled", true);
    public static int UAV_PERK_COST = config.getInt("uav-perk.cost", 250);

    public static int GAME_MAX_TIME = config.getInt("game.max-time", 300);
    public static int GAME_SCORED_REQUIRED_TO_WIN = config.getInt("game.score-required-to-win", 25);
    public static boolean GAME_SAFE_SPAWN_ENABLED = config.getBoolean("game.safe-spawn.enabled", true);
    public static int GAME_SAFE_SPAWN_DISTANCE = config.getInt("game.safe-spawn.distance", 10);
    public static boolean GAME_SAVE_INVENTORY_ENABLED = config.getBoolean("game.save-inventory.enabled", true);
    public static boolean GAME_FIREWORKS_ON_WIN = config.getBoolean("game.fireworks-on-win", true);
}
