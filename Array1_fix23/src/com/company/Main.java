//Given an int array length 3, if there is a 2 in the array immediately followed by a 3,
// set the 3 element to 0. Return the changed array.
//
//
//        fix23([1, 2, 3]) → [1, 2, 0]
//        fix23([2, 3, 5]) → [2, 0, 5]
//        fix23([1, 2, 1]) → [1, 2, 1]

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int[] array1 = {1,2,1};

        for (int i : (fix23(array1))) {
            System.out.print(i + " ");
        }
    }
    public static int[] fix23(int[] nums) {
        for (int i = 0; i < nums.length-1; i++){
            if (nums[i] == 2 && nums[i+1] == 3) {
                nums[i+1] = 0;
            }
        }
        return nums;

    }

}
