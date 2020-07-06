
//Return the sum of the numbers in the array, except ignore sections of numbers starting with a 6
// and extending to the next 7 (every 6 will be followed by at least one 7). Return 0 for no numbers.
//
//
//        sum67([1, 2, 2]) → 5
//        sum67([1, 2, 2, 6, 99, 99, 7]) → 5
//        sum67([1, 1, 6, 7, 2]) → 4

package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int[] array = {1, 1, 6, 7, 2};
        System.out.println(sum67(array));
    }

    public static int sum67(int[] nums) {
        ArrayList<Integer> arr = new ArrayList<>();
        int sum = 0;
        int trg = 0;
        for (int i : nums) {
            arr.add(i);
        }
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) == 6 && trg == 0) {
                trg = 1;
            }
            if (trg == 0) {
                sum += arr.get(i);
            }
            if (arr.get(i) == 7) {
                trg = 0;
            }
        }
        return sum;
    }

}
