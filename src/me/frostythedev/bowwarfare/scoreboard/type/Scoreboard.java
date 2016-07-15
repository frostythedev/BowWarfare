package me.frostythedev.bowwarfare.scoreboard.type;

import org.bukkit.entity.Player;

/**
 * Represents an advanced scoreboards that can display up to 48 characters in a single entry.
 *
 * @author TigerHix
 */
public interface Scoreboard {

    /**
     * Activate the scoreboards.
     */
    void activate();

    /**
     * Deactivate the scoreboards.
     */
    void deactivate();

    /**
     * Determine if the scoreboards has been already activated.
     *
     * @return activated
     */
    boolean isActivated();

    /**
     * Returns the handler for this scoreboards.
     *
     * @return handler
     */
    ScoreboardHandler getHandler();

    /**
     * Set the handler for this scoreboards.
     *
     * @param handler handler
     */
    Scoreboard setHandler(ScoreboardHandler handler);

    /**
     * Returns the update interval (default = 10L).
     *
     * @return update interval
     */
    long getUpdateInterval();

    /**
     * Set the update interval.
     *
     * @param updateInterval update interval
     * @return this
     */
    Scoreboard setUpdateInterval(long updateInterval);

    /**
     * Returns the holder of this scoreboards.
     *
     * @return holder
     */
    Player getHolder();

}
