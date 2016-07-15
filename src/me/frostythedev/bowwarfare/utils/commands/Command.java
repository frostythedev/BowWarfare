package me.frostythedev.bowwarfare.utils.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation indicates a command. Methods should be marked with this
 * annotation to tell {@link CommandsManager} that the method is a command.
 * Note that the method name can actually be anything.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    /**
     * A list of aliases for the command. The first alias is the most
     * important -- it is the main name of the command. (The method name
     * is never used for anything).
     *
     * @return Aliases for a command
     */
    String[] aliases();

    /**
     * Usage instruction. Example text for usage could be
     * {@code [-h harps] [name] [message]}.
     *
     * @return Usage instructions for a command
     */
    String usage() default "";

    /**
     * @return A short description for the command.
     */
    String desc();

    /**
     * The minimum number of arguments. This should be 0 or above.
     *
     * @return the minimum number of arguments
     */
    int min() default 0;

    /**
     * The maximum number of arguments. Use -1 for an unlimited number
     * of arguments.
     *
     * @return the maximum number of arguments
     */
    int max() default -1;

    /**
     * Flags allow special processing for flags such as -h in the command,
     * allowing users to easily turn on a flag. This is a string with
     * each character being a flag. Use A-Z and a-z as possible flags.
     * Appending a flag with a : makes the flag character before a value flag,
     * meaning that if it is given it must have a value
     *
     * @return Flags matching a-zA-Z
     */
    String flags() default "";

    /**
     * @return A long description for the command.
     */
    String help() default "";

    /**
     * Get whether any flag can be used.
     *
     * @return true if so
     */
    boolean anyFlags() default false;

}
