package model;

public class User {

    private String username;
    private String password;
    private String publicKey;
    private String privateKey;

    public User(
            String username,
            String password,
            String publicKey,
            String privateKey) {

        this.username = username;
        this.password = password;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {

        return username
                + "|"
                + password
                + "|"
                + publicKey
                + "|"
                + privateKey;
    }

    public static User fromString(String data) {

        String[] parts = data.split("\\|", -1);

        if (parts.length != 4) {
            throw new IllegalArgumentException(
                    "Invalid user data."
            );
        }

        return new User(
                parts[0],
                parts[1],
                parts[2],
                parts[3]
        );
    }
}