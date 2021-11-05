package ru.kpfu.itis.exceptions;

public class MailMessageException extends RuntimeException {

    public MailMessageException(String message) {
        super(message);
    }

    public MailMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailMessageException(Throwable cause) {
        super(cause);
    }
}
