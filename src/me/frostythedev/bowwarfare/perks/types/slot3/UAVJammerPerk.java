package me.frostythedev.bowwarfare.perks.types.slot3;

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

public class UAVJammerPerk extends Perk {

    private BWPlugin plugin;

    public UAVJammerPerk(BWPlugin plugin) {
        super("UAVJammer", new ItemStack(Material.REDSTONE_TORCH_ON), null, PerkSlot.THREE, 1500, 7);

        this.plugin = plugin;

        List<String> desc = new ArrayList<>();
        desc.add(Colors.toColors("&7Things aren't look too good, it seems"));
        desc.add(Colors.toColors("&7like the enemy is always one step ahead"));
        desc.add(Colors.toColors("&7This ought to keep those pesky recon units at bay."));
        desc.add(" ");
        desc.add(Colors.toColors("&c&lREQUIRED: &77 Kills"));

        setDescription(desc);
    }

    @Override
    public void activate(Player player) {
        player.getInventory().setItem(8, new ItemStackBuilder(new ItemStack(Material.REDSTONE_TORCH_ON))
                .setDisplayName(Colors.toColors("&c&lUAV JAMMER"))
                .setLore(Colors.toColors("&eTime Left: &745"))
                .getItemStack());
        plugin.getUavUpdateTask().addPlayerJammer(player, 45);
    }
}
