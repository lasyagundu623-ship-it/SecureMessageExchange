package crypto;

public class SHA512Test {

    public static void main(String[] args) {

        try {

            String originalMessage =
                    "This is a secure message.";

            String hash =
                    SHA512Util.hash(originalMessage);

            boolean verified =
                    SHA512Util.verify(
                            originalMessage,
                            hash
                    );

            System.out.println(
                    "Original Message:"
            );

            System.out.println(
                    originalMessage
            );

            System.out.println(
                    "\nSHA-512 Hash:"
            );

            System.out.println(
                    hash
            );

            System.out.println(
                    "\nIntegrity Verification:"
            );

            System.out.println(
                    verified ? "VALID" : "INVALID"
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}