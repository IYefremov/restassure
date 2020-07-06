//Given a string of even length, return the first half. So the string "WooHoo" yields "Woo".
//
//
//        firstHalf("WooHoo") → "Woo"
//        firstHalf("HelloThere") → "Hello"
//        firstHalf("abcdef") → "abc"

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(firstHalf("WooHoo"));
    }

    public static String firstHalf(String str) {

        return str.substring(0, str.length() / 2);
    }
}
