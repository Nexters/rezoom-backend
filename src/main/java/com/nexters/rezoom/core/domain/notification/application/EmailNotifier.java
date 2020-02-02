package com.nexters.rezoom.core.domain.notification.application;

import com.nexters.rezoom.core.domain.member.domain.Member;
import com.nexters.rezoom.core.domain.notification.domain.NotificationMessage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


/**
 * Gmail Notifier
 * <p>
 * Created by momentjin@gmail.com on 2019-09-06
 * Github : http://github.com/momentjin
 */
public class EmailNotifier implements Notifier {

    private final static String EMAIL_PROP_PATH = "src/main/resources/email-account.yml";

    @Override
    public void notifyToClient(Member member, NotificationMessage message) {
        Properties emailSenderInfoProps = this.getEmailSenderInfoProps();
        String senderId = emailSenderInfoProps.getProperty("id");
        String senderPassword = emailSenderInfoProps.getProperty("password");

        Session session = createSession(senderId, senderPassword);

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(senderId));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(member.getId()));

            mimeMessage.setSubject(message.getTitle());
            mimeMessage.setText(message.getContents());

            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private Properties getGmailProps() {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        return prop;
    }

    private Properties getEmailSenderInfoProps() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(EMAIL_PROP_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }

    private Session createSession(String id, String password) {
        Properties gmailProps = this.getGmailProps();

        return Session.getDefaultInstance(gmailProps, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(id, password);
            }
        });
    }

}
