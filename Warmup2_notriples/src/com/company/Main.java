//Given an array of ints, we'll say that a triple is a value appearing 3 times in a row in the array.
// Return true if the array does not contain any triples.
//
//
//        noTriples([1, 1, 2, 2, 1]) → true
//        noTriples([1, 1, 2, 2, 2, 1]) → false
//        noTriples([1, 1, 1, 2, 2, 2, 1]) → false

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int [] array = {1, 1};
        System.out.println(noTriples(array));
    }

    public static boolean noTriples(int[] nums) {
        if (nums.length < 3) return true;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1] && nums[i + 1] == nums[i + 2]) {
                return false;
            }
        }
        return true;
    }

}
