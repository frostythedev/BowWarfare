package me.frostythedev.bowwarfare.perks.types.slot2;

import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.perks.Perk;
import me.frostythedev.bowwarfare.perks.enums.PerkSlot;
import me.frostythedev.bowwarfare.utils.Colors;
import me.frostythedev.bowwarfare.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CarepackagePerk extends Perk {

    private BWPlugin plugin;

    public CarepackagePerk(BWPlugin plugin) {
        super("CarePackage", new ItemStack(Material.CHEST), null, PerkSlot.TWO, 950, 6);

        this.plugin = plugin;

        List<String> desc = new ArrayList<>();
        desc.add(Colors.toColors("&7You're running a bit low on supplies"));
        desc.add(Colors.toColors("&7but luckily! The troops are making another"));
        desc.add(Colors.toColors("&7rounds and can assist you."));
        desc.add(Colors.toColors("&7Use this flare to mark where you'd like it to drop."));
        desc.add(" ");
        desc.add(Colors.toColors("&c&lREQUIRED: &76 Kills"));


        setDescription(desc);
    }

    @Override
    public void activate(Player player) {
        player.getInventory().setItem(7, new ItemStackBuilder(new ItemStack(Material.CHEST))
                .setDisplayName(Colors.toColors("&e&lCAREPACKAGE"))
                .getItemStack());
    }
}
