import model.SecureMessage;
import service.MessageService;
import service.UserService;
import storage.StorageManager;

public class SecureMessageTest {

    public static void main(String[] args) {

        try {

            // Initialize storage
            StorageManager.initialize();

            String sender = "alice";
            String receiver = "bob";

            String password = "Bob@123";

            String message =
                    "Hello Bob! This is a secure message.";

            // Register users
            UserService.registerUser(
                    sender,
                    "Alice@123"
            );

            UserService.registerUser(
                    receiver,
                    password
            );

            System.out.println(
                    "Users registered successfully."
            );

            // Send secure message
            SecureMessage secureMessage =
                    MessageService.sendMessage(
                            sender,
                            receiver,
                            message
                    );

            System.out.println(
                    "\nMessage encrypted successfully."
            );

            System.out.println(
                    "Message ID:"
            );

            System.out.println(
                    secureMessage.getMessageId()
            );

            System.out.println(
                    "\nEncrypted Message:"
            );

            System.out.println(
                    secureMessage.getEncryptedMessage()
            );

            System.out.println(
                    "\nEncrypted AES Key:"
            );

            System.out.println(
                    secureMessage.getEncryptedAESKey()
            );

            // Decrypt message
            String decryptedMessage =
                    MessageService.decryptMessage(
                            secureMessage
                    );

            System.out.println(
                    "\nDecrypted Message:"
            );

            System.out.println(
                    decryptedMessage
            );

            System.out.println(
                    "\nSECURE MESSAGE EXCHANGE SUCCESSFUL"
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}