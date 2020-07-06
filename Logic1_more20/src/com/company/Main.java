//Return true if the given non-negative number is 1 or 2 more than a multiple of 20. See also: Introduction to Mod
//
//
//        more20(20) → false
//        more20(21) → true
//        more20(22) → true


package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(more20(23));
    }

    public static boolean more20(int n) {
    int res;
    res = n % 20;
        System.out.println(res);
    if (res == 1 || res == 2) return true;
    return false;
    }
}
