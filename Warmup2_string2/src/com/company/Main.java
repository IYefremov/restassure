//Given a string, return a version where all the "x" have been removed.
// Except an "x" at the very start or end should not be removed.
//
//
//        stringX("xxHxix") → "xHix"
//        stringX("abxxxcd") → "abcd"
//        stringX("xabxxxcdx") → "xabcdx"


package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(stringX("xabxxxcdx"));
    }

    public static String stringX(String str) {

        if (str.length() < 2) return str;

        String strRes = String.valueOf(str.charAt(0));

        for (int i = 1; i < str.length()-1; i++){
            if (str.charAt(i) != 'x') {
                strRes += String.valueOf(str.charAt(i));
            }
        }
        return strRes += String.valueOf(str.charAt(str.length()-1));
    }
}
