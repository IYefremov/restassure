//Given a string, return true if it ends in "ly".
//
//
//        endsLy("oddly") → true
//        endsLy("y") → false
//        endsLy("oddy") → false

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(endsLy("ly"));
    }

    public static boolean endsLy(String str) {
       // String strRes = str.substring(str.length()-2, str.length());
        //System.out.println(strRes);
        if (str.length() < 2) return false;
        return ((str.substring(str.length()-2).equals("ly")));

    }
}
