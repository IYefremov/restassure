//Given an int array length 2, return true if it does not contain a 2 or 3.
//
//
//        no23([4, 5]) → true
//        no23([4, 2]) → false
//        no23([3, 5]) → false

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int[] array = {3, 5};
        System.out.println(no23(array));
    }

    public static boolean no23(int[] nums) {
        for (int i : nums) {
            if (i == 2 || i == 3) return false;
        }
        return true;
    }
}
