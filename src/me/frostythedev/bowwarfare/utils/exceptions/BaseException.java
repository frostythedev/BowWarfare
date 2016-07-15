package me.frostythedev.bowwarfare.utils.exceptions;

/**
 * Programmed by Tevin on 7/8/2016.
 */
public class BaseException extends Exception {

    private String message;

    public BaseException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
