//Given a string, return a "rotated right 2" version where the last 2 chars are moved to the start.
// The string length will be at least 2.
//
//
//        right2("Hello") → "loHel"
//        right2("java") → "vaja"
//        right2("Hi") → "Hi"
//
package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(right2("Hi"));
    }

    public static String right2(String str) {
        return str.substring(str.length()-2) + str.substring(0, str.length()-2);

    }
}
