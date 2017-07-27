package me.frostythedev.bowwarfare.perks.types.slot1;

import me.frostythedev.bowwarfare.Config;
import me.frostythedev.bowwarfare.perks.Perk;
import me.frostythedev.bowwarfare.perks.enums.PerkSlot;
import me.frostythedev.bowwarfare.utils.Colors;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

/**
 * Programmed by Tevin on 7/14/2016.
 */
public class SpeedPerk extends Perk {

    public SpeedPerk() {
        super("Scout", new ItemStack(Material.FEATHER), null, PerkSlot.ONE, Config.SCOUT_PERK_COST, 3);

        List<String> desc = new ArrayList<>();
        desc.add(Colors.toColors("&7After taking pleasure in the death of"));
        desc.add(Colors.toColors("&7your enemies you suddenly feel a rush"));
        desc.add(Colors.toColors("&7of adreneline pumping through your veins"));
        desc.add(Colors.toColors("&7which allows you to run quicker temporarily."));
        desc.add(" ");
        desc.add(Colors.toColors("&c&lREQUIRED: &73 Kills"));

        setDescription(desc);
    }


    @Override
    public void activate(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 7, 0));
    }
}
