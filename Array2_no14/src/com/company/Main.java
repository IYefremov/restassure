//Given an array of ints, return true if it contains no 1's or it contains no 4's.
//
//
//        no14([1, 2, 3]) → true
//        no14([1, 2, 3, 4]) → false
//        no14([2, 3, 4]) → true

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int [] array = {};
        System.out.println(no14(array));
    }
    public static boolean no14(int[] nums) {
        int flag1 = 0;
        int flag4 = 0;
        for(int i : nums){
            if (flag1 == 0 && i == 1) flag1 = 1;
            if (flag4 == 0 && i == 4) flag4 = 1;
            if (flag1 == 1 && i == 4) return false;
            if (flag4 == 1 && i == 1) return false;
        }
        if (flag1 == 0 && flag4 == 0) return true;
        if (flag1 == 1 || flag4 == 1) return true;
        return false;

    }

}
