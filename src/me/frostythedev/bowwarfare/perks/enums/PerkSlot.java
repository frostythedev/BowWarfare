package me.frostythedev.bowwarfare.perks.enums;

/**
 * Programmed by Tevin on 7/14/2016.
 */
public enum PerkSlot {
    ONE("1st"),
    TWO("2nd"),
    THREE("3rd"),
    ;

    private String placement;

    PerkSlot(String placement) {
        this.placement = placement;
    }

    public String getPlacement() {
        return placement;
    }
}
