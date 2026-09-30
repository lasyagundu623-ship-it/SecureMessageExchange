package model;

public class SecureMessage {

    private String messageId;
    private String sender;
    private String receiver;
    private String encryptedMessage;
    private String encryptedAESKey;
    private String sha512Hash;
    private String hmac;
    private String timestamp;

    public SecureMessage(
            String messageId,
            String sender,
            String receiver,
            String encryptedMessage,
            String encryptedAESKey,
            String sha512Hash,
            String hmac,
            String timestamp) {

        this.messageId = messageId;
        this.sender = sender;
        this.receiver = receiver;
        this.encryptedMessage = encryptedMessage;
        this.encryptedAESKey = encryptedAESKey;
        this.sha512Hash = sha512Hash;
        this.hmac = hmac;
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getEncryptedMessage() {
        return encryptedMessage;
    }

    public String getEncryptedAESKey() {
        return encryptedAESKey;
    }

    public String getSha512Hash() {
        return sha512Hash;
    }

    public String getHmac() {
        return hmac;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {

        return messageId
                + "|"
                + sender
                + "|"
                + receiver
                + "|"
                + encryptedMessage
                + "|"
                + encryptedAESKey
                + "|"
                + sha512Hash
                + "|"
                + hmac
                + "|"
                + timestamp;
    }

    public static SecureMessage fromString(
            String data) {

        String[] parts =
                data.split("\\|", -1);

        if (parts.length != 8) {

            throw new IllegalArgumentException(
                    "Invalid secure message data."
            );
        }

        return new SecureMessage(
                parts[0],
                parts[1],
                parts[2],
                parts[3],
                parts[4],
                parts[5],
                parts[6],
                parts[7]
        );
    }
}