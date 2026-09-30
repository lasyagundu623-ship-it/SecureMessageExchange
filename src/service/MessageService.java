package service;

import crypto.AESUtil;
import crypto.HMACUtil;
import crypto.RSAUtil;
import crypto.SHA512Util;
import model.SecureMessage;
import model.User;
import storage.FileStorage;
import storage.StorageManager;

import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.UUID;

public class MessageService {

    private MessageService() {
    }

    public static SecureMessage sendMessage(
            String sender,
            String receiver,
            String message) throws Exception {

        if (sender == null ||
                receiver == null ||
                message == null ||
                message.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Invalid message details."
            );
        }

        User receiverUser =
                UserService.getUser(receiver);

        if (receiverUser == null) {
            throw new IllegalArgumentException(
                    "Receiver does not exist."
            );
        }

        /*
         * Step 1:
         * Generate a new AES session key.
         */
        SecretKey aesKey =
                AESUtil.generateKey();

        /*
         * Step 2:
         * Encrypt the actual message using AES-GCM.
         */
        String encryptedMessage =
                AESUtil.encrypt(
                        message,
                        aesKey
                );

        /*
         * Step 3:
         * Convert receiver's RSA public key.
         */
        PublicKey receiverPublicKey =
                RSAUtil.stringToPublicKey(
                        receiverUser.getPublicKey()
                );

        /*
         * Step 4:
         * Protect the AES session key using RSA.
         */
        String aesKeyString =
                AESUtil.keyToString(aesKey);

        String encryptedAESKey =
                RSAUtil.encrypt(
                        aesKeyString,
                        receiverPublicKey
                );

        /*
         * Step 5:
         * Generate SHA-512 hash of the original message.
         */
        String sha512Hash =
                SHA512Util.hash(message);

        /*
         * Step 6:
         * Generate HMAC using the AES session key.
         */
        String hmacKey =
                AESUtil.keyToString(aesKey);

        String hmac =
                HMACUtil.generateHMAC(
                        message,
                        hmacKey
                );

        /*
         * Step 7:
         * Create secure message object.
         */
        String messageId =
                UUID.randomUUID().toString();

        String timestamp =
                LocalDateTime.now().toString();

        SecureMessage secureMessage =
                new SecureMessage(
                        messageId,
                        sender,
                        receiver,
                        encryptedMessage,
                        encryptedAESKey,
                        sha512Hash,
                        hmac,
                        timestamp
                );

        /*
         * Step 8:
         * Store encrypted message.
         */
        String filePath =
                StorageManager.getMessagesDirectory()
                        + "/"
                        + messageId
                        + ".txt";

        FileStorage.write(
                filePath,
                secureMessage.toString()
        );

        return secureMessage;
    }

    public static String decryptMessage(
            SecureMessage secureMessage) throws Exception {

        User receiverUser =
                UserService.getUser(
                        secureMessage.getReceiver()
                );

        if (receiverUser == null) {
            throw new IllegalArgumentException(
                    "Receiver does not exist."
            );
        }

        /*
         * Step 1:
         * Recover receiver's private RSA key.
         */
        java.security.PrivateKey privateKey =
                RSAUtil.stringToPrivateKey(
                        receiverUser.getPrivateKey()
                );

        /*
         * Step 2:
         * Decrypt the AES session key using RSA.
         */
        String aesKeyString =
                RSAUtil.decrypt(
                        secureMessage.getEncryptedAESKey(),
                        privateKey
                );

        SecretKey aesKey =
                AESUtil.stringToKey(aesKeyString);

        /*
         * Step 3:
         * Decrypt the actual message using AES.
         */
        String decryptedMessage =
                AESUtil.decrypt(
                        secureMessage.getEncryptedMessage(),
                        aesKey
                );

        /*
         * Step 4:
         * Verify SHA-512.
         */
        boolean hashValid =
                SHA512Util.verify(
                        decryptedMessage,
                        secureMessage.getSha512Hash()
                );

        if (!hashValid) {
            throw new SecurityException(
                    "SHA-512 integrity verification failed."
            );
        }

        /*
         * Step 5:
         * Verify HMAC.
         */
        String hmacKey =
                AESUtil.keyToString(aesKey);

        boolean hmacValid =
                HMACUtil.verifyHMAC(
                        decryptedMessage,
                        secureMessage.getHmac(),
                        hmacKey
                );

        if (!hmacValid) {
            throw new SecurityException(
                    "HMAC authentication failed."
            );
        }

        return decryptedMessage;
    }

    public static SecureMessage getMessage(
            String messageId) throws Exception {

        String filePath =
                StorageManager.getMessagesDirectory()
                        + "/"
                        + messageId
                        + ".txt";

        if (!FileStorage.exists(filePath)) {
            return null;
        }

        String data =
                FileStorage.read(filePath);

        if (data.isEmpty()) {
            return null;
        }

        return SecureMessage.fromString(data);
    }
}