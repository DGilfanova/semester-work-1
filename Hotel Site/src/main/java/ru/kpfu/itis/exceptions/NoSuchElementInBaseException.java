package ru.kpfu.itis.exceptions;

public class NoSuchElementInBaseException extends RuntimeException {

    public NoSuchElementInBaseException(String message) {
        super(message);
    }

    public NoSuchElementInBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchElementInBaseException(Throwable cause) {
        super(cause);
    }
}
