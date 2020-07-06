//Given two strings, append them together (known as "concatenation") and return the result.
// However, if the strings are different lengths, omit chars from the longer string
// so it is the same length as the shorter string. So "Hello" and "Hi" yield "loHi".
// The strings may be any length.
//
//
//        minCat("Hello", "Hi") → "loHi"
//        minCat("Hello", "java") → "ellojava"
//        minCat("java", "Hello") → "javaello"

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(minCat("c", "cd"));
    }

    public static String minCat(String a, String b) {
        if (a.length() == b.length()) return a + b;
        if (a.length() < b.length()) {
            return a + b.substring(b.length() - a.length());
        } else {
            return a.substring(a.length() - b.length()) + b;
        }

    }

}
