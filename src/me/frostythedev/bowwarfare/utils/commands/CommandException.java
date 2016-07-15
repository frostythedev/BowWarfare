package me.frostythedev.bowwarfare.utils.commands;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@SuppressWarnings("serial")
public class CommandException extends Exception {

    private List<String> commandStack = new ArrayList<String>();

    public CommandException() {
        super();
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable t) {
        super(message, t);
    }

    public CommandException(Throwable t) {
        super(t);
    }

    public void prependStack(String name) {
        commandStack.add(name);
    }

    /**
     * Gets the command that was called, which will include the sub-command
     * (i.e. "/br sphere").
     *
     * @param prefix the command shebang character (such as "/") -- may be empty
     * @param spacedSuffix a suffix to put at the end (optional) -- may be null
     * @return the command that was used
     */
    public String getCommandUsed(String prefix, @Nullable String spacedSuffix) {
        checkNotNull(prefix);
        StringBuilder builder = new StringBuilder();
        if (prefix != null) {
            builder.append(prefix);
        }
        ListIterator<String> li = commandStack.listIterator(commandStack.size());
        while (li.hasPrevious()) {
            if (li.previousIndex() != commandStack.size() - 1) {
                builder.append(" ");
            }
            builder.append(li.previous());
        }
        if (spacedSuffix != null) {
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(spacedSuffix);
        }
        return builder.toString().trim();
    }

}
