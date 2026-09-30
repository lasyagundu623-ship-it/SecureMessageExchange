package storage;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileStorage {

    private FileStorage() {
    }

    public static void write(
            String filePath,
            String content) throws IOException {

        File file = new File(filePath);

        File parent = file.getParentFile();

        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new OutputStreamWriter(
                                     new FileOutputStream(file),
                                     StandardCharsets.UTF_8))) {

            writer.write(content);
        }
    }

    public static String read(
            String filePath) throws IOException {

        File file = new File(filePath);

        if (!file.exists()) {
            return "";
        }

        StringBuilder content =
                new StringBuilder();

        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     new FileInputStream(file),
                                     StandardCharsets.UTF_8))) {

            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
        }

        return content.toString().trim();
    }

    public static boolean exists(
            String filePath) {

        return new File(filePath).exists();
    }

    public static void delete(
            String filePath) {

        File file = new File(filePath);

        if (file.exists()) {
            file.delete();
        }
    }
}