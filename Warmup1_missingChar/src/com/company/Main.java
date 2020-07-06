//Given a non-empty string and an int n, return a new string where the char at index n has been removed.
// The value of n will be a valid index of a char in the original string
// (i.e. n will be in the range 0..str.length()-1 inclusive).
//
//
//        missingChar("kitten", 1) → "ktten"
//        missingChar("kitten", 0) → "itten"
//        missingChar("kitten", 4) → "kittn"


package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(missingChar("kitten", 4));
    }

    public static String missingChar(String str, int n) {

        if (str.length() <= n) return "n is too big";
        return str.substring(0, n) + str.substring(n + 1);
    }
}
