//Given an int array length 2, return true if it contains a 2 or a 3.
//
//
//        has23([2, 5]) → true
//        has23([4, 3]) → true
//        has23([4, 5]) → false

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int[] array = {5, 4};
        System.out.println(has23(array));
    }

    public static boolean has23(int[] nums) {
        for (int i : nums) {
            if (i == 2 || i == 3) return true;
        }
        return false;
    }
}
