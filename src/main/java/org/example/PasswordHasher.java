package org.example;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class PasswordHasher {

    public static void main(String[] args) {
        //hashiis 10 milions password
        hashingPassword();

        isPasswordMatch("vjht2006", "BC264F23ED755395ED2B00C17A7F5F12");
        String plainText = "vjht2006";
        String hashedPassword = "BC264F23ED755395ED2B00C17A7F5F12";
        if (isPasswordMatch(plainText, hashedPassword)) {
            System.out.println("The password and hash given match.");
        } else {
            System.out.println("The password and hash is incorrect.");
        }

        findPlainText("md5","A30C68F2FF01CE8219717ECB5F2D931A");
    }

    /**
     * hashing 10 milions password into md5
     */
    public static void hashingPassword (){
        try {
            List<String> passwords = Files.readAllLines(Paths.get("/home/stykle/10milionPassowrd"));

            for (String password : passwords) {
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(password.getBytes());
                    byte[] digest = md.digest();
                    String hash = bytesToHex(digest);
                    System.out.println("Hash of " + password + ": " + hash);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = HEX_ARRAY[v >>> 4];
            hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static boolean isPasswordMatch(String plainTextPassword, String hashedPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainTextPassword.getBytes());
            byte[] digest = md.digest();
            String hash = bytesToHex(digest);
            return hash.equals(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void findPlainText(String hashingAlgorithm, String hashValue) {
        try {
            List<String> passwords = Files.readAllLines(Paths.get("/home/stykle/10milionPassowrd"));
            for (String password : passwords) {
                try {
                    MessageDigest md = MessageDigest.getInstance(hashingAlgorithm);
                    md.update(password.getBytes());
                    byte[] digest = md.digest();
                    String hash = bytesToHex(digest);
                    if (hash.equals(hashValue)) {
                        System.out.println("Matching plain text: " + password);
                        return;
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("No matching plain text found.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
