package me.frostythedev.bowwarfare.utils;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.players.BWPlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Programmed by Tevin on 7/8/2016.
 */
public class Colors {

    public static String toColors(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static String stripColors(String input) {
        return ChatColor.stripColor(input);
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(toColors(message));
    }

    public static void message(CommandSender sender, String message){
        sendMessage(sender, BWPlugin.PREFIX + message);
    }

    public static void message(BWPlayer player, String message){
        message(Bukkit.getPlayer(player.getUuid()), message);
    }

    public static void sendToAll(String message) {
        for (Player ps : Bukkit.getOnlinePlayers()) {
            sendMessage(ps, message);
        }
    }

    public static void clearChat(CommandSender sender){
        clearChat(sender, 25);
    }

    public static void clearChat(CommandSender sender, int amount){
        for(int i = 0; i < amount; i++){
            sendMessage(sender, " ");
        }
    }
}
