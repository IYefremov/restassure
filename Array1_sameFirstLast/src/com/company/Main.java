//Given an array of ints, return true if the array is length 1 or more, and the first element and the last element are equal.
//
//
//        sameFirstLast([1, 2, 3]) → false
//        sameFirstLast([1, 2, 3, 1]) → true
//        sameFirstLast([1, 2, 1]) → true

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int [] array = {};
        System.out.println(sameFirstLast(array));
    }
    public static boolean sameFirstLast(int[] nums) {
        if (nums.length == 0) return false;
        return (nums.length >= 1 && nums[0] == nums[nums.length-1]);

    }

}
