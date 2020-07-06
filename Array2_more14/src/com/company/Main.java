//Given an array of ints, return true if the number of 1's is greater than the number of 4's
//
//
//        more14([1, 4, 1]) → true
//        more14([1, 4, 1, 4]) → false
//        more14([1, 1]) → true

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int [] array = {1, 1};
        System.out.println(more14(array));
    }

    public static boolean more14(int[] nums) {
        int sum4 = 0, sum1 = 0;
        for (int i : nums) {
            if (i == 1) sum1++;
            if (i == 4) sum4++;
        }
        return (sum1 > sum4);

    }

}
