//
//Given an array of ints, return true if every element is a 1 or a 4.
//
//
//        only14([1, 4, 1, 4]) → true
//        only14([1, 4, 2, 4]) → false
//        only14([1, 1]) → true

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int [] array = {1, 1};
        System.out.println(only14(array));
    }
    public static  boolean only14(int[] nums) {

        for (int i : nums){
            if (i != 1) {
                if ( i != 4){
                    return false;
                }
            }
        }
        return true;


    }

}
