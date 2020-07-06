//Given an int array of any length, return a new array of its first 2 elements.
// If the array is smaller than length 2, use whatever elements are present.
//
//
//        frontPiece([1, 2, 3]) → [1, 2]
//        frontPiece([1, 2]) → [1, 2]
//        frontPiece([1]) → [1]

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int[] array1 = {1, 2, 3 , 4,};
        for (int i : frontPiece(array1)) {
            System.out.print(i + " ");
        }
    }

    public static int[] frontPiece(int[] nums) {
        int [] arrRes = new int [2];
        if (nums.length < 2 ) return nums;
        arrRes[0] = nums[0];
        arrRes[1] = nums[1];
        return arrRes;

    }
}
