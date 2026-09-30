package crypto;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

    private static final String AES = "AES";
    private static final String AES_GCM = "AES/GCM/NoPadding";

    private static final int KEY_SIZE = 256;
    private static final int IV_SIZE = 12;
    private static final int TAG_SIZE = 128;

    private AESUtil() {
    }

    public static SecretKey generateKey() throws Exception {

        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        keyGenerator.init(KEY_SIZE);

        return keyGenerator.generateKey();
    }

    public static String encrypt(
            String plainText,
            SecretKey secretKey) throws Exception {

        byte[] iv = new byte[IV_SIZE];

        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);

        GCMParameterSpec gcmParameterSpec =
                new GCMParameterSpec(TAG_SIZE * 1, iv);

        Cipher cipher = Cipher.getInstance(AES_GCM);

        cipher.init(
                Cipher.ENCRYPT_MODE,
                secretKey,
                gcmParameterSpec
        );

        byte[] encryptedBytes =
                cipher.doFinal(
                        plainText.getBytes(StandardCharsets.UTF_8)
                );

        byte[] combined =
                new byte[iv.length + encryptedBytes.length];

        System.arraycopy(
                iv,
                0,
                combined,
                0,
                iv.length
        );

        System.arraycopy(
                encryptedBytes,
                0,
                combined,
                iv.length,
                encryptedBytes.length
        );

        return Base64.getEncoder()
                .encodeToString(combined);
    }

    public static String decrypt(
            String encryptedText,
            SecretKey secretKey) throws Exception {

        byte[] combined =
                Base64.getDecoder()
                        .decode(encryptedText);

        byte[] iv =
                new byte[IV_SIZE];

        byte[] encryptedBytes =
                new byte[combined.length - IV_SIZE];

        System.arraycopy(
                combined,
                0,
                iv,
                0,
                IV_SIZE
        );

        System.arraycopy(
                combined,
                IV_SIZE,
                encryptedBytes,
                0,
                encryptedBytes.length
        );

        GCMParameterSpec gcmParameterSpec =
                new GCMParameterSpec(TAG_SIZE, iv);

        Cipher cipher =
                Cipher.getInstance(AES_GCM);

        cipher.init(
                Cipher.DECRYPT_MODE,
                secretKey,
                gcmParameterSpec
        );

        byte[] decryptedBytes =
                cipher.doFinal(encryptedBytes);

        return new String(
                decryptedBytes,
                StandardCharsets.UTF_8
        );
    }

    public static String keyToString(
            SecretKey secretKey) {

        return Base64.getEncoder()
                .encodeToString(
                        secretKey.getEncoded()
                );
    }

    public static SecretKey stringToKey(
            String encodedKey) {

        byte[] decodedKey =
                Base64.getDecoder()
                        .decode(encodedKey);

        return new SecretKeySpec(
                decodedKey,
                AES
        );
    }
}