package me.frostythedev.bowwarfare.shop;

import me.frostythedev.bowwarfare.BWPlugin;
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
