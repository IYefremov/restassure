//Given a string, return a string made of the first 2 chars (if present),
// ehowever include first char only if it is 'o' and include the second only if it is 'z', so "ozymandias" yields "oz".
//
//
//        startOz("ozymandias") → "oz"
//        startOz("bzoo") → "z"
//        startOz("oxx") → "o"

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(startOz("oymandias"));
    }

    public static String startOz(String str) {
        String resStr = "";
        char char1, char2;

        if (str.length() == 0) return "";
        if (str.length() >= 1) {
            char1 = str.charAt(0);
            if (char1 == 'o') {
                resStr = String.valueOf(char1);
            }
        }
        if (str.length() >= 2) {
            char2 = str.charAt(1);
            if (char2 == 'z') {
                resStr = resStr + String.valueOf(char2);
            }
        }

        if (resStr.equals("o") || resStr.equals("z") || resStr.equals("oz")) return resStr;

        return "";

    }
}
