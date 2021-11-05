package ru.kpfu.itis.helpers.notifiers;

import ru.kpfu.itis.exceptions.MailMessageException;
import ru.kpfu.itis.helpers.constants.Constants;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailNotifier implements Notifier {

    private Properties mailProp;

    public MailNotifier(Properties mailProp) {
        this.mailProp = mailProp;
    }

    @Override
    public void notify(String to, String messageText) {

        Session session = Session.getInstance(mailProp,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mailProp.getProperty(Constants.MAIL_PROP_USER),
                                mailProp.getProperty(Constants.MAIL_PROP_PASSWORD));
                    }
                });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(mailProp.getProperty("mail.username")));

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject(Constants.MAIL_MESSAGE_THEME);

            message.setText(messageText);

            Transport.send(message);
        }
        catch (MessagingException e) {
            throw new MailMessageException("Your actions have been successfully registered, but the email service is temporarily down.");
        }
    }
}
