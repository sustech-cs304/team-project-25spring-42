package sustech.cs304.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileUtils {

    public static String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }


    /**
     * AI-generated-content
     * tool: copilot
     * version: latest
     * usage: copilot help us to generate a function to get the file extension
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

    public static boolean ifDotFile(File file){
        return file.getName().startsWith(".");
    }

    public static void modifyEnvFile(String key, String value) {
        try {
            String filePath = "./" + File.separator + ".env";
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
