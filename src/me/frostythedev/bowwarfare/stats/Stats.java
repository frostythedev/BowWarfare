package me.frostythedev.bowwarfare.stats;

/**
 * Programmed by Tevin on 7/13/2016.
 */
public class Stats {

    private String name;
    private int value;

    public Stats(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
