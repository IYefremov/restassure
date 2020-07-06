//We'll say a number is special if it is a multiple of 11 or if it is one more than a multiple of 11.
// Return true if the given non-negative number is special. Use the % "mod" operator -- see Introduction to Mod
//
//
//        specialEleven(22) → true
//        specialEleven(23) → true
//        specialEleven(24) → false

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(specialEleven(23));
    }
    public static boolean specialEleven(int n) {
        int res;
        res = n%11;
        if (res == 0 || res == 1) return true;

        return false;

    }
}
