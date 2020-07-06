//Given a string, return true if the string starts with "hi" and false otherwise.
//
//
//        startHi("hi there") → true
//        startHi("hi") → true
//        startHi("hello hi") → false

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(startHi("h"));
    }
    public static boolean startHi(String str) {
        if (str.length() <= 1) return false;
        String str1 = str.substring(0,2);
        if (str1.equals("hi")) return true;
        return false;

    }

}
