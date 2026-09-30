package crypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class SHA512Util {

    private static final String SHA_512 = "SHA-512";

    private SHA512Util() {
    }

    public static String hash(String message)
            throws Exception {

        MessageDigest digest =
                MessageDigest.getInstance(SHA_512);

        byte[] hashBytes =
                digest.digest(
                        message.getBytes(StandardCharsets.UTF_8)
                );

        return Base64.getEncoder()
                .encodeToString(hashBytes);
    }

    public static boolean verify(
            String message,
            String expectedHash) throws Exception {

        String actualHash = hash(message);

        return actualHash.equals(expectedHash);
    }
}