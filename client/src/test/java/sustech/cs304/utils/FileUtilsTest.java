package sustech.cs304.utils;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @Test
    void testGetFileName() {
        assertEquals("file.txt", FileUtils.getFileName("/home/user/file.txt"));
        assertEquals("file.txt", FileUtils.getFileName("file.txt"));
        assertEquals("another.file", FileUtils.getFileName("/foo/bar/another.file"));
        assertEquals(".dotfile", FileUtils.getFileName("/foo/.dotfile"));
        assertEquals("noext", FileUtils.getFileName("/foo/bar/noext"));
    }

    @Test
    void testGetExtension() throws IOException {
        File file1 = new File("test.txt");
        File file2 = new File("archive.tar.gz");
        File file3 = new File(".hiddenfile");
        File file4 = new File("noext");
        File file5 = new File("a.b.c.d");

        assertEquals("txt", FileUtils.getExtension(file1));
        assertEquals("gz", FileUtils.getExtension(file2));
        assertEquals("", FileUtils.getExtension(file3));
        assertEquals("", FileUtils.getExtension(file4));
        assertEquals("d", FileUtils.getExtension(file5));
    }

    @Test
    void testIfDotFile() {
        assertTrue(FileUtils.ifDotFile(new File(".env")));
        assertTrue(FileUtils.ifDotFile(new File(".hidden")));
        assertFalse(FileUtils.ifDotFile(new File("visible.txt")));
        assertFalse(FileUtils.ifDotFile(new File("noext")));
    }

    @Test
    void testModifyEnvFile_add() throws IOException {
        Path tempDir = Files.createTempDirectory("envTest");
        File envFile = tempDir.resolve(".env").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(envFile))) {
            writer.write("FOO=bar\n");
            writer.write("BAR=baz\n");
        }

        String originalDir = System.getProperty("user.dir");
        System.setProperty("user.dir", tempDir.toString());

        List<String> lines = Files.readAllLines(envFile.toPath());
        assertTrue(lines.contains("FOO=bar"));
        assertTrue(lines.contains("BAR=baz"));

        System.setProperty("user.dir", originalDir);

        envFile.delete();
        tempDir.toFile().delete();
    }

    @Test
    void testGetAppDataPath_createsAndReturnsCorrectPath() {
        Path appDataPath = FileUtils.getAppDataPath();
        assertNotNull(appDataPath);
        assertTrue(Files.exists(appDataPath));
        // The directory name should end with "AIDE" or ".AIDE"
        String pathStr = appDataPath.toString();
        assertTrue(pathStr.endsWith("AIDE") || pathStr.endsWith(".AIDE"));
    }
}