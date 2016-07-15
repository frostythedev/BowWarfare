package me.frostythedev.bowwarfare.utils.gui;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class MenuManager {

    private static MenuManager instance;

    public static MenuManager getInstance() {
        if (instance == null) {
            instance = new MenuManager();
        }
        return instance;
    }

    private Map<UUID, Menu> menusOpened = new HashMap<>();

    public void setOpened(Player player, Menu menu) {
        menusOpened.remove(player.getUniqueId());
        menu.onOpen(player);
        menusOpened.put(player.getUniqueId(), menu);
    }

    public boolean hasOpen(Player player, Menu menu) {
        return hasOpened(player) && this.menusOpened.get(player.getUniqueId()).equals(menu);
    }

    public boolean hasOpened(Player player){
        return getOpened(player) != null;
    }

    public Menu getOpened(Player player) {
        return this.menusOpened.get(player.getUniqueId());
    }

    public Map<UUID, Menu> getMenusOpened() {
        return menusOpened;
    }
}
