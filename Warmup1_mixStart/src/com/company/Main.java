//Return true if the given string begins with "mix", except the 'm' can be anything, so "pix", "9ix" .. all count.
//
//
//        mixStart("mix snacks") → true
//        mixStart("pix snacks") → true
//        mixStart("piz snacks") → false

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(mixStart("piz snacks"));
    }

    public static boolean mixStart(String str) {

        if (str.length() < 3) return false;
        if (str.substring(1, 3).equals("ix")) {
            return true;
        }
        return false;
    }

}
