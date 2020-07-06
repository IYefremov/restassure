//Given a string, return true if the first 2 chars in the string also appear at the end of the string, such as with "edited".
//
//
//        frontAgain("edited") → true
//        frontAgain("edit") → false
//        frontAgain("ed") → true

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println( frontAgain("fasfsdfsdfsdfdsfa"));
    }

    public static boolean frontAgain(String str) {
        if (str.length() == 2) return true;
        if (str.length() < 2)  return false;
        return str.substring(0,2).equals(str.substring(str.length()-2));
    }
}
