//Given 2 strings, a and b, return a new string made of the first char of a and the last char of b,
// so "yo" and "java" yields "ya". If either string is length 0, use '@' for its missing char.
//
//
//        lastChars("last", "chars") → "ls"
//        lastChars("yo", "java") → "ya"
//        lastChars("hi", "") → "h@"

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(lastChars("", ""));
    }

    public static String lastChars(String a, String b) {
        String aFist, bFirst;

        if (a.length() == 0){
            aFist = "@";
        } else {
            aFist = a.substring(0,1);
        }
        if (b.length() == 0) {
            bFirst = "@";
        } else {
            bFirst = b.substring(b.length()-1);
        }

        return aFist + bFirst;
    }
}
