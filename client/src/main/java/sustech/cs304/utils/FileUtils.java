package sustech.cs304.utils;

import java.io.File;

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
        if (lastDotIndex > 0) { // 确保文件名中包含 "."
            extension = fileName.substring(lastDotIndex + 1);
        }
        return extension;
    }

    public static boolean ifDotFile(File file){
        return file.getName().startsWith(".");
    }
}
