package me.frostythedev.bowwarfare.utils.gui.api;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import me.frostythedev.bowwarfare.utils.gui.Menu;
import me.frostythedev.bowwarfare.utils.gui.internal.ClickAction;
import me.frostythedev.bowwarfare.utils.gui.internal.Component;
import me.frostythedev.bowwarfare.utils.gui.internal.ItemStackKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public class MenuComponent implements Component {

    private int slot;

    private Menu parent;

    private List<ClickAction> temp;
    private Map<ItemStackKey, ClickAction> actions;
    private ItemStack icon;

    public MenuComponent(int slot) {
        this.slot = slot;
        this.temp = new ArrayList<>();
        this.actions = new HashMap<>();
    }

    public ItemStack getIcon(){
        return icon;
    }

    public void addAction(ItemStack stack, Consumer<Player> action, ClickType type, ClickType... moreTypes) {
        this.actions.put(ItemStackKey.of(stack), new ClickAction(action, type, moreTypes));
        if(this.icon == null){
            this.icon = stack;
        }
        this.temp.add(new ClickAction(action, type, moreTypes));
    }

    @Override
    public void click(Player player, ClickType type) {

        for (ClickAction ca : temp) {
            if (ca.shouldAct(type)) {
                ca.act(player);
            }
        }
    }

    @Override
    public int getSlot() {
        return slot;
    }

    @Override
    public Menu getParent() {
        return parent;
    }

    @Override
    public void setParent(Menu menu) {
        this.parent = menu;
    }

    public Map<ItemStackKey, ClickAction> getActions() {
        return actions;

    }

}
