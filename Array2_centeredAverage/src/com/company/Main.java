//Return the "centered" average of an array of ints, which we'll say is the mean average of the values,
// except ignoring the largest and smallest values in the array.
// If there are multiple copies of the smallest value, ignore just one copy, and likewise for the largest value.
// Use int division to produce the final average. You may assume that the array is length 3 or more.
//
//
//        centeredAverage([1, 2, 3, 4, 100]) → 3
//        centeredAverage([1, 1, 5, 5, 10, 8, 7]) → 5
//        centeredAverage([-10, -4, -2, -4, -2, 0]) → -3

package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int [] array = {-10, -4, -2, -4, -2, 0};
        System.out.println(centeredAverage(array));
    }

    public static int centeredAverage(int[] nums) {
        ArrayList<Integer> arr = new ArrayList<>();
        int sumArr = 0;
        for (int i : nums){
            arr.add(i);
        }
        Collections.sort(arr);
        for (int i = 0; i < arr.size(); i++){
            if (i != 0 && i != arr.size()-1){
                sumArr += arr.get(i);
            }
        }
        return (int) sumArr/(arr.size()-2);

    }
}
