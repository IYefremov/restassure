
//Given an array of ints, return true if the sum of all the 2's in the array is exactly 8.
//
//
//        sum28([2, 3, 2, 2, 4, 2]) → true
//        sum28([2, 3, 2, 2, 4, 2, 2]) → false
//        sum28([1, 2, 3, 4]) → false
//
package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int [] array = {1, 2, 3, 4};
        System.out.println(sum28(array));
    }
    public static boolean sum28(int[] nums) {
        int sum = 0;
        for(int i : nums){
            if (i == 2) sum += i;
        }
        return (sum == 8);

    }
}
