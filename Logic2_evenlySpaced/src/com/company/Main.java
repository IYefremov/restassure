//Given three ints, a b c, one of them is small, one is medium and one is large.
//        Return true if the three values are evenly spaced, so the difference between small
//        and medium is the same as the difference between medium and large.
//
//
//        evenlySpaced(2, 4, 6) → true
//        evenlySpaced(4, 6, 2) → true
//        evenlySpaced(4, 6, 3) → false

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(evenlySpaced(9, 9, 9));
    }

    public static boolean evenlySpaced(int a, int b, int c) {
        int small = a, medium = a, large = a, smallIndex = 0, largelIndex = 0;
        int[] array = {a, b, c};
        for (int i = 0; i < array.length; i++) {
            if (small > array[i]) {
                small = array[i];
                smallIndex = i;
            }
            if (large < array[i]) {
                large = array[i];
                largelIndex = i;
            }
        }
        for (int i = 0; i < array.length; i++) {
            if (i != smallIndex && i != largelIndex) medium = array[i];
        }

        return (Math.abs(large-medium) == Math.abs(medium-small));
}

}
