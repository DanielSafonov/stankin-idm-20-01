package ru.stankin.emailsign;

import jakarta.mail.*;
import jakarta.mail.internet.MimeMultipart;

import java.util.Base64;
import java.util.Properties;
import java.util.Scanner;

public class Consumer {
    public void startConsumer() {
        System.out.println("Consumer was started..");
        Properties props = grabUserData();
        try {
            Store store = getSession(props.getProperty("user"), props.getProperty("password")).getStore("imap");
            store.connect();
            Folder inbox = store.getFolder(new URLName("INBOX"));
            inbox.open(Folder.READ_ONLY);
            Message[] messages = inbox.getMessages();
            System.out.println("Found " + messages.length + " messages. Read last with subject \"Signed file\"..");
            int lastMessageWithSignedFile = -1;
            for (int i = 0; i < messages.length; i++) {
                if ("Signed file".equals(messages[i].getSubject()) && messages[i].isMimeType("multipart/*")) {
                    lastMessageWithSignedFile = i;
                }
            }
            if (lastMessageWithSignedFile == -1) {
                throw new RuntimeException("Email with signed file not found!");
            }
            Message msg = messages[lastMessageWithSignedFile];
            MimeMultipart content = (MimeMultipart) msg.getContent();
            byte[] attachedFile = null;
            String fileSignature = null;
            for (int i = 0; i < content.getCount(); i++) {
                if(content.getBodyPart(i).getDisposition() == null){
                    fileSignature = (String) content.getBodyPart(i).getContent();
                }
                if ("ATTACHMENT".equals(content.getBodyPart(i).getDisposition())) {
                    attachedFile = ((String) content.getBodyPart(i).getContent()).getBytes();
                }
            }
            if (attachedFile == null || fileSignature == null) {
                throw new RuntimeException("Attached file or signature not found in message!");
            }
            new Signature().validate(Base64.getDecoder().decode(fileSignature), attachedFile);
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
        props.put("user", "stankin92614f2b@gmail.com");
        props.put("password", "92614f2b-4e25-4c8b-a3f4-53c4ec272023");
        return props;
    }

    private Session getSession(final String username, final String password) {
        Properties props = new Properties();
        props.put("mail.store.protocol", "imap");
        props.put("mail.imap.host", "imap.gmail.com");
        props.put("mail.imap.ssl.enable", "true");
        props.put("mail.imap.port", "993");

        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
}
