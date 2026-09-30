package crypto;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class HMACUtil {

    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final int KEY_SIZE = 32;

    private HMACUtil() {
    }

    public static String generateKey() {

        byte[] keyBytes = new byte[KEY_SIZE];

        SecureRandom secureRandom =
                new SecureRandom();

        secureRandom.nextBytes(keyBytes);

        return Base64.getEncoder()
                .encodeToString(keyBytes);
    }

    public static String generateHMAC(
            String message,
            String secretKey) throws Exception {

        byte[] keyBytes =
                Base64.getDecoder()
                        .decode(secretKey);

        SecretKeySpec key =
                new SecretKeySpec(
                        keyBytes,
                        HMAC_ALGORITHM
                );

        Mac mac =
                Mac.getInstance(HMAC_ALGORITHM);

        mac.init(key);

        byte[] hmacBytes =
                mac.doFinal(
                        message.getBytes(
                                StandardCharsets.UTF_8
                        )
                );

        return Base64.getEncoder()
                .encodeToString(hmacBytes);
    }

    public static boolean verifyHMAC(
            String message,
            String expectedHMAC,
            String secretKey) throws Exception {

        String actualHMAC =
                generateHMAC(
                        message,
                        secretKey
                );

        return actualHMAC.equals(expectedHMAC);
    }
}