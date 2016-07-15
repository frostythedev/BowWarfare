package me.frostythedev.bowwarfare.arena.enums;

/**
 * Programmed by Tevin on 7/14/2016.
 */
public enum  Placement {
    FIRST("1st"),
    SECOND("2nd"),
    THIRD("3rd")
    ;

    private String placement;

    Placement(String placement) {
        this.placement = placement;
    }

    public String getPlacement() {
        return placement;
    }
}
