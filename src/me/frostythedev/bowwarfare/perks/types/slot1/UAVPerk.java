package me.frostythedev.bowwarfare.perks.types.slot1;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.perks.Perk;
import me.frostythedev.bowwarfare.perks.enums.PerkSlot;
import me.frostythedev.bowwarfare.utils.Colors;
import me.frostythedev.bowwarfare.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Programmed by Tevin on 7/14/2016.
 */
public class UAVPerk extends Perk {

    private BWPlugin plugin;

    public UAVPerk(BWPlugin plugin) {
        super("UAV", new ItemStack(Material.COMPASS), null, PerkSlot.ONE, 250, 3);
        this.plugin = plugin;

        List<String> desc = new ArrayList<>();
        desc.add(Colors.toColors("&7Your jammer has been broken but it's"));
        desc.add(Colors.toColors("&7finally showing signs of having life."));
        desc.add(Colors.toColors("&7With the click of a button you're"));
        desc.add(Colors.toColors("&7navigated to closest enemy."));
        desc.add(" ");
        desc.add(Colors.toColors("&c&lREQUIRED: &73 Kills"));


        setDescription(desc);
    }


    @Override
    public void activate(Player player) {
        player.getInventory().setItem(8, new ItemStackBuilder(new ItemStack(Material.COMPASS))
                .setDisplayName(Colors.toColors("&a&lUAV TRACKER"))
                .setLore(Colors.toColors("&eTime Left: &730"))
                .getItemStack());
        plugin.getUavUpdateTask().addPlayer(player, 30);
    }
}
