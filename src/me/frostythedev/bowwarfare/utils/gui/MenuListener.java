package me.frostythedev.bowwarfare.utils.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import me.frostythedev.bowwarfare.utils.gui.internal.Component;


public class MenuListener implements Listener {

    @EventHandler
    public void on(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Menu menu = MenuManager.getInstance().getOpened(player);

        if (menu != null) {
            if(event.getView().getTopInventory() != null){
                if (event.getClickedInventory().equals(event.getView().getTopInventory())) {
                    int slot = event.getSlot();
                    if (slot >= menu.getRows() * 9) {
                        return;
                    }

                    event.setCancelled(true);

                    Component component = menu.getBySlot(slot);
                    if (component != null) {
                        component.click(player, event.getClick());
                    }
                }
            }
        }
    }

}
