package me.frostythedev.bowwarfare.utils.gui.internal;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import me.frostythedev.bowwarfare.utils.gui.Menu;


public interface Component {

    int getSlot();

   default void click(Player player, ClickType type){
   }

    default void draw(Player player){
    }

    Menu getParent();

    void setParent(Menu menu);
}
