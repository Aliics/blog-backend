package fish.eyebrow.blog.backend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtil {
    public static String readFile(String fileName) {
        Path filePath = Paths.get(ClassLoader.getSystemResource(fileName).getPath());
        try {
            return Files.readString(filePath);
        }
        catch (IOException e) {
            return null;
        }
    }
}
