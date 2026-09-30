package storage;

import java.io.File;

public class StorageManager {

    private static final String DATA_DIRECTORY = "data";
    private static final String USERS_DIRECTORY = "data/users";
    private static final String MESSAGES_DIRECTORY = "data/messages";
    private static final String KEYS_DIRECTORY = "data/keys";

    private StorageManager() {
    }

    public static void initialize() {

        createDirectory(DATA_DIRECTORY);
        createDirectory(USERS_DIRECTORY);
        createDirectory(MESSAGES_DIRECTORY);
        createDirectory(KEYS_DIRECTORY);
    }

    private static void createDirectory(String path) {

        File directory = new File(path);

        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public static String getUsersDirectory() {
        return USERS_DIRECTORY;
    }

    public static String getMessagesDirectory() {
        return MESSAGES_DIRECTORY;
    }

    public static String getKeysDirectory() {
        return KEYS_DIRECTORY;
    }
}