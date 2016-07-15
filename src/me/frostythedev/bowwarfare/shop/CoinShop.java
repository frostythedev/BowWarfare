package me.frostythedev.bowwarfare.shop;

import me.frostythedev.bowwarfare.BWPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Programmed by Tevin on 7/14/2016.
 */
public class CoinShop extends BaseShop {

    public CoinShop(BWPlugin plugin, Player player) {
        super("Item Shop", 2, plugin);

        CoinComponent flash = new CoinComponent(plugin, 0, 25, new ItemStack(Material.SLIME_BALL), "&eFlashbang",
                "&7This item contains enough power to",
                "&7blind anyone in the area for as long as",
                "&75 seconds.",
                " ",
                "&c&lWARNING: &7Use with &nextreme&r &7caution");

        setItem(player, flash.getIcon(), flash);

        CoinComponent grenade = new CoinComponent(plugin, 1, 35, new ItemStack(Material.FLINT), "&eGrenade",
                "&7This item contains a frag which has the ability",
                "&7to instantly kill enemies if they're too close.",
                " ",
                "&c&lWARNING: &7Use with &nextreme&r &7caution");

        setItem(player, grenade.getIcon(), grenade);
    }
}
