//Given a string, return a version without both the first and last char of the string.
// The string may be any length, including 0.
//
//
//        withouEnd2("Hello") → "ell"
//        withouEnd2("abc") → "b"
//        withouEnd2("ab") → ""

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(withouEnd2(""));

    }

    public static String withouEnd2(String str) {
        return (str.length() <= 2) ? "" : str.substring(1,str.length()-1);

    }
}
