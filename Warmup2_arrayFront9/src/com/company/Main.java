//Given an array of ints, return true if one of the first 4 elements in the array is a 9.
// The array length may be less than 4.
//
//
//        arrayFront9([1, 2, 9, 3, 4]) → true
//        arrayFront9([1, 2, 3, 4, 9]) → false
//        arrayFront9([1, 2, 3, 4, 5]) → false


package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int [] array = {9, 2, 3};
        System.out.println(arrayFront9(array));
    }
    public static boolean arrayFront9(int[] nums) {
    if (nums.length ==0 ) return false;
    int maxCount = nums.length;
    if (maxCount > 4) maxCount = 4;

    for (int i = 0; i < maxCount; i++ ){
        if (nums[i] == 9) return true;
    }
    return false;
    }

}
