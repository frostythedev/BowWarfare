package me.frostythedev.bowwarfare.perks;

import me.frostythedev.bowwarfare.perks.enums.PerkSlot;
import me.frostythedev.bowwarfare.players.BWPlayer;
import me.frostythedev.bowwarfare.utils.Colors;
import me.frostythedev.bowwarfare.utils.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Programmed by Tevin on 7/14/2016.
 */
public abstract class Perk {

    private String name;
    private ItemStack displayIcon;
    private List<String> description;
    private PerkSlot perkSlot;
    private int cost;
    private int requiredKills;
    private HashSet<UUID> storage;

    public Perk(String name, ItemStack displayIcon, List<String> description, PerkSlot perkSlot, int cost, int requiredKills) {
        this.name = name;
        this.displayIcon = displayIcon;
        this.description = description;
        this.perkSlot = perkSlot;
        this.cost = cost;
        this.requiredKills = requiredKills;
        this.storage = new HashSet<>();
    }

    public void reset(BWPlayer bwPlayer) {
        if (hasUsed(Bukkit.getPlayer(bwPlayer.getUuid()))) {
            storage.remove(bwPlayer.getUuid());
        }
    }

    public ItemStack getFinalIcon(boolean bought) {
        ItemStackBuilder builder = new ItemStackBuilder(displayIcon);
        builder.setDisplayName(Colors.toColors("&e" + name + " Perk"));
        List<String> desc = new ArrayList<>();
        if (!bought) {
            desc.add(Colors.toColors("&c&lCOST: &e" + cost + " coins"));
        }
        for(String str : description){
            desc.add(Colors.toColors(str));
        }
        builder.setLore(desc);

        return builder.getItemStack();
    }

    public boolean hasUsed(Player player) {
        return storage.contains(player.getUniqueId());
    }

    public boolean use(BWPlayer player) {
        if (!hasUsed(Bukkit.getPlayer(player.getUuid()))) {
            if (player.getKills() == getRequiredKills()) {
                storage.add(player.getUuid());
                activate(Bukkit.getPlayer(player.getUuid()));
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public ItemStack getDisplayIcon() {
        return displayIcon;
    }

    public void setDisplayIcon(ItemStack displayIcon) {
        this.displayIcon = displayIcon;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getRequiredKills() {
        return requiredKills;
    }

    public void setRequiredKills(int requiredKills) {
        this.requiredKills = requiredKills;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public PerkSlot getPerkSlot() {
        return perkSlot;
    }

    public void setPerkSlot(PerkSlot perkSlot) {
        this.perkSlot = perkSlot;
    }

    public abstract void activate(Player player);
}
