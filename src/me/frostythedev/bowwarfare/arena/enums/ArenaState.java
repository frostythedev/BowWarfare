package me.frostythedev.bowwarfare.arena.enums;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public enum  ArenaState {
    LOBBY("&a&lLOBBY"),
    IN_GAME("&c&lINGAME"),
    ENDED("&6&lENDED"),
    RESTARTING("&4&lRESTARTING");

    private String signFormat;

    ArenaState(String signFormat) {
        this.signFormat = signFormat;
    }

    public String getSignFormat() {
        return signFormat;
    }
}
