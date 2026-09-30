package crypto;

import java.security.KeyPair;

public class RSATest {

    public static void main(String[] args) {

        try {

            String originalMessage =
                    "Secure AES session key";

            KeyPair keyPair =
                    RSAUtil.generateKeyPair();

            String encrypted =
                    RSAUtil.encrypt(
                            originalMessage,
                            keyPair.getPublic()
                    );

            String decrypted =
                    RSAUtil.decrypt(
                            encrypted,
                            keyPair.getPrivate()
                    );

            System.out.println(
                    "Original Data:"
            );

            System.out.println(
                    originalMessage
            );

            System.out.println(
                    "\nEncrypted Data:"
            );

            System.out.println(
                    encrypted
            );

            System.out.println(
                    "\nDecrypted Data:"
            );

            System.out.println(
                    decrypted
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}