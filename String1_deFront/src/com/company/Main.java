//Given a string, return a version without the first 2 chars.
// Except keep the first char if it is 'a' and keep the second char if it is 'b'.
// The string may be any length. Harder than it looks.
//
//
//        deFront("Hello") → "llo"
//        deFront("java") → "va"
//        deFront("away") → "aay"

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(deFront("abay"));
    }

    public static String deFront(String str) {
        if (str.length() < 2) return "";
        if (str.charAt(0) == 'a' && str.charAt(1) == 'b') return str;
        if (str.charAt(0) == 'a') return String.valueOf('a') + str.substring(2);
        if (str.charAt(1) == 'b') return String.valueOf('b') + str.substring(2);

        return str.substring(2);
    }
}
