package it.sevenbits.cards.validation;

/**
 * Created by taro on 27.07.15.
 */
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class Sender {

    private final String username = "discountfeedback@gmail.com";
    private final String password = "i8baM6J7lUaJ";
    private Properties props;

    public Sender() {

        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }

    public void send(String subject, String text, String toEmail){
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            //от кого
            message.setFrom(new InternetAddress(username));
            //кому
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            //тема сообщения
            try {
                message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
            }
            catch (UnsupportedEncodingException ex){
                throw new RuntimeException(ex);
            }

            //текст
            //message.setText(text);
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(text, "text/plain; charset=UTF-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);

            //отправляем сообщение
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
