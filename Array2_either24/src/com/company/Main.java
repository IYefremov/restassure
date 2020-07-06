//Given an array of ints, return true if the array contains a 2 next to a 2 or a 4 next to a 4, but not both.
//
//
//        either24([1, 2, 2]) → true
//        either24([4, 4, 1]) → true
//        either24([4, 4, 1, 2, 2]) → false
//
package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int [] array = {4, 4, 1, 2, 2};
        System.out.println(either24(array));
    }
    public static boolean either24(int[] nums) {
    int flag2 = 0;
    int flag4 = 0;
    for (int i = 0; i < nums.length - 1; i++){
        if (nums[i] == 2 && nums[i+1] ==2 ) flag2 = 1;
        if (nums[i] == 4 && nums[i+1] ==4 ) flag4 = 1;
    }

    if (flag2 == flag4) return false;
    if (flag2 == 1) return true;
    return (flag4 == 1);
    }

}
