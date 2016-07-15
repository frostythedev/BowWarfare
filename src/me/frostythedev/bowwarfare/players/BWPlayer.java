package me.frostythedev.bowwarfare.players;

import me.frostythedev.bowwarfare.perks.Perk;
import me.frostythedev.bowwarfare.stats.Stats;

import java.util.*;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public class BWPlayer {

    private UUID uuid;
    private String displayName;
    private int kills = 0;
    private int deaths = 0;
    private int coins = 0;
    private List<Perk> ownedPerks;
    private Perk perk1;
    private Perk perk2;
    private Perk perk3;
    //private Map<String, Stats> trackedStats;

    public BWPlayer(UUID uuid, String displayName) {
        this.uuid = uuid;
        this.displayName = displayName;
        this.ownedPerks = new ArrayList<>();
       // this.trackedStats = new HashMap<>();
    }

    public String activatePerks(){
        if(perk1 != null){
            if(perk1.use(this)){
                return perk1.getName();
            }
        }
        if(perk2 != null){
            if(perk2.use(this)){
                return perk2.getName();
            }
        }
        if(perk3 != null){
            if(perk3.use(this)){
                return perk3.getName();
            }
        }
        return "";
    }

    public boolean hasPerk(String name){
        if(!ownedPerks.isEmpty()){
            for(Perk perk : ownedPerks){
                if(perk.getName().equalsIgnoreCase(name)){
                    return true;
                }
            }
        }
        return false;
    }

    public Perk getPerk1() {
        return perk1;
    }

    public void setPerk1(Perk perk1) {
        this.perk1 = perk1;
    }

    public Perk getPerk2() {
        return perk2;
    }

    public void setPerk2(Perk perk2) {
        this.perk2 = perk2;
    }

    public Perk getPerk3() {
        return perk3;
    }

    public void setPerk3(Perk perk3) {
        this.perk3 = perk3;
    }

    /*public Stats getStat(String name) {
        if (trackedStats.containsKey(name)) {
            return trackedStats.get(name);
        }
        return null;
    }

    public void trackStat(Stats stats) {
        if (getStat(stats.getName()) == null) {
            trackedStats.put(stats.getName(), stats);
        }
    }

    public void addStatValue(String name, int amount) {
        if (getStat(name) != null) {
            getStat(name).setValue(getStat(name).getValue() + amount);
        }
    }

    public void removeStatValue(String name, int amount) {
        if (getStat(name) != null) {
            getStat(name).setValue(getStat(name).getValue() - amount);
        }
    }*/

    public void giveCoins(int amount) {
        setCoins(getCoins() + amount);
    }

    public void takeCoins(int amount) {
        setCoins(getCoins() - amount);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public List<Perk> getOwnedPerks() {
        return ownedPerks;
    }

    public void setOwnedPerks(List<Perk> ownedPerks) {
        this.ownedPerks = ownedPerks;
    }

    /*public Map<String, Stats> getTrackedStats() {
        return trackedStats;
    }

    public void setTrackedStats(Map<String, Stats> trackedStats) {
        this.trackedStats = trackedStats;
    }
    */
}
