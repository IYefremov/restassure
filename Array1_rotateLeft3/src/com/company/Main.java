//Given an array of ints length 3, return an array with the elements "rotated left" so {1, 2, 3} yields {2, 3, 1}.
//
//
//        rotateLeft3([1, 2, 3]) → [2, 3, 1]
//        rotateLeft3([5, 11, 9]) → [11, 9, 5]
//        rotateLeft3([7, 0, 0]) → [0, 0, 7]

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int [] array = {7, 0, 0};
        for (int i : rotateLeft3(array)) {
            System.out.println(i);
        }

    }

    public static int[] rotateLeft3(int[] nums) {
        int tmp, tmp1, tmp2;
        tmp = nums[0];
        tmp1 = nums[1];
        tmp2 = nums[2];
        nums[0] = tmp1;
        nums[1] = tmp2;
        nums[2] = tmp;
       return nums;
    }

}
