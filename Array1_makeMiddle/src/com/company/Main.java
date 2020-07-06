//Given an array of ints of even length, return a new array length 2 containing the middle two elements
// from the original array. The original array will be length 2 or more.
//
//
//        makeMiddle([1, 2, 3, 4]) → [2, 3]
//        makeMiddle([7, 1, 2, 3, 4, 9]) → [2, 3]
//        makeMiddle([1, 2]) → [1, 2]

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int[] array1 = {7, 1, 2, 3, 4, 9};


        for (int i : makeMiddle(array1)) {
            System.out.print(i + " ");
        }
    }
    public static int[] makeMiddle(int[] nums) {
        int [] arrRes = new int [2];
        arrRes[0] = nums [nums.length/2 - 1];
        arrRes[1] = nums [nums.length/2 ];
        return arrRes;
    }

}
