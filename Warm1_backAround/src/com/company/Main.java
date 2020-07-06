//Given a string, take the last char and return a new string with the last char added at the front and back,
// so "cat" yields "tcatt". The original string will be length 1 or more.
//
//
//        backAround("cat") → "tcatt"
//        backAround("Hello") → "oHelloo"
//        backAround("a") → "aaa"

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(backAround(""));
    }
    public static String backAround(String str) {
        if (str.length() == 0) return str;
        char lastChar = str.charAt(str.length()-1);
        return lastChar + str+ lastChar;

    }

}
