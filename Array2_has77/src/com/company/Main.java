
//Given an array of ints, return true if the array contains two 7's next to each other, or there are two 7's
//        separated by one element, such as with {7, 1, 7}.
//
//
//        has77([1, 7, 7]) → true
//        has77([1, 7, 1, 7]) → true
//        has77([1, 7, 1, 1, 7]) → false
//

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int [] array = {1, 7, 1};
        System.out.println(has77(array));
    }
    public static boolean has77(int[] nums) {
        int prev = 0;
        if (nums.length < 2) return false;
        for (int i = 0; i < nums.length-1; i++){
            if (nums[i] == nums[i+1] ) return true;
            prev = nums[i];
            if (nums[i+1] == prev) return true;
        }
        return false;

    }

}
