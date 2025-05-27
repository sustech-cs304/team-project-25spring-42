package sustech.cs304.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Utility class for various file-related operations such as extracting file names,
 * extensions, handling dotfiles, modifying environment variable files, and
 * determining application data storage paths across operating systems.
 */
public class FileUtils {

    /**
     * Returns the file name from a given path.
     *
     * @param path the full file path
     * @return the file name portion of the path
     */
    public static String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }


    /**
     * AI-generated-content
     * tool: copilot
     * version: latest
     * usage: copilot help us to generate a function to get the file extension
     */
    /**
     * Returns the file extension of the provided File.
     *
     * @param file the file to extract extension from
     * @return the file extension (without dot), or an empty string if none exists
     */
    public static String getExtension(File file){
        String fileName = file.getName();
        String extension = "";
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            extension = fileName.substring(lastDotIndex + 1);
        }
        return extension;
    }

    /**
     * Checks if the given File is a dotfile (starts with a '.').
     *
     * @param file the file to check
     * @return true if the file is a dotfile, false otherwise
     */
    public static boolean ifDotFile(File file){
        return file.getName().startsWith(".");
    }

    /**
     * Modifies or appends a key-value pair in a .env file located at the application root.
     * If the key exists, its value is replaced; otherwise, the key-value pair is added.
     *
     * @param key   the environment variable key
     * @param value the value to set for the key
     */
    public static void modifyEnvFile(String key, String value) {
        try {
            String filePath = getAppDataPath().toString() + File.separator + ".env";
            if (!Files.exists(Paths.get(filePath))) {
                Files.createFile(Paths.get(filePath));
            }
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            boolean keyExists = false;
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith(key)) {
                    lines.set(i, key + "=" + value);
                    keyExists = true;
                    break;
                }
            }
            if (!keyExists) {
                lines.add(key + "=" + value);
            }
            Files.write(Paths.get(filePath), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the value of a specified environment variable from the .env file.
     * If the key does not exist, returns null.
     *
     * @param key the environment variable key to look for
     * @return the value associated with the key, or null if not found
     */
    public static String getEnvValue(String key) {
        try {
            String filePath = getAppDataPath().toString() + File.separator + ".env";
            if (!Files.exists(Paths.get(filePath))) {
                Files.createFile(Paths.get(filePath));
            }
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                if (line.startsWith(key + "=")) {
                    return line.substring((key + "=").length());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the path to the application's data directory, based on the OS.
     * Creates the directory if it does not exist.
     * <ul>
     *   <li>Windows: %USER_HOME%\AppData\Roaming\AIDE</li>
     *   <li>macOS:  %USER_HOME%/Library/Application Support/AIDE</li>
     *   <li>Linux/other: %USER_HOME%/.AIDE</li>
     * </ul>
     *
     * @return the Path object for the app data directory
     */
    public static Path getAppDataPath() {
        String os = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");

        Path appDataPath;

        if (os.contains("win")) {
            appDataPath = Paths.get(userHome, "AppData", "Roaming", "AIDE");
        } else if (os.contains("mac")) {
            appDataPath = Paths.get(userHome, "Library", "Application Support", "AIDE");
        } else {
            appDataPath = Paths.get(userHome, ".AIDE");
        }
        if (!Files.exists(appDataPath)) {
            try {
                Files.createDirectories(appDataPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return appDataPath;
    }
}
