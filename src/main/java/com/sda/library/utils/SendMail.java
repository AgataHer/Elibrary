package com.sda.library.utils;

import com.sda.library.repository.PropertyRepository;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {

    static private PropertyRepository propertyRepository;

    public static boolean SendGmail(String receiver, String theme, String message) {
        final String username = Config.mailUsername;
        final String password = Config.mailPassword;
        boolean done = false;

        String from = Config.mailAccountName;

        Properties props = new Properties();
        props.put("mail.smtp.auth", Config.mailSmtpAuth.toString());
        props.put("mail.smtp.starttls.enable", Config.mailSmtpStarttlsEnable.toString());
        props.put("mail.smtp.host", Config.mailSmtpHost);
        props.put("mail.smtp.port", Config.mailSmtpPort);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message email = new MimeMessage(session);
            email.setFrom(new InternetAddress(from));
            email.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receiver));
            email.setSubject(theme);
            email.setText(message);
            Transport.send(email);
            done = true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return done;
    }
}