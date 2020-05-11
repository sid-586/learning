package Util;

public class Names {

    public static String getName() {
        char[] someChar = new char[26];
        for (int indexOfChar = 1040, i = 0; indexOfChar <= 1065; indexOfChar++, i++) {
            someChar[i] = (char) indexOfChar;
        }
        String name;
        char[] chars = new char[8];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = someChar[(int) (Math.random() * (someChar.length - 1))];
            sb.append(chars[i]);
        }
        name = String.valueOf(sb);
        return name;
    }
}
