
//Return the sum of the numbers in the array, returning 0 for an empty array.
//Except the number 13 is very unlucky, so it does not count and numbers that come immediately after a 13 also do not count.
//
//
//        sum13([1, 2, 2, 1]) → 6
//        sum13([1, 1]) → 2
//        sum13([1, 2, 2, 1, 13]) → 6

package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int [] array = {1, 2, 13, 2, 1, 13};
        System.out.println(sum13(array));
    }
    public static int sum13(int[] nums) {
        ArrayList<Integer> arr = new ArrayList<>();
        int sum = 0;
        if (nums.length == 0) return 0;
        for (int i : nums){
            arr.add(i);
        }

        for (int i = 0; i < arr.size(); i++ ) {
            if ( arr.get(i) != 13){
                sum += arr.get(i);
            } else {
                i++;
            };
        }
        return sum;
    }

}
