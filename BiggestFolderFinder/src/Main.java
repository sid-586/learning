import java.io.File;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) {
        String folderPath = "/Users/dmitrijsidelnikov/Library/Mobile\\ Documents/com~apple~CloudDocs/Java";
        File file = new File(folderPath);
        System.out.println(file.length());
    }
}
