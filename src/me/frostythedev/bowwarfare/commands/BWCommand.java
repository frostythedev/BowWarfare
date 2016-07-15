package me.frostythedev.bowwarfare.commands;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.arena.Arena;
import me.frostythedev.bowwarfare.arena.ArenaManager;
import me.frostythedev.bowwarfare.arena.enums.ArenaState;
import me.frostythedev.bowwarfare.enums.Messages;
import me.frostythedev.bowwarfare.players.BWPlayer;
import me.frostythedev.bowwarfare.utils.Colors;
import me.frostythedev.bowwarfare.utils.Locations;
import me.frostythedev.bowwarfare.utils.commands.Command;
import me.frostythedev.bowwarfare.utils.commands.CommandContext;
import me.frostythedev.bowwarfare.utils.commands.CommandNumberFormatException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public class BWCommand {

    private static BWPlugin plugin;

    public BWCommand(BWPlugin plugin) {
        BWCommand.plugin = plugin;
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

    @Command(aliases = "bowwarfare", desc = "Main BowWarfare command")
    public static void command(CommandContext args, CommandSender sender) {
        Player player = (Player) sender;
        String playerName = player.getName();
        BWPlayer bwPlayer = plugin.getPlayerManager().getPlayer(Bukkit.getPlayer(playerName));

        if (args.argsLength() == 0) {
            Colors.message(sender, "&a=====[ &2&lPage 1/1 &a]======");
            for (String str : CMD_HELP) {
                Colors.sendMessage(sender, str);
            }
        } else {
            switch (args.getString(0)) {
                case "create":
                    if (args.argsLength() >= 2) {
                        String name = args.getString(1);
                        int min = 4;
                        int max = 24;
                        try {
                            min = args.getInteger(2);
                            max = args.getInteger(3);
                        } catch (CommandNumberFormatException e) {
                            e.printStackTrace();
                        }

                        if (plugin.getArenaManager().getArena(name) != null) {
                            Messages.ARENA_ALREADY_EXISTS.send(sender);
                        } else {
                            Arena arena = plugin.getArenaManager().createArena(name, player.getLocation(), min, max, new ArrayList<>());
                            Colors.message(sender, "&aYou have created an arena with name '" + arena.getName() + "'");
                        }
                    }
                    break;
                case "delete":
                    if (args.argsLength() >= 2) {
                        String name = args.getString(1);
                        if (plugin.getArenaManager().deleteArena(name)) {
                            Colors.message(sender, "&aYou have deleted the arena with name '" + name + "'");
                        } else {
                            Colors.message(sender, "&cAn error occurred while deleting arena with name '" + name + "'");
                        }
                    }
                    break;
                case "addspawn":
                    if (args.argsLength() >= 2) {
                        String name = args.getString(1);

                        Arena arena = plugin.getArenaManager().getArena(name);
                        if (arena == null) {
                            Colors.message(sender, "&cCould not find an arena with that specified name '" + name + "'");
                        } else {
                            if (plugin.getArenaManager().addSpawn(arena, player.getLocation())
                                    .equals(ArenaManager.AddResult.ALREADY_ADDED)) {
                                Colors.message(sender, "&cThis location has already been registered as a spawnpoint.");
                            } else if (plugin.getArenaManager().addSpawn(arena, player.getLocation())
                                    .equals(ArenaManager.AddResult.INSUFFICIENT)) {
                                Colors.message(sender, "&aSpawnpoint added! &b&oArena recommendation: More Spawnpoints required.");
                            } else {
                                plugin.getArenaManager().saveArena(arena);
                                Colors.message(sender, "&aSpawnpoint added! &6Arena has been saved as is ready to open.");
                            }
                        }
                    }
                    break;
                case "removespawn":

                    break;
                case "stats":
                    if (args.argsLength() > 1) {
                        playerName = args.getString(1);
                    }
                    if (bwPlayer == null) {
                        Colors.message(sender, "&cCould not find stats for a player with the name '" + playerName + "'");
                    } else {
                        plugin.getPlayerManager().displayStats(sender, bwPlayer);
                    }
                    break;
                case "coins":
                    if (args.argsLength() > 1) {
                        playerName = args.getString(1);
                    }
                    if (bwPlayer == null) {
                        Colors.message(sender, "&cCould not find coins for a player with the name '" + playerName + "'");
                    } else {
                        Colors.message(sender, "&e" + playerName + " has &6" + bwPlayer.getCoins() + " &ecoins.");
                    }
                    break;
                case "setlobby":
                    plugin.getConfig().set("lobby-location", Locations.toString(player.getLocation()));
                    plugin.saveConfig();
                    Colors.message(player, "&aYou have set the &bglobal &alobby location to your feet.");
                    break;
                case "leave":
                    if (plugin.getArenaManager().getArena(player) != null) {
                        plugin.getArenaManager().getArena(player).removePlayer(player);
                        Colors.message(sender, "&bYou have left the arena you were previously in.");
                        player.getInventory().clear();
                        player.getInventory().setArmorContents(null);

                        if (plugin.getLobbyLocation() != null) {
                            player.teleport(plugin.getLobbyLocation());
                        }
                    } else {
                        Colors.message(sender, "&cYou are currently not in an arena.");
                    }
                    break;
                case "perks":
                    if (plugin.getArenaManager().getArena(player) != null) {
                        plugin.isInGame(player).ifPresent(arena -> {
                            if (!arena.getArenaState().equals(ArenaState.LOBBY)) {
                                Colors.message(sender, "&cYou cannot change perks while in a game please leave and try again.");
                            } else {
                                plugin.getPerkManager().openSelectorMenu(player);
                            }
                        });
                    } else {
                        plugin.getPerkManager().openSelectorMenu(player);
                    }
                    break;
                case "shop":
                    if (plugin.getArenaManager().getArena(player) == null) {
                        Colors.message(sender, "&cYou must be in a game to access the shop.");
                    } else {
                        plugin.isInGame(player).ifPresent(arena -> {
                            if (!arena.getArenaState().equals(ArenaState.LOBBY)) {
                                plugin.getShopManager().openGameShop(player);
                            } else {
                                Colors.message(sender, "&cYou must be in a game to access the shop.");
                            }
                        });
                    }
                    break;
                case "list":
                    if (plugin.getArenaManager().getArenaNames().isEmpty()) {
                        Colors.message(sender, "&cNo arenas have been loaded.");
                    } else {
                        Colors.message(sender, "&a=====[ &2&lLoaded Arenas &a&l]=====");
                        plugin.getArenaManager().getArenaNames().forEach(str -> Colors.sendMessage(sender, "&e" + str));
                    }
                    break;
                case "disable":
                    plugin.getServer().getPluginManager().disablePlugin(plugin);
                    Colors.message(sender, "&4Plugin has been disabled.");
                    break;
                case "info":
                    Colors.message(sender, "&a=====[ &2&lInformation &a&l]=====");
                    Colors.sendMessage(sender, "&dAuthor: &bfrostythedev");
                    Colors.sendMessage(sender, "&dOriginal Plugin: &bXxLeetGamerxX");
                    Colors.sendMessage(sender, "&dLast Updated: &b13/7/16");
                    Colors.sendMessage(sender, "&dIn Partnership: &bVisionCoding LLC {c}");
                    break;
            }
        }
    }
}
