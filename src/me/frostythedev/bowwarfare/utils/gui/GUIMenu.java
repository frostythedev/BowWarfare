package me.frostythedev.bowwarfare.utils.gui;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


public class GUIMenu extends Menu {

    public GUIMenu(String title, int rows) {
        super(title, rows);
    }

    @Override
    public void onOpen(Player player) {

    }

    @Override
    public Inventory createInventory(Player player) {
        Preconditions.checkArgument(getRows() > 0 && getRows() <= 6, "Inventory rows must be between [(1-6)]");
        return Bukkit.createInventory(player, getRows() * 9, getTitle());
    }
}
