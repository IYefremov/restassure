//Return true if the given non-negative number is 1 or 2 less than a multiple of 20.
// So for example 38 and 39 return true, but 40 returns false. See also: Introduction to Mod
//
//
//        less20(18) → true
//        less20(19) → true
//        less20(20) → false

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(less20(39));
    }

    public static boolean less20(int n) {
        int res = n % 20;
        System.out.println(n%20);
        if ((20-res) == 1 || (20-res) == 2) return true;
        return false;

    }

}
