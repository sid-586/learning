import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class MyFileCopyVisitors extends SimpleFileVisitor<Path> {

    private Path source, destination;

    public MyFileCopyVisitors(Path source, Path destination) {
        this.source = source;
        this.destination = destination;
    }

    public FileVisitResult visitFile(Path path, BasicFileAttributes fileAttributes) {
        Path newPathDestination = destination.resolve(source.relativize(path));
        try {
            Files.copy(path, newPathDestination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes fileAttributes) {
        Path newPathDestination = destination.resolve(source.relativize(path));
        try {
            Files.copy(path, newPathDestination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return FileVisitResult.CONTINUE;
    }

}
