import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {

        Path startPass = Paths.get("/Users/dmitrijsidelnikov/Library/Mobile Documents/com~apple~CloudDocs/Java/java_basics");
        MyFileFindVisitors myFileFindVisitors = new MyFileFindVisitors();
        try {
            Files.walkFileTree(startPass, myFileFindVisitors);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(getOutputMessage(myFileFindVisitors.sizeOfFiles, startPass.getFileName()));
    }

    public static String getOutputMessage(Long sizeOfFiles, Path startPass) {
        System.out.println(sizeOfFiles);
        String[] units = new String[]{"b", "kb", "Mb", "Gb"};
        String[] values = new String[units.length];
        for (int i = 0; i < values.length; i++) {
            long tempValue = (long) (sizeOfFiles / Math.pow(1024, i)) % 1024;
            values[i] = tempValue > 0 ? tempValue + units[i] + " " : "";
        }
        String bytes = values[0];
        String kilobytes = values[1];
        String megabytes = values[2];
        String gigabytes = values[3];

        return "Total size of all files in " + startPass + " directory is " + gigabytes + megabytes + kilobytes + bytes;
    }
}
