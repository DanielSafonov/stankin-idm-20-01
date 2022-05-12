package ru.stankin.emailsign;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Base64;
import java.util.Properties;
import java.util.Scanner;

public class Producer {
    public void startProducer() {
        System.out.println("Producer was started..");
        Properties props = grabUserData();

        try {
            Session mailServerSession = getSession(props.getProperty("user"), props.getProperty("password"));
            Message msg = buildMessage(mailServerSession, props);
            System.out.println("Trying to send file..");
            Transport.send(msg);
            System.out.println("Email successfully sent!");
        } catch (Exception e) {
            System.out.println("Error!\n" + e.getMessage());
        }
    }

    private Properties grabUserData() {
        Scanner in = new Scanner(System.in);
        Properties props = new Properties();
        //TODO: uncomment
//        System.out.print("Enter sender gmail credentials: login> ");
//        props.put("user", in.nextLine());
//        System.out.print("pass> ");
//        props.put("password", in.nextLine());
//        System.out.print("\nEnter receiver gmail address > ");
//        props.put("receiver", in.nextLine());
        props.put("user", "stankin92614f2b@gmail.com");
        props.put("password", "92614f2b-4e25-4c8b-a3f4-53c4ec272023");
        props.put("receiver", "stankin92614f2b@gmail.com");
        return props;
    }

    private Message buildMessage(Session mailServerSession, Properties props) throws MessagingException, IOException {
        Message message = new MimeMessage(mailServerSession);
        message.setFrom(new InternetAddress(props.getProperty("user")));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(props.getProperty("receiver")));
        message.setSubject("Signed file");

        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(
                Base64.getEncoder().encodeToString(new Signature().sign(
                        new FileInputStream(getFile("attachment.txt")).readAllBytes()
                ))
        );

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.attachFile(getFile("attachment.txt"));
        multipart.addBodyPart(attachmentPart);

        message.setContent(multipart);
        return message;
    }

    private File getFile(String filename) {
        try {
            URI uri = this.getClass().getClassLoader().getResource(filename).toURI();
            return new File(uri);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to find file from resources: " + filename);
        }
    }

    private Session getSession(final String username, final String password) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
}
