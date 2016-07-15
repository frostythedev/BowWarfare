package me.frostythedev.bowwarfare.perks;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.utils.gui.GUIMenu;
import me.frostythedev.bowwarfare.utils.gui.api.MenuComponent;
import me.frostythedev.bowwarfare.perks.gui.PerkBuyMenu;
import me.frostythedev.bowwarfare.players.BWPlayer;
import me.frostythedev.bowwarfare.utils.Colors;
import me.frostythedev.bowwarfare.utils.ItemStackBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.HashMap;
import java.util.Map;

/**
 * Programmed by Tevin on 7/14/2016.
 */
public class PerkManager {

    private BWPlugin plugin;
    private Map<String, Perk> loadedPerks;

    public PerkManager(BWPlugin plugin) {
        this.plugin = plugin;
        this.loadedPerks = new HashMap<>();
    }

    public void register(Perk perk) {
        if (getPerk(perk.getName()) == null) {
            loadedPerks.put(perk.getName(), perk);
        }
    }

    public void openSelectorMenu(Player player) {
        BWPlayer bwPlayer = plugin.getPlayerManager().getPlayer(player);

        GUIMenu menu = new GUIMenu("Perk Menu", 3);
        int index = 0;

        for (Perk perk : loadedPerks.values()) {

            MenuComponent mc = new MenuComponent(index);

            ItemStackBuilder icon = new ItemStackBuilder(perk.getDisplayIcon());
            icon.setDisplayName(Colors.toColors("&e" + perk.getName() + " &c{UNOWNED}"));

            icon.setLore(perk.getDescription());
            if (bwPlayer.hasPerk(perk.getName())) {

                icon.setDisplayName(Colors.toColors("&e" + perk.getName() + " &a{OWNED}"));

                mc.addAction(icon.getItemStack(), player1 -> {
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
                    Colors.message(player, "&eYou have re-selected your &6" + perk.getPerkSlot().getPlacement() + " &eperk to &6" + perk.getName());
                }, ClickType.LEFT);
            } else {

                icon.setDisplayName(Colors.toColors("&e" + perk.getName() + " &c{UNOWNED}"));

                mc.addAction(icon.getItemStack(), player1 -> {
                    new PerkBuyMenu(plugin, player1, perk).open(player1);
                }, ClickType.LEFT);
            }
            menu.setItem(player, icon.getItemStack(), mc);
            index++;
        }
        menu.open(player);
    }

    public Perk getPerk(String name) {
        if (loadedPerks.containsKey(name)) {
            return loadedPerks.get(name);
        }
        return null;
    }

    public Map<String, Perk> getLoadedPerks() {
        return loadedPerks;
    }
}
