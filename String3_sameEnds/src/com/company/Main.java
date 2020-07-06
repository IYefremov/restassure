//Given a string, return the longest substring that appears at both the beginning and end of the string without overlapping.
//        For example, sameEnds("abXab") is "ab".
//
//
//        sameEnds("abXYab") → "ab"
//        sameEnds("xx") → "x"
//        sameEnds("xxx") → "x"

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(sameEnds("mymmy"));
    }

    public static String sameEnds(String string) {
        if (string.length() <= 1) return "";

        double halfStr = Math.ceil(string.length() / 2);
        String strRes = "";
        String first = "";
        String second = "";
        first = string.substring(0, (int) halfStr);
        second = string.substring((int) halfStr);

        char firstChar = string.charAt(0); // находим первый символ строки
        int indCharInSecond = second.indexOf(firstChar); // находим первое вхождение первого символа строки во второй половине

        if (indCharInSecond != -1) {
            int i = 0;

            while ( i < (int) halfStr) {
                if (first.charAt(i) == second.charAt(indCharInSecond)) {
                    strRes += first.charAt(i);
                    indCharInSecond++;
                    i++;
                    if (first.length() + indCharInSecond == string.length()) return strRes;
                } else {
                    strRes = "";
                    indCharInSecond = second.indexOf(firstChar, i);
                    i = 0;
                }
            }
        }
        return strRes;


//        String s = "www.mysite.com";
//
////проверяем заканчивается ли строка суффиксом "com"
//        boolean isComEnding = s.endsWith("com");
//        System.out.println(isComEnding);//выведет true
//
////проверяем заканчивается ли строка суффиксом "ru"
//        boolean isRuEnding = s.contains("ru");
//        System.out.println(isRuEnding);//выведет false
//

        //  string.lastIndexOf(char, fromIndex)
//        if (string.length() == 0) return "";
//        if (string.length() == 1) return string;
//        String resSTR = "";
//        char ch = string.charAt(0);
//        int ind = string.lastIndexOf(ch);
//        for (int i = 0; i < string.length(); i++){
//            if (ind != 0 && ind < string.length()){
//                if (string.charAt(i) == string.charAt(ind)){
//                    resSTR += string.charAt(i);
//                    ind++;
//                }
//            }
//        }
        //       return resSTR;
    }
}
