package crypto;

import javax.crypto.SecretKey;

public class AESTest {

    public static void main(String[] args) {

        try {

            String originalMessage =
                    "Hello, this is a secure message.";

            SecretKey key =
                    AESUtil.generateKey();

            String encrypted =
                    AESUtil.encrypt(
                            originalMessage,
                            key
                    );

            String decrypted =
                    AESUtil.decrypt(
                            encrypted,
                            key
                    );

            System.out.println(
                    "Original Message:"
            );

            System.out.println(
                    originalMessage
            );

            System.out.println(
                    "\nEncrypted Message:"
            );

            System.out.println(
                    encrypted
            );

            System.out.println(
                    "\nDecrypted Message:"
            );

            System.out.println(
                    decrypted
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}