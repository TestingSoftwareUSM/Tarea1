import javax.crypto.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class Encrypter {
    private final Cipher cipher;
    private Key secretKey;
    String keyFilePath = "key.txt";

    public Encrypter() throws Exception {
        this.cipher = Cipher.getInstance("AES");
        this.secretKey = loadSecretKey(keyFilePath);

        if (this.secretKey == null) {
            this.secretKey = generateSecretKey();
            saveSecretKey(keyFilePath, this.secretKey);
        }
    }

    private Key generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }

    private Key loadSecretKey(String keyFilePath) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(keyFilePath))) {
            return (Key) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    private void saveSecretKey(String keyFilePath, Key key) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(keyFilePath))) {
            outputStream.writeObject(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String plaintext) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedText) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);

        // Decrypt the ciphertext
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        // Convert the decrypted bytes back to a string
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}