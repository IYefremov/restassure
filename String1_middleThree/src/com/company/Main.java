//Given a string of odd length, return the string length 3 from its middle, so "Candy" yields "and".
// The string length will be at least 3.
//
//
//        middleThree("Candy") → "and"
//        middleThree("and") → "and"
//        middleThree("solving") → "lvi"

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(middleThree("solv"));
    }

    public static String middleThree(String str) {

    return str.substring((str.length()-3)/2, str.length()-(str.length()-3)/2);
    }
}
