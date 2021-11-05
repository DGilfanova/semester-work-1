package ru.kpfu.itis.exceptions;

public class WrongExistingUserInfoException extends RuntimeException {

    public WrongExistingUserInfoException(String message) {
        super(message);
    }

    public WrongExistingUserInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongExistingUserInfoException(Throwable cause) {
        super(cause);
    }
}
