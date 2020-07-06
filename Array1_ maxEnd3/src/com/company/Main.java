//Given an array of ints length 3, figure out which is larger, the first or last element in the array,
// and set all the other elements to be that value. Return the changed array.
//
//
//        maxEnd3([1, 2, 3]) → [3, 3, 3]
//        maxEnd3([11, 5, 9]) → [11, 11, 11]
//        maxEnd3([2, 11, 3]) → [3, 3, 3]

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int[] array = {2, 11, 3};
        for (int i : (maxEnd3(array))) {
            System.out.println(i);
        }

    }

    public static int[] maxEnd3(int[] nums) {
        int max = nums[0];
        if (nums[0] <= nums[nums.length-1]){
            max = nums[nums.length-1];
        }

        for (int i = 0; i < nums.length; i++) {
            nums[i] = max;
        }
        return nums;

    }
}
