package crypto;

public class HMACTest {

    public static void main(String[] args) {

        try {

            String originalMessage =
                    "This is an authenticated message.";

            String secretKey =
                    HMACUtil.generateKey();

            String hmac =
                    HMACUtil.generateHMAC(
                            originalMessage,
                            secretKey
                    );

            boolean verified =
                    HMACUtil.verifyHMAC(
                            originalMessage,
                            hmac,
                            secretKey
                    );

            System.out.println(
                    "Original Message:"
            );

            System.out.println(
                    originalMessage
            );

            System.out.println(
                    "\nHMAC:"
            );

            System.out.println(
                    hmac
            );

            System.out.println(
                    "\nHMAC Verification:"
            );

            System.out.println(
                    verified ? "VALID" : "INVALID"
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}