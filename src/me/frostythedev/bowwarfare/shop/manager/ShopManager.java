package me.frostythedev.bowwarfare.shop.manager;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.shop.CoinShop;
import org.bukkit.entity.Player;

/**
 * Programmed by Tevin on 7/14/2016.
 */
public class ShopManager {

    private BWPlugin plugin;

    public ShopManager(BWPlugin plugin) {
        this.plugin = plugin;
    }

    public void openGameShop(Player player) {
        CoinShop shop = new CoinShop(plugin, player);
        shop.open(player);
    }
}
