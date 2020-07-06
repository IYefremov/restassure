//Given an int array, return a new array with double the length where its last element is the same as the original array,
// and all the other elements are 0. The original array will be length 1 or more.
// Note: by default, a new int array contains all 0's.
//
//
//        makeLast([4, 5, 6]) → [0, 0, 0, 0, 0, 6]
//        makeLast([1, 2]) → [0, 0, 0, 2]
//        makeLast([3]) → [0, 3]

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int[] array1 = {};

        for (int i : (makeLast(array1))) {
            System.out.print(i + " ");
        }
    }

    public static int[] makeLast(int[] nums) {
        int[] tmpArr = new int[nums.length * 2];
        for (int i = 0; i < tmpArr.length; i++) {
            if (i == tmpArr.length - 1) {
                tmpArr[i] = nums[nums.length - 1];
            } else {
                tmpArr[i] = 0;
            }
        }
        return tmpArr;

    }

}
