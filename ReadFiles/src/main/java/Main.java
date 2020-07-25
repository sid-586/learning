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
        int b, kb, mb, gb;
        String bytes = "";
        String kilobytes = "";
        String megabytes = "";
        String gigabytes = "";
        b = (int) (sizeOfFiles % 1024);
        kb = (int) (sizeOfFiles / 1024) % 1024;
        mb = (int) ((sizeOfFiles / 1024) / 1024) % 1024;
        gb = (int) ((sizeOfFiles / 1024) / 1024 / 1024) % 1024;
        if (gb != 0) {
            gigabytes = Integer.toString(gb) + " Gb ";
        }
        if (mb != 0) {
            megabytes = Integer.toString(mb) + " Mb ";
        }
        if (kb != 0) {
            kilobytes = Integer.toString(kb) + " kb ";
        }
        bytes = Integer.toString(b) + " bytes";
        return "Total size of all files in " + startPass + " directory is " + gigabytes + megabytes + kilobytes + bytes;
    }
}
