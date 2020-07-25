import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        String folderPath = "/Users/dmitrijsidelnikov/Library/Mobile Documents/com~apple~CloudDocs/Java";
        File file = new File(folderPath);
        long sum = getFolderSize(file);
        System.out.println(sum);
//        FolderSizeCalculator calculator = new FolderSizeCalculator(file);
        ForkJoinPool pool = new ForkJoinPool();
//        long size = pool.invoke(calculator);
//        System.out.println(size);
        String humanReadableSize = getHumanReadableSize(sum);
        System.out.println(humanReadableSize);
        System.out.println(getSizeFromHumanReadable(humanReadableSize));
    }

    public static long getFolderSize(File folder) {
        if (folder.isFile()) {
            return folder.length();
        }
        long sum = 0;
        File[] files = folder.listFiles();
        for (File file : files) {
            sum += getFolderSize(file);
        }
        return sum;
    }

    public static String getHumanReadableSize(Long sizeOfFiles) {
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
            gigabytes = Integer.toString(gb) + "Gb ";
        }
        if (mb != 0) {
            megabytes = Integer.toString(mb) + "Mb ";
        }
        if (kb != 0) {
            kilobytes = Integer.toString(kb) + "kb ";
        }
        bytes = Integer.toString(b) + "bytes";

        return gigabytes + megabytes + kilobytes + bytes;
    }

    public static long getSizeFromHumanReadable(String size) {


        long sizeFull = 0;
        String[] fragments = size.split(" ");
        for (int i = 0; i < fragments.length; i++) {
            if (fragments[i].contains("Gb")) {
                sizeFull += Long.parseLong(fragments[i].split("Gb")[0]) * Math.pow(1024, 3);
            }
            if (fragments[i].contains("Mb")) {
                sizeFull += Long.parseLong(fragments[i].split("Mb")[0]) * Math.pow(1024, 2);
            }
            if (fragments[i].contains("kb")) {
                sizeFull += Long.parseLong(fragments[i].split("kb")[0]) * 1024;
            }
            if (fragments[i].contains("bytes")) {
                sizeFull += Long.parseLong(fragments[i].split("bytes")[0]);
            }
        }
        return sizeFull;
    }
}