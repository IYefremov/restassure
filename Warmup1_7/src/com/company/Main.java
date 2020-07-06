//Given an int n, return true if it is within 10 of 100 or 200. Note: Math.abs(num)
// computes the absolute value of a number.
//
//
//        nearHundred(93) → true
//        nearHundred(90) → true
//        nearHundred(89) → false

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(nearHundred(211));

    }

    public static boolean nearHundred(int n) {
        if (n >=(100-10) && (n <= (100+10))) return true;
        if (n >=(200-10) && (n <= (200+10))) return true;
        return false;
    }

}
