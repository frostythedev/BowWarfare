package me.frostythedev.bowwarfare.shop;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.utils.gui.GUIMenu;

/**
 * Programmed by Tevin on 7/14/2016.
 */
public class BaseShop extends GUIMenu {

    private BWPlugin plugin;

    public BaseShop(String title, int rows, BWPlugin plugin) {
        super(title, rows);
        this.plugin = plugin;
    }
}
