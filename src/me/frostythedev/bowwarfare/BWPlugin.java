package me.frostythedev.bowwarfare;

import me.frostythedev.bowwarfare.arena.Arena;
import me.frostythedev.bowwarfare.arena.ArenaManager;
import me.frostythedev.bowwarfare.arena.adaptor.ArenaAdaptor;
import me.frostythedev.bowwarfare.arena.storage.ArenaSQLStorage;
import me.frostythedev.bowwarfare.arena.storage.ArenaYMLStorage;
import me.frostythedev.bowwarfare.commands.BWCommand;
import me.frostythedev.bowwarfare.listeners.general.ClickListener;
import me.frostythedev.bowwarfare.shop.ShopManager;
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
import me.frostythedev.bowwarfare.threads.tasks.ArenaQueryTask;
import me.frostythedev.bowwarfare.threads.tasks.PlayerArrowTask;
import me.frostythedev.bowwarfare.threads.tasks.SignUpdateTask;
import me.frostythedev.bowwarfare.threads.tasks.UAVUpdateTask;
import me.frostythedev.bowwarfare.utils.commands.*;
import me.frostythedev.bowwarfare.utils.json.JsonManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
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

    private PlayerArrowTask playerArrowTask;
    private UAVUpdateTask uavUpdateTask;

    private CommandsManager<CommandSender> commands;

    @Override
    public void onEnable() {
        instance = this;

        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            this.saveDefaultConfig();
        }

        JsonManager.getInstance().register(Arena.class, new ArenaAdaptor());
        JsonManager.getInstance().register(BWPlayer.class, new BWPlayerAdaptor(this));

        if (getConfig().getString("storage.mode").equalsIgnoreCase("sql")) {
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

        this.arenaManager.loadAllArenas();
        this.perkManager.register(new SpeedPerk());
        this.perkManager.register(new UAVPerk(this));

        new BWCommand(this);
        setupCommands();

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
        uavUpdateTask = new UAVUpdateTask(this);
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

    public void setupCommands() {
        this.commands = new CommandsManager<CommandSender>() {
            @Override
            public boolean hasPermission(CommandSender sender, String perm) {
                return sender instanceof ConsoleCommandSender || sender.hasPermission(perm);
            }
        };
        CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration(this, this.commands);
        cmdRegister.register(BWCommand.class);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        try {
            this.commands.execute(cmd.getName(), args, sender, sender);
        } catch (CommandPermissionsException e) {
            sender.sendMessage(ChatColor.RED + "You don't have permission.");
        } catch (MissingNestedCommandException e) {
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (CommandUsageException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (WrappedCommandException e) {
            if (e.getCause() instanceof NumberFormatException) {
                sender.sendMessage(ChatColor.RED + "Number expected, string received instead.");
            } else {
                sender.sendMessage(ChatColor.RED + "An error has occurred. See console.");
                e.printStackTrace();
            }
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }

        return true;
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

    public static BWPlugin getInstance() {
        return instance;
    }
}
