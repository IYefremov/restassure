
//We'll say that a value is "everywhere" in an array if for every pair of adjacent elements in the array,
//        at least one of the pair is that value. Return true if the given value is everywhere in the array.
//
//
//        isEverywhere([1, 2, 1, 3], 1) → true
//        isEverywhere([1, 2, 1, 3], 2) → false
//        isEverywhere([1, 2, 1, 3, 1], 1) → false

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int [] array = {1, 2, 1, 2, 1};
        System.out.println(isEverywhere(array, 2));
    }
    public static boolean isEverywhere(int[] nums, int val) {
        int flag = 0;
        for (int i = 0;  i < nums.length-2; i++){
            if (nums[i] == val && nums[i+2] == val){
                flag = 1;
                i +=1;
            } else flag = 0;
        }
         return (flag == 1);
    }

}
