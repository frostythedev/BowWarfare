package me.frostythedev.bowwarfare.cmds.subs;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.Config;
import me.frostythedev.bowwarfare.arena.enums.ArenaState;
import me.frostythedev.bowwarfare.utils.Colors;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCmd extends BWSubCmd {

    public ShopCmd(BWPlugin plugin) {
        super(plugin, "shop", "bw.user.shop");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(!Config.ITEM_SHOP_ENABLED){
                Colors.message(player, "&cThe shop is currently disabled.");
            }else{
                if (getPlugin().getArenaManager().getArena(player) == null) {
                    Colors.message(sender, "&cYou must be in a game to access the shop.");
                } else {
                    getPlugin().isInGame(player).ifPresent(arena -> {
                        if (!arena.getArenaState().equals(ArenaState.LOBBY)) {
                            getPlugin().getShopManager().openGameShop(player);
                        } else {
                            Colors.message(sender, "&cYou must be in a game to access the shop.");
                        }
                    });
                }
            }
        }
    }
}
