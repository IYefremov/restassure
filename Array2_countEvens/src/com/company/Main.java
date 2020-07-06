//
//Return the number of even ints in the given array. Note: the % "mod" operator computes the remainder, e.g. 5 % 2 is 1.
//
//
//        countEvens([2, 1, 2, 3, 4]) → 3
//        countEvens([2, 2, 0]) → 3
//        countEvens([1, 3, 5]) → 0
//

package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int [] array = {1, 3, 5};
        System.out.println(countEvens(array));
    }
    public static int countEvens(int[] nums) {
        ArrayList<Integer> arr = new ArrayList();
        int count = 0;
       for(int i : nums){
           arr.add(i);
       }
        for (int i : arr){
            if (i%2 == 0) count++;
        }
        return count;

    }

}
