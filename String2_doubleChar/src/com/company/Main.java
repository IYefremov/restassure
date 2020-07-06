//Given a string, return a string where for every char in the original, there are two chars.
//
//
//        doubleChar("The") → "TThhee"
//        doubleChar("AAbb") → "AAAAbbbb"
//        doubleChar("Hi-There") → "HHii--TThheerree"

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(doubleChar("Hi-There"));
    }

    public static String doubleChar(String str) {
        String strRes = "";
        for (int i = 0; i < str.length(); i++) {
            strRes += String.valueOf(str.charAt(i)) + String.valueOf(str.charAt(i));
        }
        return strRes;
    }
}
