import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class MyFileFindVisitors extends SimpleFileVisitor<Path> {

    public long sizeOfFiles;

    public FileVisitResult visitFile(Path path, BasicFileAttributes fileAttributes) {
        try {
            sizeOfFiles += Files.size(path);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return FileVisitResult.CONTINUE;
    }
}

