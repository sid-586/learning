import java.util.regex.Pattern;

public class ReadableSizeCalculator {
    public static String getHumanReadableSize(Long sizeOfFiles) {

        String[] units = new String[]{"b", "kb", "Mb", "Gb"};
        String[] values = new String[units.length];
        for (int i = 0; i < values.length; i++) {
            long tempValue = (long) (sizeOfFiles / Math.pow(1024, i)) % 1024;
            values[i] = tempValue > 0 ? tempValue + units[i] + " " : "";
        }
        return values[3] + values[2] + values[1] + values[0];
    }

    public static long getSizeFromHumanReadable(String size) {

        long sizeFull = 0;
        String[] regEx = new String[]{"b", "kb", "Mb", "Gb"};
        String[] fragments = size.split(" ");

        int fragmentsLength = getFillingNote(regEx, fragments);
        String[] realSizeFragments = new String[fragmentsLength];
        for (int j = 0; j < fragments.length; j++) {
            realSizeFragments[j] = fragments[j];
        }
        for (int j = fragments.length; j < fragmentsLength; j++) {
            realSizeFragments[j] = "0b";
        }
        for (int i = 0; i < fragmentsLength; i++) {
            Pattern pattern = Pattern.compile("\\d+" + regEx[fragmentsLength - 1 - i]);
            if (pattern.matcher(realSizeFragments[i]).find()) {
                sizeFull += Long
                        .parseLong(realSizeFragments[i].split(regEx[fragmentsLength - 1 - i])[0])
                        * Math.pow(1024, realSizeFragments.length - 1 - i);
            }
        }
        return sizeFull;
    }

    private static int getFillingNote(String[] regEx, String[] fragments) {

        int fillingNoteLength = 0;
        for (int i = regEx.length - 1; i >= 0; i--) {
            for (String note : fragments) {
                if (note.contains(regEx[i])) {
                    fillingNoteLength = i + 1;
                    return fillingNoteLength;
                }
            }
        }
        return fillingNoteLength;
    }
}
