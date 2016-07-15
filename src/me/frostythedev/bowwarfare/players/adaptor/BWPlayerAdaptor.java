package me.frostythedev.bowwarfare.players.adaptor;

import com.google.gson.*;
import me.frostythedev.bowwarfare.BWPlugin;
import me.frostythedev.bowwarfare.perks.Perk;
import me.frostythedev.bowwarfare.players.BWPlayer;
import me.frostythedev.bowwarfare.utils.json.JsonAdaptor;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public class BWPlayerAdaptor implements JsonAdaptor<BWPlayer> {

    private BWPlugin plugin;

    public BWPlayerAdaptor(BWPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public BWPlayer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        UUID uuid = UUID.fromString(jsonObject.get("uuid").getAsString());
        String name = jsonObject.get("displayName").getAsString();
        int kills = jsonObject.get("kills").getAsInt();
        int deaths = jsonObject.get("deaths").getAsInt();
        int coins = jsonObject.get("coins").getAsInt();

        String perk1 = jsonObject.get("perk1").getAsString();
        String perk2 = jsonObject.get("perk2").getAsString();
        String perk3 = jsonObject.get("perk3").getAsString();

        BWPlayer bwPlayer = new BWPlayer(uuid, name);
        bwPlayer.setKills(kills);
        bwPlayer.setDeaths(deaths);
        bwPlayer.setCoins(coins);

        if (plugin.getPerkManager().getPerk(perk1) != null) {
            bwPlayer.setPerk1(plugin.getPerkManager().getPerk(perk1));
        }
        if (plugin.getPerkManager().getPerk(perk2) != null) {
            bwPlayer.setPerk2(plugin.getPerkManager().getPerk(perk2));
        }
        if (plugin.getPerkManager().getPerk(perk3) != null) {
            bwPlayer.setPerk3(plugin.getPerkManager().getPerk(perk3));
        }

        List<Perk> perks = new ArrayList<>();

        String ownedPerks = jsonObject.get("ownedPerks").getAsString();
        if (!ownedPerks.equals("")) {
            if (!ownedPerks.contains(";")) {
                perks.add(plugin.getPerkManager().getPerk(ownedPerks));
            } else {
                String[] parts = ownedPerks.split(";");
                for (String str : parts) {
                    Perk perk = plugin.getPerkManager().getPerk(str);
                    if (perk != null) {
                        perks.add(perk);
                    }
                }
            }
        }
        bwPlayer.setOwnedPerks(perks);

        return bwPlayer;
    }

    @Override
    public JsonElement serialize(BWPlayer player, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("uuid", player.getUuid().toString());
        jsonObject.addProperty("displayName", player.getDisplayName());
        jsonObject.addProperty("kills", player.getKills());
        jsonObject.addProperty("deaths", player.getDeaths());
        jsonObject.addProperty("coins", player.getCoins());

        String perk1 = "";
        String perk2 = "";
        String perk3 = "";
        if (player.getPerk1() != null) {
            perk1 = player.getPerk1().getName();
        }
        if (player.getPerk2() != null) {
            perk2 = player.getPerk2().getName();
        }
        if (player.getPerk3() != null) {
            perk3 = player.getPerk3().getName();
        }
        jsonObject.addProperty("perk1", perk1);
        jsonObject.addProperty("perk2", perk2);
        jsonObject.addProperty("perk3", perk3);

        String ownedPerks = "";
        if (!player.getOwnedPerks().isEmpty()) {
            for (Perk perk : player.getOwnedPerks()) {
                if (ownedPerks.equals("")) {
                    ownedPerks += perk.getName();
                } else {
                    ownedPerks += ";" + perk.getName();
                }
            }
        }
        jsonObject.addProperty("ownedPerks", ownedPerks);

        return jsonObject;
    }
}
