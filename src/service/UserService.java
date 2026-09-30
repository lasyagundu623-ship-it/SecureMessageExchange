package service;

import crypto.RSAUtil;
import model.User;
import storage.FileStorage;
import storage.StorageManager;

import java.security.KeyPair;

public class UserService {

    private UserService() {
    }

    public static boolean registerUser(
            String username,
            String password) throws Exception {

        if (username == null ||
                username.trim().isEmpty()) {

            return false;
        }

        if (password == null ||
                password.isEmpty()) {

            return false;
        }

        username = username.trim();

        if (getUser(username) != null) {
            return false;
        }

        KeyPair keyPair =
                RSAUtil.generateKeyPair();

        String publicKey =
                RSAUtil.publicKeyToString(
                        keyPair.getPublic()
                );

        String privateKey =
                RSAUtil.privateKeyToString(
                        keyPair.getPrivate()
                );

        User user =
                new User(
                        username,
                        password,
                        publicKey,
                        privateKey
                );

        String filePath =
                getUserFilePath(username);

        FileStorage.write(
                filePath,
                user.toString()
        );

        return true;
    }

    public static User getUser(
            String username) throws Exception {

        if (username == null ||
                username.trim().isEmpty()) {

            return null;
        }

        String filePath =
                getUserFilePath(username.trim());

        if (!FileStorage.exists(filePath)) {
            return null;
        }

        String data =
                FileStorage.read(filePath);

        if (data.isEmpty()) {
            return null;
        }

        return User.fromString(data);
    }

    public static boolean authenticate(
            String username,
            String password) throws Exception {

        User user =
                getUser(username);

        if (user == null) {
            return false;
        }

        return user.getPassword()
                .equals(password);
    }

    private static String getUserFilePath(
            String username) {

        return StorageManager
                .getUsersDirectory()
                + "/"
                + username
                + ".txt";
    }
}