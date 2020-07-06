//Given a string, return a new string made of 3 copies of the last 2 chars of the original string.
// The string length will be at least 2.
//
//
//        extraEnd("Hello") → "lololo"
//        extraEnd("ab") → "ababab"
//        extraEnd("Hi") → "HiHiHi"

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(extraEnd("Hello"));
    }

    public static String extraEnd(String str) {

        String strRes =  str.substring(str.length()-2, str.length());
        return strRes + strRes + strRes;
    }
}
