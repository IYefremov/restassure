//Given a string, return a version without the first and last char, so "Hello" yields "ell".
// The string length will be at least 2.
//
//
//        withoutEnd("Hello") → "ell"
//        withoutEnd("java") → "av"
//        withoutEnd("coding") → "odin"

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println( withoutEnd("He"));
    }
    public static String withoutEnd(String str) {
        if (str.length() <= 2) return "";
        return str.substring(1, str.length()-1);

    }

}
