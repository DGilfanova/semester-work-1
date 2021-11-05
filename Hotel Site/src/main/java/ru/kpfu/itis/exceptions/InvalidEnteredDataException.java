package ru.kpfu.itis.exceptions;

public class InvalidEnteredDataException extends RuntimeException {

    public InvalidEnteredDataException(String message) {
        super(message);
    }

    public InvalidEnteredDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEnteredDataException(Throwable cause) {
        super(cause);
    }
}
