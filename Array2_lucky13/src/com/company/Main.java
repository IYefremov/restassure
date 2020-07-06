//Given an array of ints, return true if the array contains no 1's and no 3's.
//
//
//        lucky13([0, 2, 4]) → true
//        lucky13([1, 2, 3]) → false
//        lucky13([1, 2, 4]) → false

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int [] array = {1, 2, 4};
        System.out.println(lucky13(array));
    }

    public static boolean lucky13(int[] nums) {
        int flag = 0;
        for (int i : nums) {
            if (i == 1 || i == 3) return false;
//            if (flag == 0 && i == 1) flag = 1;
//            if (flag == 0 && i == 3) flag = 3;
//            if (flag == 1 && i == 3) return false;
//            if (flag == 3 && i == 1) return false;
        }
        return true;
    }

}
