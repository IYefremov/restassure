//Given an array of ints of odd length, return a new array length 3 containing the elements
// from the middle of the array. The array length will be at least 3.
//
//
//        midThree([1, 2, 3, 4, 5]) → [2, 3, 4]
//        midThree([8, 6, 7, 5, 3, 0, 9]) → [7, 5, 3]
//        midThree([1, 2, 3]) → [1, 2, 3]

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int[] array1 = {8, 6, 7, 5, 3, 0, 9};


        for (int i : midThree(array1)) {
            System.out.print(i + " ");
        }
    }

    public static int[] midThree(int[] nums) {
    int [] arrTmp = new int [3];
    arrTmp[0] = nums[(nums.length-3)/2];
    arrTmp[1] = nums[(nums.length-3)/2+1];
    arrTmp[2] = nums[(nums.length-3)/2+2];

    return arrTmp;
    }
}
