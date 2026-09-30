package crypto;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

public class RSAUtil {

    private static final String RSA = "RSA";
    private static final int KEY_SIZE = 2048;

    private RSAUtil() {
    }

    // Generate RSA public and private key pair
    public static KeyPair generateKeyPair() throws Exception {

        KeyPairGenerator keyPairGenerator =
                KeyPairGenerator.getInstance(RSA);

        keyPairGenerator.initialize(KEY_SIZE);

        return keyPairGenerator.generateKeyPair();
    }

    // Encrypt data using RSA public key
    public static String encrypt(
            String data,
            PublicKey publicKey) throws Exception {

        Cipher cipher =
                Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");

        cipher.init(
                Cipher.ENCRYPT_MODE,
                publicKey
        );

        byte[] encryptedBytes =
                cipher.doFinal(
                        data.getBytes(StandardCharsets.UTF_8)
                );

        return Base64.getEncoder()
                .encodeToString(encryptedBytes);
    }

    // Decrypt data using RSA private key
    public static String decrypt(
            String encryptedData,
            PrivateKey privateKey) throws Exception {

        Cipher cipher =
                Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");

        cipher.init(
                Cipher.DECRYPT_MODE,
                privateKey
        );

        byte[] encryptedBytes =
                Base64.getDecoder()
                        .decode(encryptedData);

        byte[] decryptedBytes =
                cipher.doFinal(encryptedBytes);

        return new String(
                decryptedBytes,
                StandardCharsets.UTF_8
        );
    }

    // Convert public key to Base64
    public static String publicKeyToString(
            PublicKey publicKey) {

        return Base64.getEncoder()
                .encodeToString(
                        publicKey.getEncoded()
                );
    }

    // Convert private key to Base64
    public static String privateKeyToString(
            PrivateKey privateKey) {

        return Base64.getEncoder()
                .encodeToString(
                        privateKey.getEncoded()
                );
    }

    // Convert Base64 string back to public key
    public static PublicKey stringToPublicKey(
            String keyString) throws Exception {

        byte[] keyBytes =
                Base64.getDecoder()
                        .decode(keyString);

        X509EncodedKeySpec keySpec =
                new X509EncodedKeySpec(keyBytes);

        KeyFactory keyFactory =
                KeyFactory.getInstance(RSA);

        return keyFactory.generatePublic(keySpec);
    }

    // Convert Base64 string back to private key
    public static PrivateKey stringToPrivateKey(
            String keyString) throws Exception {

        byte[] keyBytes =
                Base64.getDecoder()
                        .decode(keyString);

        PKCS8EncodedKeySpec keySpec =
                new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory =
                KeyFactory.getInstance(RSA);

        return keyFactory.generatePrivate(keySpec);
    }
}