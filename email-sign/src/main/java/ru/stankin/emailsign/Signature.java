package ru.stankin.emailsign;

import javax.crypto.Cipher;
import java.io.File;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Arrays;

public class Signature {
    public byte[] sign(byte[] file) {
        try {
            System.out.println("Signing file..");
            KeyStore keyStore = KeyStore.getInstance(
                    new File(Signature.class.getClassLoader().getResource("sender_keystore.p12").getFile()),
                    "changeit".toCharArray()
            );
            PrivateKey privateKey = (PrivateKey) keyStore.getKey("senderKeyPair", "changeit".toCharArray());
            System.out.println("Private key successfully loaded!");

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] fileHash = md.digest(file);
            System.out.println("File hash is " + Arrays.toString(fileHash));

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] digitalSignature = cipher.doFinal(fileHash);
            System.out.println("File signature is " + Arrays.toString(digitalSignature));
            return digitalSignature;
        } catch (Exception e) {
            throw new RuntimeException("Failed to sign file!", e);
        }
    }

    public boolean validate(byte[] signature, byte[] file) {
        try {
            System.out.println("Validation file signature..");
            KeyStore keyStore = KeyStore.getInstance(
                    new File(Signature.class.getClassLoader().getResource("receiver_keystore.p12").getFile()),
                    "changeit".toCharArray()
            );
            Certificate certificate = keyStore.getCertificate("receiverKeyPair");
            PublicKey publicKey = certificate.getPublicKey();
            System.out.println("Public key successfully loaded!");

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] decryptedMessageHash = cipher.doFinal(signature);

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] newMessageHash = md.digest(file);
            boolean isCorrect = Arrays.equals(decryptedMessageHash, newMessageHash);
            System.out.println("File signature is correct: " + isCorrect);

            return isCorrect;
        } catch (Exception e) {
            throw new RuntimeException("Failed to validate file signature!", e);
        }
    }
}
