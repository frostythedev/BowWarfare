package me.frostythedev.bowwarfare.cmds;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.cmds.subs.*;
import me.frostythedev.bowwarfare.utils.Colors;
import me.frostythedev.frostengine.bukkit.cmd.Command;
import org.bukkit.command.CommandSender;

public class BWCommand extends Command {

    public BWCommand(BWPlugin plugin) {
        super("bowwarfare");

        this.addSubCommand(new CreateCmd(plugin));
        this.addSubCommand(new DeleteCmd(plugin));
        this.addSubCommand(new AddSpawnCmd(plugin));
        this.addSubCommand(new RemoveSpawnCmd(plugin));
        this.addSubCommand(new StatsCmd(plugin));
        this.addSubCommand(new CoinsCmd(plugin));
        this.addSubCommand(new SetLobbyCmd(plugin));
        this.addSubCommand(new LeaveCmd(plugin));
        this.addSubCommand(new ShopCmd(plugin));
        this.addSubCommand(new PerksCmd(plugin));
        this.addSubCommand(new ListCmd(plugin));
        this.addSubCommand(new DisableCmd(plugin));
        this.addSubCommand(new InfoCmd(plugin));
    }

    private static final String[] CMD_HELP = new String[]{"&7<> - Required | [] - Optional" +
            "\n&e/bw &7- Shows this page" +
            "\n&e/bw &ccreate <name> [min] [max] &7- Creates an arena with those properties" +
            "\n&e/bw &cdelete <name> &7- Deletes an arena with that name" +
            "\n&e/bw &caddspawn <name> &7- Adds a spawn to the arena with that name" +
            "\n&e/bw &cremovespawn <name> &7- Removes a spawn from an arena with that name" +
            "\n&e/bw &cstats [player] &7- Check the stats of a player" +
            "\n&e/bw &ccoins [player] &7- Check the coins of that player" +
            "\n&e/bw &csetlobby &7- Sets the main lobby for all games" +
            "\n&e/bw &cleave &7- Leaves your current arena" +
            "\n&e/bw &cshop &7- Opens the ingame shop to allow you to buy weapons" +
            "\n&e/bw &cperks &7- Check and select your ingame perks" +
            "\n&e/bw &clist &7- List the names of all currently loaded arenas" +
            "\n&e/bw &cdisable &7- Disables the plugin" +
            "\n&e/bw &cinfo &7- Shows information about the plugin" +
            ""};

    @Override
    public void run(CommandSender sender, String[] args) {
        if (args.length == 0) {
            Colors.message(sender, "&a=====[ &2&lPage 1/1 &a]======");
            for (String str : CMD_HELP) {
                Colors.sendMessage(sender, str);
            }
        } else {
            super.run(sender, args);
        }
    }

//    abstract class BWSubCommand extends SubCommand {
//
//        private BWPlugin plugin;
//
//        public BWSubCommand(BWPlugin plugin, String name, String permission) {
//            super(name, permission);
//            this.plugin = plugin;
//        }
//
//        public BWPlugin getPlugin() {
//            return plugin;
//        }
//    }
//
//    class CreateCommand extends BWSubCommand {
//
//        public CreateCommand(BWPlugin plugin) {
//            super(plugin, "create", "bw.arena.create");
//        }
//
//        @Override
//        public void run(CommandSender sender, String[] args) {
//            if (sender instanceof Player) {
//                Player player = (Player) sender;
//                if (args.length >= 2) {
//                    String name = args[1];
//                    int min = 4;
//                    int max = 24;
//                    try {
//                        min = Integer.valueOf(args[2]);
//                        max = Integer.valueOf(args[3]);
//                    } catch (NumberFormatException e) {
//                        e.printStackTrace();
//                    }
//
//                    if (getPlugin().getArenaManager().getArena(name) != null) {
//                        Messages.ARENA_ALREADY_EXISTS.send(sender);
//                    } else {
//                        Arena arena = getPlugin().getArenaManager().createArena(name, player.getLocation(), min, max, new ArrayList<>());
//                        Colors.message(sender, "&aYou have created an arena with name '" + arena.getName() + "'");
//                    }
//                }
//            }
//        }
//    }
//
//    class DeleteCommand extends BWSubCommand {
//
//        public DeleteCommand(BWPlugin plugin) {
//            super(plugin, "delete", "bw.arena.delete");
//        }
//
//        @Override
//        public void run(CommandSender sender, String[] args) {
//            if (sender instanceof Player) {
//                if (args.length >= 2) {
//                    String name = args[0];
//                    if (getPlugin().getArenaManager().deleteArena(name)) {
//                        Colors.message(sender, "&aYou have deleted the arena with name '" + name + "'");
//                    } else {
//                        Colors.message(sender, "&cAn error occurred while deleting arena with name '" + name + "'");
//                    }
//                }
//            }
//        }
//    }
//
//    class AddSpawnCommand extends BWSubCommand {
//
//        public AddSpawnCommand(BWPlugin plugin) {
//            super(plugin, "addspawn", "bw.arena.addspawn");
//        }
//
//        @Override
//        public void run(CommandSender sender, String[] args) {
//            if (sender instanceof Player) {
//                Player player = (Player) sender;
//                if (args.length >= 2) {
//                    String name = args[0];
//
//                    Arena arena = getPlugin().getArenaManager().getArena(name);
//                    if (arena == null) {
//                        Colors.message(sender, "&cCould not find an arena with that specified name '" + name + "'");
//                    } else {
//
//                        if (!getPlugin().getArenaManager().addSpawn(arena, player.getLocation())) {
//                            Colors.message(sender, "&cThis location has already been registered as a spawnpoint.");
//                        } else {
//                            getPlugin().getArenaManager().saveArena(arena);
//                            Colors.message(sender, "&aSpawnpoint added! Size: &6" + arena.getSpawnpoints().size());
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    class RemoveSpawnCommand extends BWSubCommand {
//
//        public RemoveSpawnCommand(BWPlugin plugin) {
//            super(plugin, "removespawn", "bw.arena.removespawn");
//        }
//
//        @Override
//        public void run(CommandSender sender, String[] args) {
//            if (sender instanceof Player) {
//                Player player = (Player) sender;
//                if (args.length >= 2) {
//                    String name = args[0];
//
//                    Arena arena = getPlugin().getArenaManager().getArena(name);
//                    if (arena == null) {
//                        Colors.message(sender, "&cCould not find an arena with that specified name '" + name + "'");
//                    } else {
//                        if (!getPlugin().getArenaManager().removeSpawn(arena, player.getLocation())) {
//                            Colors.message(sender, "&cThis location has not been registered as a spawnpoint.");
//                        } else {
//                            Colors.message(sender, "&eSpawnpoint removed! Size: &6" + arena.getSpawnpoints().size());
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    class StatsCommand extends BWSubCommand {
//
//        public StatsCommand(BWPlugin plugin) {
//            super(plugin, "stats", "bw.user.stats");
//        }
//
//        @Override
//        public void run(CommandSender sender, String[] args) {
//            if (sender instanceof Player) {
//                BWPlayer bwPlayer = getPlugin().getPlayerManager().getPlayer(Bukkit.getPlayer(args[0]));
//                if (bwPlayer == null) {
//                    Colors.message(sender, "&cCould not find stats for a player with the name '" + args[0] + "'");
//                } else {
//                    getPlugin().getPlayerManager().displayStats(sender, bwPlayer);
//                }
//            }
//        }
//    }
//
//    class CoinsCommand extends BWSubCommand {
//
//        public CoinsCommand(BWPlugin plugin) {
//            super(plugin, "coins", "bw.user.coins");
//        }
//
//        @Override
//        public void run(CommandSender sender, String[] args) {
//            if (sender instanceof Player) {
//                BWPlayer bwPlayer = getPlugin().getPlayerManager().getPlayer(Bukkit.getPlayer(args[0]));
//                if (bwPlayer == null) {
//                    Colors.message(sender, "&cCould not find coins for a player with the name '" + args[0] + "'");
//                } else {
//                    Colors.message(sender, "&e" + args[0] + " has &6" + bwPlayer.getCoins() + " &ecoins.");
//                }
//            }
//        }
//    }
//
//    class SetLobbyCommand extends BWSubCommand {
//
//        public SetLobbyCommand(BWPlugin plugin) {
//            super(plugin, "setlobby", "bw.admin.setlobby");
//        }
//
//        @Override
//        public void run(CommandSender sender, String[] args) {
//            if (sender instanceof Player) {
//                Player player = (Player) sender;
//                getPlugin().getConfig().set("lobby-location", Locations.toString(player.getLocation()));
//                getPlugin().saveConfig();
//                Colors.message(player, "&aYou have set the &bglobal &alobby location to your feet.");
//            }
//        }
//    }
//
//    class LeaveCommand extends BWSubCommand {
//
//        public LeaveCommand(BWPlugin plugin) {
//            super(plugin, "leave", "bw.user.leave");
//        }
//
//        @Override
//        public void run(CommandSender sender, String[] args) {
//            if (sender instanceof Player) {
//                Player player = (Player) sender;
//                if (getPlugin().getArenaManager().getArena(player) != null) {
//                    getPlugin().getArenaManager().getArena(player).removePlayer(player);
//                    Colors.message(sender, "&bYou have left the arena you were previously in.");
//                    ScoreboardAPI.getInstance().setScoreboard(player, null);
//
//                    //TODO Restore inventories
//                    player.getInventory().clear();
//                    player.getInventory().setArmorContents(null);
//
//                    if (getPlugin().getLobbyLocation() != null) {
//                        player.teleport(getPlugin().getLobbyLocation());
//                    }
//                } else {
//                    Colors.message(sender, "&cYou are currently not in an arena.");
//                }
//            }
//        }
//    }
//
//    class PerksCommand extends BWSubCommand {
//
//        public PerksCommand(BWPlugin plugin) {
//            super(plugin, "perks", "bw.user.perks");
//        }
//
//        @Override
//        public void run(CommandSender sender, String[] args) {
//            if (sender instanceof Player) {
//                Player player = (Player) sender;
//                if (getPlugin().getArenaManager().getArena(player) != null) {
//                    getPlugin().isInGame(player).ifPresent(arena -> {
//                        if (!arena.getArenaState().equals(ArenaState.LOBBY)) {
//                            Colors.message(sender, "&cYou cannot change perks while in a game please leave and try again.");
//                        } else {
//                            getPlugin().getPerkManager().openSelectorMenu(player);
//                        }
//                    });
//                } else {
//                    getPlugin().getPerkManager().openSelectorMenu(player);
//                }
//            }
//        }
//    }
//
//    class ShopCommand extends BWSubCommand {
//
//        public ShopCommand(BWPlugin plugin) {
//            super(plugin, "shop", "bw.user.shop");
//        }
//
//        @Override
//        public void run(CommandSender sender, String[] args) {
//            if (sender instanceof Player) {
//                Player player = (Player) sender;
//                if (getPlugin().getArenaManager().getArena(player) == null) {
//                    Colors.message(sender, "&cYou must be in a game to access the shop.");
//                } else {
//                    getPlugin().isInGame(player).ifPresent(arena -> {
//                        if (!arena.getArenaState().equals(ArenaState.LOBBY)) {
//                            getPlugin().getShopManager().openGameShop(player);
//                        } else {
//                            Colors.message(sender, "&cYou must be in a game to access the shop.");
//                        }
//                    });
//                }
//            }
//        }
//    }
//
//    class ListCommand extends BWSubCommand {
//
//        public ListCommand(BWPlugin plugin) {
//            super(plugin, "list", "bw.admin.list");
//        }
//
//        @Override
//        public void run(CommandSender sender, String[] args) {
//            if (sender instanceof Player) {
//                if (getPlugin().getArenaManager().getArenaNames().isEmpty()) {
//                    Colors.message(sender, "&cNo arenas have been loaded.");
//                } else {
//                    Colors.message(sender, "&a=====[ &2&lLoaded Arenas &a&l]=====");
//                    getPlugin().getArenaManager().getArenaNames().forEach(str -> Colors.sendMessage(sender, "&e" + str));
//                }
//            }
//        }
//    }
//
//    class DisableCommand extends BWSubCommand {
//
//        public DisableCommand(BWPlugin plugin) {
//            super(plugin, "disable", "bw.admin.disable");
//        }
//
//        @Override
//        public void run(CommandSender sender, String[] args) {
//            getPlugin().getServer().getPluginManager().disablePlugin(getPlugin());
//            Colors.message(sender, "&4Plugin has been disabled.");
//        }
//    }
//
//    class InfoCommand extends BWSubCommand {
//
//        public InfoCommand(BWPlugin plugin) {
//            super(plugin, "info", "");
//        }
//
//        @Override
//        public void run(CommandSender sender, String[] args) {
//            Colors.message(sender, "&a=====[ &2&lInformation &a&l]=====");
//            Colors.sendMessage(sender, "&dAuthor: &bfrostythedev");
//            Colors.sendMessage(sender, "&dOriginal Plugin: &bXxLeetGamerxX");
//            Colors.sendMessage(sender, "&dLast Updated: &b13/7/16");
//            Colors.sendMessage(sender, "&dIn Partnership: &bVisionCoding LLC {c}");
//        }
//    }
}
