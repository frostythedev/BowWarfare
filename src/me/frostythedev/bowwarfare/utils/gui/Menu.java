package me.frostythedev.bowwarfare.utils.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import me.frostythedev.bowwarfare.utils.gui.internal.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public abstract class Menu {

    private String title;
    private int rows;

    private Map<Component, Integer> components;

    private Map<Integer, Component> temp;

    private final Map<Player, Inventory> byPlayer;

    public Menu(String title, int rows) {
        this.title = title;
        if (rows < 0 || rows > 6) {
            throw new IllegalArgumentException("Rows must be greater than 0 but smaller than or equal to 6");
        }
        this.rows = rows;
        this.components = new HashMap<>();
        this.temp = new HashMap<>();
        this.byPlayer = new HashMap<>();
    }

    public abstract void onOpen(Player player);

    public abstract Inventory createInventory(Player player);

    protected Inventory getInventory(Player player) {
        return this.byPlayer.computeIfAbsent(player, this::createInventory);
    }


    public boolean addComponent(int slot, Component component) {


        if (component == null) {
            this.temp.remove(slot);
            return true;
        }

        if (getBySlot(slot) != null) {
            removeComponent(slot);
        }

        this.temp.put(slot, component);
        return true;
    }

    public Component getBySlot(int slot) {
        if (temp.containsKey(slot)) {
            return temp.get(slot);
        }
        return null;
    }


    public boolean removeComponent(int slot) {
        if (getBySlot(slot) != null) {
            temp.remove(slot);
            return true;
        }
        return false;
    }


    public void setItem(Player player, ItemStack stack, Component component) {
        if (addComponent(component.getSlot(), component)) {
            getInventory(player).setItem(component.getSlot(), stack);
        }
    }


    public Optional<ItemStack> getItem(Player player, Component component) {
        int slot = component.getSlot();
        Inventory inv = getInventory(player);
        if (slot < 0 || slot > inv.getSize()) {
            return Optional.empty();
        }
        return Optional.ofNullable(inv.getItem(slot)).filter(stack -> stack.getType() != Material.AIR);
    }

    public Menu open(Player player) {
        InventoryView view = player.openInventory(getInventory(player));
        if (!view.getTopInventory().equals(getInventory(player))) {
            throw new IllegalStateException("Failed to open inventory (was the InventoryOpenEvent cancelled?)");
        }
        MenuManager.getInstance().setOpened(player, this);
        this.temp.values().forEach(component -> {
            component.draw(player);
        });
        return this;
    }

    public void refresh(Player player) {
        if (!MenuManager.getInstance().hasOpen(player, this)) {
            return;
        }

        InventoryView view = player.openInventory(getInventory(player));
        if (!view.getTopInventory().getName().equalsIgnoreCase(getInventory(player).getName())) {
            throw new IllegalStateException("Failed to open inventory (was the InventoryOpenEvent cancelled?)");
        }

        for (Component comp : temp.values()) {
            getItem(player, comp).ifPresent(stack -> {
                setItem(player, stack, comp);
            });

            player.updateInventory();

            this.temp.values().forEach(component -> {
                component.draw(player);
            });
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
