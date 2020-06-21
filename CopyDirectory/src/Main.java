import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Path pathSource, pathDestination;
        Scanner scanner = new Scanner(System.in);

        for (; ; ) {
            System.out.println("Введите абсолютный путь к копируемому каталогу/файлу");
            pathSource = Paths.get(scanner.nextLine());
            if (!Files.exists(pathSource, LinkOption.NOFOLLOW_LINKS)) {
                System.out.println("Путь введен некорректно(");
                continue;
            } else break;
        }
        System.out.println("Введите абсолютный путь к каталогу назначения");
        pathDestination = Paths.get(scanner.nextLine());
        try {
            Files.walkFileTree(pathSource, new MyFileCopyVisitors(pathSource, pathDestination));
            System.out.println("Каталог/файлы скопированы успешно");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
