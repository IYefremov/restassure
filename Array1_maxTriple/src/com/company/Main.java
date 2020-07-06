//Given an array of ints of odd length, look at the first, last, and middle values in the array and return the largest.
// The array length will be a least 1.
//
//
//        maxTriple([1, 2, 3]) → 3
//        maxTriple([1, 5, 3]) → 5
//        maxTriple([5, 2, 3]) → 5

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int[] array1 = {5, 1, 7, 3, 7, 8, 1};

        System.out.print(maxTriple(array1) + " ");

    }

    public static int maxTriple(int[] nums) {
        int max = nums[0];
        if (max < nums[(nums.length-1)/2]) max = nums[(nums.length-1)/2];
        if (max < nums[nums.length-1]) max = nums[nums.length-1];
        return max;
    }

}
