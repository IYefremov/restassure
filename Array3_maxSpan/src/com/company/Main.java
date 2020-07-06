
//Consider the leftmost and righmost appearances of some value in an array.
//        We'll say that the "span" is the number of elements between the two inclusive.
//        A single value has a span of 1. Returns the largest span found in the given array. (Efficiency is not a priority.)
//
//
//        maxSpan([1, 2, 1, 1, 3]) → 4
//        maxSpan([1, 4, 2, 1, 4, 1, 4]) → 6
//        maxSpan([1, 4, 2, 1, 4, 4, 4]) → 6

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int [] arr = {1, 4, 2, 1, 4, 4, 4};
        System.out.println(maxSpan(arr));
    }

    public static int maxSpan(int[] nums) {
        int maxSpan = 0;
        int currSpan = 0;
        if (nums.length == 0) return 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    currSpan = j - i;
                }
            }
            if (currSpan > maxSpan){
                maxSpan = currSpan;
            }
        }

        return maxSpan+1;
    }

}
