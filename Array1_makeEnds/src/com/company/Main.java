//Given an array of ints, return a new array length 2 containing the first and last elements from the original array.
// The original array will be length 1 or more.
//
//
//        makeEnds([1, 2, 3]) → [1, 3]
//        makeEnds([1, 2, 3, 4]) → [1, 4]
//        makeEnds([7, 4, 6, 2]) → [7, 2]

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int[] array1 = { 3};

        for (int i : (makeEnds(array1))) {
            System.out.println(i);
        }
    }

    public static int[] makeEnds(int[] nums) {
        int[] tmpArr = new int[2];

        if (nums.length == 1) {
            tmpArr[0] = nums[0];
            tmpArr[1] = nums[0];
            return tmpArr;
        }
        tmpArr[0] = nums[0];
        tmpArr[1] = nums[nums.length-1];
        return tmpArr;

    }
}
