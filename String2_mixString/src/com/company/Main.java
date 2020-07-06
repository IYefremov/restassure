//Given two strings, a and b, create a bigger string made of the first char of a, the first char of b,
// the second char of a, the second char of b, and so on. Any leftover chars go at the end of the result.
//
//
//        mixString("abc", "xyz") → "axbycz"
//        mixString("Hi", "There") → "HTihere"
//        mixString("xxxx", "There") → "xTxhxexre"

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(mixString("xxxx", "There"));
    }

    public static String mixString(String a, String b) {

        String resString = "";

        for (int i = 0; i < Math.max(a.length(), b.length()); i++) {
            if (i <= a.length() - 1) {
                resString += String.valueOf(a.charAt(i));
            }
            if (i <= b.length() - 1) {
                resString += String.valueOf(b.charAt(i));
            }
        }
        return resString;
    }

}
