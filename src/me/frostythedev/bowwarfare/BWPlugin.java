package me.frostythedev.bowwarfare;

import me.frostythedev.bowwarfare.arena.Arena;
import me.frostythedev.bowwarfare.arena.ArenaManager;
import me.frostythedev.bowwarfare.arena.adaptor.ArenaAdaptor;
import me.frostythedev.bowwarfare.arena.storage.ArenaSQLStorage;
import me.frostythedev.bowwarfare.arena.storage.ArenaYMLStorage;
import me.frostythedev.bowwarfare.cmds.BWCommand;
import me.frostythedev.bowwarfare.inventory.InventoryManager;
import me.frostythedev.bowwarfare.listeners.general.ClickListener;
import me.frostythedev.bowwarfare.scoreboard.ScoreboardLib;
import me.frostythedev.bowwarfare.shop.manager.ShopManager;
import me.frostythedev.bowwarfare.utils.Locations;
import me.frostythedev.bowwarfare.utils.gui.MenuListener;
import me.frostythedev.bowwarfare.listeners.game.DeathListener;
import me.frostythedev.bowwarfare.listeners.game.GameListener;
import me.frostythedev.bowwarfare.listeners.game.ShootListener;
import me.frostythedev.bowwarfare.listeners.general.PlayerJoinListener;
import me.frostythedev.bowwarfare.listeners.general.SignChangeListener;
import me.frostythedev.bowwarfare.perks.PerkManager;
import me.frostythedev.bowwarfare.perks.types.slot1.SpeedPerk;
import me.frostythedev.bowwarfare.perks.types.slot1.UAVPerk;
import me.frostythedev.bowwarfare.players.BWPlayer;
import me.frostythedev.bowwarfare.players.PlayerManager;
import me.frostythedev.bowwarfare.players.adaptor.BWPlayerAdaptor;
import me.frostythedev.bowwarfare.players.storage.PlayerSQLStorage;
import me.frostythedev.bowwarfare.players.storage.PlayerYMLStorage;
import me.frostythedev.bowwarfare.storage.core.DataStorage;
import me.frostythedev.bowwarfare.threads.RunnableManager;
import me.frostythedev.bowwarfare.threads.tasks.arena.ArenaQueryTask;
import me.frostythedev.bowwarfare.threads.tasks.perks.PlayerArrowTask;
import me.frostythedev.bowwarfare.threads.tasks.SignUpdateTask;
import me.frostythedev.bowwarfare.threads.tasks.perks.UAVUpdateTask;
import me.frostythedev.bowwarfare.utils.json.JsonManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Optional;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public class BWPlugin extends JavaPlugin {

    private static BWPlugin instance;
    public static final String PREFIX = "&4[&2BowWarfare&4] &r";

    private DataStorage<Arena> arenaDataStorage;
    private DataStorage<BWPlayer> playerDataStorage;

    private ArenaManager arenaManager;
    private PlayerManager playerManager;
    private RunnableManager runnableManager;
    private PerkManager perkManager;
    private ShopManager shopManager;
    private InventoryManager inventoryManager;

    private PlayerArrowTask playerArrowTask;
    private UAVUpdateTask uavUpdateTask;

    @Override
    public void onEnable() {
        instance = this;

        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            this.saveDefaultConfig();
        }

        JsonManager.getInstance().register(Arena.class, new ArenaAdaptor());
        JsonManager.getInstance().register(BWPlayer.class, new BWPlayerAdaptor(this));

        if (Config.STORAGE_MODE.equalsIgnoreCase("sql")) {
            this.arenaDataStorage = new ArenaSQLStorage();
            this.playerDataStorage = new PlayerSQLStorage();
        } else {
            this.arenaDataStorage = new ArenaYMLStorage(this);
            this.playerDataStorage = new PlayerYMLStorage(this);
        }

        this.arenaDataStorage.initialize();
        this.playerDataStorage.initialize();

        this.arenaManager = new ArenaManager(this);
        this.playerManager = new PlayerManager(this);
        this.runnableManager = new RunnableManager(this);
        this.perkManager = new PerkManager(this);
        this.shopManager = new ShopManager(this);
        this.inventoryManager = new InventoryManager();

        this.arenaManager.loadAllArenas();

       if(Config.PERKS_ENABLED){
           if(Config.SCOUT_PERK_ENABLED){
               this.perkManager.register(new SpeedPerk());
           }
           if(Config.UAV_PERK_ENABLED){
               this.perkManager.register(new UAVPerk(this));
               uavUpdateTask = new UAVUpdateTask(this);
           }
       }

        this.getServer().getPluginCommand("bowwarfare").setExecutor(new BWCommand(this));

        this.getServer().getPluginManager().registerEvents(new MenuListener(), this);
        this.getServer().getPluginManager().registerEvents(new ClickListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        this.getServer().getPluginManager().registerEvents(new SignChangeListener(this), this);
        this.getServer().getPluginManager().registerEvents(new GameListener(this), this);
        this.getServer().getPluginManager().registerEvents(new DeathListener(this), this);
        this.getServer().getPluginManager().registerEvents(new ShootListener(this), this);

        new SignUpdateTask(this);
        new ArenaQueryTask(this);

        playerArrowTask = new PlayerArrowTask(this);

        ScoreboardLib.setPluginInstance(this);
    }

    @Override
    public void onDisable() {

    }

    public Optional<Arena> isInGame(Player player) {
        if (getArenaManager().getArena(player) != null) {
            return Optional.of(getArenaManager().getArena(player));
        }
        return Optional.empty();
    }

    public Location getLobbyLocation() {
        return Locations.fromString(getConfig().getString("lobby-location"));
    }

    public DataStorage<Arena> getArenaDataStorage() {
        return arenaDataStorage;
    }

    public DataStorage<BWPlayer> getPlayerDataStorage() {
        return playerDataStorage;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public RunnableManager getRunnableManager() {
        return runnableManager;
    }

    public PerkManager getPerkManager() {
        return perkManager;
    }

    public ShopManager getShopManager() {
        return shopManager;
    }

    public PlayerArrowTask getPlayerArrowTask() {
        return playerArrowTask;
    }

    public UAVUpdateTask getUavUpdateTask() {
        return uavUpdateTask;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public static BWPlugin getInstance() {
        return instance;
    }
}
