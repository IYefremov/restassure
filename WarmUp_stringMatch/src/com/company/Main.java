//Given 2 strings, a and b, return the number of the positions where they contain the same length 2 substring.
//        So "xxcaazz" and "xxbaaz" yields 3, since the "xx", "aa", and "az" substrings appear in the same place in both strings.
//
//
//        stringMatch("xxczaaz", "xxbaaz") → 3
//        stringMatch("abc", "abc") → 2
//        stringMatch("abc", "axc") → 0

package com.company;

import sun.security.util.Length;

public class Main {

    public static void main(String[] args) {
	// write your code here

        System.out.println(stringMatch("abc", "abc"));
    }

    public static int stringMatch(String a, String b) {

        int minLenght = (a.length()< b.length()) ? a.length() : b.length();
        if (minLenght == 0) return 0;

        int count = 0;

        for (int i = 0; i < minLenght-1; i++){
            if ((a.charAt(i) == b.charAt(i)) && (a.charAt(i+1) == b.charAt(i+1) )) {
             count++;
            }
        }
        return count;
    }

}
