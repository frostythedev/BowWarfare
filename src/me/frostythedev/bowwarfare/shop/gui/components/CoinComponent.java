package me.frostythedev.bowwarfare.shop.gui.components;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.players.BWPlayer;
import me.frostythedev.bowwarfare.utils.gui.api.MenuComponent;
import me.frostythedev.bowwarfare.utils.Colors;
import me.frostythedev.bowwarfare.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Programmed by Tevin on 7/14/2016.
 */
public class CoinComponent extends MenuComponent {

    private final Integer[] AVAILABLE_SLOTS = new Integer[]{2,3,4,5,6,7};

    public CoinComponent(BWPlugin plugin, int slot, int cost, ItemStack displayIcon, String name, String... description) {
        super(slot);

        ItemStackBuilder builder = new ItemStackBuilder(displayIcon);
        builder.setDisplayName(Colors.toColors(name));
        List<String> desc = new ArrayList<>();
        desc.add(Colors.toColors("&c&lCOST: &e" + cost + " coins"));
        for(String str : description){
           desc.add(Colors.toColors(str));
        }
        builder.setLore(desc);

        addAction(builder.getItemStack(), player -> {
            BWPlayer bwPlayer = plugin.getPlayerManager().getPlayer(player);
            if(bwPlayer.getCoins() >= cost){
                bwPlayer.takeCoins(cost);

                ItemStack item = builder.getItemStack();

                boolean given = false;
                for(int i : AVAILABLE_SLOTS){
                    if(player.getInventory().getItem(i) == null || player.getInventory().getItem(i).getType().equals(Material.AIR)){
                        player.getInventory().setItem(i, new ItemStackBuilder(item).setLore("").getItemStack());
                        player.updateInventory();
                        given = true;
                        break;
                    }
                }

                if(!given){
                    bwPlayer.giveCoins(cost);
                    Colors.message(player, "&eYou cannot buy this item because your inventory is full.");
                }else{
                    Colors.message(player, "&aYou have just purchased a &e" + name + " &c(-" + cost + " coins)");
                }
            }
        }, ClickType.LEFT);
    }

}
