//Look for patterns like "zip" and "zap" in the string -- length-3, starting with 'z' and ending with 'p'.
// Return a string where for all such words, the middle letter is gone, so "zipXzap" yields "zpXzp".
//
//
//        zipZap("zipXzap") → "zpXzp"
//        zipZap("zopzop") → "zpzp"
//        zipZap("zzzopzop") → "zzzpzp"

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(zipZap("zzzopzop"));
    }

    public static String zipZap(String str) {
        if (str.length() < 3) return str;
        StringBuilder string = new StringBuilder(str);
        char fistCh, secCh, thirdCh;

        int i = 0;
        while (i < string.length() - 2) {
            fistCh = string.charAt(i);
            //  secCh = string.charAt(i+1);
            thirdCh = string.charAt(i + 2);
            if (fistCh == 'z' && thirdCh == 'p') {
                string.deleteCharAt(i + 1);
                i ++;
            } else {
                i++;
            }
        }
        return string.toString();
    }

}
