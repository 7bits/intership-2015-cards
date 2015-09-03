package it.sevenbits.cards.validation;
import java.security.*;
import java.util.Random;

public class Sha {
    public static String hash256() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] bytes = new byte[40];
        Random random = new Random();
        random.nextBytes(bytes);
        //md.update(data.getBytes());
        return bytesToHex(bytes);
    }
    public static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
}
