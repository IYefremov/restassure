//Given a string and an int n, return a string made of n repetitions of the last n characters of the string.
// You may assume that n is between 0 and the length of the string, inclusive.
//
//
//        repeatEnd("Hello", 3) → "llollollo"
//        repeatEnd("Hello", 2) → "lolo"
//        repeatEnd("Hello", 1) → "o"

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(repeatEnd("Hello", 30));
    }

    public static String repeatEnd(String str, int n) {

        if (str.length() < n) return "GGGGGGGGGGGGGGGGGGGG";
        String lastN = str.substring(str.length()-n);
        String strRes = "";

        for (int i = 0; i < n; i++){
            strRes += lastN;
        }
        return strRes;
    }

}
