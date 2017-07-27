package me.frostythedev.bowwarfare.perks.gui;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.utils.gui.GUIMenu;
import me.frostythedev.bowwarfare.utils.gui.api.MenuComponent;
import me.frostythedev.bowwarfare.perks.Perk;
import me.frostythedev.bowwarfare.players.BWPlayer;
import me.frostythedev.bowwarfare.utils.Colors;
import me.frostythedev.bowwarfare.utils.ItemStackBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * Programmed by Tevin on 7/14/2016.
 */
public class PerkBuyMenu extends GUIMenu {

    public PerkBuyMenu(BWPlugin plugin, Player player, Perk perk) {
        super(Colors.toColors("&e" + perk.getName() + " Perk"), 3);

        MenuComponent mc = new MenuComponent(4);
        setItem(player, perk.getFinalIcon(false), mc);

        MenuComponent confirm = new MenuComponent(11);

        ItemStackBuilder builder = new ItemStackBuilder(new ItemStack(Material.STAINED_CLAY, 1));
        builder.setDurability(DyeColor.GREEN.getData());
        builder.setDisplayName(Colors.toColors("&a&lConfirm"));

        confirm.addAction(builder.getItemStack(), player1 -> {
            BWPlayer bwPlayer = plugin.getPlayerManager().getPlayer(player1);

            if (bwPlayer.getCoins() >= perk.getCost()) {
                bwPlayer.takeCoins(perk.getCost());

                bwPlayer.getOwnedPerks().add(perk);
                switch (perk.getPerkSlot()) {
                    case ONE:
                        bwPlayer.setPerk1(perk);
                        break;
                    case TWO:
                        bwPlayer.setPerk2(perk);
                        break;
                    case THREE:
                        bwPlayer.setPerk3(perk);
                        break;
                }
                Colors.message(player, "&aYou have just purchased the " + perk.getName() + " Perk!");
                Colors.message(player, "&eYou have re-selected your &6" + perk.getPerkSlot().getPlacement() + " &eperk to &6" + perk.getName());
                player.closeInventory();
            } else {
                Colors.message(player, "&cYou do not have enough coins to purchase this perk.");
                player.closeInventory();
            }
        }, ClickType.LEFT);

        setItem(player, builder.getItemStack(), confirm);

        MenuComponent deny = new MenuComponent(15);

        builder.setDurability(DyeColor.RED.getData());
        builder.setDisplayName(Colors.toColors("&c&lDeny"));

        deny.addAction(builder.getItemStack(), player1 -> {
            player1.closeInventory();
            Colors.message(player1, "&eYou have cancelled the purchasing of the " + perk.getName() + " Perk.");
            Colors.message(player1, "&e&oYour balance was not affected.");
        }, ClickType.LEFT);

        setItem(player, builder.getItemStack(), deny);
    }
}
